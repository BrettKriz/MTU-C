// ----------------------------------------------------------- 
// NAME : Brett Kriz                           User ID: BAKriz
// DUE DATE : 12/9/2016
// PROGRAM ASSIGNMENT #6
// FILE NAME : P6ToolKit.h
// PROGRAM PURPOSE :                                           
//    To provide common structs to the main prog
// ----------------------------------------------------------- 

#ifndef P6TOOLKIT_H
#define	P6TOOLKIT_H

#include <stdio.h>
#include <string>		// String Stuff
#include <cstring>		// Maniplulation of strings
#include <iostream>		// Input\Output
#include <iomanip>		// Manipulation of I/O

#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>            // Because forget conveinence!
#include <sys/stat.h>
#include <strstream>

#include "ThreadClass.h"
#include "thread.h"

class sthread; class thread;

struct data{
    //_SHM SHM;
    static const int BUFF_SIZE = 8;
    static const int EOD = -1;
    static const int UNDEFINED = -2;
    
    int shm_id;
    int size; // sizeXsize
    int RESULT[BUFF_SIZE][BUFF_SIZE];
    
    int A[BUFF_SIZE][BUFF_SIZE];
    int B[BUFF_SIZE][BUFF_SIZE];
    // R = A, C = B
    sthread *R[BUFF_SIZE];
    sthread *C[BUFF_SIZE];
    
    thread *tbl[BUFF_SIZE][BUFF_SIZE];
    // ----------------------------------------------------------- 
    // FUNCTION : data
    //      To construct the data struct
    // PARAMETER USAGE : 
    //      
    // FUNCTION CALLED : 
    //      
    // ----------------------------------------------------------- 
    data(int s = -1){
        shm_id = -1;
        size = s;
        for (int y = 0; y < BUFF_SIZE; y++){
            for (int x = 0; x < BUFF_SIZE; x++){
                RESULT[x][y] = UNDEFINED;
            }
        }
    }

};

struct _SHM{
    int shm_id;
    
    // ----------------------------------------------------------- 
    // FUNCTION  setupSHM :                     
    //     Setup shared mem seg                   
    // PARAMETER USAGE : 
    //      makeNew?            
    //      Size to alloc
    //      data: pointer for the segment
    //      shm_id: pointer for the ID of the SHM
    // FUNCTION CALLED :                                           
    //    ftok, shmget, shmat, (shmctl, shmdt)       
    // ----------------------------------------------------------- 
    data* setupSHM(bool makeNew, int size = sizeof(data)){
        // Setup the shared memory
        data *d = NULL;
        key_t thee_key;
        int thee_id     = 4;
        shm_id = -1;

        thee_key = ftok("thread-main.cpp", thee_id);
        if (thee_key == -1){
            perror("[!] Fatal error! SHM Key NOT granted! Aborting...");
            return NULL;
        }else{
            //printf("*** MAIN: shared memory key = %d\n",thee_key);
        }
        // @@@ Make modular!
        if (makeNew){
            shm_id = shmget(thee_key, size, IPC_CREAT | 0666 | IPC_PRIVATE );
        }else{
            shm_id = shmget(thee_key, size, 0666|IPC_PRIVATE);
        }

        if (shm_id == -1){
            perror("[!] Fatal error! SHM Get NOT granted! Aborting...");
            return NULL;
        }else{
            //printf("*** MAIN: shared memory created\n");
        }
        //DataSec *data2;
        d = (data*)shmat(shm_id, NULL, 0);
        if ( d == (data*)(-1) ){
            perror("[!] Fatal error! SHM Attach NOT granted! Aborting...");
            // Detach and kill our SHM
            if (makeNew){
                shmctl(shm_id, IPC_RMID, NULL );
            }
            //shmdt(data);
            return NULL;
        }else{
            //printf("*** MAIN: shared memory attached and is ready to use\n");
        }

        d->shm_id = shm_id;
        return d;
    }
};
#endif	/* P6TOOLKIT_H */

