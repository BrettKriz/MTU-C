// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: BAKriz
// DUE DATE : 11/11/16                                       
// PROGRAM ASSIGNMENT #4                                        
// FILE NAME : thread-main.cpp        
// PROGRAM PURPOSE :                                           
//    To feed baby eagles until they are strong enough to fly!   
// ----------------------------------------------------------- 

#ifndef MOTHEREAGLE_H
#define	MOTHEREAGLE_H

#include <stdio.h>
#include <string>		// String Stuff
#include <cstring>		// Maniplulation of strings
#include <iostream>		// Input\Output
#include <iomanip>		// Manipulation of I/O

#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>            // Because forget conveinence!
#include <sys/stat.h>

#include "ThreadClass.h"        // Get ThreadMentor
#include "P4ToolKit.h"

struct pot; struct TSSig; struct data;

class MotherEagle : public Thread {
public:
    int id;
    char ThreadName[50];
    bool go;
    data *Data;
    MotherEagle(int ID); // , data *d
    MotherEagle(const MotherEagle& orig);
private:
    void ThreadFunc();
    void Food_Ready();
    void Goto_Sleep();
};

#endif	/* MOTHEREAGLE_H */

