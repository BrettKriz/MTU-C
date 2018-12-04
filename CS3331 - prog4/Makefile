CC       = c++
FLAGS    = 
CFLAGS   = -g -O2
DFLAGS   = -DPACKAGE=\"threadsystem\" -DVERSION=\"1.0\" -DPTHREAD=1 -DUNIX_MSG_Q=1 -DSTDC_HEADERS=1
IFLAGS   = -I/local/eit-linux/apps/ThreadMentor/include
TMLIB    = /local/eit-linux/apps/ThreadMentor/Visual/libthreadclass.a
TMLIB_NV    = /local/eit-linux/apps/ThreadMentor/NoVisual/libthreadclass.a

OBJ_FILE = BabyEagle.o MotherEagle.o thread-main.o 
EXE_FILE = prog4

${EXE_FILE}: ${OBJ_FILE}
	${CC} ${FLAGS}  -o ${EXE_FILE}  ${OBJ_FILE} ${TMLIB} -lpthread

BabyEagle.o: BabyEagle.cpp P4ToolKit.h
	${CC} ${DFLAGS} ${IFLAGS} ${CFLAGS} -c BabyEagle.cpp

MotherEagle.o: MotherEagle.cpp P4ToolKit.h
	${CC} ${DFLAGS} ${IFLAGS} ${CFLAGS} -c MotherEagle.cpp

thread-main.o: thread-main.cpp P4ToolKit.h
	${CC} ${DFLAGS} ${IFLAGS} ${CFLAGS} -c thread-main.cpp

noVisual: ${OBJ_FILE}
	${CC} ${FLAGS}  -o ${EXE_FILE}  ${OBJ_FILE} ${TMLIB_NV} -lpthread

nv: ${OBJ_FILE}
	${CC} ${FLAGS}  -o ${EXE_FILE}  ${OBJ_FILE} ${TMLIB_NV} -lpthread
					
clean:
	rm -f ${OBJ_FILE} ${EXE_FILE}
