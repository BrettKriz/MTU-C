// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: BAKriz
// DUE DATE : 11/11/16                                       
// PROGRAM ASSIGNMENT #4                                        
// FILE NAME : thread-main.cpp        
// PROGRAM PURPOSE :                                           
//    To feed baby eagles until they are strong enough to fly!   
// ----------------------------------------------------------- 

#include "MotherEagle.h"
using namespace std;

data* setupSHM3(bool makeNew, int size);

// ----------------------------------------------------------- 
// FUNCTION  Mother Eagle                          
//     Constructor
// PARAMETER USAGE :                                           
//    ID: Process ID
// FUNCTION CALLED :                                           
//    setupSHM3, strcpy, sprintf, strcat
// ----------------------------------------------------------- 
MotherEagle::MotherEagle(int ID) {
    id = ID;
    //Data = d;
    int shm_id;
    //cout << &Data << " " << Data << endl;
    Data = setupSHM3(false, sizeof(data));
    //cout << &Data << " " << Data << endl;
    go = true;
    // Make name
    char b[5];
    for (int temp = 0; temp < 5; b[temp++]='\0');
    strcpy(ThreadName, "Mother Eagle#");
    sprintf(b, "%d",id);
    strcat(ThreadName, b);
}

// ----------------------------------------------------------- 
// FUNCTION  ThreadFunc                       
//     Preform main thread-based tasks                           
// PARAMETER USAGE :                                           
//    N/A
// FUNCTION CALLED :                                           
//   ThreadFunc, Exit, Delay, Food_Ready, Goto_Sleep
// ----------------------------------------------------------- 
void MotherEagle::ThreadFunc(){
    Thread::ThreadFunc();
    
    cout.write("\n Mother Eagle started.\n",24); // 23
    
    while(go){
        Goto_Sleep();
        Delay();
        Delay();
        Delay();
        Food_Ready();
        Delay();
    }
    {
        int a = 75;
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "\n Mother Eagle retires after serving %d feedings. Game is over!!!\n", Data->T);
        cout.write(str,a);
    }
    Exit();
}

// ----------------------------------------------------------- 
// FUNCTION  Goto_sleep                          
//     Wait for babies and adjust locks                         
// PARAMETER USAGE :                                           
//    N/A               
// FUNCTION CALLED :                                           
//    rewind, Wait, setBlock, sprintf
// ----------------------------------------------------------- 
void MotherEagle::Goto_Sleep(){

    cout.write(" Mother Eagle takes a nap.\n",28);
    Data->FoodLine->rewind();
    Data->Food->S->Wait();
    Data->setBlock(true);
    Data->Food->block = true;
    {
        int a = 70; //@@@ MEASURE ME!
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "\n Mother Eagle is awoke by baby eagle %d and starts preparing food.\n", Data->Food->num);
        cout.write(str,a);
    }
    
}

// ----------------------------------------------------------- 
// FUNCTION  Food_Ready                        
//     Refresh pots, unblock babies, consider end conditions                            
// PARAMETER USAGE :                                           
//    N/A
// FUNCTION CALLED :                                           
//    fill, addT, getT, sprintf, reset, setBlock
// ----------------------------------------------------------- 
void MotherEagle::Food_Ready(){
    int till = Data->m;
    // Check if the wait list is full

    Data->addT();
    int T = Data->getT();
    for(int x = 0; x < till; x++){
        // Fill food
        // RACE CONDITION, empty
        Data->pots[x]->fill();
    }
    
    {
        int a = 35;
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "\n Mother Eagle says \"Feeding (%d)\"\n", Data->T);
        cout.write(str,a);
    }
    Data->FoodLine->reset();
    Data->Food->reset();
    Data->Food->block = false;
    Data->setBlock(false);
    
    
    if ( T == Data->t){
        // We are done here
        go = false;
    }
    
}

// ----------------------------------------------------------- 
// FUNCTION  setupSHM3 :                     
//     Setup shared mem seg                   
// PARAMETER USAGE :                                           
//      makeNew?            
//      Size to alloc
//      data: pointer for the segment
//      shm_id: pointer for the ID of the SHM
// FUNCTION CALLED :                                           
//    ftok, shmget, shmat, (shmctl, shmdt)       
// ----------------------------------------------------------- 
data* setupSHM3(bool makeNew, int size){
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
        //printf("*** ME: shared memory key = %d\n",thee_key);
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
        //printf("*** ME: shared memory got\n");
    }
    //DataSec *data2;
    d = (data*)shmat(shm_id, NULL, 0);
    if ( d == (data*)(-1) ){
        perror("[!] Fatal error! SHM Attach NOT granted! Aborting...");
        Exit();
    }else{
        //printf("*** ME: shared memory attached and is ready to use\n");
    }
    return d;
}
