// AbstractDom.cpp automatically generated from ASM2CODE

#include "AbstractDom.h"
using namespace AbstractDomnamespace;

// Conversion of ASM rules in C++ methods
void AbstractDom::r_Main() {
	;
}

// Static function definition

// Function and domain initialization
AbstractDom::AbstractDom() {
	//Init static functions Abstract domain
	card1 = new NumCard;
	//Function initialization
}

// initialize controlled functions that contains monitored functions in the init term
void AbstractDom::initControlledWithMonitored() {
}

// Apply the update set
void AbstractDom::fireUpdateSet() {
}

//init static functions and elements of abstract domains
std::set<NumCard*> NumCard::elems;
NumCard*AbstractDom::card1;


