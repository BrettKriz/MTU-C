// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: bakriz
// DUE DATE : 12/2/2016
// PROGRAM ASSIGNMENT #5
// FILE NAME : boat-monitor.cpp
// PROGRAM PURPOSE :
//    Handles the boat-monitor class
// ----------------------------------------------------------- 

#include "boat-monitor.h"

struct data;

// ----------------------------------------------------------- 
// FUNCTION : boat_monitor
//      To construct
// PARAMETER USAGE :                                           
//      Name of monitor, Number of runs
// FUNCTION CALLED :                                           
//      reset
// ----------------------------------------------------------- 
boat_monitor::boat_monitor(char *Name, int runs) : Monitor(Name, HOARE) 
{
    togo = runs;
    full = false;
    B = runs; // track max

    for(int x = 0; x < 3; x++){
        // Create Conditions
        int a = 7;
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "Seat#%d", x+1);
        con[x] = new Condition(str);
    }
    
    load = new Condition("BoatLoading");
    
    reset();
}

// ----------------------------------------------------------- 
// FUNCTION : BoatReady
//      To start the registration process
// PARAMETER USAGE :                                           
//      t the boat thread, for reference
// FUNCTION CALLED :                                           
//      BoatFull SeatFull
// ----------------------------------------------------------- 
void boat_monitor::BoatReady(thread *t){
    MonitorBegin();
    {
        int a = 28; //@@@ MEASURE ME!
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "***** The boat is ready\n");
        cout.write(str,a);
    }
    // ONLY CHANGE FLAG HERE!
    int h;
	if (!BoatFull() && !SeatFull()){
		full = false; 
    		load->Wait(); // full is true
	}
	if(!SeatFull()){
		cout.write("___\n",5);
		//t->Delay();
		reset();
		reset();
		reset();
		full = false; 
		MonitorEnd();
		BoatReady(t);
		return ;
	}
    
    // ---------------------------------------------------------------------
    bool b1,b2,b3;
    b1 = CC == 3;
    b2 = MC == 3;
    b3 = CC == 1 && MC == 2;

    {
        int a = 80; //@@@ MEASURE ME!
        int x,y,z,at;
        
        t->x = seat[0];
        t->y = seat[1];
        t->z = seat[2];
        
        x = seat[0]->num;
        y = seat[1]->num;
        z = seat[2]->num;
        at = (B-togo)+1;
        t->trips = at;
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        if (b1){
            sprintf(str, "MONITOR(%d): three cannibals (%d, %d, %d) are selected\n",at,x,y,z);
        }else if (b2){
            sprintf(str, "MONITOR(%d): three missionaries  (%d, %d, %d) are selected\n",at,x,y,z);
        }else if (b3){
            if (!seat[0]->isCannibal){
                // Fix the order lol
                if (seat[1]->isCannibal){
                    x = seat[1]->num;
                    y = seat[0]->num;
                }else if (seat[2]->isCannibal){
                    x = seat[2]->num;
                    z = seat[0]->num;
                }else{
                    perror("\n\n\n\n[!] SEAT CASE FAILED! Cant find cannibals!!\n\n");
                }
            }
            sprintf(str, "MONITOR(%d): one cannibal (%d) and two missionaries (%d, %d) are selected\n",at,x,y,z);
        }
        cout.write(str,a);
    }
    MonitorEnd();
};

// ----------------------------------------------------------- 
// FUNCTION : BoatDone
//      To prep for the boats return
// PARAMETER USAGE :                                           
//      t, the boat thread
// FUNCTION CALLED :                                           
//      reset
// ----------------------------------------------------------- 
void boat_monitor::BoatDone(thread *t){
    MonitorBegin();
    togo--;
    if (togo <= 0){
        // Done
        // End somehow here.
        MonitorEnd();
        reset();
        cout << endl << "MONITOR: " << B << " crosses have been made." << endl;
        cout << "MONITOR: This river cross is closed indefinitely for renovation.";
        cout << endl << endl;
        t->Exit();
    }
    	reset();
	reset();
    MonitorEnd();
};

// ----------------------------------------------------------- 
// FUNCTION : BoatFull
//      To determine if the boat is full
// PARAMETER USAGE :                                           
//      
// FUNCTION CALLED :                                           
//      
// ----------------------------------------------------------- 
bool boat_monitor::BoatFull(){
    bool ans = !con[0]->IsEmpty() && !con[1]->IsEmpty() && !con[2]->IsEmpty();
    return ans;
}

// ----------------------------------------------------------- 
// FUNCTION : SeatFull
//      To determine if the seats are full
// PARAMETER USAGE :                                           
//      
// FUNCTION CALLED :                                           
//      
// ----------------------------------------------------------- 
bool boat_monitor::SeatFull(){
bool ans = seat[0] != NULL && seat[1] != NULL && seat[2] != NULL;
    return ans;	
}

// ----------------------------------------------------------- 
// FUNCTION : CannibalArrives
//      To register a cannibal
// PARAMETER USAGE :                                           
//      t, the thread
// FUNCTION CALLED :                                           
//      
// ----------------------------------------------------------- 
bool boat_monitor::CannibalArrives(thread *t){
    MonitorBegin();
    if (full ) { load->Signal(); MonitorEnd();  t->Delay();  return false;} 
    {
        int a = NAME_SIZE + 9; //@@@ MEASURE ME!
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "%s%s arrives\n", t->Space->str(), t->ThreadName.str());
        cout.write(str,a);
    }
    
    for (int x = 0; x < 3; x++){
        bool arg = seat[x] == NULL && con[x]->IsEmpty();
        
        if (arg){
            CC++;
            int z = CC+MC;
            seat[x] = t; // Check reference style here!
            if (z > 3){
                perror("[!] CC + MC > 3! Thats not supposed to happen!\n\n\n\n");
            }else if (z == 3){
                full = true;
                
                load->Signal();
                con[x]->Wait();
                MonitorEnd();
		//cout.write("Cannibal Resumed!\n",19);
		t->Delay();
                return true;
            }

            con[x]->Wait();
            //cout.write("Cannibal Resumed!\n",19);
            MonitorEnd();
            return true;
        }
    }
    if (BoatFull() && !load->IsEmpty()){
        // Something slipped
        load->Signal();
    }

    MonitorEnd();
    return false;
};

// ----------------------------------------------------------- 
// FUNCTION : MissonaryArrives
//      To regiser a missionary
// PARAMETER USAGE :                                           
//      
// FUNCTION CALLED :                                           
//      
// ----------------------------------------------------------- 
bool boat_monitor::MissonaryArrives(thread *t){
    MonitorBegin(); 
    if (full  || CC == 2) { load->Signal(); MonitorEnd(); t->Delay(); return false;}  //sig? load->Signal();
    
    {
        int a = NAME_SIZE + 9; //@@@ MEASURE ME!
        char str[a];
        for (int temp = 0; temp < a; str[temp++]='\0');
        sprintf(str, "%s%s arrives\n", t->Space->str(), t->ThreadName.str());
        cout.write(str,a);
    }
    
    for (int x = 0; x < 3; x++){
        bool arg = seat[x] == NULL && con[x]->IsEmpty();
        if (arg){
            MC++;
            int z = CC+MC;
            seat[x] = t; // Check reference style here!

            if (z > 3){
                perror("[!] CC + MC > 3! Thats not supposed to happen!\n\n\n\n");
            }else if (z == 3){
                full = true;
 
                load->Signal();
                con[x]->Wait();
                MonitorEnd();
		//cout.write("Missionary Resumed!\n",20);
		t->Delay();
                return true;
            }

            con[x]->Wait();
            //cout.write("Missionary Resumed!\n",20);
            MonitorEnd();
            return true;
        }
    }
    if (BoatFull() && !load->IsEmpty()){
        // Something slipped
        load->Signal();
    }

    MonitorEnd();
    return false;
};

// ----------------------------------------------------------- 
// FUNCTION : reset
//      To reset the boats data
// PARAMETER USAGE :                                           
//      
// FUNCTION CALLED :                                           
//      
// ----------------------------------------------------------- 
void boat_monitor::reset(){
    //MonitorBegin();
    MC = 0;
    CC = 0;
    for (int x = 0; x < 3; x++){
        seat[x] = NULL;
        con[x]->Signal();
    }
    //MonitorEnd();
};
