/* CarSystem007AdaptiveCruiseC.cpp automatically generated from ASM2CODE */
#include "CarSystem007AdaptiveCruiseC.h"

using namespace CarSystem007AdaptiveCruiseCnamespace;

/* Conversion of ASM rules in C++ methods */
void CarSystem007AdaptiveCruiseC::r_CalculateSafetyDistancePlan_CC() {
	if ((currentSpeed < 200)) {
		{ //par
			speedVehicleAhead_Prec[1] = speedVehicleAhead;
			if ((speedVehicleAhead < 200) & (speedVehicleAhead > 0)) {
				setSafetyDistance[1] = (int) ((2.5 * (currentSpeed / 36)));
			}
			if (bothVehicleStanding()) {
				setSafetyDistance[1] = 2;
			}
			if ((speedVehicleAhead > speedVehicleAhead_Prec[0])
					& (currentSpeed != 0)) {
				setSafetyDistance[1] = (int) ((3.0 * (currentSpeed / 36)));
			}
		} //endpar
	}
}
void CarSystem007AdaptiveCruiseC::r_SafetyDistanceByUser() {
	if (!adaptiveCruiseControlActivated()) {
		if ((currentSpeed > 200)) {
			if ((sCSLever == HEAD)) {
				setSafetyDistance[1] = (int) ((((currentSpeed / 10) / 3.6)
						* safetyDistance));
			}
		}
	}
}
void CarSystem007AdaptiveCruiseC::r_CollisionDetection() {
	{ //par
		if ((rangeRadarSensor < brakingDistance())) {
			acousticCollisionSignals[1] = true;
		}
		if ((rangeRadarSensor > brakingDistance())
				& (acousticCollisionSignals[0] == true)) {
			acousticCollisionSignals[1] = false;
		}
	} //endpar
}
void CarSystem007AdaptiveCruiseC::r_AcceleratePlan_CC() {
	if ((currentSpeed < setVehicleSpeed[0])) {
		{ //par
			acceleration[1] = 2;
			if (!manualSpeed()) {
				if ((desiredSpeed[0] > setVehicleSpeed[0])) {
					if ((setTargetFromAccDec > speedVehicleAhead)) {
						setVehicleSpeed[1] = speedVehicleAhead;
					} else {
						setVehicleSpeed[1] = setTargetFromAccDec;
					}
				}
			}
		} //endpar
	} else {
		acceleration[1] = 0;
	}
}
void CarSystem007AdaptiveCruiseC::r_DeceleratePlan_CC() {
	if ((currentSpeed != 0)) {
		{ //par
			acceleration[1] = -5;
			if (!manualSpeed()) {
				setVehicleSpeed[1] = setTargetFromAccDec;
			}
		} //endpar
	} else {
		acceleration[1] = 0;
	}
}
void CarSystem007AdaptiveCruiseC::r_WarningPlan_CC() {
	{ //par
		if ((double) (rangeRadarSensor) < (((currentSpeed / 10) / 3.6) * 1.5)) {
			visualWarningOn[1] = true;
		} else if (visualWarningOn[0]) {
			visualWarningOn[1] = false;
		}
		if ((double) (rangeRadarSensor) < (((currentSpeed / 10) / 3.6) * 0.8)) {
			acousticWarningOn[1] = true;
		} else if (acousticWarningOn[0]) {
			acousticWarningOn[1] = false;
		}
	} //endpar
}
void CarSystem007AdaptiveCruiseC::r_Monitor_Analyze_CC() {
	if (adaptiveCruiseControlActivated()) {
		{ //par
			if (obstacleAhead() & (rangeRadarSensor < setSafetyDistance[0])) {
				r_AcceleratePlan_CC();
			}
			if (obstacleAhead() & (rangeRadarSensor > setSafetyDistance[0])) {
				r_DeceleratePlan_CC();
			}
			r_CollisionDetection();
			if (obstacleAhead()) {
				r_WarningPlan_CC();
			}
			r_CalculateSafetyDistancePlan_CC();
		} //endpar
	}
}
void CarSystem007AdaptiveCruiseC::r_MAPE_CC() {
	r_Monitor_Analyze_CC();
}

/* Static function definition */
bool CarSystem007AdaptiveCruiseC::adaptiveCruiseControlActivated() {
	return (cruiseControlMode == CCM2);
}
bool CarSystem007AdaptiveCruiseC::obstacleAhead() {
	return (rangeRadarState == READY) & (rangeRadarSensor != 0);
}
bool CarSystem007AdaptiveCruiseC::bothVehicleStanding() {
	return (currentSpeed == 0) & (speedVehicleAhead == 0);
}
int CarSystem007AdaptiveCruiseC::brakingDistance() {
	return (int) (((currentSpeed * currentSpeed) / 20));
}
bool CarSystem007AdaptiveCruiseC::manualSpeed() {
	return (sCSLever == UPWARD5_SCS) | (sCSLever == UPWARD7_SCS)
			| (sCSLever == DOWNWARD5_SCS) | (sCSLever == DOWNWARD7_SCS);
}

/* Function and domain initialization */
CarSystem007AdaptiveCruiseC::CarSystem007AdaptiveCruiseC() {
	//Static domain initialization 
	RangeRadarSensor_elems: {
		0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200;
	}
	;

	RangeRadarState_elems: {
		READY, DIRTY, NOTREADY;
	}
	;

	Aceleration_elems: {
		ACCP5, DECM2, NOACC;
	};
	//MOD
	/* Init static functions Abstract domain */
	/* Function initialization */
}

/* initialize controlled functions that contains monitored functions in the init term */
void CarSystem007AdaptiveCruiseC::initControlledWithMonitored() {
}

/* Apply the update set */
void CarSystem007AdaptiveCruiseC::fireUpdateSet() {
	acousticWarningOn[0] = acousticWarningOn[1];
	visualWarningOn[0] = visualWarningOn[1];
	speedVehicleAhead_Prec[0] = speedVehicleAhead_Prec[1];
	setSafetyDistance[0] = setSafetyDistance[1];
	brakePressure[0] = brakePressure[1];
	acceleration[0] = acceleration[1];
	acousticCollisionSignals[0] = acousticCollisionSignals[1];
	sCSLeve_Previous[0] = sCSLeve_Previous[1];
	speedLimitActive[0] = speedLimitActive[1];
	desiredSpeed[0] = desiredSpeed[1];
	keyState_Previous[0] = keyState_Previous[1];
	setVehicleSpeed[0] = setVehicleSpeed[1];
}

/* init static functions and elements of abstract domains */

