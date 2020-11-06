// sluiceGateGround.h automatically generated from ASMETA2CODE
#ifndef SLUICEGATEGROUND_H
#define SLUICEGATEGROUND_H

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
namespace sluiceGateGroundnamespace {
typedef int Minutes;

enum PhaseDomain {
	FULLYCLOSED, FULLYOPEN
};

}

using namespace sluiceGateGroundnamespace;

class sluiceGateGround {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	const set<Minutes> Minutes_elems;

	const set<PhaseDomain> PhaseDomain_elems;

public:
	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	PhaseDomain phase[2];
	static Minutes openPeriod();

	static Minutes closedPeriod();

	map<Minutes, bool> passed;
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	void r_open();
	void r_shut();
	void r_Main();

	sluiceGateGround();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif

