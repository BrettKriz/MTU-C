# Brett Kriz
# Make the files!

all: mtush

mtush: main.c
	gcc -Wall -std=c99 -g -o mtush main.c -L. -lreadline

run: mtush
	./mtush

clean:
	rm -f  mtush

