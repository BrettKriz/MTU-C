// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: bakriz
// DUE DATE : 12/2/2016
// PROGRAM ASSIGNMENT #5
// FILE NAME : boat-monitor.h
// PROGRAM PURPOSE :                                           
//    setup the boat-monitor class
// ----------------------------------------------------------- 

#ifndef BOAT_MONITOR_H
#define	BOAT_MONITOR_H

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
#include "P5ToolKit.h"

struct data; class thread;

class boat_monitor : public Monitor {

public:
    boat_monitor(char *Name, int runs);
    void BoatReady(thread *t);
    void BoatDone(thread *t);
    bool CannibalArrives(thread *t);
    bool MissonaryArrives(thread *t);
    void reset();
    bool BoatFull();
    bool SeatFull();

private:
    bool full;
    int MC;
    int CC;
    int togo;
    int B;
    thread *seat[3];
    Condition *con[3];
    Condition *load;
};

#endif	/* BOAT_MONITOR_H */

