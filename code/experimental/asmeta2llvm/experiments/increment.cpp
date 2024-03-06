/* increment.cpp automatically generated from ASM2CODE */
#include "increment.h"

using namespace incrementnamespace;

/* Conversion of ASM rules in C++ methods */
void increment::r_main_seq() {
	if ((x[0] < 100)) {
		x[1] = (x[0] + 1);
	}
}
void increment::r_main() {
//	if ((x[0] < 100)) {
		x[1] = (x[0] + 1);
//	}
}

/* Static function definition */
/* Function and domain initialization */
increment::increment() {
	/* Init static functions Abstract domain */
	/* Function initialization */
	x[0] = x[1] = 0;
}

/* initialize controlled functions that contains monitored functions in the init term */
void increment::initControlledWithMonitored() {
}

/* Apply the update set */
void increment::fireUpdateSet() {
	x[0] = x[1];
}

/* init static functions and elements of abstract domains */

// messo a mano il main perchè asmeta2cpp non lo genera
int main(void){
	increment inc;
	for(int i = 0; i < 1000000;i++)	inc.r_main();
}
