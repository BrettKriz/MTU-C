// ----------------------------------------------------------- 
// NAME : Brett Kriz                        User ID: bakriz 
// DUE DATE : 10/14/2016                                       
// PROGRAM ASSIGNMENT #2                                        
// FILE NAME : main.c           
// PROGRAM PURPOSE :                                           
//    To establish a shared memory segment
//      and run qsort.c and merge.c
// ----------------------------------------------------------- 

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <math.h>
#include <ctype.h>
#include <unistd.h> // EXEC

#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/stat.h>


#define fin_path "in.txt"
// Complex Vars

struct SHMDataSection;

typedef struct SHMDataSection 
{
    bool flag;
    int K;
    int M;
    int N;
    int A[500], X[500], Y[500], Z[100];
}DataSec;

enum _flags {
    _READ   = 004,
    _READ2  = 040,
    _WRITE  = 002,
    _WRITE2 = 020,
    _EOF    = 010
};
 // Simple Vars
FILE *f_in      = NULL;
int f_in_s    = -1;

key_t thee_key  = NULL;
int thee_id     = 7;
int shm_id;

DataSec *data;
//char *data;
int end_of_A;
int start_of_X;
int end_of_X;
int start_of_Y;
int end_of_Y;

// Protos
int nextInt();
long fileSize(char *name);
bool fileExists(char *name);
void setupSHM(bool makeNew, int size);

// ipcs
// ipcrm -m <ID>

// ----------------------------------------------------------- 
// FUNCTION  main :                       
//     Get the ball rolling for this file                           
// PARAMETER USAGE :                                           
//      argc is the number of arguments in argv
//      argv holds the total size of the shm segment
// FUNCTION CALLED :                                           
//      setupSHM
//      fileExists (DEBUGING)
//      stat
//      fscanf
//      calloc
//      execvp
//      fclose/fopen
//      shmdt
//      shmctl
//      snprintf
// ----------------------------------------------------------- 
int main(int argc, char** argv) {

    // Enter a scope
    {
        struct stat s;
        int res = fstat(STDIN_FILENO, &s);
        if (res == -1){
            perror("[!] Critical problem! Unable to fetch File size!\n");
            return (EXIT_FAILURE);
        }
        f_in_s = (int)s.st_size; // Record initial size
    }
        
    f_in = fdopen(STDIN_FILENO,"rb");
    if (f_in == NULL){
        perror("[!] FATAL ERROR!  Input file not found! Aborting...\n");
        exit(EXIT_FAILURE);
    }
    // accept inputs
    int As = 0, Xs = 0, Ys = 0; // Size counters
    
    int size = (int)f_in_s / sizeof(char);
    
    bool flag = true;
    
    int t = 0;

    
    // Start by filling A to K ints

    // Create shared memory space 
    setupSHM(true, sizeof(DataSec)); // @@@
    

    int tt = fscanf (f_in, "%d", &data->K);
    if (tt == 0){
        printf("[!] Fatal error! fscanf failed!\n");
            return (EXIT_FAILURE);
    }
    int toGo = data->K;
    
    data->M = -1;
    data->N = -1;

    printf("Starting data fill loop\n");
    for (int h=0; h < size && !feof(f_in) ; h++){ // && !feof(f_in)

        if (data->N == Ys){
            // Oops, overstepped
            break;
        }
        
        // Are we looking for another length?
        if (!flag){
            // Get the lengths & continue
            if (data->M == -1){
                int res = fscanf (f_in, "%d", &data->M);
                if (res == 0){
                    perror("[!] fscanf failed, skipping\n");
                    continue;
                }
                toGo = data->M;
            }else if (data->N == -1){
                int res = fscanf (f_in, "%d", &data->N);
                if (res == 0){
                    perror("[!] fscanf failed, skipping\n");
                    continue;
                }
                toGo = data->N;
            }
            // Return to loop
            flag = true;
            continue;
        }else{
            bool b1 = (data->N != -1);
            bool b2 = (data->M != -1);
            bool b3 = (data->K != -1);

            if (b1){
                int res = fscanf (f_in, "%d", &data->Y[Ys++]);
                if (res == 0){
                    perror("[!] fscanf failed, skipping\n");
                    continue;
                }
            }else if (b2){
                int res = fscanf (f_in, "%d", &data->X[Xs++]);
                if (res == 0){
                    perror("[!] fscanf failed, skipping\n");
                    continue;
                }
            }else if (b3){
                int res = fscanf (f_in, "%d", &data->A[As++]);
                if (res == 0){
                    perror("[!] fscanf failed, skipping\n");
                    continue;
                }
            }
            toGo--;
        }
        if (flag && toGo <= 0){
            flag = false;
        }
    }
    fclose(f_in);

    int n = data->K;
    printf("Input array for qsort has %d elements: \n",n);
    for (int i = 0; i < n; i++)
        printf("%d%s", data->A[i], i == n - 1 ? "\n" : "  ");
    
    n = data->M;
    printf("Input array x[] for merge has %d elements: \n",n);
    for (int i = 0; i < n; i++)
        printf("%d%s", data->X[i], i == n - 1 ? "\n" : "  ");
    
    n = data->N;
    printf("Input array y[] for merge has %d elements: \n",n);
    for (int i = 0; i < n; i++)
        printf("%d%s", data->Y[i], i == n - 1 ? "\n" : "  ");
    
    
    // Start processing the data into SHM
    printf("\n\n");
 
    // Shared memoery is setup!
    // @@@How to transfer key? Path?
    char Ab1[6];
    int abs1 = snprintf(Ab1, 6, "%d", f_in_s);
    
    if (abs1 < 0){
        perror("[!] CRITICAL ERROR! snprintf FAILED! Errors expected!\n");
    }
    
    char *args_qs[3];
            args_qs[0] = "./qsort";
            args_qs[1] = Ab1;
            args_qs[2] = NULL;

    // Setup argv for Merge
    char *args_ms[3];
            args_ms[0] = "./merge";
            args_ms[1] = Ab1;
            args_ms[2] = NULL;
    
    // Start the programs
    int id_qs = -1;
    int id_ms = -1;
    printf("*** MAIN: about to spawn the process for qsort\n");
    id_qs = fork();
    if (id_qs < 0){
        perror("[!] CRITICAL ERROR! Fork failed! Aborting...\n");
                exit(EXIT_FAILURE);
    }else if (id_qs == 0){
        int r1 = execvp("./qsort", args_qs);
        if (r1 == -1){
            perror("[!] CRITICAL ERROR! 1st Execvp FAILED!\n");
        }
    }else{
        wait();
    }
    n = data->K;
    printf("*** MAIN: sorted array by qsort:\n");
    for (int i = 0; i < n; i++)
        printf("%d%s", data->A[i], i == n - 1 ? "\n" : "  ");

    
    printf("*** MAIN: about to spawn the process for merge\n");
    id_ms = fork();
    if (id_ms < 0){
        perror("[!] CRITICAL ERROR! Fork failed! Aborting...\n");
        exit(EXIT_FAILURE);
    }else if (id_ms == 0){
        int r2 = execvp("./merge", args_ms );
        if (r2 == -1){
            perror("[!] CRITICAL ERROR! 2nd Execvp FAILED!\n");
        }
    }else{
        wait();
    }
    
    printf("*** MAIN: merged array:\n");
    n = ((int)(data->M)) + ((int)(data->N)); 
    for (int i = 0; i < n; i++)
        printf("%d%s", data->Z[i], i == n - 1 ? "\n" : "  ");
    printf("\n");

    // Free resources
    
    if (shmctl(shm_id, IPC_RMID, NULL) == -1) {
        perror("\n[!] INTERNAL ERROR! shmctl failed to delete shared memory segment!\n\n\n");
        exit(EXIT_FAILURE);
    }
    
    // Detach and kill our SHM
    if (shm_id != NULL){
        shmctl(shm_id, IPC_RMID, NULL );
    }
    shmdt(data);
    printf("*** MAIN: shared memory successfully detached\n");
    printf("*** MAIN: shared memory successfully removed\n");
    
    return (EXIT_SUCCESS);
}

// ----------------------------------------------------------- 
// FUNCTION  nextint : (function name)                          
//     Reads the next int from a file.                          
// PARAMETER USAGE :                                           
//    N/A               
// FUNCTION CALLED :                                           
//    fread        
// ----------------------------------------------------------- 
int nextInt(){
    char ans = '~';
    int z = fread(&ans, 1, 1, f_in);
}

// ----------------------------------------------------------- 
// FUNCTION  fileSize : (function name)                          
//     Check a files size                         
// PARAMETER USAGE :                                           
//    Name of file               
// FUNCTION CALLED :                                           
//    fopen/fclose/fseek         
// ----------------------------------------------------------- 
long fileSize(char *name){
    // Cite: Scott Kuhl
    FILE *temp2 = fopen(name, "r");
    long ans = 0l;
    if (temp2 == NULL){
        ans = -1l;
    }else if( fseek(temp2, ans, SEEK_END) != 0 ){
        ans = -1l;
        fclose(temp2);
    }else{
        ans = ftell(temp2);
        fclose(temp2);
    }

    return ans;
}

// ----------------------------------------------------------- 
// FUNCTION  fileExists : (function name)                          
//     Does a file exist                        
// PARAMETER USAGE :                                           
//    File path              
// FUNCTION CALLED :                                           
//    stat S_ISREG      
// ----------------------------------------------------------- 
bool fileExists(char *name){
    // Use stat to check if a file exists and isnt a directory
    struct stat s;
    int res = stat(name, &s);
    if (res == -1){
        return false;
    }
    
    // Bypass read restrictions
    bool b1 = s.st_mode&_READ || s.st_mode&_READ2;
    printf("\nFE: Read? %s %d %o <>\n",name,b1, (int)s.st_mode);
    return !(res) && S_ISREG(s.st_mode) ;//&& b1;
}

// ----------------------------------------------------------- 
// FUNCTION  setupSHM : (function name)                          
//     Setup shared mem seg                   
// PARAMETER USAGE :                                           
//    Add create flag to shmget?            
// FUNCTION CALLED :                                           
//    ftok, shmget, shmat, (shmctl, shmdt)       
// ----------------------------------------------------------- 
void setupSHM(bool makeNew, int size){
    // Setup the shared memory

    thee_key = ftok("main.c", thee_id);
    if (thee_key == -1){
        perror("[!] Fatal error! SHM Key NOT granted! Aborting...");
        exit(EXIT_FAILURE);
    }else{
        printf("*** MAIN: shared memory key = %d\n",thee_key);
    }
    // @@@ Make modular!
    if (makeNew){
        shm_id = shmget(thee_key, size, IPC_CREAT | 0666 | IPC_PRIVATE );
    }else{
        shm_id = shmget(thee_key, size, 0666|IPC_PRIVATE);
    }

    if (shm_id == -1){
        perror("[!] Fatal error! SHM Get NOT granted! Aborting...");
        exit(EXIT_FAILURE);
    }else{
        printf("*** MAIN: shared memory created\n");
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
    }else{
        printf("*** MAIN: shared memory attached and is ready to use\n");
    }
}