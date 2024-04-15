/* this file is under copyright*/
	// TimeLibrarySimple.h automatically generated from ASMETA2CODE
	#ifndef TIMELIBRARYSIMPLE_H
	#define TIMELIBRARYSIMPLE_H
	
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
namespace TimeLibrarySimplenamespace{
	class Timer;
	 //devo togliere questo in Timer perché non serve
	}

	
	using namespace TimeLibrarySimplenamespace;
	
	class TimeLibrarySimplenamespace::Timer{
	public:
	static set<Timer*> elems;
	Timer(){elems.insert(this);}
	};
	 //devo togliere questo in Timer perché non serve
	
	class TimeLibrarySimple {
	  
	/* DOMAIN CONTAINERS */
	public:
	/* FUNCTIONS */
	map<Timer*, int> start[2];
	map<Timer*, int> duration[2];
	int currentTime (Timer* param0_currentTime);
	bool expired (Timer* param0_expired);
	int mCurrTimeSecs;
	/* RULE DEFINITION */
	void r_reset_timer (Timer* _t);
	void r_set_duration (Timer* _t, int _ms);
	
	TimeLibrarySimple();
	
	void initControlledWithMonitored();
	
	void getInputs();
	
	void setOutputs(); 
	
	void fireUpdateSet();

	};
	#endif
