// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: bakriz
// DUE DATE : 12/2/2016
// PROGRAM ASSIGNMENT #5
// FILE NAME : thread.h
// PROGRAM PURPOSE :                                           
//    Setup the thread class
// ----------------------------------------------------------- 

#ifndef THREAD_H
#define	THREAD_H

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
#include <strstream>

#include "ThreadClass.h"        // Get ThreadMentor
#include "P5ToolKit.h"

struct data;
static const int NAME_SIZE = 47; // Minimum name size

class thread : public Thread  {
public:
    // All in one class
    bool isCannibal,  isMissionary, isBoat, seated;
    data *D;
    int num;
    strstream *Space;
    thread *x;
    thread *y;
    thread *z;
    int trips;
    
    thread(data *d, int t, int ID);
private:
    void ThreadFunc();
    void doCannibal();
    void doMissionary();
    void doBoat();
};

#endif	/* THREAD_H */

