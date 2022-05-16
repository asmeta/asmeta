// fattoriale.cpp automatically generated from ASM2CODE

#include "fattoriale.h"
using namespace fattorialenamespace;

// Conversion of ASM rules in C++ methods
void fattoriale::r_fattoriale() {
	if ((indice[0] > 1)) {
		{ //par
			fattorialeparam[1] = (fattorialeparam[0] * indice[0]);
			indice[1] = (indice[0] - 1);
		} //endpar
	}
}
void fattoriale::r_Main() {
	{ //seq
		if ((indice[0] == 1)) {
			if ((valore > 0)) {
				{ //par
					indice[1] = valore;
					fattorialeparam[1] = 1;
				} //endpar
			}
		}
		r_fattoriale_seq();
	} //endseq
}

// Static function definition

// Function and domain initialization
fattoriale::fattoriale() {
	//Init static functions Abstract domain
	//Function initialization
	indice[0] = indice[1] = 1;
}

// initialize controlled functions that contains monitored functions in the init term
void fattoriale::initControlledWithMonitored() {
}

// Apply the update set
void fattoriale::fireUpdateSet() {
	indice[0] = indice[1];
	fattorialeparam[0] = fattorialeparam[1];
}

//init static functions and elements of abstract domains


