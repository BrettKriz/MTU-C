CC       = c++
FLAGS    = 
CFLAGS   = -g -O2
DFLAGS   = -DPACKAGE=\"threadsystem\" -DVERSION=\"1.0\" -DPTHREAD=1 -DUNIX_MSG_Q=1 -DSTDC_HEADERS=1
IFLAGS   = -I/local/eit-linux/apps/ThreadMentor/include
TMLIB    = /local/eit-linux/apps/ThreadMentor/Visual/libthreadclass.a
TMLIB_NV    = /local/eit-linux/apps/ThreadMentor/NoVisual/libthreadclass.a

OBJ_FILE = IncDec-mon.o 
EXE_FILE = tester

${EXE_FILE}: ${OBJ_FILE}
	${CC} ${FLAGS}  -o ${EXE_FILE}  ${OBJ_FILE} ${TMLIB} -lpthread

IncDec-mon.o: IncDec-mon.cpp IncDec-mon.h
	${CC} ${DFLAGS} ${IFLAGS} ${CFLAGS} -c IncDec-mon.cpp
	
clean:
	rm -f ${OBJ_FILE} ${EXE_FILE}
