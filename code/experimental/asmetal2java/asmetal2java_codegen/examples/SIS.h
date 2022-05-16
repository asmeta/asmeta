// SIS.h automatically generated from ASMETA2CODE
#ifndef SIS_H
#define SIS_H

//Arduino.h uses WString instead...
#include<string>
typedef std::string String;
#include<iostream> 
#include<vector> 
using namespace std;
#include <set>
#include <map>
#include <list>
#include <bits/stl_tree.h>
#include <boost/tuple/tuple.hpp>
#define ANY String

/////////////////////////////////////////////////
/// DOMAIN DEFINITIONS
/////////////////////////////////////////////////
/* Domain definitions here */
namespace SISnamespace {
typedef int WaterpressureType;

typedef int Delta;

enum Switch {
	ON, OFF
};

enum Pressure {
	TOOLOW, NORMAL, HIGH
};

}

using namespace SISnamespace;

class SIS {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	const set<WaterpressureType> WaterpressureType_elems;

	const set<Delta> Delta_elems;

	const set<Switch> Switch_elems;

	const set<Pressure> Pressure_elems;

public:
	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	WaterpressureType waterpressure[2];
	Pressure pressure[2];
	bool overridden[2];
	Switch safetyInjection[2];
	Switch reset;
	Switch block;
	Delta delta;
	static WaterpressureType low();

	static WaterpressureType permit();

	static WaterpressureType maxwp();

	static WaterpressureType minwp();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	void r_wp();
	void r_1();
	void r_2();
	void r_3();
	void r_4();
	void r_5();
	void r_6();
	void r_7();
	void r_8();
	void r_Main();

	SIS();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif

