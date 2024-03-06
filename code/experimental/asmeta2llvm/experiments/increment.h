/* this file is under copyright*/
// increment.h automatically generated from ASMETA2CODE
#ifndef INCREMENT_H
#define INCREMENT_H

/*Arduino.h uses WString instead... */
#include <string.h>				
#include <iostream> 
#include <vector> 
#include <set>
#include <map>
#include <list>
//Andrea Belotti
#include <chrono>
//#include <tuple>
//#include <bits/stl_tree.h>

using namespace std;

//typedef std::string String;
#define ANY String

/* DOMAIN DEFINITIONS */
namespace incrementnamespace {
//devo togliere questo in Timer perché non serve
}

using namespace incrementnamespace;

//devo togliere questo in Timer perché non serve

class increment {

	/* DOMAIN CONTAINERS */
public:
	/* FUNCTIONS */
	int x[2];
	/* RULE DEFINITION */
	void r_main_seq();
	void r_main();

	increment();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif
