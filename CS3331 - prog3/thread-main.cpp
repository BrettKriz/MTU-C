// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: BAKRIZ
// DUE DATE : 10/28/2016                                       
// PROGRAM ASSIGNMENT #3                                        
// FILE NAME : thread-main.cpp           
// PROGRAM PURPOSE :                                           
//    Create and manage multiple threads        
// ----------------------------------------------------------- 

#include <cstdlib>
#include <cstring>

#include <iostream>
#include <math.h>
#include <stdio.h>

#include "thread.h"

using namespace std;

// 2d array for intermediate results
// one of k+1 rows and N columns
// B[0,*] Is of input elements
// B[0,i] = Xi for i = (0,1,2..,n-1)
// In H 'runs' (ROW ITERATIONS), (B[H,i]) N threads are created
// - Ti computes and stores in B[H,i] & exits
// 

// B[k,n]
struct data;

data B;

static int K_MAX = 13;
static int N_MAX = 5000;

// ----------------------------------------------------------- 
// FUNCTION  main:                          
//     Run the main task on startup                          
// PARAMETER USAGE :                                           
//      K & N track requested sizes              
//      X[] temporarly holds the incoming data
//      Xs tracks X's size
//      B is a struct that contains a massive data table for results
// FUNCTION CALLED :                                           
//    log2, pow, sprintf, write, Begin, Join         
// ----------------------------------------------------------- 
int main(int argc, char** argv) {
    cout << endl << "Concurrent Prefix Sum Computation" << endl << endl;
    // Create vars and read from stdin

    int N = -1;
    int K = -1;
    int X[N_MAX];
    int Xs = 1; // track size
    
     // Enter a scope
    cin >> N;
    K = log2(N) + 1; // base 2
    cout << "Number of input data = " << N << endl << "Input array:" << endl;
    
    for (int h = 0; h < K; h++){
        for (int x = 0; x < N; x++){
            B.B[x][h] = -1;
        }
    }
    
    for (int x = 0; x < N; x++){
        // Read in
        cin >> X[x];
        cout << X[x] << "\t";
        B.B[x][0] = X[x];
        Xs++;
    }
    cout << endl << endl;
    
    for (int h = 0; h < K; h++){
        for (int x = 0; x < N; x++){
            cout << B.B[x][h] << "\t";
        }
        cout << endl;
    }

    for (int k = 1; k < K; k++){
        int gap = pow(2,k-1);
        {
        char str[25];
        for (int temp = 0; temp < 25; str[temp++]='\0');
        sprintf(str, "Run %d:\n",k);
        cout.write(str,25);
        }
        for (int x = 0; x < N; x++){
            
            thread *cur = new thread(k,x,NULL,NULL); // &l,&r
            cur->B = &B;
            // FTW you cant properly reference a 2D array
            // Unless its in a stuct, then suddenly thats fine
            cur->hS = K;
            cur->iS = N;
            cur->Begin(); 

            if (x+1 == N){
                cur->Join();
                cout << "Result after run " << k << ":" << endl;
                for (int y = 0; y < N; y++){
                    cout << "\t" << B.B[y][k];
                }
                cout << endl;
            }else{
                
            }
        }
    }
    
    for (int h = 0; h < K; h++){
        for (int x = 0; x < N; x++){
            int cur = B.B[x][h];
            // Make sure everyones done
            while (cur == -1){sleep(1); cur = B.B[x][h]; }
        }
    }
    
    cout << endl << "Final result after run K: " << endl;

    for (int h = 0; h < K; h++){
        for (int x = 0; x < N; x++){
            int cur = B.B[x][h];
            // Make sure everyones done
            while (cur == -1){sleep(1); cur = B.B[x][h]; }
            
            cout << "\t" << cur;
        }
        cout << endl;
    }
    cout << endl << endl;
    return 0;
}
