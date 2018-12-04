// ----------------------------------------------------------- 
// NAME : Brett Kriz                           User ID: BAKriz
// DUE DATE : 12/9/2016
// PROGRAM ASSIGNMENT #6
// FILE NAME : thread.cpp
// PROGRAM PURPOSE :                                           
//    To implement the thread classes
// ----------------------------------------------------------- 

#include "thread.h"
#define BUFF_SIZE 8
#define UNDEFINED -2

// ----------------------------------------------------------- 
// FUNCTION : thread
//      To construct a thread
// PARAMETER USAGE : 
//      UniqueID for mailboxs, ci & ri to identify position
// FUNCTION CALLED : 
//      setupSHM, 
// ----------------------------------------------------------- 
thread::thread(int uniqueID = -1, int ci = -1, int ri = -1)
{
    // Attach to SHM
    _SHM *zzz = new _SHM();
    D = zzz->setupSHM(false);
    
    EOD = -1;
    //done = false;
    NoMoreRows = false;
    NoMoreCols = false;
    ANS = UNDEFINED;
    LNs = 0;
    UNs = 0;
    RI = ri; // +1
    CI = ci; // +1
    
    UserDefinedThreadID = uniqueID;
    uid = uniqueID;
    // Just to be sure
    left = NULL; down = NULL; right = NULL; up = NULL;

    for (int x = 0; x < BUFF_SIZE; x++){
        LN[x] = UNDEFINED;
        UN[x] = UNDEFINED;
    }
    ThreadName.seekp(0, ios::beg);
    ThreadName << "P[" << CI << "," << RI << "]" << '\0';
}

// ----------------------------------------------------------- 
// FUNCTION : ~thread
//      To destruct the thread
// PARAMETER USAGE : 
//      
// FUNCTION CALLED : 
//      
// ----------------------------------------------------------- 
thread::~thread(){
    delete left;
    delete down;    // Needed?
    delete right;   // ^
    delete up;
}

// ----------------------------------------------------------- 
// FUNCTION : 
//      To run the primary func of the thread
// PARAMETER USAGE : 
//      
// FUNCTION CALLED : 
//      GetID, Exit, Yield, Send, Receive, IsChannelEmpty
// ----------------------------------------------------------- 
void thread::ThreadFunc(){
    Thread::ThreadFunc();
    Thread_t self = GetID();
    
    {
        int a = 7 + 20; //@@@ MEASURE ME!
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "\t\tThread %s started\n", ThreadName.str());
        cout.write(str,a);
    }
    
    bool b1,b2,b3,b4;
    b1 = left == NULL;
    b2 = right == NULL && !NoMoreCols;
    b3 = up == NULL;
    b4 = down == NULL && !NoMoreRows;
    
    if (D == NULL){
        perror("[!] DATA SEGMENT NOT ATTACHED! Aborting...");
        Exit();
    }else if(b1 || b2 || b3 || b4){
        perror("[!] Missing Mailbox(s)! Aborting...");

        int a = 30 + 20; //@@@ MEASURE ME!
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "\tRES - %s: l%d r%d u%d d%d \tBUT NC %d NR %d \n", ThreadName.str(),b1,b2,b3,b4,NoMoreCols,NoMoreRows);
        cout.write(str,a);
        
        Exit();
    }
    // Start read in
    int cur = -1337;
    int ret = -4;
    int lv, uv;
    long int q = 0;
    bool gol = true;
    bool gou = true;
    
    while(gol || gou){
        // Do Left
        if (left->IsChannelEmpty() || up->IsChannelEmpty()){
            // Well, forfit run time then
            Delay();
            continue;
        }
        // Check for Left
        cur = -1337;
        if (gol){
            ret = left->Receive(&cur, sizeof(int));
            lv = cur;
            if (cur == EOD){
                gol = false;
            }else if (cur != -1337){
                LN[LNs++] = cur;
                if (!NoMoreCols){
                    right->Send(&cur, sizeof(int));
                }
            }
        }
        
        // Check for Up
        cur = -1337;
        if (gou){
            ret = up->Receive(&cur, sizeof(int));
            uv = cur;
            if (cur == EOD){
                gou = false;
            }else if (cur != -1337){
                UN[UNs++] = cur;
                if (!NoMoreRows){
                    down->Send(&cur, sizeof(int));
                }
            }
        }
        
        if (cur == -1337){
            cout << "!\n";
            continue; // Something went wrong
        }
        
        if (lv != -1 && uv != -1){
            int a = 7 + 40 + 17; //@@@ MEASURE ME!
            char str[a];
            for (int temp = 0; temp < a; str[temp++]='\0');
            sprintf(str, "\t\tThread %s received %d from above and %d from left\n", ThreadName.str(),uv,lv);
            cout.write(str,a);
        }

        if ( (q % 450) == 0){
            // Thread is using too much runtime
            // Delay to play nicer
            Delay();
        }
        
        if (lv != -1 && uv != -1){
            int a = 7 + 40 + 15; //@@@ MEASURE ME!
            char str[a];
            for (int temp = 0; temp < a; str[temp++]='\0');
            sprintf(str, "\t\tThread %s sent %d to above and %d to right\n", ThreadName.str(),uv,lv);
            cout.write(str,a);
        }
        
        q++;
    }// END WHILE
    
    if (UNs != LNs){
        perror("[!] UNs =/= LNs!  Unexpected result! \n");
        return;
    }
    
    ANS = 0; // Show that we've progressed
    // Work with the data
    for (int z = 0; z < UNs; z++){
        cur = LN[z] * UN[z];
        ANS += cur;
    }
    
    // See if we have neighbors
    // Send neighbor data
    if (!NoMoreCols){
        right->Send(&EOD, sizeof(int));
    }
    
    if (!NoMoreRows){
        down->Send(&EOD, sizeof(int));
    }
    // Done!
    D->RESULT[CI][RI] = ANS;

    {
        int a = 7 + 45 + 17; //@@@ MEASURE ME!
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "\t\tThread %s received EOD, saved result %d and terminated\n", ThreadName.str(),ANS);
        cout.write(str,a);
    }
    
    ThrYield();
    Exit();
}

// sthread section
// ----------------------------------------------------------- 
// FUNCTION : sthread
//      To construct a start thread
// PARAMETER USAGE : 
//      uniqueID for mailbox, Index for naming, S for size,
//      isR to determine if its a Row or Column thread
// FUNCTION CALLED : 
//      setupSHM
// ----------------------------------------------------------- 
sthread::sthread(int uniqueID = -1, int I = -1, int s = 0, bool isR = false){
    _SHM *zzz = new _SHM();
    D = zzz->setupSHM(false);
    out = NULL;
    isr = isR;
    i = I;
    size = s;
    EOD = -1;
    UserDefinedThreadID = uniqueID;
    uid = uniqueID;
    out_t = NULL;

    ThreadName.seekp(0, ios::beg);
    if (isR){
        ThreadName << "Row Thread r[" << i << "]" << '\0';
    }else{
        ThreadName << "\tColumn Thread c[" << i << "]" << '\0';
    }
}

// ----------------------------------------------------------- 
// FUNCTION : ~sthread
//      To delete safely
// PARAMETER USAGE : 
//      
// FUNCTION CALLED : 
//      
// ----------------------------------------------------------- 
sthread::~sthread(){
    //delete out; // Is this even needed?
}

// ----------------------------------------------------------- 
// FUNCTION : ThreadFunc
//      To run the primary func of the thread
// PARAMETER USAGE : 
//      
// FUNCTION CALLED : 
//      GetID, Send, Yeild
// ----------------------------------------------------------- 
void sthread::ThreadFunc(){
    Thread::ThreadFunc();
    Thread_t self = GetID();
    {
        int a = 21 + 10; //@@@ MEASURE ME!
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "%s started\n", ThreadName.str());
        cout.write(str,a);
    }
    // Send tbl
    int cur = -1337;
    int ret = -4;
    for (int x = 0; x < size; x++){
        if (isr){
            // R
            cur = D->A[x][i];
        }else{
            cur = D->B[i][x];
        }
        
        ret = out->Send(&cur, sizeof(int));
        {
        int a = 21 + 22; //@@@ MEASURE ME!
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "%s sent %d to %s\n", ThreadName.str(), cur, out_t->ThreadName.str());
        cout.write(str,a);
        }
        ThrYield(); // Allow for iteration elsewhere
    }
    out->Send(&EOD, sizeof(int));
    {
        int a = 21 + 22 + 16; //@@@ MEASURE ME!
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "%s sent EOD to %s and terminated\n", ThreadName.str(), out_t->ThreadName.str());
        cout.write(str,a);
    }
    ThrYield(); // Stick around for just a bit
    //Exit() // It seems to exit by itself
}
