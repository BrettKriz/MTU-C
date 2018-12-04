# Brett Kriz
# Make the files!

all: finddup

finddup: main.c
	gcc -Wall -std=c99 -g main.c -o finddup -lcrypto -lssl

run: finddup
	./finddup

clean:
	rm -f finddup

