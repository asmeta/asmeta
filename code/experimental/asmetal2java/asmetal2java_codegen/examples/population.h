// population.h automatically generated from ASMETA2CODE
#ifndef POPULATION_H
#define POPULATION_H

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
namespace populationnamespace {
class Person;
enum GenderDomain {
	MALE, FEMALE
};

}

using namespace populationnamespace;

class populationnamespace::Person {
public:
	static std::set<Person*> elems;
	Person() {
		elems.insert(this);
	}
};

class population {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	const set<GenderDomain> GenderDomain_elems;

public:
	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	map<Person*, GenderDomain> gender[2];
	map<Person*, unsigned int> age[2];
	map<Person*, bool> alive[2];
	map<Person*, Person*> mother[2];
	map<Person*, Person*> father[2];
	static Person* male1;

	static Person* female1;

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	void r_reproduce(Person* _p);
	void r_dead(Person* _p);
	void r_Main();

	population();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif

