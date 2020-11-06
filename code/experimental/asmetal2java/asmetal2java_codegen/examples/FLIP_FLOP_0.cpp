// FLIP_FLOP_0.cpp automatically generated from ASM2CODE

#include "FLIP_FLOP_0.h"
using namespace FLIP_FLOP_0namespace;

// Conversion of ASM rules in C++ methods
void FLIP_FLOP_0::r_Fsm() {
	if ((ctl_state[0] == 0)) {
		ctl_state[1] = 1;
	} else {
		ctl_state[1] = 0;
	}
}
void FLIP_FLOP_0::r_flip_flop_1() {
	{	//seq
		r_Fsm_seq();
		r_Fsm_seq();
	}	//endseq
}

// Static function definition

// Function and domain initialization
FLIP_FLOP_0::FLIP_FLOP_0() :
		// Static domain initialization 
		State_elems(set<unsigned int> { 0, 1 }) {
	//Init static functions Abstract domain
	//Function initialization
	ctl_state[0] = ctl_state[1] = 0;
	high = false;
	low = false;
}

// initialize controlled functions that contains monitored functions in the init term
void FLIP_FLOP_0::initControlledWithMonitored() {
}

// Apply the update set
void FLIP_FLOP_0::fireUpdateSet() {
	ctl_state[0] = ctl_state[1];
}

//init static functions and elements of abstract domains


