// ProdDomain.h automatically generated from ASMETA2CODE
#ifndef PRODDOMAIN_H
#define PRODDOMAIN_H

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
namespace ProdDomainnamespace {
typedef int Second;

typedef int Minute;

typedef int Hour;

}

using namespace ProdDomainnamespace;

class ProdDomain {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	const set<Second> Second_elems;

	const set<Minute> Minute_elems;

	const set<Hour> Hour_elems;

public:
	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	map<tuple<Second, Minute, Hour>, Second> time[2];
	int number[2];
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	void r_Main();

	ProdDomain();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif

