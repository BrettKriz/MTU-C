// ----------------------------------------------------------- 
// NAME : Brett Kriz                        User ID: bakriz 
// DUE DATE : 10/14/2016                                       
// PROGRAM ASSIGNMENT #2                                        
// FILE NAME : qsort.c           
// PROGRAM PURPOSE :                                           
//    To sort a shared memory segment
//      and run quicksort in parallel
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

// Variables
key_t thee_key  = NULL;
int thee_id     = 7;
int shm_id;
DataSec *data;

int *A;
int A_s = -1337;
int f_in_s = -1;
// Protos
void setupSHM(bool makeNew);
int partition( int left, int right, int pivot_index);
void swap( int left, int right);
void quicksort( int left, int right);

// ----------------------------------------------------------- 
// FUNCTION  main :                       
//     Get the ball rolling for this file                           
// PARAMETER USAGE :                                           
//      argc is the number of arguments in argv
//      argv holds the total size of the shm segment
// FUNCTION CALLED :                                           
//      setupSHM
//      quicksort
//      shmdt
//      strtoimax
// ----------------------------------------------------------- 
int main(int argc, char** argv) {

    // Read argv's
    if (argc < 2){
        perror("\t[!] Not enough arguments for QSORT! Run Rejected!\n\n");
        return (EXIT_FAILURE);
    }
    
    // Fetch File size
    char *endp;
    intmax_t temp = strtoimax(argv[1], &endp, 10); // What a cool function
    f_in_s = (int)temp;

    
    // obtain the SHM
    
    setupSHM(false);

    // Define pointers for ease
    A_s = (int)(data->K);


    // Run the sort (which forks)
    quicksort( 0, A_s-1);

    // (Do not destroy the SHM)
    // Detach from the SHM
    if (shm_id != NULL){
        shmdt(data);
    }
    
    printf("\nQSORT Done!\n");
    
    return (EXIT_SUCCESS);
}

// ----------------------------------------------------------- 
// FUNCTION  partition : (function name)                          
//     Partition the array using the pivot                           
// PARAMETER USAGE :                                           
//      left & right are index ranges
//      pivot_index inside the index range
// FUNCTION CALLED :                                           
//    swap         
// ----------------------------------------------------------- 
int partition( int left, int right, int pivot_index)
{
    int pivot_value = data->A[pivot_index];
    int store_index = left;
    int i;

    swap( pivot_index, right);
    for (i = left; i < right; i++)
        if (data->A[i] <= pivot_value) {
            swap( i, store_index);
            ++store_index;
        }
    swap( store_index, right);
    return store_index;
}

// ----------------------------------------------------------- 
// FUNCTION  swap : (function name)                          
//     Swap 2 elements                           
// PARAMETER USAGE :                                           
//    left and right, the indexes to swap               
// FUNCTION CALLED :                                           
//             
// ----------------------------------------------------------- 
void swap(int left, int right)
{
    // Convenience function
    int t;
    t = data->A[left];
    data->A[left] = data->A[right];
    data->A[right] = t;
}

// ----------------------------------------------------------- 
// FUNCTION  quicksort : (function name)                          
//     sort an array in shm                           
// PARAMETER USAGE :                                           
//      left: starting index
//      right: ending index
// FUNCTION CALLED :                                           
//      fork
//      wait
//      partition
//      quicksort
// ----------------------------------------------------------- 
void quicksort( int left, int right)
{   // data->A
    int pivot_index = left;
    int pivot_new_index;
    int lchild = -1;
    int rchild = -1;

    if (right > left) {
        pivot_new_index = partition( left, right, pivot_index);
        printf("   ### Q-PROC(%d): pivot element is a[%d] = %d\n",getpid(),pivot_new_index, data->A[pivot_new_index]);
        // Left starts
        lchild = fork();
        if (lchild < 0) {
            perror("[!] CRITICAL ERROR! Fork failed! Aborting...\n");
            exit(EXIT_FAILURE);
        }
        if (lchild == 0) {
            printf("   ### Q-PROC(%d): entering with a[%d...%d]\n",getpid(),left, pivot_new_index-1);
            int n = pivot_new_index-1;
            for (int i = left; i < n; i++)
                printf("%d%s", data->A[i], i == n - 1 ? "\n" : "  ");
            
            quicksort( left, pivot_new_index - 1);
            exit(EXIT_SUCCESS);
        } else {
            // Right starts
            rchild = fork();
            if (rchild < 0) {
                perror("[!] CRITICAL ERROR! Fork failed! Aborting...\n");
                exit(EXIT_FAILURE);
            }
            if (rchild == 0) {
                printf("   ### Q-PROC(%d): entering with a[%d...%d]\n",getpid(),pivot_new_index+1, right);
                int n = right;
                for (int i = pivot_new_index+1; i < n; i++)
                    printf("%d%s", data->A[i], i == n - 1 ? "\n" : "  ");
            
                quicksort( pivot_new_index + 1, right);
                exit(EXIT_SUCCESS); // May cause errors?
            }
        }
        //printf("Waiting\n");
        wait();
        //waitpid(lchild, &status, 0);
        wait();
        //waitpid(rchild, &status, 0);
        printf("   ### Q-PROC(%d): section a[%d...%d] sorted\n",getpid(),left,right);
        int n = right;
            for (int i = left; i < n; i++)
                printf("%d%s", data->A[i], i == n - 1 ? "\n" : "  ");
    }
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