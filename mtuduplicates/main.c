/* 
 * File:   main.c
 * Author: Brett Kriz
 *
 * Created on April 20, 2016, 2:40 PM
 */
#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
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

#include <openssl/md5.h>
/*
struct summ {
    char file_name[500]; // May contain spaces, is null-terminated, full path
    unsigned char digest[16];
};
typedef struct summ sum;
*/
typedef struct{
    char file_name[500]; /* May contain spaces, is null-terminated, full path */
    unsigned char digest[16];
} Sum;


bool doCD(char *arg);
Sum newSum(char *path, unsigned char *sum);
bool findFiles(char *start );
bool fileExists(char *name);
//unsigned char* doSum(char *arg);
void findSimilar();
int compareSum (const void * a, const void * b);
char * doCWD(char *str);
unsigned long int getHash(char *name, unsigned char tbl[16]);


int cap = 100;
int size = 0;
Sum *tbl = NULL;

/*
 * 
 */
int main(int argc, char** argv) {
    // Check args for starting directory
    if (argc < 2){
        printf("Please include a directory!\n");
        return(EXIT_FAILURE);
    }
    
    tbl = malloc(sizeof(Sum)*cap);
    
    findFiles( argv[1] );
    // Then find similars
    findSimilar();
    //printf("\n\n\tDone!\n");
    free(tbl);
    return (EXIT_SUCCESS);
}

Sum newSum(char *path, unsigned char *sum){
    //printf("~");
    Sum z;
    
    int pl = strlen(path)+1;
    for (int y = 0; y < 499; y++){
        if (y < pl){
            z.file_name[y] = path[y];
        }else{
            z.file_name[y] = '\0';
        }
    }
    
    z.file_name[499] = '\0';
    
    for(int x = 0; x < 16; x++){
        z.digest[x] = sum[x];
    }
    //z.digest[16] = '\0';

    
    // Add it right onto the stack
    if (size +1 >= cap){
        // Resize
        int news = (int)(cap*1.5);
        void *tmp = realloc(tbl, sizeof(sum) * news );
        if (tmp == NULL){
            // FAILED TO ALLOC
            perror("[!] FATAL MEMORY FAILURE!\n");
            return z;
        }
        tbl = tmp;
        cap = news;
        
    }
    
    tbl[size] = z;
    size++;
    
    return z;
}

bool findFiles(char *start ){ // "." for start
    struct dirent **TBL;
    bool flag = false;
    //printf("\n<entering func for '%s'>\n",start);

    doCD(start);
    
    char *cwd = getcwd(NULL,0);
    int count = scandir(cwd, &TBL, NULL, alphasort); // Loosing blocks here??
    free(cwd);
    if (TBL == NULL || count == -1){
        perror("\t[!] Table is null! Scandir failed! \n");
        return false;
    }

    //printf("\n\n\nCount: %d\n",count);
    for (int x = 0; x < count; x++){
        char *cur = TBL[x]->d_name;
        // Setup bools
        flag = false;
        
        bool isFile = false;
        bool b1 = !strcmp(cur,"..");
        bool b2 = !strcmp(cur,".");
        bool b5 = b1 || b2 ;
        
        //printf("'%s'\t<..> B1: %d, B2: %d\t<.> B3: %d, B4: %d\n",cur,b1,b2,b3,b4);
        
        if (b5){
            // Oh we shouldn't
            continue;
        }
        
        struct stat s;
        int res = stat(cur, &s);
        if (res == -1){
            cwd = getcwd(NULL,0);
            printf("\n[!] Stat failed! %s/%s\n",cwd,cur);
            free(cwd);
            continue;
        }
        
        isFile = S_ISREG(s.st_mode);

        // Are we looking for you?
        if (!isFile){
            // We should recur here.
            // or the results too.
            //printf("\tRecurring on '%s' \n",cur);
            doCD(cur);
            flag = flag || findFiles(cur);

            doCD("..");

        }else{
            // Do a sum
            unsigned char digest[16]; 
#if 0
            unsigned char *t = doSum(cur);
            //int len = strlen((char *)t);
            //printf("Digests: %d~>[%s]\n",len,t);
            
            for(int x = 0; x < size; x++){
                //if (x < len){
                    digest[x] = t[x];
                //}else{
                //    printf("Not enough length (%d)!\n",x);
                //    digest[x] = '\0';
                //}
            }
            free(t);
#endif
#if 1
            getHash(cur, digest);
            if (strlen((char *)digest) < 1){
                // Oh...
                perror("Invalid digest found, Skipping!");
                continue;
            }
#endif
            //digest[16] = '\0';
            //if (md == 0){
            //    printf("Problem reading '%s', skipping...\n",cur);
            //    continue;
            //}
            char *n = doCWD(cur);
            newSum(n, digest);
            free(n);
            
            
        }        
    }
    //doCD(norm_cwd);
    free(TBL);

    return flag; // cd Programming/CS3411/Prog5
}

bool fileExists(char *name){
    struct stat s;
    int res = stat(name, &s);
    if (res == -1){
        return false;
    }
    // Bypass read restrictions
    return !(res) && S_ISREG(s.st_mode) ;//&& b1;
}
/*
unsigned char* doSum(char *arg){
    // Open the file and feed it into the MD5

    int amt = 2400;
    unsigned char *md = malloc(sizeof(unsigned char) * 16);
    MD5_CTX ctx;
    ssize_t r;
    char buff[amt];
    
    if (!fileExists(arg)){
        printf("File doesn't exist!!\n");
        return 0;
    }
    
    FILE *f = fopen(arg, "rb");
    if (f == NULL){
        perror("Failed to open");
    }
    
    MD5_Init(&ctx);
    r = fread(&buff, amt, 1, f);
    while(r > 0){
        MD5_Update(&ctx, buff, r);
        r = fread(&buff, amt, 1, f);
    }
    fclose(f);
    MD5_Final(md, &ctx);
    //printf("MD5: %s\n",md);
    //md[16] = '\0';
    return md;
}
*/
bool doCD(char *arg){
    //int at = 1;
    if (arg == NULL){
        printf("[!] Invalid argument! Requires at least 1!\n");
        //printf("[i] Ple
        return true;
    }else if (strcmp(arg,".") == 0){
        // Current, so stay here...
        return true;
    }else if (strcmp(arg,"..") == 0){
        // Go up a directory!
        char *str = getcwd(NULL,0);
        //printf("\tIm in %s \n",str);
        // Chop input down to parent
        int at = 0;
        for (int z = 0; z < strlen(str)+1; z++){
            char cur = str[z];

            if (cur == '\0' && at == 0){
                // there are no slashes
                at = z;
                //printf("at break %d\n",at);
                break;
            }
            if (cur == '/' || cur == '\\'){
                // Slashes found
                at = z;
                //printf("\tat %d\n",at);
            }
        }
        
        // Alloc space and edit in null terminator
        char *str2 = malloc( (sizeof(char) * at) +1);
        strncpy(str2, str, at);
        str2[at] = '\0'; // Make sure to null the END

        // Set it as the new path
    //printf("CD: '%s'\n",str2);
        int a = chdir(str2);
        if (a == -1){
            // Thats not a valid path...
            perror("[!] Crippling error, cd .. FAILED!\n");
            free(str);
            free(str2);
            return false;
        }

        fflush(stdout);
        free(str);
        free(str2);
        return true;
    }

    //printf(" %s ",arg);

    //bool isABS = false;
    bool found = false;
    char *fill = "/";
    char *cur2 = getcwd(NULL,0);
    if (strcmp(arg, "/") == 0 || strcmp(arg,"\\") == 0){
        // Cant be abs path
        fill = "";
        //isABS = true;
    }

    char *cur = arg;
    int a1 = 5;
    int a2 = 5;
    char *s1 = malloc(strlen(cur)+strlen(cur2)+strlen(fill)+1);
    int f1 = sprintf(s1, "%s%s%s", cur2, fill, cur);
    if (f1 < 1 ){
        printf("[!] Error sprintf\n");
    }
    
    char *s2 = malloc(strlen(cur)+strlen(fill)+1);
    int f2 = sprintf(s2, "%s%s", fill, cur);
    if (f2 < 1){
        printf("[!] Error Sprintf\n");
    }

    a1 = chdir(s1);
    if (a1 == -1){
        // Thats not a valid path...
    }else{
        found = true;
        //printf("~1 %s\n",s1);
    }

    a2 = chdir(s2);
    if (a2 == -1 && !found){
        // Thats not a valid path...
    }else{
        found = true;
        //printf("~2 %s\n",s2);
    }
    //printf("1 \n");
    if (s1 != NULL){
        free(s1);
    }
    
    //printf("2 \n");
    if (s2 != NULL){
       free(s2); 
    }
    
    //printf("3 \n");
    if (cur2 != NULL){
        free(cur2);
    }
    
    //free

    //doCD(norm_cwd);
    
    return found;
}

void findSimilar(){
    // Compair all files
    //qsort(tbl, size, sizeof(Sum), compareSum);
    int at = 0;
    bool same = false;
    bool streak = false;
    //printf("\n");

    for (at = 0; at < size; at++){
        streak = false;
        Sum cur = ( Sum) tbl[at];
        
        if (strlen((char *)cur.digest) < 1){
            // Nothing to do here!
            continue;
        }
        //char *cstr = doCWD(cur.file_name);
        //printf("\t# ");
        //for (int z = 0; z < 16; z++){
        //    printf("%02x ",cur.digest[z]);
        //}
        //printf("\t%s\n",cur.file_name);
                
        
        for (int x = at+1; x < size; x++){
            //Go down and compare
            Sum pos = ( Sum) tbl[x];
            int ans = strncmp((char*)cur.digest, (char*)pos.digest, 16);
            same = (ans == 0);
            
            if (same){
                if (!streak){
                    printf("%s", cur.file_name);
                    streak = true;
                }
                //char *cpos = doCWD(pos.file_name);
                printf(", %s", pos.file_name );
                //free(cpos);
                // Null the found files out
                // They've been matched.
                fflush(stdout);
                for(int h = 0; h < 16; h++){pos.digest[h] = '\0';}
                for(int h = 0; h < 16; h++){pos.file_name[h] = '\0';}
                //free(pos.file_name);
                
                tbl[x] = pos;
                
            }
        }
        // After
        if (streak){
            printf("\n");
        }
        //free(cstr);
        //
    }
}

int compareSum (const void * a, const void * b){
    int ans = 0;
    Sum *A = ( Sum*)a;
    Sum *B = ( Sum*)b;
    
    //unsigned char as[16] = A.digest;
    //unsigned char bs[16] = B.digest;
    
    ans = strncmp((char*)A->digest, (char*)B->digest, 16);

    return ans;
}

char * doCWD(char *str){
    char *cwd = getcwd(NULL, 0); // FREE ME
    char *name = NULL; // FREE ME
    int size = (strlen(cwd)+strlen(str)+2);
    char *temp = realloc(name, sizeof(char*) * size ); // FREE ME (name & temp after)
    if (temp == NULL){
        printf("[!] Failed to Malloc!\n");
        free(cwd);
        return false;
    }else{
        name = temp;
    }
    // Cat into the mem
    temp = strcpy(name, cwd);
    if (temp == NULL){
        printf("[!] Failed to StrCpy!\n");
        free(cwd);
        free(name);
        return false;
    }
    temp = strcat(name,"/");
    if (temp == NULL){
        printf("[!] Failed to StrCat1!\n");
        free(cwd);
        free(name);
        return false;
    }
    temp = strcat(name,str);
    if (temp == NULL){
        printf("[!] Failed to StrCat2!\n");
        free(cwd);
        free(name);
        return false;
    }
    
    free(cwd);
    return name;
}

unsigned long int getHash(char *name, unsigned char tbl[16]){
    unsigned long int ans = -1;
    // Find process size

    int bsize = 800;
    char buff[bsize];
    for (int x = 0; x < 16; x++) { tbl[x] = '\0'; }
    for (int x = 0; x < bsize; x++) { buff[x] = '\0'; }
    sprintf(buff, "md5sum %s", name);
    
    //printf("\n<> CHECKING CONTENTS: %s <>\n\n",buff);
    
    FILE *p = popen(buff,"r");
    if (p == NULL){
        printf("\n[!] popen failed! Check the arguments \n");
        return 0;
    }

    // Proceed to reading the answer
    //int size = -1337;
    int extra = 1; // 3 B/C space, newline, null byte
    int arrs = sizeof(char) * (32+strlen(name)+extra);//(16+strlen(name)+extra)
    char *arr = malloc(arrs);
    if (arr == NULL){
        perror("Malloc failed! ");
        return 0;
    }
    for(int h = 0; h < arrs; h++){arr[h] = '\0';}
    int amt = -1;

    amt = fscanf(p, "%s", arr);
    
    //printf("arr: %s\n",arr);
    // Check the scanf
    if (amt <= 0 || arr == NULL || strlen(arr) < 1){
        perror("\t[!] sscanf failed!");
        free(arr);
        return 0;
    }else{
        //printf("%s\n",arr);
    }
    
    // 8 Bytes at a time
    char *end = NULL;
    int z = 0;
    for(int x = 0 ; x < 32; x += 2){
        char cur[2];
        char c1 = arr[x];
        char c2 = arr[x+1];
        cur[0] = c1;
        cur[1] = c2;
        // Process it
        ans = strtoul(cur, &end, 16);
        uint ans2 = (uint)ans;
        //printf("[%c %c =%d] ",c1,c2,(int)ans2);
        //printf("ans @%d :\t%ld vs %d \t(end = '%s')\n",x,ans,ans2,end); 
        
        tbl[z] = (unsigned char) ans2;
        z++;
    }
    //printf(" <>\n\n");
    // Done, clean up!
    int ret = pclose(p);
    if (ret == -1){
        printf("\n[!] pclose failed! \n");
    }

    if (arr != NULL){
        free(arr);
    }
    
    return ans; 
}