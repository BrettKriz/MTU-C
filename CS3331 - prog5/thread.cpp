// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: bakriz
// DUE DATE : 12/2/2016
// PROGRAM ASSIGNMENT #5
// FILE NAME : thread.cpp
// PROGRAM PURPOSE :                                           
//    Handles the thread class' and their functions
// ----------------------------------------------------------- 

#include "thread.h"
#include "boat-monitor.h"
struct data;

// ----------------------------------------------------------- 
// FUNCTION : thread
//      To construct a thread
// PARAMETER USAGE :                                           
//      Data space pointer, t type of thread, ID
// FUNCTION CALLED :                                           
//      
// ----------------------------------------------------------- 
thread::thread(data *d, int t, int ID = -1) {
    // Get SHM
    D = d;
    isCannibal = false;
    isMissionary = false;
    isBoat = false;
    
    num = ID;
    
    // Setup name
    ThreadName.seekp(0, ios::beg);
    
    {
     int  i;

     Space = new strstream();
     for (i = 0; i < ID; i++) // No more than 30 total
          (*Space) << ' ';
     (*Space) << '\0';
    }
    
    if (t == 0){
        // C
        ThreadName << "Cannibal" << ID << '\0';
        isCannibal = true;
    }else if (t == 1){
        // M
        ThreadName << "Missionary" << ID << '\0';
        isMissionary = true;
    }else if (t == 2){
        // BOAT
        ThreadName << "Boat-Thread" << '\0';
        isBoat = true;
    }
}

// ----------------------------------------------------------- 
// FUNCTION : ThreadFunc
//      To run the primary thread func
// PARAMETER USAGE :                                           
//      
// FUNCTION CALLED :                                           
//      
// ----------------------------------------------------------- 
void thread::ThreadFunc(){
    Thread::ThreadFunc();
    {
        int a = NAME_SIZE + 9; //@@@ MEASURE ME!
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "%s%s starts\n", Space->str(), ThreadName.str() );
        cout.write(str,a);
    }
    
    if (isBoat){
        doBoat();
    }else if (isCannibal){
        doCannibal();
    }else if (isMissionary){
        doMissionary();
    }else{
        // PROBLEM!
        perror("[!] CRITICAL ISSUES! Thread not given a role!\n");
    }
}

// ----------------------------------------------------------- 
// FUNCTION : doCannibal
//      To run a cannibals code section
// PARAMETER USAGE :                                           
//      
// FUNCTION CALLED :                                           
//      CannibalArrives
// ----------------------------------------------------------- 
void thread::doCannibal(){
    if (!isCannibal){ 
        perror("[!] Invalid thread type entering loop section CANNIBAL \n");
        return;
    }
    
    bool res;
    while(true){
        Delay();
        /*{
        int a = NAME_SIZE + 9; //@@@ MEASURE ME!
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "%s%s arrives\n", Space->str(), ThreadName.str());
        cout.write(str,a);
        }*/
        res = D->BOAT_M->CannibalArrives(this);
    }
}

// ----------------------------------------------------------- 
// FUNCTION : doMissionary
//      To run missionaries code segment
// PARAMETER USAGE :                                           
//      
// FUNCTION CALLED :                                           
//      MissionaryArrives
// ----------------------------------------------------------- 
void thread::doMissionary(){
    if (!isMissionary){ 
        perror("[!] Invalid thread type entering loop section MISSIONARY \n");
        return;
    }
        
    bool res;
    while(true){
        Delay();
        /*{
        int a = NAME_SIZE + 9; //@@@ MEASURE ME!
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "%s%s arrives\n", Space->str(), ThreadName.str());
        cout.write(str,a);
        }*/
        res = D->BOAT_M->MissonaryArrives(this);
    }
}

// ----------------------------------------------------------- 
// FUNCTION : doBoat
//      To run boats code segment
// PARAMETER USAGE :                                           
//      
// FUNCTION CALLED :                                           
//      BoatReady, BoatDone
// ----------------------------------------------------------- 
void thread::doBoat(){
    if (!isBoat){ 
        perror("[!] Invalid thread type entering loop section BOAT \n");
        return;
    }
    
    while(true){
        Delay();
        D->BOAT_M->BoatReady(this);
        {
            int a = 21 + 40; //@@@ MEASURE ME!
            int X,Y,Z;
            char Xc,Yc,Zc;
            X = x->num;
            Y = y->num;
            Z = z->num;
            Xc = x->isCannibal ? 'C' : 'M';
            Yc = y->isCannibal ? 'C' : 'M';
            Zc = z->isCannibal ? 'C' : 'M';

            char str[a];
            for (int temp = 0; temp < a; str[temp++]='\0');
            sprintf(str, "***** Boat load (%d): Passenger list (%c%d, %c%d, %c%d)\n", trips, Xc, X, Yc, Y, Zc, Z);
            cout.write(str,a);
        }
        Delay();
        D->BOAT_M->BoatDone(this);
        {
            int a = 21 + 15; //@@@ MEASURE ME!
            char str[a];
            for (int temp = 0; temp < a; str[temp++]='\0');
            sprintf(str, "***** Boat load (%d): Completed\n", trips);
            cout.write(str,a);
        }
    }
}
