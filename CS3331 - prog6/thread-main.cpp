// ----------------------------------------------------------- 
// NAME : Brett Kriz                           User ID: BAKriz
// DUE DATE : 12/9/2016
// PROGRAM ASSIGNMENT #6
// FILE NAME : thread-main.cpp
// PROGRAM PURPOSE :                                           
//    To execute the program logic
// ----------------------------------------------------------- 

#include <cstdlib>
#include <iostream>
#include <string>
#include <fstream>
#include <strstream>		//FILE I/O header file	

#include "thread.h"
#include "P6ToolKit.h"
#include "ThreadClass.h"        // Get ThreadMentor

using namespace std;

static const int BUFF_SIZE = 8;
static const int EOD = -1;
static const int UNDEFINED = -2;

// ----------------------------------------------------------- 
// FUNCTION : main
//      To run the primary code path of the program
// PARAMETER USAGE : 
//      (Not actually used)
// FUNCTION CALLED : 
//      setupSHM, sync/flush, Join, Begin
// ----------------------------------------------------------- 
int main(int argc, char** argv) {

    int tbl_s = BUFF_SIZE;
    // Create SHM for C matrix
    _SHM *zzz = new _SHM();
    data *D = NULL;
    D = zzz->setupSHM(true);

    // R,C
    int l,m, u,v;
    int Rv,Cv;
    int UID = 0;
    // Begin processing ````````````````````````````````````````````````````````
    // Start reading in A
    cin >> l >> m;
    // fill vars
    tbl_s = m;
    D->size = m;
    Rv = l;
    Cv = m;
    
    cout << "\nMatrix A: " << Rv << " rows and " << Cv << " columns" << endl;
    for (int r = 0; r < Rv; r++){
        // Read row
        for (int c = 0; c < Cv; c++){
            // Read col
            int temp;
            cin >> temp;
            D->A[c][r] = temp;
            
            cout << temp << '\t';
            if (c+1 == Cv){
                cout << endl;
            }
        }
    }
    // Start reading in B
    cin >> u >> v;
    if (m != u){
        // Cant multiply the matrices
        // Abort
        cout << endl << "[i] M =/= U!  Multiplication cannot be done!" << endl;
        cout << endl << "\tProgram complete!" << endl;
        return 0;
    }
    // Continue read in
    Rv = u;
    Cv = v;
    cout << "\nMatrix B: " << Rv << " rows and " << Cv << " columns" << endl;
    for (int r = 0; r < Rv; r++){
        // Read row
        for (int c = 0; c < Cv; c++){
            // Read col
            int temp;
            cin >> temp;
            D->B[c][r] = temp;
            
            cout << temp << '\t';
            if (c+1 == Cv){
                cout << endl;
            }
        }
    }
    cout << endl << endl;
    
    // Create threads
    for (int y = 0; y < l; y++){
        for (int x = 0; x < v; x++){
            thread *temp = new thread(UID,x,y);
            D->tbl[x][y] = temp;
            UID++;
            
            // Mark ends
            bool b1 =   y+1 == l;
            bool b2 =   x+1 == v;
            if (b1){
                D->tbl[x][y]->NoMoreRows = true;
            }
            if (b2){
                D->tbl[x][y]->NoMoreCols = true;
            }
        }
    }

    // Create sthreads R = A, C = B
    for (int x = 0; x < l; x++){
        // R = A, C = B
        D->R[x] = new sthread(UID++, x, m, true);
    }

    for (int x = 0; x < v; x++){
        D->C[x] = new sthread(UID++, x, u, false);
    }

    for (int y = 0; y < l; y++){
        for (int x = 0; x < v; x++){
            thread *at = D->tbl[x][y];
            // Intermix threads to setup mailboxes
            // Consider y cases
            if (y == 0){
                // row is zero
                sthread *o = D->C[x];

                strstream *str = new strstream();
                (*str) << "UDS" << o->i << "-" << at->ThreadName.str();
                (*str) << '\0'; // "UDS"
                str->sync();
                // Not actually bi-directional...                           sender  ,  receiver
                SynOneToOneChannel *temp = new SynOneToOneChannel(str->str(), o->uid, at->uid  );
                at->up = temp ;
                o->out = temp;
                o->out_t = at;
            }else{
                // Grab from prev row
                thread *o = D->tbl[x][y-1];
                
                strstream *str = new strstream();
                (*str) << "UD" << o->ThreadName.str() << "-" << at->ThreadName.str();
                (*str) << '\0';
                str->sync();
                int t1,t2;
                t1 = o->uid;
                t2 = at->uid;
                
                SynOneToOneChannel *temp = new SynOneToOneChannel(str->str(), t1, t2); // "UD"
                at->up = temp;
                o->down = temp;
            }
            // Consider x cases
            if (x == 0){
                // Row is zero
                sthread *o = D->R[y];

                strstream *str = new strstream();
                (*str) << "LRS" << o->i << "-" << at->ThreadName.str();
                (*str) << '\0';
                str->sync();
                
                SynOneToOneChannel *temp = new SynOneToOneChannel(str->str(),o->uid, at->uid ); // "LRS"
                at->left = temp;
                o->out = temp;
                o->out_t = at;
            }else{
                // Grab from prev row
                thread *o = D->tbl[x-1][y];
                
                strstream *str = new strstream();
                (*str) << "LR" << o->ThreadName.str() << "-" << at->ThreadName.str();
                (*str) << '\0';
                str->sync();
                
                int t1,t2;
                t1 = o->uid;
                t2 = at->uid;
                
                SynOneToOneChannel *temp = new SynOneToOneChannel(str->str(), t1, t2); // "LR"
                at->left = temp;
                o->right = temp;
            }
        }
    }

    // Start sthreads
    for (int z = 0; z < l; z++){
        D->R[z]->Begin();
    }
    for (int z = 0; z < v; z++){
        D->C[z]->Begin();
    }

    // Start threads
    for (int y = 0; y < l; y++){
        for (int x = 0; x < v; x++){
            D->tbl[x][y]->Begin();
        }
    }
    // Assure all threads are done
    D->tbl[v-1][l-1]->Join(); // Ending corner
    for (int y = 0; y < l; y++){
        for (int x = 0; x < v; x++){
            if (x != v-1 && y != l-1){ // If its the one we just joined, dont
                D->tbl[x][y]->Join(); // Make sure everything is done
            }
        }
    }
    cout.flush();
    
    // Print out C 
    cout << "\nMatrix C = A*B: " << l << " rows and " << v << " columns" << endl;
    for (int y = 0; y < l; y++){
        for (int x = 0; x < v; x++){
            
            cout << D->RESULT[x][y] << '\t';
            if (x+1 == v){
                cout << endl;
            }
        }
    }
    cout << endl << endl;
    
    // Record results
    if (shmctl(D->shm_id, IPC_RMID, NULL) == -1) {
        perror("\n[!] INTERNAL ERROR! shmctl failed to delete shared memory segment!\n\n\n");
        exit(EXIT_FAILURE);
    }
    
    // Detach and kill our SHM
    if (D->shm_id != NULL){
        shmctl(D->shm_id, IPC_RMID, NULL );
    }
    shmdt(D);
    
    cout << "\n\tProgram complete!\n";
    return 0;
}

