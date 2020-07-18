/* this file is under copyright*/
// CarSystem007AdaptiveCruiseC.h automatically generated from ASMETA2CODE
#ifndef CARSYSTEM007ADAPTIVECRUISEC_H
#define CARSYSTEM007ADAPTIVECRUISEC_H

#define ARDUINOCOMPILER
#include <Arduino.h>
// The following two libs have to be installed into your Arduino Sketchbook
#include <ArduinoSTL.h>
#include <boost_1_51_0.h>
#include <string.h>				
#include <iostream> 
#include <vector> 
#include <set>
#include <map>
#include <list>
#include <boost/tuple/tuple.hpp>
using namespace std;
/*Arduino.h uses WString instead... */
#include <string.h>
#define ANY String

#include "CarSystem006Functions.h" 

/* DOMAIN DEFINITIONS */
namespace CarSystem007AdaptiveCruiseCnamespace {
enum RangeRadarState {
	READY, DIRTY, NOTREADY
};

typedef int RangeRadarSensor;

typedef double SafetyDistance;

typedef int BrakePressure;

typedef int TimeImpactBrake;

enum Aceleration {
	ACCP5, DECM2, NOACC
};

}

using namespace CarSystem007AdaptiveCruiseCnamespace;

class CarSystem007AdaptiveCruiseC: public virtual CarSystem006Functions {

	/* DOMAIN CONTAINERS */
	const set<RangeRadarState> RangeRadarState_elems;

	const set<RangeRadarSensor> RangeRadarSensor_elems;

	const set<SafetyDistance> SafetyDistance_elems;

	const set<BrakePressure> BrakePressure_elems;

	const set<TimeImpactBrake> TimeImpactBrake_elems;

	const set<Aceleration> Aceleration_elems;

public:
	/* FUNCTIONS */
	bool adaptiveCruiseControlActivated();

	bool acousticWarningOn[2];
	bool visualWarningOn[2];
	RangeRadarState rangeRadarState;
	RangeRadarSensor rangeRadarSensor;
	bool obstacleAhead();

	double safetyDistance;
	CurrentSpeed speedVehicleAhead;
	CurrentSpeed speedVehicleAhead_Prec[2];
	int setSafetyDistance[2];
	bool bothVehicleStanding();

	BrakePressure brakePressure[2];
	int acceleration[2];
	bool acousticCollisionSignals[2];
	int brakingDistance();

	CurrentSpeed setTargetFromAccDec;
	bool manualSpeed();

	/* RULE DEFINITION */
	void r_CalculateSafetyDistancePlan_CC();
	void r_SafetyDistanceByUser();
	void r_CollisionDetection();
	void r_AcceleratePlan_CC();
	void r_DeceleratePlan_CC();
	void r_WarningPlan_CC();
	void r_Monitor_Analyze_CC();
	void r_MAPE_CC();

	CarSystem007AdaptiveCruiseC();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif
