/* this file is under copyright*/
// CarSystem003HighBeam.h automatically generated from ASMETA2CODE
#ifndef CARSYSTEM003HIGHBEAM_H
#define CARSYSTEM003HIGHBEAM_H

#define ARDUINOCOMPILER
#include <Arduino.h>
// The following two libs have to be installed into your Arduino Sketchbook
#include <ArduinoSTL.h>
//#include <boost_1_51_0.h>
#include <string.h>				
#include <iostream> 
#include <vector> 
#include <set>
#include <map>
#include <list>
//#include <boost/tuple/tuple.hpp>
using namespace std;
/*Arduino.h uses WString instead... */
#include <string.h>
#define ANY String

#include "CarSystem003Domains.h" 
#include "CarSystem002Functions.h" 

/* DOMAIN DEFINITIONS */
namespace CarSystem003HighBeamnamespace {
enum CameraState {
	CAMERA_READY, CAMERA_DIRTY, CAMERA_NOTREADY
};

enum PitmanArmForthBack {
	BACKWARD, FORWARD, NEUTRAL_FB
};

typedef int HighBeamMotor;

}

using namespace CarSystem003HighBeamnamespace;

class CarSystem003HighBeam: public virtual CarSystem003Domains,
		public virtual CarSystem002Functions {

	/* DOMAIN CONTAINERS */
	const set<CameraState> CameraState_elems;

	const set<PitmanArmForthBack> PitmanArmForthBack_elems;

	const set<HighBeamMotor> HighBeamMotor_elems;

public:
	/* FUNCTIONS */
	CameraState cameraState;
	bool oncomingTraffic;
	PitmanArmForthBack pitmanArmForthBack;
	bool highBeamOn[2];
	HighBeamRange highBeamRange[2];
	HighBeamMotor highBeamMotor[2];
	PitmanArmForthBack pitmanArmForthBackPrevious[2];
	bool adaptiveHighBeamActivated();

	bool adaptiveHighBeamDeactivated();

	bool headlampFlasherActivated();

	bool headlampFlasherDeactivated();

	bool headlampFixedActivated();

	bool headlampFixedDeactivated();

	bool drivesFasterThan(CurrentSpeed param0_drivesFasterThan,
			int param1_drivesFasterThan);
	HighBeamMotor lightIlluminationDistance(
			CurrentSpeed param0_lightIlluminationDistance);
	HighBeamRange luminousStrength(CurrentSpeed param0_luminousStrength);
	CurrentSpeed calculateSpeed();

	static HighBeamMotor percentageHBM(int param0_percentageHBM);
	/* RULE DEFINITION */
	void r_set_high_beam_headlights(bool _v, HighBeamMotor _d,
			HighBeamRange _l);
	void r_Manual_high_beam_headlights();
	void r_Execute_HBH(bool _setHighBeam, HighBeamMotor _setHighBeamMotor,
			HighBeamRange _setHighBeamRange);
	void r_IncreasingPlan_HBH();
	void r_DecreasingPlan_HBH();
	void r_Monitor_Analyze_HBH();
	void r_MAPE_HBH();
	void r_HighBeam();

	CarSystem003HighBeam();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif
