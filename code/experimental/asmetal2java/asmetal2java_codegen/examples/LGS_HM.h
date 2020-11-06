// LGS_HM.h automatically generated from ASMETA2CODE
#ifndef LGS_HM_H
#define LGS_HM_H

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
namespace LGS_HMnamespace {
enum LandingSet {
	FRONT, LEFT, RIGHT
};

enum HandleStatus {
	UP, DOWN
};

enum DoorStatus {
	CLOSED, OPENING, OPEN, CLOSING
};

enum GearStatus {
	RETRACTED, EXTENDING, EXTENDED, RETRACTING
};

enum CylinderStatus {
	CYLINDER_RETRACTED,
	CYLINDER_EXTENDING,
	CYLINDER_EXTENDED,
	CYLINDER_RETRACTING
};

}

using namespace LGS_HMnamespace;

class LGS_HM {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	const set<LandingSet> LandingSet_elems;

	const set<HandleStatus> HandleStatus_elems;

	const set<DoorStatus> DoorStatus_elems;

	const set<GearStatus> GearStatus_elems;

	const set<CylinderStatus> CylinderStatus_elems;

public:
	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	HandleStatus handle;
	DoorStatus doors[2];
	GearStatus gears[2];
	CylinderStatus cylindersDoors();

	CylinderStatus cylindersGears();

	bool generalElectroValve[2];
	bool openDoorsElectroValve[2];
	bool closeDoorsElectroValve[2];
	bool retractGearsElectroValve[2];
	bool extendGearsElectroValve[2];
	map<LandingSet, bool> gearsExtended;
	map<LandingSet, bool> gearsRetracted;
	map<LandingSet, bool> doorsClosed;
	map<LandingSet, bool> doorsOpen;
	map<LandingSet, bool> gearsShockAbsorber;
	bool gearsExtended();

	bool gearsRetracted();

	bool doorsClosed();

	bool doorsOpen();

	bool gearsShockAbsorber();

	bool aGearExtended();

	bool aGearRetracted();

	bool aDoorClosed();

	bool aDoorOpen();

	bool timeout;
	bool anomaly[2];
	bool greenLight();

	bool orangeLight();

	bool redLight();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	void r_closeDoor();
	void r_retractionSequence();
	void r_outgoingSequence();
	void r_healthMonitoring();
	void r_Main();

	LGS_HM();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif

