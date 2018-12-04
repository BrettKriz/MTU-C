// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: BAKRIZ
// DUE DATE : 10/28/2016                                       
// PROGRAM ASSIGNMENT #3                                        
// FILE NAME : thread.h           
// PROGRAM PURPOSE :                                           
//    Define structures and data for thread class
//      and data struct
// ----------------------------------------------------------- 

#ifndef THREAD_H
#define	THREAD_H
#include "ThreadClass.h"

using namespace std;

struct data{
        // I   H
    int B[5000][13];
};

// ----------------------------------------------------------- 
// FUNCTION  thread:                          
//     Declares class thread from Thread                            
// PARAMETER USAGE :                                           
//      i & h are coordiates on the result table
//      iS & hS are max sizes for coordiates
//      B points to the struct of the result table
// FUNCTION CALLED :                                           
//    thread() ThreadFunc()         
// ----------------------------------------------------------- 
class thread : public Thread {
public:
    char name[50];
    int i, h;
    int iS, hS;
    data *B;
    thread *l, *r;
    thread(int H, int I, thread *L, thread *R);
    thread();

private:
    void ThreadFunc();
};

#endif	/* THREAD_H */

