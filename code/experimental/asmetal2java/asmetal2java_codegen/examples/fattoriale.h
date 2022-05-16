// fattoriale.h automatically generated from ASMETA2CODE
#ifndef FATTORIALE_H
#define FATTORIALE_H

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
namespace fattorialenamespace {
}

using namespace fattorialenamespace;

class fattoriale {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
public:
	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	int valore;
	int indice[2];
	int fattorialeparam[2];
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	void r_fattoriale();
	void r_Main();

	fattoriale();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif

