// AbstractDom.h automatically generated from ASMETA2CODE
#ifndef ABSTRACTDOM_H
#define ABSTRACTDOM_H

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
namespace AbstractDomnamespace {
class NumCard;
}

using namespace AbstractDomnamespace;

class AbstractDomnamespace::NumCard {
public:
	static std::set<NumCard*> elems;
	NumCard() {
		elems.insert(this);
	}
};

class AbstractDom {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
public:
	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	static NumCard* card1;

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	void r_Main();

	AbstractDom();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif

