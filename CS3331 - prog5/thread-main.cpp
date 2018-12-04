// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: bakriz
// DUE DATE : 12/2/2016
// PROGRAM ASSIGNMENT #5
// FILE NAME : thread-main.cpp
// PROGRAM PURPOSE :                                           
//    Handles the primary code paths of the project
// ----------------------------------------------------------- 

#include <cstdlib>
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

using namespace std;

// ----------------------------------------------------------- 
// FUNCTION : main
//      To load and run the prog
// PARAMETER USAGE :                                           
//      Integer sizes for threads
// FUNCTION CALLED :                                           
//      setupSHM atoi
// ----------------------------------------------------------- 
int main(int argc, char** argv) {
    // Make sure we can run correctly
    if (argc < 4){
        cout << "\t[!] NOT ENOUGH ARGUMENTS!" << endl << endl;
        cout << "\tExpected Format:" << endl << endl;
        cout << "\tC M B" << endl;
        cout << "\tC Cannibals" << endl;
        cout << "\tM Missionaries" << endl;
        cout << "\tB # of Boat trips" << endl;
        cout << "\t0's will trigger default values of 8" << endl;
        cout << "\tor 5 for B!" << endl << endl;
        return 1;
    }else if (argc > 4){
        cout << "\t[i] UNEXPECTED ARGUMENTS! " << argc-1 << " found! ";
        cout << "(Only needed 3)\n\tIgnoring..." << endl << endl;
        return 0;
    }
    
    int c,m,b;
    c = atoi(argv[1]);
    m = atoi(argv[2]);
    b = atoi(argv[3]);
    
    if (c <= 0){ c = 8; }
    if (m <= 0){ m = 8; }
    if (b <= 0){ b = 5; }
    // Check for base cases
    bool b1 = c+m < 3;
    bool b2 = m < 2;
    
    if (b1 || b2){
        // Failure case
        cout << endl << endl;
        cout << "\t The only missionary is eaten by a cannibal on arrival!" << endl;
        cout << "\t Only a fool would mission alone..." << endl << endl;
        cout << "\t Program Done! (Base Case Found)!" << endl;
        cout << endl;
    }else{
        cout << endl;
        cout << "\tCANNIBALS:\t" << c << endl;
        cout << "\tMISSIONARIES:\t" << m << endl;
        cout << "\tBoat Trips:\t" << b << endl;
        cout << endl;
    }
    // Prepare for run
    _SHM *zzz = new _SHM();
    data *D = zzz->setupSHM(true);
    // Initialize the data space
    D->init(c,m,b);
    D->shm_id = zzz->shm_id;
    
    // Make data
    D->BOAT_M = new boat_monitor("BOAT", b);
    D->Boat = new thread(D, 2, -1);

    for (int x = 0; x < m; x++){
        // MISSIONARY
        thread *temp = new thread(D, 1, x+1);
        D->MT[x] = temp;
    }

    for (int x = 0; x < c; x++){
        // CANNIBAL
        thread *temp = new thread(D, 0, x+1);
        D->CT[x] = temp;
    }
    
    // Start everything
    D->Boat->Begin();
    for (int x = 0; x < m; x++){
        D->MT[x]->Begin();
    }
    
    for (int x = 0; x < c; x++){
        D->CT[x]->Begin();
    }
    D->Boat->Join(); // Exit gracefully
    
    // prolly going to just force exit
    return 0;
}

