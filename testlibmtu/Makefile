# Brett Kriz
# Make the files!

all: testlibmtu

libmtu.so: mtu.c
	gcc -Wall -g -std=c99 -fPIC -c mtu.c
	gcc -shared -o libmtu.so mtu.o

testlibmtu: libmtu.so main.c
	gcc -Wall -std=c99 -g -o testlibmtu main.c  -L. -lmtu
	
run: testlibmtu libmtu.so
	./testlibmtu

clean:
	rm -f libmtu.so *.o testlibmtu

