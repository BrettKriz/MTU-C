#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=gfortran
AS=as

# Macros
CND_PLATFORM=Cygwin_4.x-Windows
CND_DLIB_EXT=dll
CND_CONF=Release
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/_ext/264196224/BabyEagle.o \
	${OBJECTDIR}/_ext/264196224/MotherEagle.o \
	${OBJECTDIR}/thread-main.o


# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=
CXXFLAGS=

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/cs3331_-_prog4.exe

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/cs3331_-_prog4.exe: ${OBJECTFILES}
	${MKDIR} -p ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}
	${LINK.cc} -o ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/cs3331_-_prog4 ${OBJECTFILES} ${LDLIBSOPTIONS}

${OBJECTDIR}/_ext/264196224/BabyEagle.o: /cygdrive/C/Users/Brett\ Kriz/Documents/NetBeansProjects/CS3331\ -\ prog4/BabyEagle.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/264196224
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/264196224/BabyEagle.o /cygdrive/C/Users/Brett\ Kriz/Documents/NetBeansProjects/CS3331\ -\ prog4/BabyEagle.cpp

${OBJECTDIR}/_ext/264196224/MotherEagle.o: /cygdrive/C/Users/Brett\ Kriz/Documents/NetBeansProjects/CS3331\ -\ prog4/MotherEagle.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/264196224
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/264196224/MotherEagle.o /cygdrive/C/Users/Brett\ Kriz/Documents/NetBeansProjects/CS3331\ -\ prog4/MotherEagle.cpp

${OBJECTDIR}/thread-main.o: thread-main.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/thread-main.o thread-main.cpp

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}
	${RM} ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/cs3331_-_prog4.exe

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
