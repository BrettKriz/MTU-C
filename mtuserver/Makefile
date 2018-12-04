# Brett Kriz
# Make the files!
#-L. -lreadline

all: server client

server: main.c
	gcc -Wall -std=c99 -g -o server main.c -L. -lreadline

client: client.c
	gcc -Wall -std=c99 -g -o client client.c -L. -lreadline

run: server client
	./server
	./client

clean:
	rm -f  server client

