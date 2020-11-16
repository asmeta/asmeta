// euclideMCD.cpp automatically generated from ASM2CODE

#include "euclideMCD.h"
using namespace euclideMCDnamespace;

// Conversion of ASM rules in C++ methods
void euclideMCD::r_Main() {
	if ((numA[0] != numB[0])) {
		if ((numA[0] > numB[0])) {
			numA[1] = (numA[0] - numB[0]);
		} else {
			numB[1] = (numB[0] - numA[0]);
		}
	}
}

// Static function definition

// Function and domain initialization
euclideMCD::euclideMCD() {
	//Init static functions Abstract domain
	//Function initialization
	numA[0] = numA[1] = 6409;
	numB[0] = numB[1] = 3289;
}

// initialize controlled functions that contains monitored functions in the init term
void euclideMCD::initControlledWithMonitored() {
}

// Apply the update set
void euclideMCD::fireUpdateSet() {
	numA[0] = numA[1];
	numB[0] = numB[1];
}

//init static functions and elements of abstract domains


