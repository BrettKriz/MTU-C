/* 
 * File:   main.c
 * Author: Brett Kriz
 *
 * Created on April 5, 2016, 5:57 PM
 */
#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdarg.h>
#include <unistd.h>
//#include <funistd.h>
#include <errno.h>
// Additional
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <netdb.h>
#include <netinet/in.h>
#include <fcntl.h>
#include <dirent.h>
#include <locale.h>


#include <arpa/inet.h>


typedef struct {
    char file_name[257]; /* May contain spaces, is null-terminated --- must be the basename() of the file (man 3 basename) */
    uint64_t file_size; /* File size in bytes. 8 bytes */
    uint8_t deleted;    /* 0=file, 1=deleted file, 1 byte */
    mode_t mode;        /* See "man 2 stat", 4 bytes */
    bool completed;     // Is our file offically whole?
} mtuhdr;

// Protos
bool body();
bool setupFolder();
mtuhdr newHDR(void);

struct addrinfo* do_getaddrinfo(const char* hostname, const char* port);

int do_socket(struct addrinfo *ai);
void do_setsockopt(int sock);
void do_bind(int sock, struct addrinfo *ai);
void do_listen(int sock, int queueSize);
bool checkPass(char *arg);
int getNextBit();
char getNextByte();
bool findFiles(char *start, char *arg, bool ListAll, int sock );
bool list(int f);
bool put(int f, char *arg);
bool get(int f, char *fname);
bool fileExists(char *name);
char* extract(char arg[], mtuhdr part);
bool doCD(char *arg);
int Send(int s, const void *buff, size_t len, int f);
bool SendFile(int sock, char *fn, char *base);

//#include <readline/readline.h>
//#include <readline/history.h>

#define FILLER '\0'
#define D_TRUE 0x01
#define D_FALSE 0x00

#define _LEN_NAME 256
#define _LEN_SIZE 8
#define _LEN_MODES 4

#define _PASS "Meahoy, memoyay? MeyoyYOY, ladyonmamoy!" // Doodlebob
#define _FND "found\n"

#define _GET "get\n"
#define _LIST "list"
#define _PUT "put\n"

#define _FILES "/files"
#define _F_START "./files/"

// Globals

bool expect_EOF = false;
bool halt_flag = false;
int bitsLeft = 0;
int lol =0;
mode_t _mode = 0777;
char *norm_cwd = "."; // Set me!

FILE *f_in      = NULL;
FILE *f_out     = NULL;
FILE *_s        = NULL;
/*
 * https://github.com/skuhl/sys-prog-examples/blob/master/simple-examples/internet-stream-server.c
 * https://github.com/skuhl/sys-prog-examples/blob/master/simple-examples/internet-stream-client.c
 */
struct addrinfo* do_getaddrinfo(const char* hostname, const char* port)
{
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


int main(int argc, char** argv) {
    // Server and CLient
      printf("\n");
    for (int x = 0; x <argc; x++){
        printf("%s @ %d\n",argv[x],x);
    }
    printf("\n");
    
    if (argc < 2){
        // Too few args
        printf("[i] A port number is required to run...\n\t!Halting Server!\n\n");
        return (EXIT_FAILURE);
    }
    
    setupFolder();
    
    char *port = argv[1];
    struct addrinfo *socketaddrinfo = do_getaddrinfo(NULL, port);
    if (socketaddrinfo == NULL){
        // Bad port!
        printf("[!] Bad port number! '%s' isn't valid!\n\t!Halting Server!\n\n", port);
        return (EXIT_FAILURE);
    }
    


    // socket(): create an endpoint for communication
    int sock = do_socket(socketaddrinfo);
        
    printf("\n\nPress Ctrl+C to exit server.\n");
    printf("Run a web browser on the same computer as you run the server and point it to:\n");
    printf("127.0.0.1 %s\n\n", port);
    printf("Waiting for connection ...\n");
    
    
    do_setsockopt(sock);
    do_bind(sock, socketaddrinfo);
    do_listen(sock, 128);
    freeaddrinfo(socketaddrinfo); // done with socketaddrinfo
    doCD("files");
    norm_cwd = getcwd(NULL,0);
    body(sock);// await a connection
    
    
    free(norm_cwd);
    return (EXIT_SUCCESS);
}

bool body(int sock){

    while(true){
            // Get a socket for a particular incoming connection.
            int newsock = accept(sock, NULL, NULL);
            if(newsock == -1)
            {
                    //perror("accept(): ");
                    continue; // try again if accept failed.
            }else{
                printf("Client connected\n");
            }

            bool b1 = false;
            bool b2 = false;
            bool b3 = false;
            
//            char http_body[] = "hello world";
  //          char http_headers[1024];
    //        snprintf(http_headers, 1024,
      //               "HTTP/1.0 200 OK\r\n"
        //             "Content-Length: %zu\r\n"
          //           "Content-Type: text/html\r\n\r\n",
            //         strlen(http_body)); // should check return value

            
            // Search for password
            int at = 0;
            int pass_size = strlen(_PASS);
            //size_t *point_s = (size_t*) pass_size;
            char tbl[(int)pass_size+1]; // Free me? no
            while(at<pass_size) {tbl[at++] = FILLER;}
            
            int rd = -1;
            do{
                rd = read(newsock, tbl, strlen(_PASS)); // Does it wait for read?
                //printf("REMOVE ME1> %s & %d(%d)\n",tbl,rd,pass_size);
            }while(rd == -1);
            at = 0;
			//printf("\n\n\n");
			//printf("%s\n",tbl);
#if 0
            char newline;
            read(newsock,&newline,1);
            printf("<%c>\n",newline);
#endif 

            tbl[pass_size] = '\0';

            if (!checkPass(tbl)){
                // Wrong, close socket
                //printf("[i] Rejecting client connection!\n");
                printf("\nRejected password\n");
                close(newsock);
                continue;
            }else{
                printf("Password accepted\n");
            }
            
            //free(tbl) //NOPE
            
            // Read input
			printf("~R+\n");
            FILE *sock = fdopen(newsock, "r+");
            if (sock == NULL){
                // Oh.
                printf("[!] fdopen failed! idek what now");
                
                continue;
            }
            
            _s = sock;
            
            rd = -1;
            size_t buff_size;
            char *buff = NULL;  // FREE <>
            at = 0;
			printf("~--R+\n");
            //while (at<buff_size) {buff[at] = FILLER;at++;} // SEGFAULT HERE
            bool go = true;

            /*
                // DEBUG
                printf("\n\nSTART:\n");
                buff = malloc(buff_size);
                do{
                    rd = read(newsock, buff, buff_size);
                    if (rd < 1){
                        printf("..Done Read\n");
                        break;
                    }
                    printf("%s\n",buff);
                }while(true);

               //return true; // DEBUG
             */
            
            printf("IN MAIN ARG LOOP\n");
            do{
                // Use this loop to slowly process the file
                //rd = read(newsock, buff, buff_size); // Does it wait for read?
                printf("gl0-1\n");
                rd = getline(&buff, &buff_size, sock);

                printf("gl1\n");
                if (rd == -1){
                    // Well thats bad.
                    if (at%7 == 0 && at < 50){ // The loop gets stuck here.
                            printf(".");
                    }
                    if (at == 500){
                        printf("Theres a problem clearly...\n");
                        break;
                    }
                    at++;
					
                    continue;
                }
                    
                    printf("[%s]\n",buff);
                
                // OP Type
                 b1 = !strcmp(buff, _LIST);
                 b2 = !strcmp(buff, _PUT);
                 b3 = !strcmp(buff, _GET);
                 doCD(norm_cwd);
                printf("BOOLS: %d %d %d\n\tBUFF: [%s]\n",b1,b2,b3,buff);
			
                //printf("@ switch\n\n");
                 if (b1){
                     list(newsock);
                free(buff);
                     fflush(sock);
                 }else if (b2){ // PUT
                    //printf("B2 s\n");
                    free(buff);
                    buff = NULL;

                    rd = getline(&buff, &buff_size, sock);
                //printf("B2 M\n");
                    if (rd == -1){
                        // Well thats bad.
                        //free(buff);
                        perror("Getline failed\n");
                        break;
                    }
                    //printf("B2 E going to call\n");

                    put(newsock, buff); // UPLOAD
                free(buff);
                    fflush(sock);
                 }else if (b3){ // GET
                    //printf("B3 s\n");
                    free(buff);
                    buff = NULL;

                    rd = getline(&buff, &buff_size, sock);
                    if (rd == -1){
                        // Well thats bad.
                        //free(buff);
                        perror("Getline failed\n");
                        break;
                    }
                    //printf("B3 E going to call\n");
                    get(newsock, buff); // DOWNLOAD
                    
                free(buff);
                    fflush(sock);
                 }else{
                    // Wait wut
                    //printf("[!] Fall-through error! Buffer must contain misc data?\n [%s]\n", buff);
                    printf("Invalid operation\n");
                    free(buff);
                 }
                
                fflush(sock);
                go = false; // We're done here
                
                
                //printf("REMOVE ME2> [\n%s\n] @LEN %d\n",buff,rd);
            }while(go);

            printf("Im out of the loop, ready for another command!\n");
            /* shutdown() (see "man 2 shutdown") can be used before
               close(). It allows you to partially close a socket (i.e.,
               indicate that we are done sending data but still could
               receive). This could allow us to signal to a peer that we
               are done sending data while still allowing us to receive
               data. SHUT_RD will disallow reads from the socket, SHUT_WR
               will disallow writes, and SHUT_RDWR will disallow read and
               write. Also, unlike close(), shutdown() will affect all
               processes that are sharing the socket---while close() only
               affects the process that calls it. */
#if 1
            if(shutdown(newsock, SHUT_WR) == -1)
            {
                    perror("shutdown");
                    close(newsock);
                    exit(EXIT_FAILURE);
            }
#endif
#if 1
            if(close(newsock) == -1)
            {
                    perror("close");
                    exit(EXIT_FAILURE);
            }
#endif        
	}
    printf("\nSERVER AT END\n");
    return true;
}

bool setupFolder(){
    // Malloc space to look for a directory
    char *cwd = getcwd(NULL, 0); // FREE ME
    char *name = NULL; // FREE ME
    int size = (strlen(cwd)+strlen(_FILES));
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
    temp = strcat(name,_FILES);
    if (temp == NULL){
        printf("[!] Failed to StrCat!\n");
        free(cwd);
        free(name);
        return false;
    }
    // Far too much work for just this.
    struct stat s;
    int res = stat(name, &s);
    if (res == -1){
        // Good its missing
        
        mkdir(name, _mode);
        printf("Creating files subdirectory \t %s\n",name);
    }else{
        printf("files subdirectory already exists...\n");
    }
    
    free(cwd);
    free(name);
    return true;
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

void do_setsockopt(int sock)
{
	/* This section of code is not strictly necessary for a server.
	   It eliminates a problem where you run your server, kill it, and
	   run it again on the same port but the call to bind() produces
	   an error. The solution is to add this line of code or to wait
	   a couple of minutes before running the server again. The
	   problem is that the server's socket gets stuck in the TIME_WAIT
	   state even though the server has been killed. When the server
	   restarts, it is unable to acquire that socket again unless you
	   wait for a long enough time.
	   If you comment out this code and want to see the socket reach
	   the TIME_WAIT state, run your server and run "netstat -atnp |
	   grep 8080" to see the server listening on the port. Next,
	   connect to the server with a client and re-run the netstat
	   command. You will see that the socket remains in the TIME_WAIT
	   state for a while (a couple minutes).
	*/
	int yes = 1;
#if 1
	if(setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)) != 0)
	{
		perror("server: setsockopt() failed: ");
		close(sock);
		exit(EXIT_FAILURE);
	}
#endif


#if 0
	/* Keepalive periodically sends a packet after a connection has
	 * been idle for a long time to ensure that our connection is
	 * still OK. It typically is not needed and defaults to OFF.
	 http://www.tldp.org/HOWTO/html_single/TCP-Keepalive-HOWTO/
	 http://tools.ietf.org/html/rfc1122
	*/
	
	/* Check the status for the keepalive option */
	int optval;
	socklen_t optlen=sizeof(int);
	if(getsockopt(sock, SOL_SOCKET, SO_KEEPALIVE, &optval, &optlen) < 0) {
		perror("getsockopt()");
		close(sock);
		exit(EXIT_FAILURE);
	}
	printf("SO_KEEPALIVE is %s\n", (optval ? "ON" : "OFF"));
	/* Set the option active */
	yes = 1;
		if(setsockopt(sock, SOL_SOCKET, SO_KEEPALIVE, &yes, sizeof(int)) < 0) {
			perror("setsockopt()");
			close(sock);
			exit(EXIT_FAILURE);
		}
	printf("SO_KEEPALIVE set on socket\n");
#endif
	
}

void do_bind(int sock, struct addrinfo *ai)
{
	if(bind(sock, ai->ai_addr, ai->ai_addrlen) == -1)
	{
		perror("server: bind() failed: ");
                
		perror("\n");
		perror("If bind failed because the address is already in use,\n");
		perror("it is likely because a server is already running on\n");
		perror("the same port. Only one server can listen on each port\n");
		perror("at any given time.\n");
		close(sock);
		exit(EXIT_FAILURE);
	}
}

void do_listen(int sock, int queueSize)
{
	/* Listen for incoming connections on the socket */
	if(listen(sock, queueSize)==-1)
	{
		perror("server: listen() failed: ");
		close(sock);
		exit(EXIT_FAILURE);
	}
}

int Send(int s, const void *buff, size_t len, int f){ // 0 is fail
    int size = 0;
    int sum = 0;
    //lol = 0;
    //while(sum < len-1 || lol < 425){
    
    size = send(s, buff, len, f);
    if(size == -1)
    {
        printf("%d.",lol);

        //exit(EXIT_FAILURE);
       // return false;

    }else{
        sum += size;
    }
     lol++;
     
    //}
    if (sum < len){
        printf("<>send#%d: \tSend: %d vs %d ~> [%s]\n", lol, sum, (int)len, (char*)buff);
        return sum;
    }

     fflush(_s);
     
    printf("send#%d: \tSend: %d vs %d ~> [%s]\n", lol, sum, (int)len, (char*)buff);
    return sum;
}

bool checkPass(char *arg){
    return !strcmp(arg, _PASS);
}

bool list(int f){
    // List all the stuff in path
    printf("File listing:\n");
   // doCD("files");
    bool flag = findFiles(".", _PASS, true, f);
    //printf("(%d)\n",flag);
    return flag;
}

bool get(int f, char *fname){
    // Get a file to be determined

    // Now that we have a file name
    // Send that file out the socket
    // Or return not found
    //doCD("files");
    printf("IN\n");
    bool flag = findFiles(".", fname, false, f);
    printf("OUT\n");
    
    return flag;
}

// UPLOAD
bool put(int f, char *arg){
    // PUT
    fflush(_s);
    char *bn = basename(arg);
    printf("Running put for '%s' from '%s'!\n",bn,arg);
    doCD("files");
    FILE *out = fopen(bn, "wb");
    if (out == NULL){
        // Oh.\]
        perror("Fopen failed!");
        Send(f, "0", 2,0);
        doCD("..");
        return false;
    }
    
    // Get a 
    bool pass = false;
    int c = 0;
    while(true){
        char recvbuf[1025];
        ssize_t recvdBytes = 0;

        recvdBytes = recv(f, recvbuf, 1024, 0);
        recvbuf[1024] = '\0';

        if (recvdBytes < 1){
            
            if (pass){
                printf("Breaking\n");
                break;
            }else{
                //printf(".");
                if (c > 500001){
                    printf("Timed out.. \n");
                    return false;
                }
                c++;
                continue;
            }
        }else{
            pass = true;
        }
        
        // Put it in
        int wroteOut = fwrite(recvbuf, 1, strlen(recvbuf), out);
        if (wroteOut < 1){
            // Something is wrong.
            printf("\n[!] Wrote out 0 bytes");
            Send(f, "0", 2,0);
            doCD("..");
            return false;
        }
        
    }
    fwrite("\0", 1, 1, out);
    fflush(out);
    int val = fclose(out);
    
    if (val == -1){
        perror("Fclose failed!");
        Send(f, "0", 2,0);
        doCD("..");
        return false;
    }
    
    Send(f, "1", 2,0);
    printf("Wrote '%s' to files..\n",bn);
    //free(bn);
    doCD("..");
            
    return true;
}

// Sets up a new HDR on the fly
mtuhdr newHDR(void){
    mtuhdr x;
    x.completed = false;
    x.deleted = D_FALSE;
    x.file_size = 0;
    x.mode = 0777;
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
            perror("\nEOF found!\n");
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
    char ans = '\0';
    int z = fread(&ans, 1, 1, f_in);

    if (z == 0){
        if (expect_EOF == false){
            perror("\n[!] Unexpected EOF!\n");
            halt_flag = true;
            return '\0';
            //fclose(f_in);
            //exit(EXIT_FAILURE);
        }else{
            perror("\n\t[i] Expected EOF!\n");
            //fclose(f_in);
            halt_flag = true;
            return '\0';
        }
    }
    // Fflush the socket FD
    // Null terminate sent strings
    // 

    return ans;
}

bool findFiles(char *start, char *arg, bool ListAll, int sock ){ // "." for start
    struct dirent **tbl;
    bool flag = false;
    //printf("\n<entering func for '%s'>\n",start);
    /*
        ino_t  d_ino       file serial number
        char   d_name[]    name of entry
     */
    char *cwd = getcwd(NULL,0);
    int count = scandir(cwd, &tbl, NULL, alphasort);
    free(cwd);
    if (tbl == NULL || count == -1){
        perror("\n[!] Table is null! Scandir failed! \n");
        return false;
    }

    //printf("\n\n\nCount: %d\n",count);
    for (int x = 0; x < count; x++){
        char *cur = tbl[x]->d_name;
        // Setup bools
        flag = false;
        
        bool isFile = false;
        bool b1 = !strcmp(cur,"..");
        bool b2 = !strcmp(cur,"..");
        bool b3 = !strcmp(cur,".");
        bool b4 = !strcmp(cur,".");
        bool b5 = b1 || b2 || b3 || b4;
        bool b6 = !strncmp(arg, cur, strlen(arg)-1);
        
        //printf("'%s'\t<..> B1: %d, B2: %d\t<.> B3: %d, B4: %d\n",cur,b1,b2,b3,b4);
        
        if (b5){
            // Oh we shouldn't
            continue;
        }
        
        struct stat s;
        int res = stat(cur, &s);
        if (res == -1){
            cwd = getcwd(NULL,0);
            printf("\n[!] Stat failed! %s/'%s'\n",cwd,cur);
            perror("stat");
            free(cwd);
            return false;
        }
        
        isFile = S_ISREG(s.st_mode);

        // Call it out
        if (ListAll){
            // Just list em all
#if 0
            cwd = getcwd(NULL,0);
            printf("%s/%s\n",cwd,cur);
            free(cwd);
#endif
#if 1
            
            int rr = Send(sock, cur, strlen(cur)+1, 0);
            if (rr < 1){
                printf("PROBLEM @ SEND!\n");
            }
            
            rr = Send(sock, "\n", 2, 0);
            if (rr < 1){
                printf("PROBLEM @ SEND!\n");
            }else{
                printf("rr:%d\n",rr);
            }
            //printf("%s\t%d\n",cur,rr);
#endif
        }
        
        // Are we looking for you?
        if (!isFile){
            // We should recur here.
            // or the results too.
            //printf("\tRecurring on '%s' \n",cur);
            doCD(cur);
            flag = flag || findFiles(cur, arg, ListAll, sock);
            //cwd = getcwd(NULL,0);
            //printf("<1> %s \n",cwd);
            //free(cwd);
            doCD("..");
            //cwd = getcwd(NULL,0);
            //printf("<2> %s \n",cwd);
            //free(cwd);
            //printf("Back from recur on '%s'\n",cur);
            
        }else if (!ListAll && b6){ // -1 because of getline /n
            
            // FOUND
            if (!flag){
               Send(sock, _FND, strlen(_FND)+1, 0);
                flag = true; 
            }
            
            //Send(sock, "\n", 1, 0);
            
            // Send it out
            //Send(sock, cur, strlen(cur), 0);
            char fill = '/';
            cwd = getcwd(NULL,0);
            char *path = malloc(strlen(cwd)+strlen(cur)+2);
            int f1 = sprintf(path, "%s%c%s", cwd, fill, cur);
            if (f1 < 1 ){
                printf("[!] Error sprintf\n");
            }
            // SEND FILE
            if (!SendFile(sock, path, cur)){
                printf("[!] Sendfile returned false... [%d],[%s]\n",sock,cur);
            }
            free(cwd);
            free(path);
        }        
    }
    //doCD(norm_cwd);

    return flag; // cd Programming/CS3411/Prog5
}
bool SendFile(int sock, char *fn, char *base){
    // Send a file over the wire
    mtuhdr part = newHDR();
    for(int x = 0; x < strlen(base); x++){
        char dis = base[x];
        if (dis == '\0'){printf("\tFound null in name @ %d!\n",x);}
        part.file_name[x] = dis;
    }
    char *body = NULL; // FREE?
    body = extract(fn, part);
    if (body == NULL){
        // Darn
        printf("[!] Data failure for SendFile:\n [%s],[%s]\n",part.file_name,body);
        return false;
    }
    
    // FILE SIZE, BINARY, LITTLE ENDIAN
    // Send it out
    int f = 0;
    int s = -1;
    //
    printf("SENDING FILE...\n");
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
char* extract(char arg[], mtuhdr part){
    // Extract a file from an archive
    bool b1 = fileExists(arg);
    if (!b1) {
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

    part.file_size = size;
    part.deleted = D_FALSE;
    part.mode = s.st_mode;

    char *body = malloc(size+1);
    //for(int x=0; x<size; x++){bb[x] = FILLER;}

    // Read all the file in
    int x = 0;
    char dis = '\0';
    printf("\n");
    do{
        dis = getNextByte();
        printf("%c",dis);
#if 0
        if (halt_flag){
            halt_flag = false;
            break;
        }
#endif
        body[x] = dis;
        x++;
    }while(x < size);
    body[size] = '\0';
    printf("(%d vs %d)\n",size+1,x);

    //body = bb;

    return body;
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
        printf("CD: '%s'\n",str2);
        int a = chdir(str2);
        if (a == -1){
            // Thats not a valid path...
            perror("[!] Crippling error, cd .. FAILED!\n");
            free(str);
            free(str2);
            return false;
        }
    //printf("\tdone~~~ '%s'\n\n",str2);
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

/*/

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
        for (int z = 0; z < strlen(str); z++){
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
        char *str2 = malloc(sizeof(char) * at);
        strncpy(str2, str, at);
        str2[at] = '\0'; // Make sure to null the END

        // Set it as the new path
        int a = chdir(str2);
        if (a == -1){
            // Thats not a valid path...
            perror("[!] Crippling error, cd .. FAILED!\n");
            free(str);
            free(str2);
            return false;
        }
    //printf("\tdone~~~ '%s'\n\n",str2);
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
 */