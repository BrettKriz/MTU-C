// Brett Kriz
// lib mtu

#pragma once

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>
#include <stdint.h>
#include <stdarg.h>
// Additional
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <dirent.h>

unsigned int mtu_countUTF8(char *bytes);
int mtu_writeFileOpen(char *filename, char *string1, int seek, char *string2);
int mtu_writeFileFopen(char *filename, char *string1, int seek, char *string2);
unsigned int mtu_popen();
int mtu_canNegate(int a);
int mtu_canDivide(int a, int b);
void mtu_qsort(float *list, int n);
char* mtu_lang();
int* mtu_pairSum(int a, int b, ...);
int mtu_printFiles();
// Helpers
int mtu_canAdd(int a, int b);
int compairFloats (const void * a, const void * b);
void outt(int arg, char *str);


void outt(int arg, char *str){
    char z = 'i';
    if (arg == 1){
        z = '!';
    }else if (arg == 2){
        z = '?';
    }

    printf("\n[%c] %s \n",z,str);
}

// Start bodies
unsigned int mtu_countUTF8(char *bytes){ 
    unsigned int ans = 0;

    return ans;
}
int mtu_writeFileOpen(char *filename, char *string1, int seek, char *string2){
    // Start, open a file
    FILE f = open(filename, O_WRONLY | O_TRUNC | O_CREAT , 0777);
    if (f == NULL){
        printf("\n[!] Unable to open the file %s\n",filename);
        return 0;
    }
    // Write in
    int wrote = write(f, &string1, sizeof(string1));
    if (wrote == 0){
        printf("\n[!] Unable to write to file '%s' \n",string1);
        return 0;
    }
    // Now seek
    int fval = lseek(f, seek, SEEK_SET);
    if (fval == -1){
        // Bad!
        printf("\n[!] lseek fatal failure! Amount: %d\n",seek);
        return 0;
    }
    // Write in again
    wrote = fwrite(f, &string2, sizeof(string2));
    if (wrote == 0){
        printf("\n[!] Unable to write to file '%s' \n",string1);
        return 0;
    }
    // Done! Clean up
    int closed = close(f);
    if (closed == -1){
        // x,x
        printf("\n[!] Unable to close file F! Something is very wrong!!! \n");
        return 0;
    }
    
    return 1;
}
int mtu_writeFileFopen(char *filename, char *string1, int seek, char *string2){
    // F only
    // Start, open a file
    FILE f = fopen(filename, "wb");
    if (f == NULL){
        printf("\n[!] Unable to open the file %s\n",filename);
        return 0;
    }
    // Write in
    int wrote = fwrite(&string1,sizeof(string1),1,f);
    if (wrote == 0){
        printf("\n[!] Unable to write to file '%s' \n",string1);
        return 0;
    }
    // Now seek
    int fval = fseek(f, seek, SEEK_SET);
    if (fval == -1){
        // Bad!
        printf("\n[!] Fseek fatal failure! Amount: %d\n",seek);
        return 0;
    }
    // Write in again
    wrote = fwrite(&string2,sizeof(string2),1,f);
    if (wrote == 0){
        printf("\n[!] Unable to write to file '%s' \n",string1);
        return 0;
    }
    // Done! Clean up
    int closed = fclose(f);
    if (closed == -1){
        // x,x
        printf("\n[!] Unable to close file F! Something is very wrong!!! \n");
        return 0;
    }
    
    return 1; 
}
unsigned int mtu_popen(){ 
    unsigned int ans = 0;
    
    pid_t id = getpid();
    
    char buff[30];
    for (int x = 0; x < 30; x++) { buff[x] = '\0'; }
    sprintf(buff, "ps -p %d -o rss=", id);
    
    FILE *p = popen(buff,"w");
    if (p == NULL){
        printf("\n[!] popen failed! Check the arguments \n");
        return 0;
    }
    
    // Proceed to reading the answer
    int size = 0;
    int amt = sscanf(p, "%d", &size);
    if (amt == 0){
        printf("\n[!] sscanf failed! Check the arguments \n");
    }else{
        ans = size;
        printf("\n[i] Current process size is %d \n", size);
    }
    // Done, clean up!
    int ret = pclose(p);
    if (ret == -1){
        printf("\n[!] pclose failed! \n");
    }

    return ans; 
}
int mtu_canNegate(int a){ 
    bool b1 = (a(signed long) * -1l) == (signed long)(a * -1);
    
    return b1; 
}
int mtu_canDivide(int a, int b){ 
    bool ans = ((signed long double)a/(signed long double)b) == (signed long double)(a/b);
    
    return (int)ans; 
}
int mtu_canAdd(int a, int b){
    // Check for overflow
    long x = (a + b);
    long y = (long)a + (long)b;
    
    return (int)(x == y);
}
void mtu_qsort(float *list, int n){ 
    // Not much to do here I guess!
    qsort(list, n, sizeof(float), compairFloats);
    return ; 
}

int compairFloats (const void * a, const void * b){
    int ans = 0;
    
    if (( *(float*)a < *(float*)b )){
        // A < B = negative
        ans = -1;
    }else if (( *(float*)a > *(float*)b )){
        // A > B = positive
        ans = 1;
    }
    
    return ans;
}

char* mtu_lang(){ 
    char *ret = getenv();
    if (ret == NULL){
        // BaD!
        return NULL;
    }
    // I guess we got a string
    return ret; 
}
int* mtu_pairSum(int a, int b, ...){ 
    //va_list val;
    // Read in 2 at a time
    // input might be uneven
    // Last pair of ints will be 0 0
    int size = 0;
    va_list args;
    va_start(args, b); // or should it be 'b'? @@@
    
    int x = a;
    int y = b;
    static int ans = malloc();
    
    while( !(x == 0 && y == 0) ){
        
        
        bool b1 = mtu_canAdd(x,y);
        if (b1 == true){
            // Overflow
            va_end(args);
            return NULL;
        }
        // Refresh and continue
        x = va_arg(args, int);
        y = va_arg(args, int);
    }
    
    
    va_end(args);
    return ans; 
}
int mtu_printFiles(){
    struct dirent **tbl;
    /*
        ino_t  d_ino       file serial number
        char   d_name[]    name of entry
     */
    int count = scandir(".", &tbl, NULL, alphasort);
    if (tbl == NULL){
        perror("\n[!] Table is null! Scandir failed! \n");
        return -1;
    }
    
    // Start working with this...
    
    int z = 0;
    bool swapped = true;
    while (swapped == true){
        swapped = false;
        z++;
        for (int x = 0; x < count-z; x++){
            struct stat st1;
            struct stat st2;
            
            int res = stat(tbl[x], &st1);
            if (res == -1){
                printf("\n[!] Stat failed! \n");
                return -1;
            }
            res = stat(tbl[x+1], &st2);
            if (res == -1){
                printf("\n[!] Stat failed! \n");
                return -1;
            }
            
            int s1 = (int)st1.st_size;
            int s2 = (int)st2.st_size;
            
            if (s1 > s2){
                // Flip em
                char *temp = tbl->d_name[x];
                tbl->d_name[x] = tbl->d_name[x+1];
                tbl->d_name[x+1] = temp;
                
                swapped = true;
            }
        }
    }

    return count; 
}






