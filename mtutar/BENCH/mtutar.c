/*
 * File:   mtutar.c
 * Author: Brett Kriz
 *
 * Created on January 28, 2016, 7:18 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>
#include <stdint.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <libgen.h>

#define HELP_1 "-x <archive.mtu> to extract a file"
#define HELP_2 "-d <archive.mtu> <file name to delete w/ extension>   to delete a file from the archive!"
#define HELP_3 "-a <archive.mtu> <File1 w/ extension> <File2 w/ extension> ..."

#define FILLER '\0'
#define D_TRUE 0x01
#define D_FALSE 0x00

#define _LEN_NAME 256
#define _LEN_SIZE 8
#define _LEN_MODES 4
#define _MAGIC "CS3411"

typedef struct {
    char file_name[257]; /* May contain spaces, is null-terminated --- must be the basename() of the file (man 3 basename) */
    uint64_t file_size; /* File size in bytes. 8 bytes */
    uint8_t deleted;    /* 0=file, 1=deleted file, 1 byte */
    mode_t mode;        /* See "man 2 stat", 4 bytes */
    bool completed;     // Is our file offically whole?
} mtuhdr;

enum _flags {
    _READ   = 004,
    _READ2  = 040,
    _WRITE  = 002,
    _WRITE2 = 020,
    _EOF    = 010
};

// Primary Functions
bool archive( int arg_n, char** arch );
bool delete( char arch[], char file[], bool setAs );
bool extract( char arg[] );
void show_Help();

// Tools Functions
bool fileExists( char *name );
int validArchive(void);
mtuhdr newHDR(void);
int getNextBit();
char getNextByte();
bool addToTbl( mtuhdr arg );
long fileSize( char *name );
void bnr(void);
bool createBlank(char arg[]);
bool validDir(char *name);
bool archiveReadable(char *name);
bool archiveWriteable(char *name);

// VARS
bool expect_EOF = false;
bool halt_flag = false;
bool empty_arch = false;
FILE *f_in = NULL;
FILE *f_out = NULL;

int bitsLeft = 0;
int tbl_size = 0;
mtuhdr tbl[50];

int main(int argc, char** argv) {
    printf("\n");
    for (int x = 0; x <argc; x++){
        printf("%s @ %d\n",argv[x],x);
    }

    printf("\n");
    
    
    
    if (argc <= 1){
        // Print help
        show_Help();
        return (EXIT_SUCCESS);
    }
    
    bool b1 = !strcmp("-x",argv[1]);
    bool b2 = !strcmp("-d",argv[1]);
    bool b3 = !strcmp("-a",argv[1]);
    
    
    
    if (b3 && argc == 3){
        empty_arch = true;
    }else if (argc < 3){
            printf("\n[i] Malformed arguments! Please enter more arguments!\n");
            return (EXIT_FAILURE);
    }else{
        printf("Starting %s...\n\n",argv[0]);
    }
    // Decode what must be done
    //char[] op_arg = argv[1];
    
    bool bvd = validDir(argv[0]);
    bool bar = archiveReadable(argv[2]);
    bool baw = archiveWriteable(argv[2]);

    
    bool res = true;
    if (b1){
        if (bar == true && bvd == true && baw == true){
            res = extract(argv[2]);
        }else{
            printf("\n[!] Archive isn't READABLE by permissions OR Directory isn't WRITEABLE!\n");
            exit(EXIT_FAILURE);
        }
    }else if (b2){
        if (baw == true && bvd == true && baw == true){
            res = delete(argv[2], argv[3], false);
        }else{
            printf("\n[!] Archive isn't WRITEABLE by permissions OR Directory isn't WRITEABLE!!\n");
            exit(EXIT_FAILURE);
        }
    }else if (b3){
        if (bvd == true){
            res = archive(argc, argv);
        }else{
            printf("Directory isn't WRITEABLE! %d %d",bvd, baw);
            exit(EXIT_FAILURE);
        }
    }else{
        printf("\n[!] Command argument isn't valid! %s\n\n",argv[1]);
        show_Help();
    }

    printf("\n Made it to the end! Function returned %d \n",res);
    fflush(stdout);
    return (!(res));
}

// Delete a file from the archive
bool delete( char arch[], char file[], bool setAs ){
    // Deal with the archive
    int count = 0;
    int matches = 0;
    bool b1 = fileExists(arch);
    if (!b1) {
        return b1;
    }
    // Okay now open it
    f_in = fopen(arch, "r+b");
    if (f_in == NULL){
        // Cry and give up
        printf("\n%s couldn't be opened!",arch);
        //fclose(f_in);
        exit(EXIT_FAILURE);
    }
    /*
    int i1 = f_in->_flags & (_READ | _EOF| _WRITE);
    if ( i1 != _READ || i1 != _WRITE){
        // Thats bad
        fclose(f_in);
        printf("\n [!] Archive lacks permissions to continue! (%o,%o)\n",i1,f_in->_flags);
        exit(EXIT_FAILURE);
    }
    //*/
    bool b2 = validArchive();
    if (b2 == 0){
        // Bad Archive
        printf("\nThe archive is invalid and cannot be opened!\nExiting...");
        fclose(f_in);
        exit(EXIT_FAILURE);
    }

    // Now deal with the file
    // FILE *target = NULL;
    /*
    bool b3 = fileExists(file);
    if (!b3) {
        return b3;
    }
    //*/

    // Cycle through and find this file
    mtuhdr file2 = newHDR();
    //file2.file_size = (uint64_t)fileSize(file);
    for (int x = 0; x < strlen(file) ; x++){
        // Buffer the file
        file2.file_name[x] = file[x];
    }

    expect_EOF = true;
    bool go = true;
    while(go && !feof(f_in)){
        //Look at parts of the archive
        char cur = '~';
        bool isMatch = true;
        bool closed = false;
        bnr();
        for(int x = 0; x < _LEN_NAME; x++){

            cur = (char)getNextByte();
            if (halt_flag == true) {go = false; break;}

            printf("%c",cur);
            if (closed == true) { continue; } // Progress the read
            // Now we need to deal with this
            bool lb1 = cur != file2.file_name[x];
            bool lb2 = cur == '\0';
            if (lb1){
                isMatch = false;
            }

            if (lb2 || lb1){
                // Good, we dont need to search more
                closed = true;
            }

        }
        if (!go) {break;}

        printf("\n\tIs match?: %d \n",isMatch);
        fflush(stdout);

        uint64_t found_size = 0;
        // We need to compare other things now
        for (int x = 0; x < _LEN_SIZE; x++){
            // stack up with shifts
            char cur = (char)getNextByte();
            if (halt_flag == true) {return true;}
            uint64_t cint = 0;

            cint = (uint64_t)(((int) cur) << 8*x);
            found_size += cint;
        }
        // find out if its deleted already...
        char del = (char)getNextByte();
        if (halt_flag == true) {return true;}

        int fill = (int)D_TRUE;
        //uint isAt = (uint)D_FALSE;
        if (setAs == true){
            fill = (int)D_FALSE;
            //isAt = (uint)D_TRUE;
        }

        int dist = _LEN_MODES + found_size;
        if (isMatch == true){
            // Good match
            int fval = fseek(f_in, -1, SEEK_CUR);
            if (fval == -1) {printf("\n[!] Fseek fatal failure! Amount: %d\n",-1);return true;}
            // Crash point
            int sizer = sizeof(del);
            size_t fws = fwrite(&fill,sizer,1,f_in);
            printf("\n..Match! %d Bytes wrote\n",(int)fws);
            matches++;
            //go = false;
        }else{
            // Not this, go forward
            printf("\n..Not a match, skipping %d bytes forward...\n", dist);
        }
        int fval = fseek(f_in, dist,SEEK_CUR);
        if (fval == -1) {printf("\n[!] Fseek fatal failure! Amount: %d\n",dist); return true;}

        count++;

        //printf("\n~~~> %d\n",(int)ftell(f_in));
        fflush(stdout);
    }// End while

    // Clean up

    expect_EOF = false;
    printf("\n\tDeletion of %s completed in archive %s! (Found %d files, %d file(s) deleted)\n",file, arch, count,matches);
    fflush(stdout);
    return true;
}

// Create an archive
bool archive( int arg_n, char** arg ){

    if (empty_arch == true){
        // jump
        return createBlank(arg[2]);
    }
    //char arch[] = arg[2];
    bool adding = fileExists(arg[2]);
    int count = 0;

    // Now enter
    f_out = fopen(arg[2], "ab");
    if (f_out == NULL){
        printf("\n[!] File cannot be opened! %s\n", arg[2]);
        exit(EXIT_FAILURE);
    }
    /*
    // Check permissions
    int i1 = f_out->_flags & (_READ | _EOF| _WRITE);
    if ( i1 != _READ || i1 != _WRITE){
        // Thats bad
        fclose(f_out);
        printf("\n [!] Archive lacks permissions to continue! (%o,%o)\n",i1,f_out->_flags);
        exit(EXIT_FAILURE);
    }
    //*/
    if (!adding){
        // Okay add magic key
        int wrote = fwrite(&_MAGIC,sizeof(_MAGIC)-1,1,f_out);
        if (wrote == 0){
            // Fail
            printf("\n[!] Cannot write to file! Aborting! %d\n", wrote);
            exit(EXIT_FAILURE);
        }
    }

    for (int x = 3; x < arg_n; x++){
        bnr();
        printf(" %s\n", arg[x]);
        // Generate HDRs here
        // Put them into TBL for later
        if (fileExists(arg[x]) == false){
            // Ooops!
            printf("\t '%s' was not found! Skipping...\n",arg[x]);
            continue;
        }

        long size = fileSize(arg[x]);
        if (size == -1){
            //  Ooops!
            printf("\t '%s' has a size problem! Skipping...\n",arg[x]);
            continue;
        }
        // Good enough to proceed

        mtuhdr cur = newHDR();
        cur.deleted = D_FALSE;
        char *name = arg[x];
        //printf("\n\t(base name was causing segfaults)\n");
        //
        
        // Clean input
        int start_at = 0;
        for (int x = 0; x < _LEN_NAME; x++){
            char cur = name[x];
            
            
            if (cur == '\0'){
                // there are no slashes
                start_at = 0;
                break;
            }
            if (cur == '/' || cur == '\\'){
                // Slashes found
                start_at++;
                break;
            }
            start_at++;
        }
        printf("\n\n @ LOOP: %d :\n",(int)strlen(name)-start_at);
        fflush(stdout);
        
        
        for (int z = 0; z < strlen(name)-start_at; z++){
            // Buffer the file
            int where = z+start_at;
            
            char dis = name[where];
            printf("%d%c ",where,dis);
            cur.file_name[z] = dis;
            //printf("%c",cur.file_name[z]);
        }
        //*/
        printf("\n%s\n",cur.file_name);
        struct stat s;
        stat(arg[x], &s);
        printf("\nMODE: %o",s.st_mode);
        cur.mode = s.st_mode;
        cur.file_size = (uint64_t)s.st_size;

        cur.completed = true;
        addToTbl(cur);

        // WRITE OUT
        // File name
        int wrote = fwrite(&cur.file_name,_LEN_NAME,1,f_out);
        if (wrote == 0){
            printf("\n[!] Cannot write to file! Aborting! %d\n", wrote);
            exit(EXIT_FAILURE);
        }
        // FILE SIZE, little endian?
        fwrite(&cur.file_size,1,8,f_out);
        /*
        for (int z = 0; z < _LEN_SIZE; z++){
            char *zcur = (char)( ( ((int)cur.file_size) >> 8*z) & (0xFF) );
            printf(" %d",zcur);
            fwrite(&zcur,1,1,f_out);
        }
        //*/
        // 1 byte, DELETED
        fwrite(&cur.deleted,1,1,f_out);
        // 4 byte, MODE
        fwrite(&cur.mode,4,1,f_out);

        // n bytes, BODY

        char *body = (char*) malloc( cur.file_size*8 );
        if (body == NULL){
            printf("\n[!!] Couldn't allocate enough memory to write %s!(%d Bytes)\n\tShutting down...\n",cur.file_name, (int)cur.file_size);
            fclose(f_out);
            exit(EXIT_FAILURE);
        }
        f_in = fopen(name, "rb"); // cur.file_name
        for (int x = 0; x < cur.file_size ; x++){
            // Read it in!
            char cur2 = getNextByte(); // SEGFAULT POSSIBLE HERE
            if (halt_flag == true && x+1<cur.file_size) {
                printf("\n[!] Malformed file detected!\n\tShutting down...\n");
                fclose(f_out);
                exit(EXIT_FAILURE);
            }
            //printf("Cur: %d",(int)cur2);
            body[x] = cur2;
        }


        int wroteOut = fwrite(body, cur.file_size, 1, f_out);
        if (wroteOut == 0){
            // Something is wrong.
            printf("\n[!] Wrote out 0 bytes for %d!", (int)cur.file_size);
        }
        bool ret = !((bool)(fclose(f_in)));
        if (ret != true){
            printf("\n[!] File could not be closed? wut");
            exit(EXIT_FAILURE);
        }
        free(body);

        count++;
        printf("\n#%d : %s wrote out with %d Bytes of data...\n\n",count,arg[x],(int)cur.file_size);
    }
    // Clean up
    fflush(stdout);
    //fflush(f_out);
    //fclose(f_out);

    printf("\n %d files safely archived into %s!\n", count, arg[2]);
    return true;
}

// Extract files from an archive
bool extract(char arg[]){
    // Extract a file from an archive
    bool b1 = fileExists(arg);
    if (!b1) {
        return b1;
    }
    // Okay now open it
    f_in = fopen(arg, "rb");
    if (f_in == NULL){
        // Cry and give up
        printf("\n%s couldn't be opened!",arg);
        //fclose(f_in);
        exit(EXIT_FAILURE);
    }
    /*
    int i1 = f_in->_flags & (_READ | _EOF| _WRITE);
    if ( i1 != _READ || i1 != _WRITE){
        // Thats bad
        fclose(f_in);
        printf("\n [!] Archive lacks permissions to continue! (%o,%o)\n",i1,f_in->_flags);
        exit(EXIT_FAILURE);
    }
    //*/
    bool b3 = archiveReadable(arg);
    if (b3 == false){
        // Bye;
        fclose(f_in);
        exit(EXIT_FAILURE);
    }

    int b2 = validArchive();
    if (b2 <= 0){
        // Bad Archive
        printf("\nThe archive is invalid and cannot be opened!\nExiting...");
        fclose(f_in);
        exit(EXIT_FAILURE);
    }
    
    
    int count = 0;
    bool breakout = false;
    // Read and process the data
    while(!feof(f_in) && breakout == false){
    // @mtuhdr pullHDRFromArch()
        mtuhdr part = newHDR();

        // Populate the HDR with data
        // First FILE NAME
        
        //fseek to the desired position
        if (breakout == true){
            // Leave
            breakout = false;
            break;
        }
        
        
        bool inWhiteSpace = false;
        for (int x = 0; x < _LEN_NAME; x++){

            char cur = '~';
            if (x == 0){expect_EOF = true;}
            // I realized too late !feof only works AFTER the end is hit
            cur = getNextByte();
            if (expect_EOF == true && halt_flag == true){
                //We've hit our exit param!
                breakout = true;
                break;
            }
            expect_EOF = false;

            if (cur == '\0' && !inWhiteSpace){
                bnr();

                //break;
                inWhiteSpace = true;
            }
            // Tack it onto the answer
            if (!inWhiteSpace){
                part.file_name[x] = cur;
            }else{
                //printf(".");
            }
        }

        if (breakout == true){
            // Leave
            breakout = false;
            break;
        }

        printf("\nFILE NAME: %s\n",part.file_name);
        // FILE SIZE, BINARY, LITTLE ENDIAN

        for (int x = 0; x < _LEN_SIZE; x++){
            // stack up with shifts
            char cur = getNextByte();
            if (halt_flag == true) {return false;}
            uint64_t cint = 0;

            cint = (uint64_t)(((int) cur) << 8*x);
            part.file_size += cint;
        }
        printf("\nSIZE: %d \n",(int)part.file_size);

        // EXISTS/DELETED
        part.deleted = (uint8_t)getNextByte();
        printf("\nEXISTS: %d \n",part.deleted == D_FALSE);

        if (part.deleted == D_TRUE){
            // Why bother? Why even save it?
            //                  +4 bytes because of MODES loop
            fseek(f_in, part.file_size + _LEN_MODES, SEEK_CUR);
            printf("\nSkipping Deleted File %s! Seeking %d Bytes ahead... \n", part.file_name,(int)(part.file_size + _LEN_MODES));
            count++;
            continue;
        }

        // MODES
        uint32_t bAns = 0;
        int z = fread(&bAns, _LEN_MODES, 1, f_in);

        if (z == 0){
            if (expect_EOF == false){
                printf("\n[!] Unexpected EOF in EXTRACT!\n");
                fclose(f_in);
                exit(EXIT_FAILURE);
            }else{
                printf("\n\t[i] Expected EOF in EXTRACT!\n");
                fclose(f_in);
                halt_flag = true;
                return '~';
            }
        }
        part.mode = (mode_t)bAns;
        printf("\nMode permission: %o",(int)part.mode);

        // FILE CONTENTS
        // Check if theres a file that exists

    //@void copyHDRFromArch(mtuhdr arg)
        if (fileExists(part.file_name)){
            // Prompt the user for input

            //Check for input...
            char in = FILLER;
            bool go = true;
            bool decision = false;
            int iters = 0;
            do{
                if (iters > 25){
                    // Wow.
                    printf("\nWow. No file for you.\n");
                    break;
                }
                printf("\nFile '%s' exists. Overwrite it? [y/n]\n", part.file_name);
                scanf("%c", &in);
                bool isY = (in == 'y' || in == 'Y');
                bool isN = (in == 'n' || in == 'N');
                go = !(isY || isN);
                if (go){
                    printf("..What?\n");
                }else{
                    // Mark out answer
                    decision = isY;
                }
                iters++;
            }while(go);

            // Now that we know

            if (!decision){
                // We're done here!
                printf("\nFile overwrite declined for %s! Skipping %d Bytes ahead...\n", part.file_name, (int)part.file_size);
                fseek(f_in, part.file_size , SEEK_CUR);
                count++;
                continue; // Abandon this iteration
            }else{
                printf("\tFile %s will be overwritten!\n",part.file_name);
            }
        } // End prompt

        // Overwrite it
        // If the file size in the header says there are N bytes,
        //there will be N bytes here.
        char *body = (char*) malloc( part.file_size*8 );
        if (body == NULL){
            printf("\n[!!] Couldn't allocate enough memory to write %s!(%d Bytes)\n\tShutting down...\n",part.file_name, (int)part.file_size);
            fclose(f_in);
            exit(EXIT_FAILURE);
        }

        // Cite: Scott Kuhl
        for (int x = 0; x < part.file_size ; x++){
            // Read it in!
            char cur = getNextByte(); // SEGFAULT POSSIBLE HERE
            //printf("Cur: %d",(int)cur);
            body[x] = cur;
        }

        f_out = fopen(part.file_name, "wb");
        if (f_out == NULL){
            // Fail
            printf("\n[!] Cant open %s for writing! \n",part.file_name);
            exit(EXIT_FAILURE);
        }
        int wroteOut = fwrite(body, 1, part.file_size, f_out);
        if (wroteOut < 1){
            // Something is wrong.
            printf("\n[!] Wrote out 0 bytes for %d!", (int)part.file_size);
        }

        // Be Tidy
        free(body);
        fflush(f_out);
        fclose(f_out);
        //Modify the MODE of the file!
        int ch_val = chmod(part.file_name, part.mode); //part.mode
        printf("\nCHmod: %d",ch_val);
        f_out = NULL;
        // Cleanup and prepare for another run

        part.completed = true;
        addToTbl(part);

        count++;
        printf("\n\tFile #%d, %s, extracted and stable!\n", count, part.file_name);
    }

    fflush(stdout);
    //fclose(f_in);
    printf("\n\nExtraction of %s completed! %d files found! %d files extracted! \n",arg,count,tbl_size);
    
    return true;
}

bool createBlank(char arg[]){
    if (fileExists(arg)){
        // Oh cool
        return true;
    }
    f_in = fopen(arg, "w");
    if (f_in == NULL){printf("\n Problem creating file!"); return true;}

    // Okay add magic key

    int ret = fwrite(&_MAGIC,sizeof(_MAGIC)-1,1, f_in);
    if (ret <= 0){
        printf("\n0 bytes were wrote! Why??? %d\n",ret);
    }
    //fflush(f_in);
    fclose(f_in);
    
    printf("\n\t Blank archive created...'%s'\n",arg);
    return true;
}

// Use seek to find the file size in bytes
long fileSize(char *name){
    // Cite: Scott Kuhl
    FILE *temp2 = fopen(name, "r");
    long ans = 0l;
    if (temp2 == NULL){
        ans = -1l;
    }else if( fseek(temp2, ans, SEEK_END) != 0 ){
        ans = -1l;
        fclose(temp2);
    }else{
        ans = ftell(temp2);
        fclose(temp2);
    }

    return ans;
}

// Display help menu
void show_Help(){
    printf("%s\n%s\n%s\n\n",HELP_1,HELP_2,HELP_3);
}

// Use stat to check if a file exists and isnt a directory
bool fileExists(char *name){
    struct stat s;
    int res = stat(name, &s);
    // Bypass read restrictions
    bool b1 = s.st_mode&_READ || s.st_mode&_READ2;
    printf("\nFE: Read? %s %d %o <>\n",name,b1, (int)s.st_mode);
    return !(res) && S_ISREG(s.st_mode) ;//&& b1;
}
// */

bool validDir(char *name){
    struct stat s;
    int res = stat(name, &s);
    // Bypass read restrictions
    bool b1 = !access(name,W_OK);
    //bool b2 = s.st_mode & S_IROTH;
    printf("\nVD: WRITE? %s %d\n",name, b1);
    return !(res) && b1; //&& ( b2 );
}

bool archiveReadable(char *name){
    struct stat s;
    int res = stat(name, &s);
    // Bypass read restrictions
    bool b1 = s.st_mode&_READ || s.st_mode&_READ2;
    printf("\nFE: Read? %s %d\n",name,b1);
    return !(res) && S_ISREG(s.st_mode);// && b1; //&& (s.st_mode & S_IROTH );// && (s.st_mode & S_IROTH )
}

bool archiveWriteable(char *name){
    struct stat s;
    int res = stat(name, &s);
    // Bypass read restrictions
    bool b1 = (s.st_mode & _WRITE ) || s.st_mode&_WRITE2;
    printf("\nAW b1:%d %o\n",b1,(int)s.st_mode);
    return !(res) && S_ISREG(s.st_mode); //&& b1;// && (s.st_mode & S_IROTH )
}

// Checks for the magic number of an file
int validArchive(void){
    // Only take files
    
    bool flag = false;
    char arg[] = "CS1212";
    //!feof(f_in)
    for (int x = 0; x<6; x++){
        // Compare chars
        char temp = '~';
        int z = fread(&temp, 1, 1, f_in);

        if ( z == 0 ){
            if (x != 0){
                printf("\n\t[!] Unexpected EOF found!\n");
                fclose(f_in);
                exit(EXIT_FAILURE);
                return false;
            }else{
                // As expected, an EOF
                printf("\n\t[i] Expected EOF found!\n");
                fclose(f_in);
                return -1;
            }
        }

        arg[x] = temp;
    }

    //arg[7] = '\0';

    flag = !strcmp(_MAGIC,arg);

    printf("\nValid Archive? %s\n",flag ? "True" : "False");
    if (!flag){
        exit(EXIT_FAILURE);
    }
    
    return flag;
}
/*
char[] B2S(bool arg){
    return (arg ? "True" : "False");
}
//*/

// Sets up a new HDR on the fly
mtuhdr newHDR(void){
    mtuhdr x;
    x.completed = false;
    x.deleted = D_FALSE;
    x.file_size = 0;

    //x.file_name = char[256];
    for (int z=0; z<256; z++){
        x.file_name[z] = FILLER;
    }

    return x;
}

// Gets the next BIT from f_in
int getNextBit(){
    char temp = '~';
    if (bitsLeft == 0){
        int z = fread(&temp, 1, 1, f_in);

        if (z == 0){
            printf("\nEOF found!\n");
            return -1;
            //exit(EXIT_FAILURE);
        }
        bitsLeft = 8;
    }

    bitsLeft--;
    return (temp >> bitsLeft) & 1;
}

// Gets the next BYTE from f_in
char getNextByte(){ // Arbitrarly uses f_in
    char ans = '~';
    int z = fread(&ans, 1, 1, f_in);

    if (z == 0){
        if (expect_EOF == false){
            printf("\n[!] Unexpected EOF!\n");
            fclose(f_in);
            exit(EXIT_FAILURE);
        }else{
            printf("\n\t[i] Expected EOF!\n");
            fclose(f_in);
            halt_flag = true;
            return '~';
        }
    }

    return ans;
}

// Adds a HDR to a table. Currently broken for storage
bool addToTbl(mtuhdr arg){
    if (arg.completed != true){ return false; }
    if (tbl_size < 50){
        tbl[tbl_size] = arg;
    }else if (tbl_size == 50){
        printf("\n\t[!] Non critical error! Tbl is overflowing!\n");
    }
    tbl_size++;
    return true;
}

void bnr(void){
    // print a banner
    printf("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
}