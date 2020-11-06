// sluiceGateGround.cpp automatically generated from ASM2CODE

#include "sluiceGateGround.h"
using namespace sluiceGateGroundnamespace;

// Conversion of ASM rules in C++ methods
void sluiceGateGround::r_open() {
	;
}
void sluiceGateGround::r_shut() {
	;
}
void sluiceGateGround::r_Main() {
	{ //par
		if ((phase[0] == FULLYCLOSED)) {
			if (passed[closedPeriod()]) {
				{ //par
					r_open();
					phase[1] = FULLYOPEN;
				} //endpar
			}
		}
		if ((phase[0] == FULLYOPEN)) {
			if (passed[openPeriod()]) {
				{ //par
					r_shut();
					phase[1] = FULLYCLOSED;
				} //endpar
			}
		}
	} //endpar
}

// Static function definition
Minutes sluiceGateGround::openPeriod() {
	return 10;
}
Minutes sluiceGateGround::closedPeriod() {
	return 170;
}

// Function and domain initialization
sluiceGateGround::sluiceGateGround() :
		// Static domain initialization 
		Minutes_elems(set<int> { 10, 170 }), PhaseDomain_elems( { FULLYCLOSED,
				FULLYOPEN }) {
	//Init static functions Abstract domain
	//Function initialization
	phase[0] = phase[1] = FULLYCLOSED;
}

// initialize controlled functions that contains monitored functions in the init term
void sluiceGateGround::initControlledWithMonitored() {
}

// Apply the update set
void sluiceGateGround::fireUpdateSet() {
	phase[0] = phase[1];
}

//init static functions and elements of abstract domains


