// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: BAKriz
// DUE DATE : 12/2/16                                       
// PROGRAM ASSIGNMENT #5                                        
// FILE NAME : P5ToolKit.cpp        
// PROGRAM PURPOSE :                                           
//    To provide structs for thread-main   
// ----------------------------------------------------------- 

#ifndef P5TOOLKIT_H
#define	P5TOOLKIT_H

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
#include "boat-monitor.h"

class boat_monitor; class thread;

struct data{
    //_SHM SHM;
    int shm_id;
    int C, M, B;
    boat_monitor *BOAT_M;
    thread *MT[25];
    thread *CT[25];
    thread *Boat;
    
    data(){
        shm_id = -1;
    }
    
    // ----------------------------------------------------------- 
    // FUNCTION  init
    //     initialize the values for data
    // PARAMETER USAGE :                                           
    //    M N T2 : Standard input for main program...
    // FUNCTION CALLED :                                           
    //    N/A
    // ----------------------------------------------------------- 
    void init(int c, int m, int b) {
        C = c;
        M = m;
        B = b;
        
        // Setup stuff
        if (shm_id == -1){
            // oops!
            perror("[!] SHM ID = -1!  Avoiding initialization error! \n");
            return;
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
        int thee_id     = 2;
        int shm_id = -1;

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











#endif	/* P5TOOLKIT_H */

