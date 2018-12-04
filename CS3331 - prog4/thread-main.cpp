// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: BAKriz
// DUE DATE : 11/11/16                                       
// PROGRAM ASSIGNMENT #4                                        
// FILE NAME : thread-main.cpp        
// PROGRAM PURPOSE :                                           
//    To feed baby eagles until they are strong enough to fly!   
// ----------------------------------------------------------- 

#include <iostream>		// Input\Output
#include <iomanip>		// Manipulation of I/O
#include <string>		// String Stuff
#include <cstring>		// Maniplulation of strings
#include <fstream>		// File stream (I/O)
#include <cmath>		// Basic Math Operations
#include <vector>		// Vectors

#include <stdlib.h>		// Standard Library
#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>            // Because forget conveinence!
#include <sys/stat.h>

#include "ThreadClass.h"        // Get ThreadMentor
#include "P4ToolKit.h"
#include "BabyEagle.h"
#include "MotherEagle.h"

using namespace std;
struct pot; struct TSSig; struct data;
data* setupSHM(bool makeNew, int size);


int main(int argc, char** argv) {
    // M N T
    // M pots
    // N eagle babies
    // T # of feedings
    // 0 < M <= N <= 20
    if (argc < 4){
        cout << "\t[!] NOT ENOUGH ARGUMENTS!" << endl;
        cout << "\n\tExpected Format:" << endl << endl;
        cout << "\tM N T" << endl;
        cout << "\tM pots\n\tN eagle babies\n\tT # of feedings" << endl;
        cout << "\t0 < M <= N <= 20" << endl << endl;
        return 1;
    }
    // Setup vars
    
    int M,N,T;
    //cin >> M >> N >> T;
    
    M = atoi(argv[1]);
    N = atoi(argv[2]);
    T = atoi(argv[3]);
    
    cout << endl << "MAIN: There are " << N << " baby eagles, " ;
    cout << M << " feeding pots, and " << T << " feedings" << endl;
    sleep(1);
    cout << "MAIN: Game starts!!!!!" << endl << endl << endl;
    // Generate actors
    data *D = NULL;
    D = setupSHM(true,sizeof(data));
    D->init(M,N,T);
    int shm_id = D->shm_id;

    // Make mother
    MotherEagle *mom = new MotherEagle(1);
    D->mom = mom;
    // Make babies
    for (int x = 0; x < N; x++){
        BabyEagle *cur = new BabyEagle(x+1);
        D->babys[x] = cur;
    }
    // Make pots
    for (int x = 0; x < N; x++){
        pot *cur = new pot(x+1);
        D->pots[x] = cur;
    }
    // Now that we are all setup, lets start
    mom->Begin();
    for (int x = 0; x < N; x++){
        D->babys[x]->Begin();
    }
    
    for (int z = 0; z < M; z++){
        D->Death->Wait();
        //cout << "\t#" << z << endl;
    } // count <= 0  So 0 works to resume
    
    
    cout << endl << endl << "\tProgram DONE!" << endl;
    // Detach and kill our SHM
    if (shm_id != NULL){
        shmctl(shm_id, IPC_RMID, NULL );
    }
    shmdt(D);
    return 0;
}


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
data* setupSHM(bool makeNew, int size){
    // Setup the shared memory
    data *d = NULL;
    key_t thee_key;
    int thee_id     = 6;
    int shm_id = -1;
    
    thee_key = ftok("thread-main.cpp", thee_id);
    if (thee_key == -1){
        perror("[!] Fatal error! SHM Key NOT granted! Aborting...");
        exit(EXIT_FAILURE);
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
        exit(EXIT_FAILURE);
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
        exit(EXIT_FAILURE);
    }else{
        //printf("*** MAIN: shared memory attached and is ready to use\n");
    }
    
    d->shm_id = shm_id;
    
    return d;
}