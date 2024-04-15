	/* TimeLibrarySimple.cpp automatically generated from ASM2CODE */
	#include "TimeLibrarySimple.h"
	
	using namespace TimeLibrarySimplenamespace;
	
	/* Conversion of ASM rules in C++ methods */
	void TimeLibrarySimple::r_reset_timer (Timer* _t){
		start[1][_t] = currentTime(_t);
	}
	void TimeLibrarySimple::r_set_duration (Timer* _t, int _ms){
		duration[1][_t] = _ms;
	}
	
	/* Static function definition */
	int TimeLibrarySimple::currentTime(Timer* _t){return mCurrTimeSecs;}
	bool TimeLibrarySimple::expired(Timer* _t){return (currentTime(_t) >= (start[0][_t] + duration[0][_t]));}
	/* Function and domain initialization */
	TimeLibrarySimple::TimeLibrarySimple(){
	/* Init static functions Abstract domain */
	/* Function initialization */
	}

	
	/* initialize controlled functions that contains monitored functions in the init term */
	void TimeLibrarySimple::initControlledWithMonitored(){
	}
	

	/* Apply the update set */
	void TimeLibrarySimple::fireUpdateSet(){
	}
	
	/* init static functions and elements of abstract domains */
	set< Timer*>Timer::elems;
	
