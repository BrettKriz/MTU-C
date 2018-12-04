// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: BAKRIZ
// DUE DATE : 10/28/2016                                       
// PROGRAM ASSIGNMENT #3                                        
// FILE NAME : thread.cpp           
// PROGRAM PURPOSE :                                           
//    Initialize thread and define its ThreadFunc()     
// ----------------------------------------------------------- 

#include <cstring>
#include <fstream> 
#include <math.h>

#include "thread.h"

using namespace std;

// ----------------------------------------------------------- 
// FUNCTION  thread:                       
//     Constructor for thread                            
// PARAMETER USAGE :                                           
//      H & I reference a point in the results table
//      l & r are pointers to other threads (UNUSED FEATURE)
// FUNCTION CALLED :                                           
//   strcpy sprintf strcat    
// ----------------------------------------------------------- 
thread::thread(int H, int I, thread *L, thread *R) : h(H),i(I),l(L),r(R) {
    // I and H are coordinates
    char b[15];
    for (int temp = 0; temp < 15; b[temp++]='\0');
    strcpy(name, "Thread(");
    sprintf(b, "%d,%d)",i,h);
    strcat(name, b);
}

// ----------------------------------------------------------- 
// FUNCTION  main:                          
//     Run the needed thread task!                      
// PARAMETER USAGE :                                           
//      K & N track requested sizes              
//      l & r are pointers to other threads (UNUSED FEATURE)
//      B is a struct that contains a massive data table for results
// FUNCTION CALLED :                                           
//      pow, sprintf, write, Begin, 
//      Join(Not truely used), Exit
// ----------------------------------------------------------- 
 void thread :: ThreadFunc(){
     // Access the array at the needed
     // Position and save a row lower
     Thread::ThreadFunc();
     {
        char str[100];
        for (int temp = 0; temp < 100; str[temp++]='\0');
        sprintf(str, "\t%s Created\n",name);
        cout.write(str,100);
     }
     // Create vars
     int gap = i-pow(2,h-1);
     bool isCopy = (gap < 0);
     int ans = -1;
     
     // Wait for needed threads (UNUSED)
     if (NULL != l){
         l->Join();
     }
     if (NULL != r){
         r->Join();
     }
     
     if (B->B[i][h-1] == -1 || B->B[gap][h-1] == -1){
         //Yield();
         Delay();
     }

     {
        char str[120];
        for (int temp = 0; temp < 120; str[temp++]='\0');
        sprintf(str, "\t%s computes X[%d] + X[%d]\n",name,i,gap);
        cout.write(str,120);
     }
     
     if (isCopy){
         // Easy!
        {
        char str[100];
        for (int temp = 0; temp < 100; str[temp++]='\0');
        sprintf(str, "\t%s copies X[%d]\n",name,i);
        cout.write(str,100);
        }
         B->B[i][h] = (B->B[i][h-1]);
         Exit();
     }
     
     // Compute the sum
     ans = ( B->B[gap][h-1] ) + ( B->B[i][h-1] );
     B->B[i][h] = ans;
     
     {
        char str[50];
        for (int temp = 0; temp < 50; str[temp++]='\0');
        sprintf(str, "\t%s exits\n\n",name);
        cout.write(str,50);
     }
     
     Exit();
 }

 

