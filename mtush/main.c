/* 
 * File:   main.c
 * Author: Brett Kriz
 * Terminal
 * Created on March 17, 2016, 3:18 PM
 */

#define _GNU_SOURCE

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdarg.h>
// Additional
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <dirent.h>
#include <locale.h>
#include <unistd.h>

#include <sys/wait.h>
#include <sys/types.h>

#include <readline/readline.h>
#include <readline/history.h>


// Define colors and stuff
#define NRM  "\x1B[0m"

#define BLK  "\x1B[30m"
#define RED  "\x1B[31m"
#define GRN  "\x1B[32m"
#define YEL  "\x1B[33m"
#define BLU  "\x1B[34m"
#define MAG  "\x1B[35m"
#define CYN  "\x1B[36m"
#define WHT  "\x1B[37m"

#define BLK_BG  "\x1B[40m"
#define RED_BG  "\x1B[41m"
#define GRN_BG  "\x1B[42m"
#define YEL_BG  "\x1B[43m"
#define BLU_BG  "\x1B[44m"
#define MAG_BG  "\x1B[45m"
#define CYN_BG  "\x1B[46m"
#define WHT_BG  "\x1B[47m"

#define BOLD  "\x1B[1m"
#define ULINE "\x1B[4m"

// Other defined tools
#define R_S  "\001\x1B[0m\002"
#define R_E  "\001\x1B[31m\002"

#define PRMPT "> "

#define READ_END 0
#define WRITE_END 1

typedef struct{
    //char* target;
    //char* des;
    int target;
    int des;
    bool isValid;
} do_pipe;

int body();
bool fileExists(char *name);
bool doCat(char *fn);
int launchPipedOp(do_pipe args);
do_pipe newPipedOp(char *t, char *d);
int compairFSize (const struct dirent **a, const struct dirent **b);
int printFiles(char arg);
bool forkChild(char* prog, char** args, int in, int out);
bool doCD(char *arg);

bool isRunning = true;

/*
 * Handle init & loading
 */
int main(int argc, char** argv) {
#if 0
    for(int x = 0; x < argc; x++){
        printf(" %s",argv[x]);
    }
    int val = execvp(argv[1],argv[2]);
    printf("\n%d",val);
#endif
    int ret = body();
    
    printf("\n\nDONE!\n");
    return ret; // (EXIT_SUCCESS)
}

int body(){
    int ret = 0;
 //   int in_p = 0;//stdin
//    int out_p = 1;//stdout
    bool problem = false;
    
    do{
        char *cwd = getcwd(NULL,0);
        if (!isRunning){ return 0; }
        
        printf(" mtush " GRN "%s" NRM, cwd );
        char *arg = readline(PRMPT); //R_S PRMPT R_E
        char **args = NULL;
        char **seqs = NULL;
        free(cwd);
        
        if (!arg){
            //oh...
            continue;
        }else if (strcmp(arg, "exit") == 0){
            printf("\n\tGoodbye\n");
            free(arg);
            isRunning = false;
            return 0;
            //exit(EXIT_SUCCESS);
        }
        // -------------------------------------------------------------- //
        // TOKENIZE on |
        char *token = strtok(arg,"|");
        int slots = 0;
        while (token){
            
            void *tmp = realloc(args, sizeof(char*) * ++slots );
            if (tmp == NULL){
                // FAILED TO ALLOC
                printf("[!] FATAL MEMORY FAILURE @ TOKENIZE!\n");
                problem = true;
                //free(args);
                break;
            }else{
                args = tmp;
            }
            // Split and save
            args[slots-1] = token;
            token = strtok(NULL,"|");
        }
        // check for issues
        if (problem == true){
            printf("~~ ABORTING\n");
            problem = false;
            free(args);
            free(arg);
            continue;
        }
        if (args != NULL){
            void *tmp = realloc(args, sizeof(char*) * (slots+1));
            if (tmp == NULL){
                //free(args);
                printf("[!] Unhandled NULL realloc!\n");
            }else{
                args = tmp;
            }
            args[slots] = 0; // Null terminate this array
        }
        // Hold up! Lets make some pipes!
        /*
        int *pipes = malloc(sizeof(int) * 4 * (slots-1));
        int z = 0;
        if (pipes == NULL){
            printf("[!] Malloc failed! Fatal Error\n");
            problem = true;
            z = slots;
        }
            
        for (z = 0; z < slots*2; z = z+2){
            int zzz[2];
            int zz = pipe(zzz);
            if (zz == -1){
                printf("[!] Can't make pipe! Fatal Error\n");
                problem = true;
                free(pipes);
            } // fml
            pipes[z] = zzz[0]; // read
            pipes[z+1] = zzz[1]; // write
            printf("PIPES: %d , %d\n",zzz[0],zzz[1]);
        }
        */
        // check for issues
        if (problem == true){
            printf("~~ ABORTING\n");
            problem = false;
            free(args);
            free(arg);
            continue;
        }
// ---------------------------------------------------------- //
        // TOKENIZE ON SPACE
        for (int x = 0; x < slots; x++){
            // TOKENIZE on SPACE
            int slots2 = 0;
            char *token2 = strtok(args[x]," ");
            if (token2 == NULL){
                token2 = strtok(args[x], " ");
                printf("Was null, now is %s @ %d & %d\n",token2,x,slots2);
            }else{
                printf("Is %s @ %d & %d\n",token2,x,slots2);
            }
            printf("~~%d!~\n",x);
            while (token2){
                //printf("@%d ",slots2);
                void *tmp = realloc(seqs, sizeof(char*) * ++slots2 );
                if (tmp == NULL){
                    // FAILED TO ALLOC
                    printf("[!] FATAL MEMORY FAILURE @ TOKENIZE!\n");
                    problem = true;
                    //free(seqs);
                    break;
                }else{
                    seqs = tmp;
                }
                // Split and save
                seqs[slots2-1] = token2;
                //printf("Token@%d: %s\n",slots2-1,token2);
                token2 = strtok(NULL," ");
            }
            free(token2);
            //
    for(int y = 0; y < slots2; y++){
        //+++++++
        printf("@%d %s\n",y,seqs[y]);
    }
    printf("\n\n");
            //*/
            
            // check for issues
            if (problem == true){
                printf("~~ ABORTING\n");
                problem = false;
                free(seqs);
                
                break;
            }
            if (seqs != NULL){
                void *tmp = realloc(seqs, sizeof(char*) * (slots2+1));
                if (tmp == NULL){
                    printf("[!] Unhandled NULL realloc!\n");
                }else{
                    seqs = tmp;
                }
                seqs[slots2] = 0; // Null terminate this array
            }
            //
    for(int y = 0; y < slots2; y++){
        //+++++++2
        printf("@%d %s\n",y,seqs[y]);
    }
    printf("\n");
             //*/
            
            // Do things here
            // FIrst arg should be the command name?
            // Check for built in functions tho
            bool vf1 = false;
            int b1 = strcmp(seqs[0],"cd");
            int b2 = strcmp(seqs[0],"exit");
printf("Im so far lol\n");
            if (b2 == 0){
                free(args);
                free(arg);
                return 0;
            }
            
            //bool flag = (slots > 1); // We have some pipes lol
                //printf("b1: %d | >%s< ->? >cd<\n\n", b1, seqs[0]);
                //printf("SIZE %d,%d ~> %s %s \n\n\n",slots, slots2,seqs[0], seqs[1]);
            if (b1 == 0){
                //printf("IN\n");
                vf1 = doCD(seqs[1]);
                //printf("OUT\n");
            }else{
                // Setup for piping
                // Consider flag
                // Use slots for fd thing
                // ?Make pipes before hand?
                /*
                int inp[2];
                inp[0] = STDOUT_FILENO;
                inp[1] = STDIN_FILENO;
                int outp[2];
                outp[0] = STDOUT_FILENO;
                outp[1] = STDIN_FILENO;
                //int left = slots - x;
                
                // More than 1 slot?
                if (flag == true && false){ // Use new pipes
                    int i1[2];
                    i1[0] = pipes[x]; // read
                    i1[1] = pipes[x+1];
                    int i2[2];
                    i2[0] = pipes[x+2]; // write
                    i2[1] = pipes[x+3];
                    
                    inp[0] = i1[0];
                    inp[1] = i1[1];
                    
                    outp[0] = i2[0];
                    outp[1] = i2[1];
                    
                    if (x == 0){
                        // Make in pipe normal?
                        inp[0] = STDOUT_FILENO;
                        inp[1] = STDIN_FILENO;
                    }

                    if (x+1 == slots){
                        // Make out pipe spill to...
                        outp[0] = STDOUT_FILENO;
                        outp[1] = STDIN_FILENO;
                    }
                }
                 */                
//        printf("%d ~~~- USING PIPES: %d , %d\n",flag, inp, outp);
                //vf1 = forkChild(seqs[0], seqs, inp, outp, flag);
                vf1 = forkChild(seqs[0], seqs, 0, 1);
        printf("~~+~\n");
            }
            //printf("VF1 %d \n",vf1);
            if (!vf1){
                // Bad!
                printf("[!] Failed to execute! Problem on path %d!\n", (int)vf1);
                continue;
            }
            
            free(seqs); // Is it still valid tho?
        } // End Token Space
        
        // check for issues
        if (problem == true){
            printf("[!] Problem detected! ABORTING\n");
            problem = false;
            free(args);
            free(arg);
            continue;
        }

        //for (int z = 0; z < slots*2; z++){
            // Close all that stuff
        //    close(pipes[z]);
        //}
// -------------------------------------------------------------- //
        /*
        bool isLS = false;
        bool isCD = false;
        bool isCat = false;
        //bool isTac = false; // Reverse read, keep lines same
        int size = slots;
        
        // Process the request tokens
        // Go thru x (calls)
        // and thru y (parts of call)
        for (int c = 0; c < size; c++){
            // <Insert C++ Joke Here>
            char *call;
            for (int p = 0; p < slots2; p++){
                
            }
        }

        for (int x = 0; x < size; x++){
            // Look at parts
            char *cur = args[x];
            
            //```````````
            
            if (isLS){
                int val = 0;
                // Looking for 1 arg
                if (strcmp(cur,"-l")){
                    // -L 1
                    val = 1;
                }else if (strcmp(cur,"-1")){
                    // -1
                    val = 2;
                }
                isLS = false;
                printFiles(val);
            }else if (isCD){
                // Looking for a path string?
                if (strcmp(cur,".") == 0){
                    // Current, so stay here...
                    isCD = false;
                    printf("BUMP");
                    continue;
                }
                
                
                bool isABS = false;
                bool found = false;
                char *fill = "/";
                char *cur2 = getcwd(NULL,0);
                if (cur[0] == '/' || cur[0] == '\\'){
                    // Cant be abs path
                    fill = "";
                    isABS = true;
                }
                
                int a1 = 5;
                int a2 = 5;
                char *s1 = malloc(strlen(cur)+strlen(cur2)+strlen(fill)-1);
                int f1 = sprintf(s1, "%s%s%s", cur2, fill, cur);
                char *s2 = malloc(strlen(cur)+strlen(fill)-1);
                int f2 = sprintf(s2, "%s%s", fill, cur);

                a1 = chdir(s1);
                if (a1 == -1){
                    // Thats not a valid path...
                }else{
                    found = true;
                }

                a2 = chdir(s2);
                if (a2 == -1 && !found){
                    // Thats not a valid path...
                }else{
                    found = true;
                }
                free(s1);
                free(s2);
                isCD = false;
            }else if (isCat){
                // Find file to cat
                
                //doCat(cur);
                
                isCat = false;
                continue;
            }
            //
            
            if (strcmp(cur,"ls")){
                // ls
                isLS = true;
                continue;
            }else if (strcmp(cur,"exit")){
                // Thats odd
                free(args);
                free(arg);
                exit(EXIT_SUCCESS);
            }else if (strcmp(cur,"cat")){
                isCat = true;
            }else if (strcmp(cur,"tac")){
                
            //}else if (){
            }else if (strcmp(cur,"cd")){
                // Change working directory
                isCD = true;
                continue;
            }else if (strcmp(cur,"|")){
                // Pipin
                // Check if pipe is valid
                if (x > 0 && x+1 < size){
                    // good
//                    do_pipe p = (do_pipe)newPipedOp( args[x-1], args[x+1] );
//                    launchPipedOp(p);
                    
                    
                }else{
                    // bad!
                    break;
                }
            }else{

                // Load a program?
                FILE *f = popen("./ ~~~","r"); // Check read vs write here
                if (f == NULL){
                    printf("[!] FAILED TO CREATE PIPE! ABORTING!\n");
                }
            }
         */
            free(args);
            free(arg);
            
        }while (isRunning);

    // Go until exit
    return ret;
}

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
        printf("BEEP! Im in %s \n",str);
        // Chop input down to parent
        int at = 0;
        for (int z = 0; z < strlen(str); z++){
            char cur = str[z];

            if (cur == '\0' && at == 0){
                // there are no slashes
                at = z;
                printf("at break %d\n",at);
                break;
            }
            if (cur == '/' || cur == '\\'){
                // Slashes found
                at = z;
                printf("at %d\n",at);
            }
        }
        
        // Alloc space and edit in null terminator
        char *str2 = malloc(sizeof(char) * at);
        strncpy(str2, str, at);
        str2[at] = '\0'; // Make sure to null the END

        // Set it as the new path
        int a = chdir(str2);
        if (a == -1){
            // Thats not a valid path...
            printf("[!] Crippling error, cd .. FAILED!\n");
            free(str);
            free(str2);
            return false;
        }
    printf("done~~~\n\n");
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
    char *s1 = malloc(strlen(cur)+strlen(cur2)+strlen(fill)-1);
    int f1 = sprintf(s1, "%s%s%s", cur2, fill, cur);
    if (f1 < 1 ){
        printf("[!] Error sprintf\n");
    }
    char *s2 = malloc(strlen(cur)+strlen(fill)-1);
    int f2 = sprintf(s2, "%s%s", fill, cur);
    if (f2 < 1){
        printf("[!] Error Sprintf\n");
    }

    a1 = chdir(s1);
    if (a1 == -1){
        // Thats not a valid path...
    }else{
        found = true;
    }

    a2 = chdir(s2);
    if (a2 == -1 && !found){
        // Thats not a valid path...
    }else{
        found = true;
    }
    free(s1);
    free(s2);
    free(cur2);
    //free

    return found;
}

bool forkChild(char* prog, char** args, int in, int out){
    //printf("\n");
    // open it up
    int fval = fork();
    if (fval < 0){
        // Failed
        printf("[!] Fork failed! Aborting!\n");
        return false;
    }else if (fval == 0){
        // CHILD
        int rez = execvp(prog, args);
        if (rez == -1){
            printf("[!] Exec failed! \n");
        }
            //printf("%d\n",rez);
    }else if (fval > 0){
        // PARENT
        //int f = dup2(,);
        //if (f == -1){
            
        //}
        int cstat = 0;
        pid_t ret  = wait(&cstat);
        if (ret == -1){
            // Problem!
            printf("[!] Problem detected while waiting for %s ! Dropping... \n",prog);
            return false;
        }
        
        
    }

    //int f = dup2(); // Redirect output to input

    return true;
}
/*
bool forkChild(char* prog, char* args, int in[2], int out[2], bool useSTD){ // In and out need to be FDs

    if (useSTD){
        //
    }
    int fval = fork();
    if (fval < 0){
        // Failed
        printf("[!] Fork failed! Aborting!\n");
        return false;
    }else if (fval == 0){
        // CHILD
        //
        // Setup STDIN pipe and disable writing
        close(in[WRITE_END]);
        int f1 = dup2(in[READ_END],STDIN_FILENO);
        if (f1 == -1){
            perror("[!] Failure to duplicate IN pipe!\n");
        }
        
        // Setup STDOUT pipe and disable reading
        close(out[READ_END]);
        int f2 = dup2(out[WRITE_END],STDOUT_FILENO);
        if (f2 == -1){
            perror("[!] Failure to duplicate OUT pipe!\n");
        }
         
        // exec it
        int rez = execvp(prog, args);
        if (rez == -1){
            perror("[!] Failure to exec func !\n");
            exit(EXIT_FAILURE);
        }
        
            //printf("%d\n",rez);
    }else if (fval > 0){
        // PARENT
        
        //if (doClose){}
        // Check which FD it is
        if (!useSTD){
            close(in[WRITE_END]);
            close(in[READ_END]);
            close(out[WRITE_END]);
            close(out[READ_END]);
        }
        
        int cstat = 0;
        pid_t ret  = wait(&cstat);
        if (ret == -1){
            // Problem!
            printf("[!] Problem detected while waiting for %s ! Dropping... \n",prog);
            return false;
        }
        
        
    }
    
    //close(fd[READ_END]);
    //close(fd[WRITE_END]);

    //int f = dup2(); // Redirect output to input

    return true;
}
/*/
bool fileExists(char *name){
    struct stat s;
    int res = stat(name, &s);
    if (res == -1){
        return false;
    }
    // Bypass read restrictions
    printf("\nFE: Read? %s %o <>\n",name, (int)s.st_mode);
    return !(res) && S_ISREG(s.st_mode) ;//&& b1;
}
