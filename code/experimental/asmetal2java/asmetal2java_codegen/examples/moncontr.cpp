// moncontr.cpp automatically generated from ASM2CODE

#include "moncontr.h"
using namespace moncontrnamespace;

// Conversion of ASM rules in C++ methods
void moncontr::r_Main() {
	controlledfunction2[1] = controlledfunction[0];
}

// Static function definition

// Function and domain initialization
moncontr::moncontr() {
	//Init static functions Abstract domain
	//Function initialization
}

// initialize controlled functions that contains monitored functions in the init term
void moncontr::initControlledWithMonitored() {
	controlledfunction[0] = controlledfunction[1] = (monitoredfunction + 1);
}

// Apply the update set
void moncontr::fireUpdateSet() {
	controlledfunction[0] = controlledfunction[1];
	controlledfunction2[0] = controlledfunction2[1];
}

//init static functions and elements of abstract domains


