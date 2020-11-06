// FLIP_FLOP_0.h automatically generated from ASMETA2CODE
#ifndef FLIP_FLOP_0_H
#define FLIP_FLOP_0_H

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
namespace FLIP_FLOP_0namespace {
typedef unsigned int State;

}

using namespace FLIP_FLOP_0namespace;

class FLIP_FLOP_0 {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	const set<State> State_elems;

public:
	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	State ctl_state[2];
	bool high;
	bool low;
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	void r_Fsm();
	void r_flip_flop_1();

	FLIP_FLOP_0();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif

