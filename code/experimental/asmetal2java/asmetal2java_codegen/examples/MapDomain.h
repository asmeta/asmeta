// MapDomain.h automatically generated from ASMETA2CODE
#ifndef MAPDOMAIN_H
#define MAPDOMAIN_H

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
namespace MapDomainnamespace {
}

using namespace MapDomainnamespace;

class MapDomain {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
public:
	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	map<string, int> voti[2];
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	void r_Main();

	MapDomain();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif

