// LGS_3L.h automatically generated from ASMETA2CODE
#ifndef LGS_3L_H
#define LGS_3L_H

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
namespace LGS_3Lnamespace {
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

using namespace LGS_3Lnamespace;

class LGS_3L {

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
	bool gearsAllExtended();

	bool gearsAllRetracted();

	bool doorsAllClosed();

	bool doorsAllOpen();

	bool gearsAllShockAbsorber();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	void r_closeDoor();
	void r_retractionSequence();
	void r_outgoingSequence();
	void r_Main();

	LGS_3L();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif

