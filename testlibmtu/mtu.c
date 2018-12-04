/* 
 * File:   mtu.c
 * Author: Brett Kriz
 *
 */
#define _BSD_SOURCE

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
#include <string.h>
#include <locale.h>
#include <locale.h>

#define _DEFAULT_MODE 0777
#define _LANG_STR "LANG"
// 0xEF,0xBB,0xBF


/*
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

void outt(int arg, char *str);
 */
int compairFloats (const void * a, const void * b);
int compairFSize (const struct dirent **a, const struct dirent **b);

// Start bodies
unsigned int mtu_countUTF8(char *bytes){ 
    unsigned int ans = 0;
// Its off... also segfaults
    char* dis = setlocale(LC_ALL, "");
    if (dis == NULL){
        printf("[!] UTF8 disabled!!! Can't return proper results!\n");
        return 0;
    }else{
        printf("[i] UTF8 enabled! String length estimated to be %d bytes long roughly\n",(unsigned int)strlen(bytes));
    }
    
    printf("\n");
    // 0xEF,0xBB,0xBF
    //bool found_magic = !false;
    //bool b1 = false;
    //bool b2 = false;
    char cur = '?';
    unsigned long int x = 0;
    for(x = 0; true; x++){
        // Make sure its UTF8
        // ASCII is in UTF8
        cur = bytes[x];
        if (cur == '\0'){
            printf("\n[i] End found @ %d!\n",(unsigned int)x);
            break;
        }else if (x < 5500){
            printf("%c",cur);
        }else if (x == 5500){
            printf("... Theres too much here...\n");
        }
        
        // Cant make this work...
        /*
        if (cur == _mb_1 && found_magic == false){
            b1 = true;
            printf("1 ");
        }
        if (b1  && cur == _mb_2){
            b2 = true; // Noice
            printf("2 ");
        }else{
            b1 = false;
        }
        if (b2 && cur == _mb_3){
            printf(" 3! Confirmed UTF8!\n");
            found_magic = true;
            b1 = false;
            b2 = false;
        }else{
            b1 = false;
            b2 = false;
        }
         */
    }
    printf("\n");
    ans = (unsigned int)x;
    
    return ans;
}
int mtu_writeFileOpen(char *filename, char *string1, int seek, char *string2){
    // Start, open a file
    if ( seek >= strlen(string1) || seek < 0){
        // Thats a problem!
        printf("[!] Seek is too long!! %d \n",seek);
        return 0;
    }
    printf("\nWRITING FILE %s\n",filename);
    int f = open(filename, O_WRONLY | O_TRUNC | O_CREAT , _DEFAULT_MODE);
    if (f < 0){
        printf("\n[!] Unable to open the file %s\n",filename);
        return 0;
    }
    // Write in
    for (int x = 0; x < strlen(string1); x++){
        char cur = string1[x];
        printf(" %d,%c ",x,cur);
        int wrote = write(f, &cur, 1);
        if (wrote == 0){
            printf("\n[!] Unable to write to file '%s' \n",string1);
            close(f);
            return 0;
        }
    }
    // Now seek
    int fval = lseek(f, seek, SEEK_SET);
    if (fval == -1){
        // Bad!
        printf("\n[!] lseek fatal failure! Amount: %d\n",seek);
        close(f);
        return 0;
    }
    // Write in again
    for (int x = 0; x < strlen(string2); x++){
        char cur = string2[x];
        printf(" %d,%c ",x,cur);
        int wrote = write(f, &cur, 1);
        if (wrote == 0){
            printf("\n[!] Unable to write to file '%s' \n",string1);
            close(f);
            return 0;
        }
    }
    // Done! Clean up
    //fflush(&f);
    int closed = close(f);
    if (closed == -1){
        // x,x
        printf("\n[!] Unable to close file F! Something is very wrong!!! \n");
        return 0;
    }
    printf("\n\tDONE!\n");
    return 1;
}
int mtu_writeFileFopen(char *filename, char *string1, int seek, char *string2){
    // F only
    // Start, open a file
    if ( seek >= strlen(string1) || seek < 0){
        // Thats a problem!
        printf("[!] Seek is too long!! %d \n",seek);
        return 0;
    }
    printf("\nWRITING FILE %s\n",filename);
    FILE *f = fopen(filename, "wb");
    if (f == NULL){
        printf("\n[!] Unable to open the file %s\n",filename);
        return 0;
    }
    // Write in
    
    for (int x = 0; x < strlen(string1); x++){
        char cur = string1[x];
        printf(" %d,%c ",x,cur);
        int wrote = fwrite(&cur,1,1,f);
        if (wrote == 0){
            printf("\n[!] Unable to write to file '%s' \n",string1);
            fclose(f);
            return 0;
        }
    }
    
    // Now seek
    int fval = fseek(f, seek, SEEK_SET);
    if (fval == -1){
        // Bad!
        printf("\n[!] Fseek fatal failure! Amount: %d\n",seek);
        fclose(f);
        return 0;
    }
    // Write in again
    for (int x = 0; x < strlen(string2); x++){
        char cur = string2[x];
        printf(" %d,%c ",x,cur);
        int wrote = fwrite(&cur,1,1,f);
        if (wrote == 0){
            printf("\n[!] Unable to write to file '%s' \n",string1);
            fclose(f);
            return 0;
        }
    }
    
    // Done! Clean up
    fflush(f);
    int closed = fclose(f);
    if (closed == -1){
        // x,x
        printf("\n[!] Unable to close file F! Something is very wrong!!! \n");
        return 0;
    }
    printf("\n\tDONE!\n");
    return 1; 
}
unsigned int mtu_popen(){
    unsigned int ans = 0;
    // Find process size
    pid_t id = getpid();
    
    char buff[150];
    int bsize = 150;
    for (int x = 0; x < bsize; x++) { buff[x] = '\0'; }
    sprintf(buff, "ps -p %d -o rss=", id);
    
    //printf("\n<> CHECKING CONTENTS: %s <>\n\n",buff);
    
    FILE *p = popen(buff,"r");
    if (p == NULL){
        printf("\n[!] popen failed! Check the arguments \n");
        return 0;
    }

    // Proceed to reading the answer
    int size = -1337;
    char arr = 'x';
    int amt = fscanf(p, "%s", &arr);
    // Convert string to int
    size = atoi(&arr);

    if (amt <= 0){
        printf("\n[!] sscanf failed! Check the arguments \n");
    }else{
        ans = size;
        //printf("\n\t[i] Current process size is %d \n", size);
    }
    // Done, clean up!
    int ret = pclose(p);
    if (ret == -1){
        printf("\n[!] pclose failed! \n");
    }else{
        //printf("[i] pipe closed!\n");
    }

    
    return ans; 
}
int mtu_canNegate(int a){ 

    bool b1 = ((signed long)a * -1l) == (signed long)(a * -1);
    
    return b1; 
}
int mtu_canDivide(int a, int b){
    long long aa = (long long)a;
    long long bb = (long long)b;
    printf("[i] Attempting to divide %d by %d !\n",a,b);
    if (b == 0 || bb == 0){
        return 0;
    }

    bool b1 = (a < 0 && b < 0);
    bool b2 = true;
    bool b3 = true;
    if (b1 && (!mtu_canNegate(a) || !mtu_canNegate(b))){
        // Oooh thats bad...
        // Check for problems there
        //printf("")
        b2 = (((long int)a)*-1) == (a*-1);
        b3 = (((long int)b)*-1) == (b*-1);
    }
    
    if (b1 == true && (b2 == false || b3 == false )){
        // Caught it
        printf("[i] Overflow possibility detected! <> b1:%d b2:%d b3:%d <> Zeroing! \n",b1,b2,b3);
        return 0;
    }
        
        
    // Check for overflow possibilities
    if ((long int)b != b){
        return 0;
    }
    if ((long int)a != a){
        return 0;
    }
    printf("[i] Data1: %d %d %d\n",b1,b2,b3);
    long long v1 = (aa/bb);
    long long v2 = (a/b);
    
    bool ans = (v1 == v2);
    printf("[i] Data2: %d %d\n",(int)v1,(int)v2);
    return (int)ans; 
}
int mtu_canAdd(int a, int b){
    // Check for overflow
    long long x = (long long)(a + b);
    long long y = (long long)a + (long long)b;
    bool flag = ( x == y);
    //printf("\tcanADD? %d == %d ? %d",x,y,flag);
    if (flag != true){
        printf("\n\tCant add %d & %d!\n",a,b);
    }
    
    return (int)flag;
}

void mtu_qsort(float *list, int n){ 
    // Not much to do here I guess!
    qsort(list, n, sizeof(float), compairFloats);
    return ; 
}
int compairFloats (const void * a, const void * b){
    int ans = 0;
    
    if (( *(float*)a < *(float*)b )){
        // A < B = Gnegative
        ans = -1;
    }else if (( *(float*)a > *(float*)b )){
        // A > B = positive
        ans = 1;
    }
    
    return ans;
}
char* mtu_lang(){ 
    char *ret = getenv(_LANG_STR);
    if (ret == NULL){
        // BaD!
        printf("[!] Cannot find env var '%s'\n",_LANG_STR);
        return NULL;
    }
    // I guess we got a string
    return ret; 
}
int* mtu_pairSum(int a, int b, ...){
    //va_list val;
    // Read in 2 at a time
    // inpuft might be uneven
    // Last pair of ints will be 0 0
    va_list args;
    va_start(args, b); // or should it be 'b'? @@@
    
    int x = a;
    int y = b;
    bool flag = false;
    
    int ans2[400];
    int limit = 400;
    //printf("Entering loop\n");
    int at = 0;
    do{
        // Restrict first time
        if (flag == true){
            //printf("Grabing more args\n");
            x = va_arg(args, int);
            y = va_arg(args, int);
        }else{
            flag = true;
        }
        
        if (at >= limit){
            // Thats bad...
            printf("[!] pairSum failed! Sums surpass %d for the buffer!\n\tABORTING...\n", limit);
            break;
        }
        
        bool b1 = mtu_canAdd(x,y);
        if (b1 == false){
            // Overflow
            printf("[!] Over flow detected! Halting! (%d+%d)\n",a,b);
            va_end(args);
            return NULL;
        }
        // Store it
        ans2[at] = x+y;
        //printf("Adding to answer and iterating\n");
        // Refresh and continue
        at++;
    }while( !(x == 0 && y == 0) );
    
    printf("[i] Size found: %d     %d\n",at,(int)sizeof(int));
    
    int size = at*sizeof(int);
    int *ans = malloc(size); // sizeof(int)
    // ^^^ Might need to use #DEFINE ^^^
    //printf("Past alloc, heading to end of file");
    for (int z = 0; z < size/sizeof(int); z++){
        ans[z] = 0;
    }
    for (int z = 0; z < at; z++){
        // Transfer it over
        ans[z] = ans2[z];
    }
    //printf("cleanup\n");
    // Cleanup and return out
    va_end(args);
    //printf("at end\n");
    return ans; 
}
int compairFSize (const struct dirent **a, const struct dirent **b){
    int ans = 0;
    
    const char *aa = (*a)->d_name;
    const char *bb = (*b)->d_name;
    
    struct stat st1;
    struct stat st2;

    int res = stat(aa, &st1);
    if (res == -1){
        printf("\n[!] Stat failed! AA \n");
        return -1;
    }
    res = stat(bb, &st2);
    if (res == -1){
        printf("\n[!] Stat failed! BB \n");
        return -1;
    }

    int s1 = (int)st1.st_size; // A
    int s2 = (int)st2.st_size; // B
    
    if (( s1 < s2 )){
        // A < B = Gnegative
        ans = -1;
    }else if (( s1 > s2 )){
        // A > B = positive
        ans = 1;
    }
    
    return ans;
}
int mtu_printFiles(){
    struct dirent **tbl;
    /*
        ino_t  d_ino       file serial number
        char   d_name[]    name of entry
     */
    int count = scandir(".", &tbl, NULL, compairFSize);
    if (tbl == NULL){
        perror("\n[!] Table is null! Scandir failed! \n");
        return -1;
    }
    //printf("\n\n\nCount: %d\n",count);
    for (int x = 0; x < count; x++){
        struct stat s;
        int res = stat(tbl[x]->d_name, &s);
        if (res == -1){
            printf("\n[!] Stat failed! \n");
            return -1;
        }
        printf("%s %d\n",tbl[x]->d_name,(int)s.st_size);
    }
    // Start working with this...
    /*
    int z = 0;
    bool swapped = true;
    while (swapped == true){
        swapped = false;
        z++;
        for (int x = 0; x < count-z; x++){
            
            // ********************
            
            if (s1 > s2){
                // Flip em
                //
                char *temp = tbl.d_name[x];
                tbl.d_name[x] = tbl.d_name[x+1];
                tbl.d_name[x+1] = temp;
                //
                swapped = true;
            }
        }
    }
    //*/
    return count; 
}






