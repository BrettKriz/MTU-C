# Program 2 Makefile
# main.c qsort.c merge.c
# -std=c99

all: main qsort merge

main: main.c
	gcc -std=c99 main.c -o main -g

qsort: qsort.c
	gcc -std=c99 qsort.c -o qsort -g
	
merge: merge.c
	gcc -std=c99 merge.c -o merge -g
	
clean:
	rm -f  main qsort merge