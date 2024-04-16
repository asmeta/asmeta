// pillbox_final.h automatically generated from ASMETA2CODE
#ifndef PILLBOX_FINAL_H
#define PILLBOX_FINAL_H

#include <string.h>				
#include <iostream> 
#include <vector> 
#include <set>
#include <map>
#include <list>
#include <chrono>

using namespace std;

#define ANY String
#include "../../STDL/TimeLibrarySimple.h" 
	
	
/* DOMAIN DEFINITIONS */
namespace pillbox_finalnamespace{
	class Drawer;
	enum LedLights {OFF, ON};
	enum Drugs {TYLENOL, ASPIRINE, MOMENT};
}
	
using namespace pillbox_finalnamespace;

class pillbox_finalnamespace::Drawer{
public:
	static set<Drawer*> elems;
	Drawer(){elems.insert(this);}
};

class pillbox_final : public virtual TimeLibrarySimple{  
	/* DOMAIN CONTAINERS */
	const set<LedLights> LedLights_elems;
	const set<Drugs> Drugs_elems;
public:
	/* FUNCTIONS */
	map<Drawer*, bool> isPillTaken;
	map<Drawer*, LedLights> drawerLed[2];
	map<Drawer*, vector<int>> time_consumption[2];
	map<Drawer*, Drugs> drug[2];
	map<Drawer*, unsigned int> drugIndex[2];
	map<Drawer*, bool> isPillTobeTaken[2];
	static Timer* tenMinutes;
	bool isOn (Drawer* param0_isOn);
	bool isOff (Drawer* param0_isOff);
	bool areOthersOn (Drawer* param0_areOthersOn);
	bool pillDeadlineHit (Drawer* param0_pillDeadlineHit);
	bool isThereAnyOtherDeadline (Drawer* param0_isThereAnyOtherDeadline);
	static Drawer* drawer1;
	static Drawer* drawer2;
	static Drawer* drawer3;

	/* RULE DEFINITION */
	void r_reset (Drawer* _drawer);
	void r_pillToBeTaken (Drawer* _drawer);
	void r_ON (Drawer* _drawer);
	void r_choosePillToTake();
	void r_setOtherDrawers();
	void r_Main();
	
	pillbox_final();
	void initControlledWithMonitored();
	void getInputs();
	void setOutputs(); 
	void fireUpdateSet();
};
#endif
