// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: BAKriz
// DUE DATE : 11/11/16                                       
// PROGRAM ASSIGNMENT #4                                        
// FILE NAME : thread-main.cpp        
// PROGRAM PURPOSE :                                           
//    To provide structs for thread-main   
// ----------------------------------------------------------- 

#ifndef P4TOOLKIT_H
#define	P4TOOLKIT_H

#include <stdio.h>
#include <string>		// String Stuff
//#include <sstream>              // String streams FTW
#include <cstring>		// Maniplulation of strings
#include <iostream>		// Input\Output
#include <iomanip>		// Manipulation of I/O

#include "ThreadClass.h"        // Get ThreadMentor

class MotherEagle; class BabyEagle;

struct pot{
    bool empty;
    int id;
    Semaphore *Mutex;
    
    pot(int ID){
        empty = true;
        id = ID;
        //stringstream concat;
        //concat << "Pot" << id << " Mutex";
        //Mutex = Semaphore(concat.str(),1);
        
        int a = 13;
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "Pot%d Mutex", id);
        Mutex = new Semaphore(str,1);
        
    }
    
    bool TS(){
        bool res;
        Mutex->Wait();
        res = ts();
        Mutex->Signal();
        return res;
    }
    void fill(){
        Mutex->Wait();
        empty = false;
        Mutex->Signal();
    }
private:
    bool ts(){
        bool t = empty;
        empty = true;
        return t;
    }
};

struct TSSig{
    bool lock;
    bool block;
    int num;
    Semaphore *S;
    Semaphore *Mutex;
    
    // ----------------------------------------------------------- 
    // FUNCTION  TSSig
    //     Constructor
    // PARAMETER USAGE :                                           
    //    N/A
    // FUNCTION CALLED :                                           
    //    N/A
    // ----------------------------------------------------------- 
    TSSig(){
        lock = false;
        block = false;
        num = -1;
        S = new Semaphore("Food Prep",0);
        Mutex = new Semaphore("TSSig Mutex", 1);
    }
    
    // ----------------------------------------------------------- 
    // FUNCTION  ts2 (LEGACY)
    //     Test Set function for BLOCK                        
    // PARAMETER USAGE :                                           
    //   N/A
    // FUNCTION CALLED :                                           
    //    N/A
    // ----------------------------------------------------------- 
    bool ts2(){
        bool t = block;
        block = true;
        return block;
    }
    
    // ----------------------------------------------------------- 
    // FUNCTION  ts
    //     Test set function for LOCK
    // PARAMETER USAGE :                                           
    //   N/A
    // FUNCTION CALLED :                                           
    //    N/A
    // ----------------------------------------------------------- 
    bool ts(){
        bool t = lock;
        lock = true;
        return t;
    }
    
    // ----------------------------------------------------------- 
    // FUNCTION  sig
    //     preform a signal guarded by a Test Set
    // PARAMETER USAGE :                                           
    //    NuM: ID of caller
    // FUNCTION CALLED :                                           
    //    Signal, ts
    // ----------------------------------------------------------- 
    bool sig(int Num){
        Mutex->Wait();
        bool a = !ts();
        bool ans = false;
        if ( a ){ // ATOMIC???   && b
            num = Num;
            S->Signal();
            ans = true;
        }
        Mutex->Signal();
        return ans;
    }
    
    // ----------------------------------------------------------- 
    // FUNCTION  reset
    //    Reset the value of lock and num
    // PARAMETER USAGE :                                           
    //    N/A
    // FUNCTION CALLED :                                           
    //    Wait, Signal
    // ----------------------------------------------------------- 
    void reset(){
        Mutex->Wait();
        lock = false;
        num = -1;
        Mutex->Signal();
    }
};

struct line{
    // A queue more so
    int n;
    int count;
    Semaphore *Mutex;
    Semaphore *Food;
    Semaphore *Fill;
    
    // ----------------------------------------------------------- 
    // FUNCTION  line
    //     Constructor
    // PARAMETER USAGE :                                           
    //    N: the size to compair agianst later
    // FUNCTION CALLED :                                           
    //    N/A
    // ----------------------------------------------------------- 
    line(int N){
        n = N;
        count = 0;
        Mutex = new Semaphore("Line Mutex",1);
        Food = new Semaphore("Food Line",0);
        Fill = new Semaphore("Line Fill",0);
    }
    
    // ----------------------------------------------------------- 
    // FUNCTION  rewind
    //     Iterate over N waits
    // PARAMETER USAGE :                                           
    //    N/A
    // FUNCTION CALLED :                                           
    //    Wait
    // ----------------------------------------------------------- 
    void rewind(){
        // Caller will rewind...
        for (int x = 0; x < n; x++){
            Fill->Wait();
        }
    }
    
    // ----------------------------------------------------------- 
    // FUNCTION  halt
    //     Create a waiting semaphore with an external count
    // PARAMETER USAGE :                                           
    //    N/A
    // FUNCTION CALLED :                                           
    //    Wait, Signal
    // ----------------------------------------------------------- 
    int halt(){
        Mutex->Wait();
        count++;
        Mutex->Signal();
        // Now actually wait
        Food->Wait();
    }
    
    // ----------------------------------------------------------- 
    // FUNCTION  isFull
    //     Check if the wait semaphore is full
    // PARAMETER USAGE :                                           
    //    N/A
    // FUNCTION CALLED :                                           
    //    Wait, Signal
    // ----------------------------------------------------------- 
    bool isFull(){
        Mutex->Wait();
        int cur = count;
        bool ans = (cur == n);
        Mutex->Signal();
        if (cur > n){
            perror("[!] COUNT ERROR! Count > M @ LINE SEM!!");
        }
        return ans;
    }
    
    // ----------------------------------------------------------- 
    // FUNCTION  reset
    //     Reset the value of count and signal that many times
    // PARAMETER USAGE :                                           
    //    N/A
    // FUNCTION CALLED :                                           
    //    Wait, Signal
    // ----------------------------------------------------------- 
    int reset(){
        Mutex->Wait();
        
        int at = count;
        if (count > n){
            printf("[!] COUNT ERROR! Count > M @ LINE SEM!! %d vs %d\n",count,n);
        }else if (count < n){
            printf("[!] COUNT ERROR! Count < M @ LINE SEM!! %d vs %d\n",count,n);
        }
        // Start releasing these
        for (int x = 0; x < at; x++){
            count--;
            Food->Signal();
        }
        
        Mutex->Signal();
        
    }
};

struct data{
    int shm_id;
    bool block;
    int t; // # feeds to do
    int m; // # of pots
    int n; // # of babys
    
    int T; // feed count
    //int Deaths = 0;
    Semaphore *TSem;
    Semaphore *Death; 
    Semaphore *Mutex;
    TSSig *Food;
    line *FoodLine;
    
    pot *pots[20];
    BabyEagle *babys[20];
    MotherEagle *mom;
    
    // ----------------------------------------------------------- 
    // FUNCTION  init
    //     initialize the values for data
    // PARAMETER USAGE :                                           
    //    M N T2 : Standard input for main program...
    // FUNCTION CALLED :                                           
    //    N/A
    // ----------------------------------------------------------- 
    void init(int M, int N, int T2) {
        block = !true;
        m = M;
        n = N;
        t = T2;
        T = 0; // Feed Count
        Mutex = new Semaphore("Block Mutex",1);
        TSem = new Semaphore("Feed Count",1);
        Death = new Semaphore("Baby 'Deaths'",0); // So much for M*-1
        Food = new TSSig();
        FoodLine = new line(N);
        // We'll do the rest in main
        // Since they are pointers
    }
    
    // ----------------------------------------------------------- 
    // FUNCTION  getBlock
    //     Return the value of block threadSafe
    // PARAMETER USAGE :                                           
    //    N/A
    // FUNCTION CALLED :                                           
    //    Wait, Signal
    // ----------------------------------------------------------- 
    bool getBlock(){
        bool ans;
        Mutex->Wait();
        ans = block;
        Mutex->Signal();
        return ans;
    }
    // ----------------------------------------------------------- 
    // FUNCTION  setBlock
    //     set the value of block (Not threadSafe)
    // PARAMETER USAGE :                                           
    //    V: new value for block
    // FUNCTION CALLED :                                           
    //    N/A
    // ----------------------------------------------------------- 
    bool setBlock(bool v){
        bool ans;
        //Mutex->Wait();
        block = v;
        ans = block;
        //Mutex->Signal();
        return ans;
    }
    
    // ----------------------------------------------------------- 
    // FUNCTION  getT
    //     Get the feed count
    // PARAMETER USAGE :                                           
    //    N/A
    // FUNCTION CALLED :                                           
    //    Wait, Signal
    // ----------------------------------------------------------- 
    int getT(){
        int ans;
        TSem->Wait();
        ans = T;
        TSem->Signal();
        return ans;
    }
    
    // ----------------------------------------------------------- 
    // FUNCTION  addT
    //     Increment the value of T
    // PARAMETER USAGE :                                           
    //    N/A
    // FUNCTION CALLED :                                           
    //    Wait, Signal
    // ----------------------------------------------------------- 
    int addT(){
        int ans;
        TSem->Wait();
        T++;
        ans = T;
        TSem->Signal();
        return T;
    }
};


#endif	/* P4TOOLKIT_H */

