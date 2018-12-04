/* 
 * File:   client.c
 * Author: Brett Kriz
 * Auther: Scott Kuhl
 *
 * Created on April 9, 2016, 5:32 PM
 */

#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <netdb.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <arpa/inet.h>

#define _PASS "Meahoy, memoyay? MeyoyYOY, ladyonmamoy!" // Doodlebob

#define _GET "get"
#define _LIST "list"
#define _PUT "put"

#define _FND "found\n"

#define _NL "\n"
#define _MIN_ARG 4
#define _DEBUG      true // TURN ME OFF!!

#define NRM  "\x1B[0m"

#define RED  "\x1B[31m"
#define GRN  "\x1B[32m"

void do_connect(int sock, struct addrinfo *ai);
int do_socket(struct addrinfo *ai);
bool Send(int s, const void *buff, size_t len, int f);
bool createFile(char *arg, char *name);
bool SendFile(int sock, char *fn, char *base);
bool fileExists(char *name);
char* extract(char arg[]);
        char getNextByte();
int lol = 0;

FILE *f_in = NULL;

/* We will use getaddrinfo() to get an addrinfo struct which will
 * contain information the information we need to create a TCP
 * stream socket. The caller should use
 * freeaddrinfo(returnedValue) when the returend addrinfo struct
 * is no longer needed. */
struct addrinfo* do_getaddrinfo(const char* hostname, const char* port){
	/* First we need to create a struct that tells getaddrinfo() what
	 * we are interested in: */
	struct addrinfo hints; // a struct 
	memset(&hints, 0, sizeof(hints)); // Set bytes in hints struct to 0
	hints.ai_family = AF_INET;    // AF_INET for IPv4, AF_INET6 for IPv6, AF_UNSPEC for either
	hints.ai_socktype = SOCK_STREAM;  // Use TCP

	// hints.ai_flags is ignored if hostname is not NULL. If hostname
	// is NULL, this indicates to getaddrinfo that we want to create a
	// server.
	hints.ai_flags = AI_PASSIVE; 
	struct addrinfo *result;  // A place to store info getaddrinfo() provides to us.
	
	/* Now, call getaddrinfo() and check for error: */
	int gai_ret = getaddrinfo(hostname, port, &hints, &result);
	if(gai_ret != 0)
	{
		// Use gai_strerror() instead of perror():
		fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(gai_ret));
		exit(EXIT_FAILURE);
	}
	return result;
}

/* Create a socket. Returns a file descriptor. */
int do_socket(struct addrinfo *ai)
{
	int sock = socket(ai->ai_family,
	                  ai->ai_socktype,
	                  ai->ai_protocol);
	if(sock == -1)
	{
		perror("socket() error:");
		exit(EXIT_FAILURE);
	}
	return sock;
}

void do_connect(int sock, struct addrinfo *ai)
{
	if(connect(sock, ai->ai_addr, ai->ai_addrlen) == -1)
	{
		perror("connect() failure: ");
		exit(EXIT_FAILURE);
	}else{
            printf("Connecting to server\n");
        }
}


int main(int argc, char** argv)
{
    printf("\tARGC: %d\n",argc);
    for (int x=0; x < argc; x++){
        printf("%s\n",argv[x]);
    }
    printf("\n");
    
    if (argc < _MIN_ARG){
        // Too few!
        printf("[!] Not enough arguments (%d vs %d)! Please make sure to include host IP and Port # (1024 - 9999)!\n\tHalting...\n",argc,_MIN_ARG);
        exit(EXIT_FAILURE);
    }
    // Figure out what we are doing with the server
    char *op = argv[3];
    // Must invert strcmp value for bools
    bool b1 = !strcmp(op, _LIST);
    bool b2 = !strcmp(op, _PUT);
    bool b3 = !strcmp(op, _GET);
    bool b4 = b1 || b2 || b3;
    
    printf("\tBOOLS: %d %d %d\n",b1,b2,b3);
    
    if (!b4){
        // Not valid
        printf("[!] Invalid operation, try again\n");
        exit(EXIT_FAILURE);
    }
    
    if (!b1){
        if ((b2 || b3) && argc < _MIN_ARG+1){
            // Too few!
            printf("[!] Not enough arguments (%d vs %d)! Please make sure to include a file name!\n\tHalting...\n",argc,_MIN_ARG+1);
            exit(EXIT_FAILURE);
        }
    }
    char *hostname = argv[1];
    char *port = argv[2]; // can also be a name in /etc/services

    // getaddrinfo(): network address and service translation:
    struct addrinfo *socketaddrinfo = do_getaddrinfo(hostname, port);

    // socket(): create an endpoint for communication
    int sock = do_socket(socketaddrinfo);

    // connect(): initiate a connection on a socket
    do_connect(sock, socketaddrinfo);
    freeaddrinfo(socketaddrinfo); // done with socketaddrinfo

    // Send pass
    printf("Sending password\n");
    int sent = -1;
    sent = Send(sock, _PASS, strlen(_PASS), 0);
    if (sent == -1){
        printf("Send1 failed!");
        return false;
    }

    // Send a newline?
    //Send(sock, _NL, strlen(_NL), 0);
    
    // Send command from ARGV
    sent = Send(sock, op, strlen(op), 0);
    if (sent == -1){
        printf("Send2 failed!");
        return false;
    }
    
    if (!b1){
        sent = Send(sock, "\n", 1, 0);
        if (sent == -1){
            printf("Send3 failed!");
            //return false;
        }
        
        char *add = argv[4];
        sent = Send(sock, add, strlen(add)+1, 0);
        if (sent == -1){
            printf("Send4 failed!");
            return false;
        }
        
        if (b2){ // PUT
            sent = Send(sock, "\n", 1, 0);
            if (sent == -1){
                printf("Send5 failed!");
                //return false;
            }
            printf("Sending the file ...\n");
            // We need to send even more
            char *bn = basename(add);
            SendFile(sock, add, bn);
            //free(bn);
        }
    }
    
    
    // Send a newline?
    //Send(sock, _NL, strlen(_NL), 0); // Might need this extra one for getline
printf("~~~~~~~~~~~~\n");
    // Close the socket quick
    if(shutdown(sock, SHUT_WR) == -1)
    {
        perror("\tshutdown");
        close(sock);
        exit(EXIT_FAILURE);
    }else{
        printf("Shutdown done safely! Awaiting reply!\n");
    }

    if (b1){
        printf("File listing:\n");
    }

    // Wait for socket return packets
    printf("%s",GRN);
    bool first = true;
    bool c1 = false;
    while(true){
        char recvbuf[1025];
        ssize_t recvdBytes = 0;

        /* Read up to 1024 bytes from the socket. Even if we know that
           there are far more than 1024 bytes sent to us on this
           socket, recv() will only give us the bytes that the OS can
           provide us. If there are no bytes for us to read (and the
           connection is open and without error), recv() will block
           until there are bytes available.

           Note that it is possible to make recv() non-blocking with
           fcntl(). */
        recvdBytes = recv(sock, recvbuf, 1024, 0);
        recvbuf[1024] = '\0';
        //printf("\t<>[%s]\n",recvbuf);
        //char *token = strtok(recvbuf,"\n");
        if (b2 && recvdBytes > 0){ // PUT
            bool wasMade = !strncmp(recvbuf,"1",1);
            if (wasMade){
                printf("File was put successfully!\n");
            }else{
                printf("File was unable to be put!\n");
            }
            break;
        }else if (b1 && recvdBytes > 0){
            // Just print it...
            first = false;
            //printf("File listing:\n");
            printf("%s\n",recvbuf);
            
        }else if (first && !b1 ){
            // Check return values
            c1 = !strncmp(recvbuf,_FND,strlen(_FND)); // No NL here
            
            printf("Token bool: %d \n",c1);
            if (c1){
                printf("File was found! Downloading...\n");
                
            }else{
                printf("File wasn't found! Retry your command\n");
                break;
            }
            first = false;
        }else if(recvdBytes > 0 && c1 && first == false){ // print bytes we received to console
            if (b3 == true){
                // Write a file out
                createFile(recvbuf, argv[4]);
                //fwrite(recvbuf, 1, recvdBytes, stdout);
                printf("[%s]\n",recvbuf);
            }else{
                fwrite(recvbuf, 1, recvdBytes, stdout);
                printf("[%s]\n",recvbuf);
            }
            //printf(">");
            
        }else if(recvdBytes == 0 && !first) // server closed connection
        {
            printf("\n\n\tDone!\n");
            break;
        }

    }
    
    close(sock);
    printf("%s",NRM);
    fflush(stdout);
    exit(EXIT_SUCCESS);
}
bool createFile(char *arg, char *name){
    // Take arg, use getline to strip name
    // Then force in content
    //char name[256];
    //for (int x = 0; x < 255; x++){name[x] = '\0';}
    
    int len = strlen(arg);
    int x = 0;
    printf("%s",arg);
    // Now that we know the files name
    // Content!
    FILE *out = fopen(name,"wb");

    printf("\nSTARTING: ");
    for(x = 0; x <len; x++){
        char cur = arg[x];
        int wroteOut = fwrite(&cur, 1, sizeof(char), out);
        if (wroteOut < 1){
            // Something is wrong.
            printf("[!] Wrote out 0 bytes!\n");
        }
        printf("%c",cur);
    }
    printf("\nDONE!\n");
    fflush(out);
    int closed = fclose(out);
    if (closed == -1){
        // x,x
        printf("\n[!] Unable to close file out! Something is very wrong!!! \n");
        return 0;
    }
    printf("\n\tFile Created '%s'!\n",name);
    
    return true;
}
bool Send(int s, const void *buff, size_t len, int f){
    int size = 0;
    int sum = 0;
    lol = 0;
    size = send(s, buff, len, f);
    if(size == -1)
    {
        printf("send!");
        //exit(EXIT_FAILURE);
        return false;

    }else{
        sum += size;
    }
     lol++;
    if (sum < len){
        printf("<>send#%d: not all bytes sent!! %d vs %d ~> [%s]\n", lol, sum, (int)len, (char*)buff);
        return false;
    }
     printf("send#%d: \tSend: %d vs %d ~> [%s]\n", lol, sum, (int)len, (char*)buff);
   
    return true;
}

bool SendFile(int sock, char *fn, char *base){
    // Send a file over the wire
    for(int x = 0; x < strlen(base)+1; x++){
        char dis = base[x];
        if (dis == '\0'){printf("\tFound null in name @ %d!\n",x);}

    }
    char *body = NULL; // FREE?
    body = extract(fn);
    if (body == NULL){
        // Darn
        printf("[!] Data failure for SendFile!\n");
        return false;
    }
    
    // FILE SIZE, BINARY, LITTLE ENDIAN
    // Send it out
    int f = 0;
    int s = -1;
    //
    printf("\tSENDING FILE...\n");
    //s = Send(sock, body, strlen(body), f);
    //if (s < 1){printf("Send failure1!\n");}
    
    
    char fill = '\0';
 
    int sr = strlen(body)+2;
    char *full = malloc(sr);
    int f1 = sprintf(full, "%s%c", body, fill);
    if (f1 < 1 ){
        printf("[!] Error sprintf\n");
    }
    s = Send(sock, full, sr, f);
    if (s < 1){printf("Send failure1!\n");}
    
    free(full);
    /*/
    s = Send(sock, part.file_name, strlen(part.file_name)+1, f);
    if (s < 1){printf("Send failure1!\n");}
    s = Send(sock, "\n", 2, 0);
    if (s < 1){printf("Send failure2!\n");}
    
    // Body
    int len = strlen(body)+1;

    s = Send(sock, body, len, f);
    //printf("Postsend (Is %d, sent %d)\n",len,s);
    if (s < 1){printf("Send failure3!\n");}
    s = Send(sock, "\n", 2, 0);
    if (s < 1){printf("Send failure4!\n");}
    
    if (body != NULL){
        //free(body);  
    }
    // */
    //printf("Leaving func!\n");
    return true;
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
char* extract(char arg[]){
    // Extract a file from an archive
    bool b1 = fileExists(arg);
    if (!b1) {
        perror("FNF");
        return NULL;
    }
    // Okay now open it
    f_in = fopen(arg, "rb");
    if (f_in == NULL){
        // Cry and give up
        printf("\n%s couldn't be opened!",arg);
        //fclose(f_in);
        //exit(EXIT_FAILURE);
        return NULL;
    }else{
        printf("Opened '%s'\n",arg);
    }

    struct stat s;
    int res = stat(arg, &s);
    if (res == -1) {return NULL;}

    int size = s.st_size;

    char *body = malloc(size+1);
    //for(int x=0; x<size; x++){bb[x] = FILLER;}

    // Read all the file in
    int x = 0;
    char dis = '\0';
    //bool nf = false;
    printf("\t~~~~\n");
    do{
        dis = getNextByte();
        printf("%c",dis);
        body[x] = dis;
        if (dis == '\0'){
            printf("\tNull @ %d\n",x);
            //if (!nf){
            //    nf = true;
            //}else{
            //    break;
            //}
        }
        x++;
    }while(x < size);
    body[size] = '\0';
    printf("(%d vs %d)\n",size+1,x);

    //body = bb;

    return body;
}
char getNextByte(){ // Arbitrarly uses f_in
    char ans = '\0';
    int z = fread(&ans, 1, 1, f_in);

    if (z == 0){
        perror("\n[!] Unexpected EOF!\n");
        return '\0';
    }
    // Fflush the socket FD
    // Null terminate sent strings
    // 

    return ans;
}


/*
     // Get command
    char *cmd = NULL;
    char **args = NULL;
    while (true){
        cmd = readline("Enter a command: "); // FREE ME
        if (!cmd){
            //oh...
            continue;
        }else{
            break;
        }
    }
 
 */