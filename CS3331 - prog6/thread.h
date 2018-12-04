// ----------------------------------------------------------- 
// NAME : Brett Kriz                           User ID: BAKriz
// DUE DATE : 12/9/2016
// PROGRAM ASSIGNMENT #6
// FILE NAME : thread.h
// PROGRAM PURPOSE :                                           
//    To define thread classes used
// ----------------------------------------------------------- 

#ifndef THREAD_H
#define	THREAD_H

#include <cmath>		// Basic Math Operations
#include <vector>		// Vectors

#include <stdlib.h>		// Standard Library
#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>            // Because forget conveinence!
#include <sys/stat.h>

#include "ThreadClass.h"        // Get ThreadMentor
#include "P6ToolKit.h"



class sthread;

class thread : public Thread {
public:
    thread(int uniqueID, int ci, int ri);
    ~thread();
    int RI, CI;
    int LN[8], UN[8];
    int LNs, UNs;
    int ANS;
    bool NoMoreRows, NoMoreCols;
    data *D;
    int EOD;
    SynOneToOneChannel *right;
    SynOneToOneChannel *down;
    SynOneToOneChannel *left;
    SynOneToOneChannel *up;
    
    int uid;
    
    
private:
    void ThreadFunc();
};

class sthread : public Thread{
public:
    sthread(int uniqueID, int I, int s, bool isR);
    ~sthread();
    int i;
    int size;
    int EOD;
    int uid;
    bool isr;
    
    data *D;
    SynOneToOneChannel *out;
    thread *out_t;

private:
    void ThreadFunc();
};

#endif	/* THREAD_H */

