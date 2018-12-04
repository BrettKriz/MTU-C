/* 
 * File:   main.c
 * Author: Brett Kriz
 *
 * Created on February 16, 2016, 7:35 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include "libmtu.h"

#define _S "Success"
#define _F "Failure"

#define _CUTF "mtu_countUTF8" 
#define _WFO "mtu_writeFileOpen"
#define _WFFO "mtu_writeFileFopen"
#define _PO "mtu_popen"
#define _CN "mtu_canNegate"
#define _CD "mtu_canDivide"
#define _QS "mtu_qsort"
#define _L "mtu_lang"
#define _PS "mtu_pairSum"
#define _PF "mtu_printFiles"

#define _XX -1337
// export LD_LIBRARY_PATH=.

void _pres(char *msg, int ans,int right);
bool _showtbl(float *tbl1,float *tbl2, int n);

/*
 * Used for testing libmtu.h
 */
int main(int argc, char** argv) {
    // Test library functions
    /*
    In general, if there are easy, obvious, and multiple opportunities to test something (for example, mtu_pairSum()), you should probably do multiple tests.
    Your test program should print messages like this. For each test, you should print a message describing the test that includes the function name you must print the word "Success" or "Failure" next to each test. Examples:
        Testing that mtu_pairSum(0,0) returns an array containing 0. Success.
        Testing that mtu_canNegate(1) returns 1. It returned 0. Failure.
    Your test program should be self-contained. If you want your program to check to see if mtu_writeFileOpen() overwrites files, you should create a file in your testlibmtu program, try calling mtu_writeFileOpen() and then deleting the file after it is done.
    Your test for mtu_lang() may only work when you run it on a specific computer because different computers might use different language settings. This is OK.
     There is probably no easy way to test the mtu_popen() function call---other than to try to check that it isn't returning any outrageous numbers.

     */
    
    /*
        unsigned int mtu_countUTF8(char *bytes);
        int mtu_writeFileOpen(char *filename, char *string1, int seek, char *string2);
        int mtu_writeFileFopen(char *filename, char *string1, int seek, char *string2);
        unsigned int mtu_popen();
        int mtu_canNegate(int a);
        int mtu_canDivide(int a, int b);
        void mtu_qsort(float *list, int n);
     * 
        char* mtu_lang();
        int* mtu_pairSum(int a, int b, ...);
        int mtu_printFiles();
     */
    // Uh lets see
    
    printf("\n\tStart Tests:\n\n");
    
    char str1[] = "Οὐχὶ ταὐτὰ παρίσταταί μοι γιγνώσκειν, ὦ ἄνδρες ᾿Αθηναῖοι, ὅταν τ᾿ εἰς τὰ πράγματα ἀποβλέψω καὶ ὅταν πρὸς τοὺς λόγους οὓς ἀκούω· τοὺς μὲν γὰρ λόγους περὶ τοῦ τιμωρήσασθαι Φίλιππον ὁρῶ γιγνομένους, τὰ δὲ πράγματ";
    char *ptr1 = str1;
    int len = (int)strlen(str1);
    unsigned int count = mtu_countUTF8(ptr1); // Seems to work?
    //printf("countUTF8: %d (vs. strlen of %d) \n",count, (int)strlen(str1));
    _pres(_CUTF,count, len );
    
    char str2[] = "No unicode here";
    char *ptr2 = str2;
    len = (int)strlen(str2);
    count = mtu_countUTF8(ptr2 ); // Seems to work?
    //printf("countUTF8: %d (vs. strlen of %d) \n",count, (int)strlen(str1));
    _pres(_CUTF,count, len );
    
    // Works!
    
    mtu_writeFileOpen("wfo1.txt","cat",2,"b");
    _pres(_WFO,1,_XX);
    mtu_writeFileFopen("wffo1.txt","can",2,"t");
    _pres(_WFFO,1,_XX);
    
    
    mtu_writeFileOpen("wfo2.txt","xxxyyy",4,"Z");
    _pres(_WFO,1,_XX);
    mtu_writeFileFopen("wffo2.txt","xxxyyy",2,"Z");
    _pres(_WFFO,1,_XX);
    //
    
    // Works!
    int size = mtu_popen();
    printf("\nProg size: %d \n",size);
    _pres(_PO,size,_XX); // Thats hard to predict...
    
    int ans = -1;
    ans = mtu_canNegate((int)0); // Find a no case...
    printf("Can negate? %d \n",ans);
    _pres(_CN, ans, 1);
    ans = mtu_canNegate(5); // Yes
    printf("Can negate? %d \n",ans);
    _pres(_CN, ans, 1);
    ans = mtu_canNegate(-2147483648); // No?
    printf("Can negate? %d \n",ans);
    _pres(_CN, ans, 0);
    
    //
    ans = mtu_canDivide(1,1);
    printf("Can divide? %d \n",ans);
    _pres(_CD, ans, 1);
    ans = mtu_canDivide(1,0);
    printf("Can divide? %d \n",ans);
    _pres(_CD, ans, 0);
    ans = mtu_canDivide(100,0);
    printf("Can divide? %d \n",ans);
    _pres(_CD, ans, 0);
    ans = mtu_canDivide(-2147483648,1);
    printf("Can divide? %d \n",ans);
    _pres(_CD, ans, 1);
    ans = mtu_canDivide(1,0xFFFF); // This wont work.
    printf("Can divide? %d \n",ans);
    _pres(_CD, ans, 1);
    ans = mtu_canDivide(-2147483648,-1); // This wont work.
    printf("Can divide? %d \n",ans);
    _pres(_CD, ans, 0);
    ans = mtu_canDivide(2147483648,-1); // idk
    printf("Can divide? %d \n",ans);
    _pres(_CD, ans, _XX);
    
    // Works! Not much to test here!
    float f1 = 0.0;
    float f2 = 1.0;
    float f3 = 2.0;
    float f4 = 3.0;
    float f5 = 4.0;
    float f6 = 5.0;
    
    float arr[] = {f6,f4,f3,f2,f5,f1};
    float arr1[] = {f6,f4,f3,f2,f5,f1};
    
    float arr2[] = {f1,f2,f2,f1,f2,f5,f1};
    float arr3[] = {f1,f2,f2,f1,f2,f5,f1};
    
    float arr4[] = {f2,f3,f4};
    float arr5[] = {f2,f3,f4};
    
    size = 6;
    mtu_qsort(arr,size);
    _showtbl(arr,arr1,size);
    bool b1 = true;

    for(int xx = 0; xx < size; xx++){
        if (arr[xx] != arr1[xx]){
            // Not the same, cool
            b1 = false;
            break;
        }
    }
    _pres(_QS, b1, false);
//----------------------------------------------//
    size = 7;
    mtu_qsort(arr2,size);
    _showtbl(arr2,arr3,size);
    b1 = true;

    for(int xx = 0; xx < size; xx++){
        if (arr2[xx] != arr3[xx]){
            // Not the same, cool
            b1 = false;
            break;
        }
    }
    _pres(_QS, b1, false);
    
    size = 3;
    mtu_qsort(arr4,size);
    _showtbl(arr4,arr5,size);
    b1 = true;

    for(int xx = 0; xx < size; xx++){
        if (arr4[xx] != arr5[xx]){
            // Not the same, cool
            b1 = false;
            break;
        }
    }
    _pres("mtu_pairSum (in order)", b1, true);// should be same
    
    
    // Works!
    printf("\nLANG: %s\n\n",mtu_lang());
    _pres(_L,1,_XX);
    int *pans;
    //int *anz;
    bool good = false;
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            size = 1;
            int anz1[] = {0};
            good = false;
    pans = mtu_pairSum(0,0);

    if (pans == NULL){
        // oops
        //printf("\t Null @ pairsum! Something went wrong!\n");
        _pres(_PS, -1,-5);
    }else{
        
        for(int x = 0; x < size; x++){
            int cur = pans[x];
            printf("%d ",cur);
            for (int y = 0; y < size; y++){
                int cur2 = anz1[y];
                if (cur == cur2 ){
                    good = true;
                }
            }
        }
        printf("\n");
        _pres(_PS, good, true);
        free(pans);
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            size = 1;
            int anz2[] = {3,6,9,0};
            good = false;
    pans = mtu_pairSum(1,2,4,2,5,4,0,0);

    if (pans == NULL){
        // oops
        //printf("\t Null @ pairsum! Something went wrong!\n");
        _pres(_PS, -1,-5);
    }else{
        for(int x = 0; x < size; x++){
            int cur = pans[x];
            printf("%d ",cur);
            for (int y = 0; y < size; y++){
                int cur2 = anz2[y];
                if (cur == cur2 ){
                    good = true;
                }
            }
        }
        printf("\n");
        _pres(_PS, good, true);
        free(pans);
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    printf("\n");
    mtu_printFiles();
    _pres(_PF, 1, _XX);
    
    printf(" \n\tEnd of Tests!\n");
    
    return (EXIT_SUCCESS);
}

/* A helper function for printing*/
void _pres(char *msg, int ans,int right){
    bool f = (ans == right);
    bool b1 = right == _XX;
    signed int filler = _XX;
    if (b1){
        filler = 404;
    }
    
    if (f || b1){
        printf("\nTesting that %s returns %d. It returned %d. %s \n", msg, filler, ans, _S);
    }else{
        printf("\nTesting that %s returns %d. It returned %d. %s \n", msg, right, ans, _F);
    }
}

/* Helper function for printing tables*/
bool _showtbl(float *tbl1,float *tbl2, int n){
    // Print tbl1 then tbl2 and see if they are equal
    bool ans = true;
    int z = -1;
    for(z = 0; z < n; z++){
        if (tbl1[z] != tbl2[z]){
            ans = false;
            break;
        }
    }
    // Now print
    printf("\n{ ");
    for(int x = 0; x < n; x++){printf("%f%s",tbl1[x],(x+1 == n) ? " " : ", ");}
    printf("}\n{ ");
    for(int x = 0; x < n; x++){printf("%f%s",tbl2[x],(x+1 == n) ? " " : ", ");}
    printf("}\n");
    for(int x = 0; x < n; x++){printf("%s",(x == z) ? "\t^" : "\t");}
    printf("\n");
    
    return ans;
}