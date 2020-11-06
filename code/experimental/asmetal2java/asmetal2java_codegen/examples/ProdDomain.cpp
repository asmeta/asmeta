// ProdDomain.cpp automatically generated from ASM2CODE

#include "ProdDomain.h"
using namespace ProdDomainnamespace;

// Conversion of ASM rules in C++ methods
void ProdDomain::r_Main() {
	if ((time[0][make_tuple(0, 0, 0)] == 0)) {
		time[1][make_tuple(1, 3, 2)] = 1;
	}
}

// Static function definition

// Function and domain initialization
ProdDomain::ProdDomain() :
		// Static domain initialization 
		Second_elems(set<int> { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
				14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
				30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45,
				46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59 }), Minute_elems(
				set<int> { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
						16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
						30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43,
						44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
						58, 59 }), Hour_elems(set<int> { 0, 1, 2, 3, 4, 5, 6, 7,
				8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 }) {
	//Init static functions Abstract domain
	//Function initialization
	for (auto const& _x : Second_elems) {
		for (auto const& _y : Minute_elems) {
			for (auto const& _z : Hour_elems) {
				time[0].insert( { make_tuple(_x, _y, _z), 0 });
				time[1].insert( { make_tuple(_x, _y, _z), 0 });
			}
		}
	}
}

// initialize controlled functions that contains monitored functions in the init term
void ProdDomain::initControlledWithMonitored() {
}

// Apply the update set
void ProdDomain::fireUpdateSet() {
	time[0] = time[1];
	number[0] = number[1];
}

//init static functions and elements of abstract domains


