// ----------------------------------------------------------- 
// NAME : Brett Kriz                        User ID: bakriz 
// DUE DATE : 10/14/2016                                       
// PROGRAM ASSIGNMENT #2                                        
// FILE NAME : merge.c           
// PROGRAM PURPOSE :                                           
//    To establish another shared memory segment
//    calculate the insertion positions for 2 arrays
//    and also zipper the 2 arrays into 1 array
// ----------------------------------------------------------- 

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <math.h>
#include <unistd.h> // EXEC
#include <inttypes.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>

// Structs
struct SHMDataSection;

typedef struct SHMDataSection 
{
    bool flag;
    int K;
    int M;
    int N;
    int A[500], X[500], Y[500], Z[100];
}DataSec;

typedef struct SHMTbl
{
    int Yp;
    int Xp;
    int Zp;
}InTbl;

// Variables
key_t thee_key  = NULL;
int thee_id     = 7;
int shm_id;

DataSec *data;

key_t in_key  = NULL;
int in_id     = 7;
int shm_id2;

InTbl *indata;
int f_in_s;
int M = -1;
int N = -1;

// Protos
int bs (int *tbl, int s, int target, int index);
void doBS();
void setupSHM(bool makeNew);
void setupSHMInner();

// ----------------------------------------------------------- 
// FUNCTION  main :                       
//     Get the ball rolling for this file                           
// PARAMETER USAGE :                                           
//      argc is the number of arguments in argv
//      argv holds the total size of the shm segment
// FUNCTION CALLED :                                           
//      setupSHM
//      setupSHMInner
//      doBS (do Binary Search)
//      shmdt
//      shmctl
// ----------------------------------------------------------- 
int main(int argc, char** argv) {
    printf("\nMERGE ACTIVE!\n");
    if (argc < 2){
        printf("\t[!] Not enough arguments for QSORT! Run Rejected!\n\n");
        return (EXIT_FAILURE);
    }
    // Fetch File size
    printf("Attempting to get size! \n");
    char *endp;
    intmax_t temp = strtoimax(argv[1], &endp, 10); // What a cool function
    f_in_s = (int)temp;
    printf("\tFile SIZE: %d\tRemaining: .%s.\n",f_in_s,endp);
  
    // obtain the SHM
    setupSHM(false);
    setupSHMInner(); // indata
    // Define pointers
    indata->Yp = 0;
    indata->Xp = 0;
    indata->Zp = 0;
    // Fork off M+N times
    // Run the sort
    doBS();
    
    // (Do not destroy the SHM)
    // Detach from the SHM
    if (shm_id != NULL){
        shmdt(data);
    }
    // Detach and kill our SHM
    if (shm_id2 != NULL){
        shmctl(shm_id2, IPC_RMID, NULL );
        
    }
    shmdt(indata);
    
    return (EXIT_SUCCESS);
}


//for (i = 0; i < n; i++)
        //printf("%d%s", a[i], i == n - 1 ? "\n" : " ");
// ----------------------------------------------------------- 
// FUNCTION  doBS : (do Binary Search)                         
//     Coordinate forking, binary searches, and insertion to Z                            
// PARAMETER USAGE :                                           
//    N/A
// FUNCTION CALLED :                                           
//      bs
//      wait
// ----------------------------------------------------------- 
void doBS(){
    // Fork off #X forks to binary search
    printf("Starting %d \n",getpid());
    
    // Create a new SHM seg for this
    for (int i = 0; i < data->M; i++){
        int curx = data->X[i];
        // Fork and search for index
        int id = fork();
        if (id == 0){
            // T = how many smaller elements in Y
            printf("      $$$ M-PROC(%d): handling x[%s] = %d\n",getpid(),i,curx);
            int t = bs( data->Y , (int)(data->N), curx, i );

            //indata->Y[i] = t;
            bool b1 = (int)(indata->Yp) < t;
            bool b2 = (int)(indata->Yp) < (int)(data->N);
            bool b3 = (int)(data->Y[i]) < curx;
   
            if (b1 && b2){
                // Bring YP up to speed
                for (; indata->Yp < t && (indata->Yp) < (int)(data->N);){
                    // Make Yp equal to t.
                    printf("Adding Y[%d]: %d \n",(int)indata->Yp,(int)data->Y[indata->Yp]);
                    data->Z[indata->Zp] = data->Y[indata->Yp];
                    indata->Yp++;
                    indata->Zp++;
                }
            }
            // Add what we need
            printf("      $$$ M-PROC(%d): About to write X[%d] = %d to position %d of the output array\n",getpid(),(int)i,curx,indata->Zp);
            data->Z[indata->Zp++] = data->X[i];
            indata->Xp++;
            
  
            exit(EXIT_SUCCESS);
        }else{
            wait();
        }
    }
    
    for (int i = (indata->Yp); i < data->N; i++){
        int cury = data->Y[i];
        // Fork and search for index
        int id = fork();
        if (id == 0){
            printf("      $$$ M-PROC(%d): handling y[%s] = %d\n",getpid(),i,cury);
            // T = how many smaller elements in Y
            int t = bs( data->X , (int)(data->M), cury, i );
            
            //indata->Y[i] = t;
            bool b1 = (int)(indata->Xp) < t;
            bool b2 = (int)(indata->Xp) < (int)(data->M);
            bool b3 = (int)(data->X[i]) < cury;

            if (b1 && b2){
                // Bring YP up to speed
                for (; indata->Xp < t && (indata->Xp) < (int)(data->M);){
                    // Make Yp equal to t.
                    printf("Adding X[%d]: %d \n",(int)indata->Xp,(int)data->X[indata->Xp]);
                    data->Z[indata->Zp] = data->X[indata->Xp];
                    indata->Xp++;
                    indata->Zp++;
                }
            }
            // Add what we need
            printf("      $$$ M-PROC(%d): About to write X[%d] = %d to position %d of the output array\n",getpid(),(int)i,cury,indata->Zp);
            data->Z[indata->Zp++] = data->Y[i];
            indata->Yp++;
            
  
            exit(EXIT_SUCCESS);
        }else{
            wait();
        }
    }

    printf("DONE! %d \n\n", getpid());
}

/*
 if (i == (int)(data->M)+1){
                // Fill remaining Y
                for (int r = indata->Yp; r < data->N; r++){
                    data->Z[indata->Zp] = data->Y[indata->Yp];
                    indata->Yp++;
                    indata->Zp++;
                }
            }
 
 */

// ----------------------------------------------------------- 
// FUNCTION  BS : (Binary Search, (bsearch reserved))                          
//      Preform a modified binary search to locate an
//      insertion index in Tbl
// PARAMETER USAGE :                                           
//      Tbl: Array to be searched             
//      S: tbl size
//      target: int to find index for
//      i: current index of target
// FUNCTION CALLED :                                           
//            
// ----------------------------------------------------------- 
int bs (int *tbl, int s, int target, int index) {
    int left = 0, right = s - 1;
    int center;
    
    while (left <= right) {
        center = (left + right) / 2;
        // Compare 
        if (tbl[center] == target) {
            perror("[!] NOTICE: Binary Search found equal index somehow...");
            return center+1; // Thats odd...
        } else if (tbl[center] < target) {
            left = center + 1;
        } else {
            right = center - 1;
        }
    }
    
    if (left == right && left == 0 && target < tbl[0]){
        return index;
    }else if(left == right && left == s-1 && target > tbl[s-1]){
        return index + s;
    }
    
    return (target > tbl[left]) ? (left + 1) : left;
}
// ----------------------------------------------------------- 
// FUNCTION  setupSHM : (function name)                          
//     Setup shared mem seg                   
// PARAMETER USAGE :                                           
//    Add create flag to shmget?            
// FUNCTION CALLED :                                           
//    ftok, shmget, shmat, (shmctl, shmdt)       
// ----------------------------------------------------------- 
void setupSHM(bool makeNew){
    // Setup the shared memory

    thee_key = ftok("main.c", thee_id);
    if (thee_key == -1){
        perror("[!] Fatal error! SHM Key NOT granted! Aborting...");
        exit(EXIT_FAILURE);
    }
    // @@@ Make modular!
    if (makeNew){
        shm_id = shmget(thee_key, f_in_s, IPC_CREAT | 0666 | IPC_PRIVATE );
    }else{
        shm_id = shmget(thee_key, f_in_s, 0666|IPC_PRIVATE);
    }

    if (shm_id == -1){
        perror("[!] Fatal error! SHM Get NOT granted! Aborting...");
        exit(EXIT_FAILURE);
    }
    //DataSec *data2;
    data = (DataSec*)shmat(shm_id, NULL, 0);
    if ( data == (DataSec*)(-1) ){
        perror("[!] Fatal error! SHM Attach NOT granted! Aborting...");
        // Detach and kill our SHM
        if (makeNew){
            shmctl(shm_id, IPC_RMID, NULL );
        }
        //shmdt(data);
        exit(EXIT_FAILURE);
    }
}

// ----------------------------------------------------------- 
// FUNCTION  setupSHMInner : (function name)                          
//     Setup shared mem seg for internal use                
// PARAMETER USAGE :                                           
//    N/A         
// FUNCTION CALLED :                                           
//    ftok, shmget, shmat, (shmctl, shmdt)       
// ----------------------------------------------------------- 
void setupSHMInner(){
    // Setup the shared memory
    in_key = ftok("merge.c", thee_id);
    if (in_key == -1){
        printf("[!] Fatal error! SHM Key NOT granted! Aborting...");
        exit(EXIT_FAILURE);
    }else{
        printf("      $$$ M-PROC: shared memory key = %d\n",thee_key);
    }
    int ids = 2*sizeof(int);
    // @@@ Make modular!
        shm_id2 = shmget(in_key, ids, IPC_CREAT | 0666 | IPC_PRIVATE );

    
    if (shm_id2 == -1){
        printf("[!] Fatal error! SHM Get NOT granted! Aborting...");
        exit(EXIT_FAILURE);
    }else{
        printf("      $$$ M-PROC: shared memory created\n");
    }
    //DataSec *data2;
    indata = (InTbl*)shmat(shm_id2, NULL, 0);
    if ( indata == (InTbl*)(-1) ){
        printf("[!] Fatal error! SHM Attach NOT granted! Aborting...");
        shmctl(shm_id2, IPC_RMID, NULL ); 
        //shmdt(indata);
        exit(EXIT_FAILURE);
    }else{
        printf("      $$$ M-PROC: shared memory attached and is ready to use\n");
    }
}