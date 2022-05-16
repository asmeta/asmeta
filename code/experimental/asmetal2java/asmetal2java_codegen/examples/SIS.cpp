// SIS.cpp automatically generated from ASM2CODE

#include "SIS.h"
using namespace SISnamespace;

// Conversion of ASM rules in C++ methods
void SIS::r_wp() {
	if ((waterpressure[0] + delta) < minwp()) {
		waterpressure[1] = minwp();
	} else if ((waterpressure[0] + delta) > maxwp()) {
		waterpressure[1] = maxwp();
	} else {
		waterpressure[1] = (waterpressure[0] + delta);
	}
}
void SIS::r_1() {
	if ((waterpressure[0] >= low()) & (pressure[0] == TOOLOW)) {
		pressure[1] = NORMAL;
	}
}
void SIS::r_2() {
	if ((waterpressure[0] >= permit()) & (pressure[0] == NORMAL)) {
		{ //par
			pressure[1] = HIGH;
			overridden[1] = false;
		} //endpar
	}
}
void SIS::r_3() {
	if ((waterpressure[0] < low()) & (pressure[0] == NORMAL)) {
		pressure[1] = TOOLOW;
	}
}
void SIS::r_4() {
	if ((waterpressure[0] < permit()) & (pressure[0] == HIGH)) {
		{ //par
			pressure[1] = NORMAL;
			overridden[1] = false;
		} //endpar
	}
}
void SIS::r_5() {
	if ((reset == ON) & (pressure[0] == TOOLOW) | (pressure[0] == NORMAL)) {
		overridden[1] = false;
	}
}
void SIS::r_6() {
	if ((block == ON) & (reset == OFF) & (pressure[0] == TOOLOW)) {
		overridden[1] = true;
	}
}
void SIS::r_7() {
	if ((pressure[0] == TOOLOW)) {
		if (overridden[0]) {
			safetyInjection[1] = OFF;
		} else {
			safetyInjection[1] = ON;
		}
	}
}
void SIS::r_8() {
	if ((pressure[0] != TOOLOW)) {
		safetyInjection[1] = OFF;
	}
}
void SIS::r_Main() {
	{ //par
		r_wp();
		r_1();
		r_2();
		r_3();
		r_4();
		r_5();
		r_6();
		r_7();
	} //endpar
}

// Static function definition
WaterpressureType SIS::low() {
	return 9;
}
WaterpressureType SIS::permit() {
	return 10;
}
WaterpressureType SIS::maxwp() {
	return 20;
}
WaterpressureType SIS::minwp() {
	return 0;
}

// Function and domain initialization
SIS::SIS() :
		// Static domain initialization 
		WaterpressureType_elems(set<int> { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
				12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
				28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43,
				44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
				60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75,
				76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91,
				92, 93, 94, 95, 96, 97, 98, 99, 100 }), Delta_elems(set<int> {
				-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5 }), Switch_elems(
				{ ON, OFF }), Pressure_elems( { TOOLOW, NORMAL, HIGH }) {
	//Init static functions Abstract domain
	//Function initialization
	waterpressure[0] = waterpressure[1] = 3;
	pressure[0] = pressure[1] = TOOLOW;
	reset = OFF;
	block = OFF;
	overridden[0] = overridden[1] = false;
	safetyInjection[0] = safetyInjection[1] = OFF;
}

// initialize controlled functions that contains monitored functions in the init term
void SIS::initControlledWithMonitored() {
}

// Apply the update set
void SIS::fireUpdateSet() {
	waterpressure[0] = waterpressure[1];
	pressure[0] = pressure[1];
	overridden[0] = overridden[1];
	safetyInjection[0] = safetyInjection[1];
}

//init static functions and elements of abstract domains


