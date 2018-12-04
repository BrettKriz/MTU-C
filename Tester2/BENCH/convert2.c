/* Brett Kriz
 * 
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>

#define filename "file.dat"

#define _char 0
#define _char_s 7

#define _int 1
#define _int_bits 5 // Add 1 to it to calculate the actual number of bits?

#define _float 2
#define _float_s 32

#define _EOF 3

typedef union Data{
    signed int i;
    char c[4];
    float f;
} numbers;

int body();
int C2I(char arg);
void display(numbers arg, int type);

unsigned char data[1048576];
//unsigned char buffer[2048];
int size = 1048576;
unsigned char temp = '~';
unsigned char filler = '?';

int main(void) {
    // Prints welcome message...
    //std::cout << "Welcome ..." << std::endl;

    return body();
}

int body(){
     FILE *f = fopen(filename, "r");
    
    if (f == NULL){
        // Error!
        printf("The file could not be found!");
        fclose(f);
        exit(EXIT_FAILURE);
    }
    
    // Get variables involved for the read
    //fprintf(f);

    int real_size = 0;

    for (int i = 0; i < size; i++){
        data[i] = filler;
        //buffer[i] = filler;
    }
    
    
    
    printf("\n");
    //printf("Read '%d' from file which is character '%c' and hex 0x%x\n", data, data, data);
            
    for(int x = 0; x < size; x++){
        // shove the current char into cur
        if (x == size*0.95){
            printf("\n[!] USER WARNING! File size is reaching the limit of %d!\n", size);
        }
        
        char z = '~';
        int count = fread(&z, 1, 1, f);
        
        if (count <= 0){
            // Were done here.
            real_size = (unsigned int)x;
            break;
        }
        
      //  printf("%c",z);
        if (z != '1' || z != '0'){x--;continue;}
        data[x] = z;
    }
    
    if (fread(&temp, 1, 1, f)){
        // Our buffer was too small...
        printf("The file exceeded the 2047 bit limit!");
        return EXIT_FAILURE;
    }
    
    fclose(f);
    /*
    printf("\n END \n");
    // *
    for (int i = 0; i < size; i++){
        printf("%c",data[i]);
    }
    printf("\n");
    for (int i = 0; i < real_size; i++){
        printf("%c",data[i]);
    }
    printf("\n");
    for (int i = 0; i < real_size; i++){
        for (int h = 0; h < i; h++){
            printf(" ");
        }
        printf("^%d \n",i);
    }
    */
    
    // ---------------------------------------------------- //
    bool go = true;
    int at = 0;
    int at2 = 0;
    
    // Now lets decode it
    while(go){
        // Checks
        bool b1;
        bool b2;
        bool b3;
        bool b4;
        int gofor;
        int type = -1;
        
        
        // Grab 2 chars and process.
        if (at >= real_size || at+1 >= real_size){
            // We found the end
            break;
        }else{
            char a0 = data[at];
            char a1 = data[at+1];
            
            // Consider the following
            b1 = (a0 == '0' && a1 == '0');
            b2 = (a0 == '0' && a1 == '1'); // int
            b3 = (a0 == '1' && a1 == '0'); // float
            b4 = (a0 == '1' && a1 == '1' && true) || (a0 == filler || a1 == filler); // EOF
        }

        //printf("\n\nBOOLS: %d %d %d %d \n",b1,b2,b3,b4);
        
        if (b4){
            break;
        }

        if (b1){
            gofor = _char_s;
            type  = _char;
        }else if (b2){
            gofor = _int_bits;
            type  = _int;
        }else if (b3){
            // This needs more
            gofor = _float_s;
            type  = _float;
        }else{
            // Fatal error
            break;
        }
        
        // Increment at for a read
        at += 2;
        numbers arg;
        arg.i = 0;
        at2 = at;
        bool allow = true;
        // Read a length
        while (at < at2+(gofor)){
            // Read in the bits
            char cur = data[at];
            //printf("\n%c@%d     ",cur,at);
            if (cur == filler){
                // Guess we're done..
                printf("File ended abruptly! Check the file or buffer for any mistakes! %c and %d",cur,at);
                exit(EXIT_FAILURE);
            }
            arg.i = arg.i << 1;
            arg.i += C2I(cur);
            /*
            printf("%d, ",arg.i);
            //printf("~%d~ ",arg.c[0],arg.c[1],arg.c[2],arg.c[3]);
            printf("%c, ",arg.c[0]);
            printf("%f",arg.f);
             */

            if ( b2 && (at+1 == at2+gofor) && allow ){
                // Adjust for int length stuff
                gofor += arg.i + 1;
                arg.i = 0;
                allow = false;
                //printf(" * ");
            }
            at++;
        }

        display(arg, type);
        // Increment and continue
        //at++;
        //break;
    }
    
    return EXIT_SUCCESS;
}

void display(numbers arg, int type){
    // Char int float
    //printf("\n\n");
    switch(type){
        case 0: printf("char = %c\n",arg.c[0]); break; // Check this...
        case 1: printf("int = %d\n",arg.i); break;
        case 2: printf("float = %f\n",arg.f); break;
        default: printf("[!] display was given an incorrect type-arg! %d",type);break;
    }
}

int C2I(char arg){
    if (arg == '0'){
        return 0;
    }else if (arg == '1'){
        return 1;
    }else{
        // Uh-oh!
        printf("[!] Problem in C2I. Arg is not 0 or 1! %c",arg);
        //exit(EXIT_FAILURE);
    }
    return -1;
}
