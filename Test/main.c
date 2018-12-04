/* 
 * File:   main.c
 * Author: Brett Kriz
 *
 * Created on September 29, 2016, 1:17 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <math.h>
#include <ctype.h>
#include <unistd.h> // EXEC

#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/stat.h>

int a[]= {1,3,5,7,8};
int b[]= {2,4,6,9,12};

static int a2[5];
static int b2[5];

int c[10];
pid_t me;

void test1();
int bs (int s, int target, int index);
void doBS();


void doBS(){
    // Fork off #X forks to binary search
    printf("Starting %d \n",me);

    for (int i = 0; i < 5; i++){
        int curx = a[i];
        // Fork and search for index
        int id = fork();
        if (id == 0){
            // Child
            //index = i;
            int t = bs( 5, curx, i );
           printf(">@ %d\t%d ->i: %d \n",i,curx,t);
            a2[i] = t;
            
            exit(EXIT_SUCCESS);
        }else{
            wait();
        }
        
    }
    printf("DONE! %d \n\n", getpid());
}


int bs (int s, int target, int index) {
    int left = 0, right = s - 1;
    int center;
    
    while (left <= right) {
        center = (left + right) / 2;
        // Compare 
        if (b[center] == target) {
            perror("[!] NOTICE: Binary Search found equal index somehow...");
            return center+1; // Thats odd...
        } else if (b[center] < target) {
            left = center + 1;
        } else {
            right = center - 1;
        }
    }
    
    if (left == right && left == 0 && target < b[0]){
        return index;
    }else if(left == right && left == s-1 && target > b[s-1]){
        return index + s;
    }
    
    return (target > b[left]) ? (left + 1) : left;
}

/*
 * 
 */
int main(int argc, char** argv) {
    me = getpid();
    doBS();
    
    if (me != getpid()){
        return (1);
    }
    
    for (int x = 0; x < 5; x++){
        printf("@%d\t. %d .\n",x,a2[x]);
        
    }
    printf("\n\n\n");
    for (int x = 0; x < 5; x++){
        int at = a2[x];
        int cont = c[at];
        int extra = 0;
        if (cont != 0)
            extra = 1;
        
        c[at] = a[x+extra];
        printf("@%d\tA %d ->pos: %d\tEX: %d At: %d \n",x,a[x],a2[x],x+extra,at);
    }
    printf("\n\n");
    for (int x = 0; x < 5; x++){
        printf("%d ",a[x]);
    }
    printf("\n");
    for (int x = 0; x < 5; x++){
        printf("%d ",b[x]);
    }
    printf("\nC RES\n");
    for (int x = 0; x < 10; x++){
        printf("%d ",c[x]);
    }
    
    // MErge
    
    
    
    
    
    
    
    
    
    
    return (EXIT_SUCCESS);
}
/*
 
    x[i] is less than y[0]: In this case, x[i] is larger than i elements in array x[ ] and smaller than all elements of y[ ]. 
 * Therefore, x[i] should be in position i of the output array.
 * 
    x[i] is larger than y[n-1]: In this case, x[i] is larger than i elements in x[ ] and n elements in y[ ].
 *  Therefore, a[i] should be in position i+n of the output array.
 * 
    x[i] is between y[k-1] and y[k]: A slightly modified binary search will find a k such that x[i] is between y[k-1] and y[k].
 *  In this case, x[i] is larger than i elements in x[ ] and k elements in x[ ].
 *  Therefore, x[i] should be in position i+k of the output array. 
 */



void test1(){
    
    char A[] = {'b','w','b','w','b','w','b','w'};
    int N = 4;
    
    int s = (N*2);
    int bias = floor(s/2);
    int c = 0;
    
    for(int x=0; x<s; x++ ){
        printf(" %c",A[x]);
    }
    int h = ceil(log(s));
    printf("\n\nSorting...%d\n",h);
    
    
    for (int z = 0; z < h; z++){
        for (int x =0; x < s; x++){
                char cur = A[x];
                
                if (cur == 0){
                    printf("\tNULL HIT!\n");
                    continue;
                }

                if (cur == 'b') {
                        if (x < bias) {
                            char t = cur;
                            A[x] = A[x+1];
                            A[x+1] = t;
                            c++;

                        }
                }else if (x > bias){
                    char t = cur;
                    A[x] = A[x-1];
                    A[x-1] = t;
                    c++;
                }

        }
        // 
        printf("\n");
        for(int x=0; x<s; x++ ){
         printf(" %c",A[x]);
        }
        
    }
    //printf("\n");
    
    printf("\nDONE\n\n");
    for(int x=0; x<s; x++ ){
        printf(" %c",A[x]);
    }
    printf("\n");
    printf("TOTAL: %d\n\n",c);
}