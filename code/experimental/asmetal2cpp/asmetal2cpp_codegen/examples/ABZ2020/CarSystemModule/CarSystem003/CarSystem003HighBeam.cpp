/* CarSystem003HighBeam.cpp automatically generated from ASM2CODE */
#include "CarSystem003HighBeam.h"

using namespace CarSystem003HighBeamnamespace;

/* Conversion of ASM rules in C++ methods */
void CarSystem003HighBeam::r_set_high_beam_headlights(bool _v, HighBeamMotor _d,
		HighBeamRange _l) {
	{ //par
		highBeamOn[1] = _v;
		highBeamMotor[1] = _d;
		highBeamRange[1] = _l;
	} //endpar
}
void CarSystem003HighBeam::r_Manual_high_beam_headlights() {
	{ //par
		if (headlampFlasherActivated()) {
			r_set_high_beam_headlights(true, 14, 100);
		}
		if (headlampFlasherDeactivated() | headlampFixedDeactivated()) {
			highBeamOn[1] = false;
		}
		if (headlampFixedActivated()) {
			r_set_high_beam_headlights(true, 7, 100);
		}
	} //endpar
}
void CarSystem003HighBeam::r_Execute_HBH(bool _setHighBeam,
		HighBeamMotor _setHighBeamMotor, HighBeamRange _setHighBeamRange) {
	r_set_high_beam_headlights(_setHighBeam, _setHighBeamMotor,
			_setHighBeamRange);
}
void CarSystem003HighBeam::r_IncreasingPlan_HBH() {
	{
		auto _d = lightIlluminationDistance(calculateSpeed());
		auto _l = luminousStrength(calculateSpeed());
		r_Execute_HBH(true, _d, _l);
	}
}
void CarSystem003HighBeam::r_DecreasingPlan_HBH() {
	r_Execute_HBH(true, 30, 0);
}
void CarSystem003HighBeam::r_Monitor_Analyze_HBH() {
	if (adaptiveHighBeamActivated()) {
		{ //par
			if (drivesFasterThan(currentSpeed, 300) & !oncomingTraffic) {
				r_IncreasingPlan_HBH();
			}
			if (oncomingTraffic) {
				r_DecreasingPlan_HBH();
			}
		} //endpar
	}
}
void CarSystem003HighBeam::r_MAPE_HBH() {
	{ //par
		r_Monitor_Analyze_HBH();
		if (adaptiveHighBeamDeactivated()) {
			highBeamOn[1] = false;
		}
	} //endpar
}
void CarSystem003HighBeam::r_HighBeam() {
	{ //par
		pitmanArmForthBackPrevious[1] = pitmanArmForthBack;
		r_Manual_high_beam_headlights();
		r_MAPE_HBH();
	} //endpar
}

/* Static function definition */
HighBeamMotor CarSystem003HighBeam::percentageHBM(int _x) {
	return /*conditionalTerm*/
	((_x <= 65)) ? 0 :
	/*conditionalTerm*/
	((_x <= 100)) ? 1 :
	/*conditionalTerm*/
	((_x <= 120)) ? 2 :
	/*conditionalTerm*/
	((_x <= 140)) ? 3 :
	/*conditionalTerm*/
	((_x <= 160)) ? 4 :
	/*conditionalTerm*/
	((_x <= 180)) ? 5 :
	/*conditionalTerm*/
	((_x <= 200)) ? 6 :
	/*conditionalTerm*/
	((_x <= 220)) ? 7 :
	/*conditionalTerm*/
	((_x <= 240)) ? 8 :
	/*conditionalTerm*/
	((_x <= 260)) ? 9 :
	/*conditionalTerm*/
	((_x <= 280)) ? 10 :
	/*conditionalTerm*/
	((_x <= 300)) ? 11 :
	/*conditionalTerm*/
	((_x <= 320)) ? 12 :
	/*conditionalTerm*/
	((_x <= 340)) ? 13 :
	/*conditionalTerm*/
	((_x <= 360)) ? 14 : 14;
}
HighBeamMotor CarSystem003HighBeam::lightIlluminationDistance(CurrentSpeed _y) {
	auto _x = (_y / 10);
	return /*conditionalTerm*/
	((_x <= 171.0)) ?
			percentageHBM((int) (((((_x * _x) * 2.0) * 0.00025) + 95.0))) : 11;
}
HighBeamRange CarSystem003HighBeam::luminousStrength(CurrentSpeed _x) {
	return /*conditionalTerm*/
	((_x <= 1200)) ? (int) (((((7 * _x) / 10) + 60.0) / 9.0)) : 100;
}
CurrentSpeed CarSystem003HighBeam::calculateSpeed() {
	return /*conditionalTerm*/
	((cruiseControlMode == CCM2)) ? setVehicleSpeed[0] : currentSpeed;
}
bool CarSystem003HighBeam::drivesFasterThan(CurrentSpeed _speed, int _x) {
	return (_speed >= _x);
}
bool CarSystem003HighBeam::adaptiveHighBeamActivated() {
	return (lightRotarySwitch == AUTO) & engineOn(keyState)
			& (pitmanArmForthBack == BACKWARD);
}
bool CarSystem003HighBeam::adaptiveHighBeamDeactivated() {
	return (lightRotarySwitch == AUTO) & (pitmanArmForthBack == NEUTRAL_FB)
			& (pitmanArmForthBackPrevious[0] == BACKWARD);
}
bool CarSystem003HighBeam::headlampFlasherActivated() {
	return (pitmanArmForthBack == FORWARD)
			& (pitmanArmForthBackPrevious[0] == NEUTRAL_FB);
}
bool CarSystem003HighBeam::headlampFlasherDeactivated() {
	return (pitmanArmForthBack == NEUTRAL_FB)
			& (pitmanArmForthBackPrevious[0] == FORWARD);
}
bool CarSystem003HighBeam::headlampFixedActivated() {
	return (pitmanArmForthBack == BACKWARD) & (lightRotarySwitch == ON)
			& (keyState == KEYINSERTED) | engineOn(keyState);
}
bool CarSystem003HighBeam::headlampFixedDeactivated() {
	return (pitmanArmForthBack == NEUTRAL_FB)
			& (pitmanArmForthBackPrevious[0] == BACKWARD)
			& (lightRotarySwitch == ON) | (lightRotarySwitch == OFF)
			| (keyState == NOKEYINSERTED) & !headlampFlasherActivated();
}
HighBeamMotor varHighBeamMotor_0 = 0;
HighBeamMotor varHighBeamMotor_1 = 1;
HighBeamMotor varHighBeamMotor_2 = 2;
HighBeamMotor varHighBeamMotor_3 = 3;
HighBeamMotor varHighBeamMotor_4 = 4;
HighBeamMotor varHighBeamMotor_5 = 5;
HighBeamMotor varHighBeamMotor_6 = 6;
HighBeamMotor varHighBeamMotor_7 = 7;
HighBeamMotor varHighBeamMotor_8 = 8;
HighBeamMotor varHighBeamMotor_9 = 9;
HighBeamMotor varHighBeamMotor_10 = 10;
HighBeamMotor varHighBeamMotor_11 = 11;
HighBeamMotor varHighBeamMotor_12 = 12;
HighBeamMotor varHighBeamMotor_13 = 13;
HighBeamMotor varHighBeamMotor_14 = 14;
CameraState varCameraState_CAMERA_READY = CAMERA_READY;
CameraState varCameraState_CAMERA_DIRTY = CAMERA_DIRTY;
CameraState varCameraState_CAMERA_NOTREADY = CAMERA_NOTREADY;
PitmanArmForthBack varPitmanArmForthBack_BACKWARD = BACKWARD;
PitmanArmForthBack varPitmanArmForthBack_FORWARD = FORWARD;
PitmanArmForthBack varPitmanArmForthBack_NEUTRAL_FB = NEUTRAL_FB;

/* Function and domain initialization */
CarSystem003HighBeam::CarSystem003HighBeam() :
// Static domain initialization 
HighBeamMotor_elems(set<int> { &varHighBeamMotor_0, &varHighBeamMotor_1,
		&varHighBeamMotor_2, &varHighBeamMotor_3, &varHighBeamMotor_4,
		&varHighBeamMotor_5, &varHighBeamMotor_6, &varHighBeamMotor_7,
		&varHighBeamMotor_8, &varHighBeamMotor_9, &varHighBeamMotor_10,
		&varHighBeamMotor_11, &varHighBeamMotor_12, &varHighBeamMotor_13,
		&varHighBeamMotor_14 }), CameraState_elems( {
		&varCameraState_CAMERA_READY, &var_CameraState_CAMERA_DIRTY,
		&var_CameraState_CAMERA_NOTREADY }), PitmanArmForthBack_elems( {
		&varPitmanArmForthBack_BACKWARD, &var_PitmanArmForthBack_FORWARD,
		&var_PitmanArmForthBack_NEUTRAL_FB }) {
	/* Init static functions Abstract domain */
	/* Function initialization */
}

/* initialize controlled functions that contains monitored functions in the init term */
void CarSystem003HighBeam::initControlledWithMonitored() {
}

/* Apply the update set */
void CarSystem003HighBeam::fireUpdateSet() {
	highBeamOn[0] = highBeamOn[1];
	highBeamRange[0] = highBeamRange[1];
	highBeamMotor[0] = highBeamMotor[1];
	pitmanArmForthBackPrevious[0] = pitmanArmForthBackPrevious[1];
}

/* init static functions and elements of abstract domains */

