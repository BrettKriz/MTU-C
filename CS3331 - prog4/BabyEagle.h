// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: BAKriz
// DUE DATE : 11/11/16                                       
// PROGRAM ASSIGNMENT #4                                        
// FILE NAME : thread-main.cpp        
// PROGRAM PURPOSE :                                           
//    To eat until I am strong enough to fly!    
// ----------------------------------------------------------- 

#ifndef BABYEAGLE_H
#define	BABYEAGLE_H

#include <stdio.h>
#include <string>		// String Stuff
#include <cstring>		// Maniplulation of strings
//#include <iostream>		// Input\Output
#include <iomanip>		// Manipulation of I/O

#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>            // Because forget conveinence!
#include <sys/stat.h>

#include "ThreadClass.h"        // Get ThreadMentor
#include "P4ToolKit.h"

class BabyEagle : public Thread {
public:
    int id;
    data *Data;
    bool init;
    char ThreadName[50];
    bool fed;
    char spc[20];
    BabyEagle(int ID); // , data *d
    BabyEagle(const BabyEagle& orig);
    
private:
    void ThreadFunc();
    void Ready_to_Eat();
    void Finish_Eating();
};

#endif	/* BABYEAGLE_H */

