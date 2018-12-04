// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: BAKriz
// DUE DATE : 11/11/16                                       
// PROGRAM ASSIGNMENT #4                                        
// FILE NAME : thread-main.cpp        
// PROGRAM PURPOSE :                                           
//    To eat until I am strong enough to fly!   
// ----------------------------------------------------------- 

#include "BabyEagle.h"
using namespace std;

data* setupSHM2(bool makeNew, int size);

// ----------------------------------------------------------- 
// FUNCTION  BabyEagle
//     Constructor                           
// PARAMETER USAGE :                                           
//    n/a               
// FUNCTION CALLED :                                           
//    setupSHM2, strcpy, strcat, sprintf
// ----------------------------------------------------------- 
BabyEagle::BabyEagle(int ID) { // , data *d
    id = ID;
    init = true;
    //cout << &Data << " " << Data << endl;
    Data = setupSHM2(false, sizeof(data));
    //cout << &Data << " " << Data << endl;
    fed = false;
    // Setup space amount by id
    for (int x = 0; x < 20; x++){
        char cur = '\0';
        if (x < id){
            cur = ' ';
        }
        spc[x] = (char)cur;
    }
    
    // Setup name
    char b[15];
    for (int temp = 0; temp < 15; b[temp++]='\0');
    strcpy(ThreadName, "Baby Eagle ");
    sprintf(b, "%d",id);
    strcat(ThreadName, b);

}

// ----------------------------------------------------------- 
// FUNCTION  ThreadFunc                       
//     Preform main thread-based tasks                           
// PARAMETER USAGE :                                           
//    N/A
// FUNCTION CALLED :                                           
//   ThreadFunc, sprintf, Delay, Ready_to_Eat, Finish_Eating
// ----------------------------------------------------------- 
void BabyEagle::ThreadFunc(){
    Thread::ThreadFunc();
    
    {
        int a = 50;
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "%s%s started.\n", spc, ThreadName);
        cout.write(str,a);
    }
    
    while(true){
        Delay();
        Ready_to_Eat();
        Delay();
        Finish_Eating();
    }
}

// ----------------------------------------------------------- 
// FUNCTION  Ready_to_Eat                    
//     Prepare/Attempt to eat from a pot
// PARAMETER USAGE :                                           
//    N/A
// FUNCTION CALLED :                                           
//   getBlock, sprintf, sig, halt, Delay, Signal
// ----------------------------------------------------------- 
void BabyEagle::Ready_to_Eat(){
    fed = false;
    int till = Data->m;
    
    {
        int a = 60;
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "%s%s is ready to eat.\n", spc, ThreadName);
        cout.write(str,a);
    }
    
    while(true){
        //while(Data->getBlock() && !init){
            //Delay();
        //}
        for (int x = 0; x < till; x++){
            // Avoid race conditions
            if (Data->getBlock()){
                break;  
            }
            if( !(Data->pots[x]->TS()) ){
                // EAT  
                {
                    int a = 70;
                    char str[a];
                    for (int temp = 0; temp < a; str[temp++]='\0');
                    sprintf(str, "%s%s is eating using pot %d.\n", spc, ThreadName, x+1);
                    cout.write(str,a);
                }
                return;
                 //break;
            }
        }
        // Call mom
        if (!Data->getBlock()){
            bool ans = Data->Food->sig(id); // .sig(id);
            if (ans){
                Delay();
                Delay();
                // Baby eagle 2 sees all feeding pots are empty and wakes up the mother.
                int a = 92;
                char str[a];
                for (int temp = 0; temp < a; str[temp++]='\0');
                sprintf(str, "%s%s sees all feeding pots are empty and wakes up the mother.\n", spc, ThreadName);
                cout.write(str,a);
            }
        }
        init = false;
        Data->FoodLine->Fill->Signal(); 
        Data->FoodLine->halt();
        Delay();
        Delay();
    }// EndWhile
}

// ----------------------------------------------------------- 
// FUNCTION  Finish Eating                   
//     Emit message, preform end tasks and deaths                        
// PARAMETER USAGE :                                           
//    N/A
// FUNCTION CALLED :                                           
//   sprintf, Signal, GetT, Exit
// ----------------------------------------------------------- 
void BabyEagle::Finish_Eating(){
    fed = true;
    {
        int a = 55;
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "%s%s finishes eating.\n", spc, ThreadName);
        cout.write(str,a);
    }
    // Race conition for Data.T!!
    int T = (int)Data->getT();
    if ( T == (((int)Data->t)) ){
        Data->Death->Signal();
        {
            int a = 75;
            char str[a];
            for (int temp = 0; temp < a; str[temp++]='\0');
            sprintf(str, "%s%s is now strong & flies out of the nest!\n\n", spc, ThreadName);
            cout.write(str,a);
        }
        Exit();
    }
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
//    ftok, shmget, shmat, (shmctl, shmdt), Exit
// ----------------------------------------------------------- 
data* setupSHM2(bool makeNew, int size){
    // Setup the shared memory
    key_t thee_key;
    int thee_id     = 6;
    int shm_id;
    data *d;
    
    thee_key = ftok("thread-main.cpp", thee_id);
    if (thee_key == -1){
        perror("[!] Fatal error! SHM Key NOT granted! Aborting...");
        Exit();
    }else{
        //printf("*** BB: shared memory key = %d\n",thee_key);
    }
    // @@@ Make modular!
    if (makeNew){
        shm_id = shmget(thee_key, size, IPC_CREAT | 0666 | IPC_PRIVATE );
    }else{
        shm_id = shmget(thee_key, size, 0666 | IPC_PRIVATE );
    }

    if (shm_id == -1){
        perror("[!] Fatal error! SHM Get NOT granted! Aborting...");
        Exit();
    }else{
        //printf("*** BB: shared memory got\n");
    }
    //DataSec *data2;
    d = (data*)shmat(shm_id, NULL, 0);
    if ( d == (data*)(-1) ){
        perror("[!] Fatal error! SHM Attach NOT granted! Aborting...");
        Exit();
    }else{
        //printf("*** BB: shared memory attached and is ready to use\n");
    }
    return d;
}