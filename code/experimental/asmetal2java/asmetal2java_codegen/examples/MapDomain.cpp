// MapDomain.cpp automatically generated from ASM2CODE

#include "MapDomain.h"
using namespace MapDomainnamespace;

// Conversion of ASM rules in C++ methods
void MapDomain::r_Main() {
	voti[1] = [&]() {
		map<string, int> v = {make_pair(AA,5), make_pair(BB,7)};
		return v;
	}();
}

// Static function definition

// Function and domain initialization
MapDomain::MapDomain() {
	//Init static functions Abstract domain
	//Function initialization
}

// initialize controlled functions that contains monitored functions in the init term
void MapDomain::initControlledWithMonitored() {
}

// Apply the update set
void MapDomain::fireUpdateSet() {
	voti[0] = voti[1];
}

//init static functions and elements of abstract domains


