// ----------------------------------------------------------- 
// NAME : Brett Kriz                         User ID: bakriz 
// DUE DATE : 09/23/2016                                       
// PROGRAM ASSIGNMENT 1                                        
// FILE NAME : prog1.c            
// PROGRAM PURPOSE :                                           
//    To test general concepts of forking
//    Multiple times to make functions run
//    In a concurrent manor.
// ----------------------------------------------------------- 

#include <stdio.h>
#include <stdlib.h>

#include <time.h>
#include <math.h>
#include <stdbool.h>
#include <stdarg.h>

#include <dirent.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <dirent.h>
#include <locale.h>

float curve(int s);
float buffon(int r);

float M_PI ;
float M_PI2;

void p1();
void p2();
void p3();
void p4();

int tryFork(int index);
bool forkChild(char* prog, char** args, int in, int out);
long int fib(int n);
long int fib_r(int n);

bool buffon_r();
float buffon(int s);
float curve(int r);
int max (int *tbl, int size, int i, int j, int k);
void downheap (int *tbl, int size, int i);
void heapsort (int *tbl, int size);


int M; // sort m random ints
int N; // nth fib
int R; // r needle throws
int S; // s points picked

/* FUNCTION main
 *      Start the processes here
 * PARAMETER USAGE : 
 *      argc: number of arguments
 *      argv: The list of arguments
 * FUNCTION CALLED :
 *      tryFork()
 */
int main(int argc, char** argv) {
    M_PI = acos(-1.0);
    M_PI2 = (2.0*M_PI);
    printf("Main Process Started\n");
    if (argc < 5){
        // Somethings wrong
        printf("Main Process Exits\n");
        perror("Not enough arguments! Exiting...");
        return (EXIT_FAILURE);
    }
    
    srand(time(NULL)); // Dont do it in a for loop
    
    int uniqueID = 0;
    // All numbers provided are correct
    M = atoi(argv[1]); // sort m random ints
    N = atoi(argv[2]); // nth fib
    R = atoi(argv[3]); // r needle throws
    S = atoi(argv[4]); // s points picked
    
    printf("Number of Random Numbers\t= %d\n",M);
    printf("Fibonacci Input\t\t\t= %d\n",N);
    printf("Buffon's Needle Iterations\t= %d\n",R);
    printf("Integration Iterations\t\t= %d\n",S);
    
    // Check our arguments and start splitting
    int a,b,c,d;
    a = tryFork(1);
    b = tryFork(2);
    c = tryFork(3);
    d = tryFork(4);
    // Now we wait if we are FALSE
    /*
    if (a == false) {wait();}
    if (b == false) {wait();}
    if (c == false) {wait();}
    if (d == false) {wait();}
     * */
    printf("Main Process Waits\n");
    int status;
    
    pid_t wait1 = wait(&status);
    //printf("1,");
    pid_t wait2 = wait(&status);
    //printf("2,");
    pid_t wait3 = wait(&status);
    //printf("3,");
    pid_t wait4 = wait(&status);
    //printf("4\n\n");
    
    //printf("IDS: %d %d %d %d \n",wait1,wait2,wait3,wait4);
    printf("Main Process Exits\n");
    return (EXIT_SUCCESS);
}

/**
 * FUNCTION p1
 * Handles all tasks for a child program
 *  Attempting to use quicksort
 * PARAMETER USAGE :
 * 
 * FUNCTION CALLED :
 *  malloc
 *  free
 *  heapsort
 */
void p1(){
    // Heap sort
    printf("Heap Sort Process Created\n");
    int *tbl = malloc( sizeof(int) * M);
    
    for (int i = 0; i < M; i++){
        int cur = rand() % 100;
        //printf(" %d",cur);
        tbl[i] = cur;
    }
    printf("   Heap Sort Process Started\n");
    // Show cards
    printf("   Random Numbers Generated:\n   ");
    for (int i = 0; i < M; i++)
        printf("%4d%s", tbl[i], (i == M - 1) || (i != 0 && (i % 20 == 0)) ? "\n   " : "   ");
    
    heapsort(tbl, M); 
    //printf("\n +++++++++++++++++++++++++++++++");
    printf("   Sorted Sequence:\n   ");
     for (int i = 0; i < M; i++)
        printf("%4d%s", tbl[i], (i == M - 1) || (i != 0 && (i % 20 == 0)) ? "\n   " : "   ");
    
    printf("   Heap Sort Process Exits\n");
    //printf("\n\n");
    free(tbl);
    
}

/**
 * FUNCTION 
 * Handles all tasks for a child program
 *  Attempting to 
 * PARAMETER USAGE :
 * 
 * FUNCTION CALLED :
 */
void p2(){
    // Fibonacci
    printf("Fibonacci Process Created\n");
    fib(N);
}

/**
 * FUNCTION 
 * Handles all tasks for a child program
 *  Attempting to 
 * PARAMETER USAGE :
 * 
 * FUNCTION CALLED :
 */
void p3(){
    // Buffon
    printf("Buffon's Needle Process Created\n");
    buffon(R);
}

/**
 * FUNCTION p4 :
 *  Handles all tasks for a child program
 *  Attempting to 
 * PARAMETER USAGE :
 * 
 * FUNCTION CALLED :
 */
void p4(){
    // Curve Area
    printf("Integration Process Created\n");
    curve(S);
    
}

/**
 * FUNCTION tryFork
 *  Attempts to fork the program and guide
 *  The child process to the correct function
 *  Based on the index
 * PARAMETER USAGE :
 *  index : Points to the correct function
 * FUNCTION CALLED :
 *  p1
 *  p2
 *  p3
 *  p4
 *  fork
 */
int tryFork(int index){
    pid_t ID;
    ID = fork();
    
    if (ID < 0){
        // Failed
        printf("[!] Fork failed! Aborting!\n");
        return -1;
    }else if (ID == 0){
        // CHILD
        // RUN the requested procedure
        
        switch(index){
            case 1 : p1(); exit(EXIT_SUCCESS);
            case 2 : p2(); exit(EXIT_SUCCESS);
            case 3 : p3(); exit(EXIT_SUCCESS);
            case 4 : p4(); exit(EXIT_SUCCESS);
            default : printf("[!] Bad index sent! %d \n", index);
        }
        
        return false;
    }else if (ID > 0){
/*
        int cstat = 0;
        pid_t ret  = wait(&cstat);
        if (ret == -1){
            // Problem!
            printf("[!] Problem detected while waiting for %s ! Dropping... \n",prog);
            return false;
        }  
        */
    }
    return true;
}

/**
 * FUNCTION fib :
 *  Starts the recursive call to determine
 *  the nth Fibonacci number
 * PARAMETER USAGE :
 *  n : Base number which we the fibonacci number of
 * FUNCTION CALLED :
 *  fib_r
 */
long int fib(int n){
    // Call a recursive func
    // N times for the fibonacci
    printf("      Fibonacci Process Started\n");
    printf("      Input Number %d\n",n);
    
    long int ans = 0;
    ans = fib_r( n );
    
    printf("      Fibonacci Number f(%d) is %ld\n",n,ans);
    printf("      Fibonacci Process Exits\n");
    return ans;
}

/**
 * FUNCTION fib_r :
 *  A recursive call to calculate a part of
 *  the nth fibonacci number
 * PARAMETER USAGE :
 *  n : the current variable size from the base function
 * FUNCTION CALLED :
 *  fib_r
 */
long int fib_r(int n){
    long int ans = -1;
    // Check the base case's
    if ( n == 0 )
      ans = 0;
   else if ( n == 1 )
      ans = 1;
   else
      ans = ( fib_r(n-1) + fib_r(n-2) );
    // Send it out;

    return ans;
}

/**
 * FUNCTION buffon_r :
 *  Calculates a trial of buffons needle problem
 *  and returns if it crossed a line or not
 * PARAMETER USAGE :
 *  
 * FUNCTION CALLED :
 *  sin
 *  rand
 */
bool buffon_r(){
    // Setup values
    float L = 1;
    float G = 1;    
    float d = -1;
    float a = -1; // angle
    // Grab random numbers to use
    d = ((float)rand() / (float)(RAND_MAX)) * 0.9999998;
    a = ((float)rand() / (float)(RAND_MAX)) * M_PI2-0.00001;
    
    // Compute the answer
    float ans = (d+(L*sin(a)));
    bool b1 = (ans < 0);
    bool b2 =  (ans > G);

    return (b1 || b2);
}

/**
 * FUNCTION buffon :
 *  Run r simulations of the buffon needle problem
 *  and return the rate of crossed needles
 * PARAMETER USAGE :
 *  r : Number of simulations to run
 * FUNCTION CALLED :
 *  buffon_r
 */
float buffon(int r){
    
    if (r < 1){return 0.0;}
    // Run r times
    // Return the rate
    float rate = 0.0;
    int t = 0; // Times true
    printf("         Buffon's Needle Process Started\n");
    printf("         Input Number %d",r);
    
    // Run r trials of buffon and count wins
    for (int i = 0; i < r; i++){
        // Count
        if (buffon_r(i) == true){
            t++;
        }
    }
    // Create the rate
    rate = ((float)t)/(float)r;
    //printf("\n\n");
    printf("         Estimated Probability is %5f\n",rate);
    printf("         Buffon's Needle Proess Exits\n");
    
    return rate;
}

/**
 * FUNCTION curve :
 *  Determine how many of s random points
 *  fall under the area of a sin(x) curve
 * PARAMETER USAGE :
 *  s : The number of random points to test
 * FUNCTION CALLED :
 *  rand
 *  sin
 */
float curve(int s){
    printf("            Integration Process Started\n");
    printf("            Input Number %d\n");
    if (s < 1){
        printf("            Total Hit 0\n");
        printf("            Estimated Area is 1\n");
        printf("            Integration Process Exits\n");
        return 0.0;
    }
    float ans = 0.0;
    int t = 0; // Point was in area between
    // Other variables
    
    // Start simulating points
    for (int x = 0; x < s; x++){
        float a = -1;
        float b = -1;
        // Confirm range
        //while( fmod(a = rand() , M_PI+1) == 0 );
        //while( (b = rand() % 2)    == 0 );
        
        a = ((float)rand() / (float)(RAND_MAX)) * (M_PI - 0.00001);
        b = ((float)rand() / (float)(RAND_MAX)) * 0.9999998;
        // (a,b) Is in area
        // Between sin(x) and
        // the x axis?
        if (b <= sin(a)){
            t++;
        }
    }
    // Now that were done, do a count
    ans = (t/(float)s) * M_PI;
    printf("            Total Hit %d\n",t);
    printf("            Estimated Area is %8f\n",ans);
    printf("            Integration Process Exits\n");
    return ans;
}

/**
 * FUNCTION max : 
 *  Determines which way to move based on
 *  the value at index i (vs index j or k)
 * PARAMETER USAGE :
 *  tbl[] : The table of values 
 *  size : The size of said table
 *  i : index in question
 *  j : leaf index 1
 *  k : leaf index 2
 * FUNCTION CALLED :
 * 
 */
int max (int *tbl, int size, int i, int j, int k) {
    int m = i;
    if (j < size && tbl[j] > tbl[m]) {
        m = j;
    }
    if (k < size && tbl[k] > tbl[m]) {
        m = k;
    }
    return m;
}

/**
 * FUNCTION downheap :
 *  Move a position in the tbl tree from i
 * PARAMETER USAGE :
 *  tbl[] : The table of values 
 *  size : The size of said table
 *  i : the index to move 
 * FUNCTION CALLED :
 *  max
 * 
 */
void downheap (int *tbl, int size, int i) {
    while (true) {
        // find the larger item
        int j = max(tbl, size, i, 2*i + 1, 2*i + 2);
        // Did we reach the index?
        if (j == i) {
            break;
        }
        // Swap the items
        int t = tbl[i];
        tbl[i] = tbl[j];
        tbl[j] = t;
        i = j;
        // Repeat
    }
}
 
/**
 * FUNCTION heapsort :
 *  Sorts a table using a tree/heap structure
 * PARAMETER USAGE :
 *  tbl[] : The table of values to sort
 *  size : The size of said table
 * FUNCTION CALLED :
 *  downheap
 * 
 */
void heapsort (int *tbl, int size) {
    int i;
    // Start the sort near the halfway
    // And work down to index 0
    for (i = (size - 2) / 2; i >= 0; i--) {
        downheap(tbl, size, i);
    }
    for (i = 0; i < size; i++) {
        // Use a temp to swap places
        // With top of the tree
        int t = tbl[size - i - 1];
        tbl[size - i - 1] = tbl[0];
        tbl[0] = t;
        // And then move the position down
        // to a leaf
        downheap(tbl, size - i - 1, 0);
    }
}