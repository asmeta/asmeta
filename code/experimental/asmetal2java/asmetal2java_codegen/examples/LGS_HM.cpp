// LGS_HM.cpp automatically generated from ASM2CODE

#include "LGS_HM.h"
using namespace LGS_HMnamespace;

// Conversion of ASM rules in C++ methods
void LGS_HM::r_closeDoor() {
	if (doors[0] == OPEN) {
		{ //par
			closeDoorsElectroValve[1] = true;
			doors[1] = CLOSING;
		} //endpar
	} else if (doors[0] == CLOSING) {
		if (doorsClosed()) {
			{ //par
				generalElectroValve[1] = false;
				closeDoorsElectroValve[1] = false;
				doors[1] = CLOSED;
			} //endpar
		}
	} else if (doors[0] == OPENING) {
		{ //par
			closeDoorsElectroValve[1] = true;
			openDoorsElectroValve[1] = false;
			doors[1] = CLOSING;
		} //endpar
	}
}
void LGS_HM::r_retractionSequence() {
	if ((gears[0] != RETRACTED)) {
		if (doors[0] == CLOSED) {
			{ //par
				generalElectroValve[1] = true;
				openDoorsElectroValve[1] = true;
				doors[1] = OPENING;
			} //endpar
		} else if (doors[0] == CLOSING) {
			{ //par
				closeDoorsElectroValve[1] = false;
				openDoorsElectroValve[1] = true;
				doors[1] = OPENING;
			} //endpar
		} else if (doors[0] == OPENING) {
			if (doorsOpen()) {
				{ //par
					openDoorsElectroValve[1] = false;
					doors[1] = OPEN;
				} //endpar
			}
		} else if (doors[0] == OPEN) {
			if (gears[0] == EXTENDED) {
				if (gearsShockAbsorber()) {
					{ //par
						retractGearsElectroValve[1] = true;
						gears[1] = RETRACTING;
					} //endpar
				}
			} else if (gears[0] == RETRACTING) {
				if (gearsRetracted()) {
					{ //par
						retractGearsElectroValve[1] = false;
						gears[1] = RETRACTED;
					} //endpar
				}
			} else if (gears[0] == EXTENDING) {
				{ //par
					extendGearsElectroValve[1] = false;
					retractGearsElectroValve[1] = true;
					gears[1] = RETRACTING;
				} //endpar
			}
		}
	} else {
		r_closeDoor();
	}
}
void LGS_HM::r_outgoingSequence() {
	if ((gears[0] != EXTENDED)) {
		if (doors[0] == CLOSED) {
			{ //par
				generalElectroValve[1] = true;
				openDoorsElectroValve[1] = true;
				doors[1] = OPENING;
			} //endpar
		} else if (doors[0] == OPENING) {
			if (doorsOpen()) {
				{ //par
					openDoorsElectroValve[1] = false;
					doors[1] = OPEN;
				} //endpar
			}
		} else if (doors[0] == CLOSING) {
			{ //par
				closeDoorsElectroValve[1] = false;
				openDoorsElectroValve[1] = true;
				doors[1] = OPENING;
			} //endpar
		} else if (doors[0] == OPEN) {
			if (gears[0] == RETRACTED) {
				{ //par
					extendGearsElectroValve[1] = true;
					gears[1] = EXTENDING;
				} //endpar
			} else if (gears[0] == EXTENDING) {
				if (gearsExtended()) {
					{ //par
						extendGearsElectroValve[1] = false;
						gears[1] = EXTENDED;
					} //endpar
				}
			} else if (gears[0] == RETRACTING) {
				{ //par
					extendGearsElectroValve[1] = true;
					retractGearsElectroValve[1] = false;
					gears[1] = EXTENDING;
				} //endpar
			}
		}
	} else {
		r_closeDoor();
	}
}
void LGS_HM::r_healthMonitoring() {
	{ //par
		if (openDoorsElectroValve[0] & aDoorClosed() & timeout) {
			anomaly[1] = true;
		}
		if (openDoorsElectroValve[0] & !doorsOpen() & timeout) {
			anomaly[1] = true;
		}
		if (closeDoorsElectroValve[0] & aDoorOpen() & timeout) {
			anomaly[1] = true;
		}
		if (closeDoorsElectroValve[0] & !doorsClosed() & timeout) {
			anomaly[1] = true;
		}
		if (retractGearsElectroValve[0] & aGearExtended() & timeout) {
			anomaly[1] = true;
		}
		if (retractGearsElectroValve[0] & !gearsRetracted() & timeout) {
			anomaly[1] = true;
		}
		if (extendGearsElectroValve[0] & aGearRetracted() & timeout) {
			anomaly[1] = true;
		}
		if (extendGearsElectroValve[0] & !gearsExtended() & timeout) {
			anomaly[1] = true;
		}
	} //endpar
}
void LGS_HM::r_Main() {
	if (!anomaly[0]) {
		{ //par
			if ((handle == UP)) {
				r_retractionSequence();
			} else {
				r_outgoingSequence();
			}
			r_healthMonitoring();
		} //endpar
	}
}

// Static function definition
bool LGS_HM::gearsExtended() {
	return [&]()->bool { /*<--- forAllTerm*/
		for(auto _s : LandingSet_elems)
		if(!(gearsExtended[_s]))
		return false;
		return true;
	}();
}
bool LGS_HM::gearsRetracted() {
	return [&]()->bool { /*<--- forAllTerm*/
		for(auto _s : LandingSet_elems)
		if(!(gearsRetracted[_s]))
		return false;
		return true;
	}();
}
bool LGS_HM::doorsClosed() {
	return [&]()->bool { /*<--- forAllTerm*/
		for(auto _s : LandingSet_elems)
		if(!(doorsClosed[_s]))
		return false;
		return true;
	}();
}
bool LGS_HM::doorsOpen() {
	return [&]()->bool { /*<--- forAllTerm*/
		for(auto _s : LandingSet_elems)
		if(!(doorsOpen[_s]))
		return false;
		return true;
	}();
}
bool LGS_HM::aGearExtended() {
	return [&]()->bool { /*<--- ExistsTerm*/
		for(auto _s : LandingSet_elems)
		if(gearsExtended[_s])
		return true;
		return false;
	}();
}
bool LGS_HM::aGearRetracted() {
	return [&]()->bool { /*<--- ExistsTerm*/
		for(auto _s : LandingSet_elems)
		if(gearsRetracted[_s])
		return true;
		return false;
	}();
}
bool LGS_HM::aDoorClosed() {
	return [&]()->bool { /*<--- ExistsTerm*/
		for(auto _s : LandingSet_elems)
		if(doorsClosed[_s])
		return true;
		return false;
	}();
}
bool LGS_HM::aDoorOpen() {
	return [&]()->bool { /*<--- ExistsTerm*/
		for(auto _s : LandingSet_elems)
		if(doorsOpen[_s])
		return true;
		return false;
	}();
}
bool LGS_HM::gearsShockAbsorber() {
	return [&]()->bool { /*<--- forAllTerm*/
		for(auto _s : LandingSet_elems)
		if(!(gearsShockAbsorber[_s]))
		return false;
		return true;
	}();
}
bool LGS_HM::greenLight() {
	return (gears[0] == EXTENDED);
}
bool LGS_HM::orangeLight() {
	return (gears[0] == EXTENDING) | (gears[0] == RETRACTING);
}
bool LGS_HM::redLight() {
	return anomaly[0];
}
CylinderStatus LGS_HM::cylindersDoors() {
	return [&]() { /*<--- caseTerm*/
		if(doors[0]==OPEN)
		return CYLINDER_EXTENDED;
		else if(doors[0]==OPENING)
		return CYLINDER_EXTENDING;
		else if(doors[0]==CLOSING)
		return CYLINDER_RETRACTING;
		else if(doors[0]==CLOSED)
		return CYLINDER_RETRACTED;
	}();
}
CylinderStatus LGS_HM::cylindersGears() {
	return [&]() { /*<--- caseTerm*/
		if(gears[0]==RETRACTED)
		return CYLINDER_RETRACTED;
		else if(gears[0]==EXTENDING)
		return CYLINDER_EXTENDING;
		else if(gears[0]==EXTENDED)
		return CYLINDER_EXTENDED;
		else if(gears[0]==RETRACTING)
		return CYLINDER_RETRACTING;
	}();
}

// Function and domain initialization
LGS_HM::LGS_HM() :
		// Static domain initialization 
		LandingSet_elems( { FRONT, LEFT, RIGHT }), HandleStatus_elems( { UP,
				DOWN }), DoorStatus_elems( { CLOSED, OPENING, OPEN, CLOSING }), GearStatus_elems(
				{ RETRACTED, EXTENDING, EXTENDED, RETRACTING }), CylinderStatus_elems(
				{ CYLINDER_RETRACTED, CYLINDER_EXTENDING, CYLINDER_EXTENDED,
						CYLINDER_RETRACTING }) {
	//Init static functions Abstract domain
	//Function initialization
	doors[0] = doors[1] = CLOSED;
	gears[0] = gears[1] = EXTENDED;
	generalElectroValve[0] = generalElectroValve[1] = false;
	extendGearsElectroValve[0] = extendGearsElectroValve[1] = false;
	retractGearsElectroValve[0] = retractGearsElectroValve[1] = false;
	openDoorsElectroValve[0] = openDoorsElectroValve[1] = false;
	closeDoorsElectroValve[0] = closeDoorsElectroValve[1] = false;
	anomaly[0] = anomaly[1] = false;
}

// initialize controlled functions that contains monitored functions in the init term
void LGS_HM::initControlledWithMonitored() {
}

// Apply the update set
void LGS_HM::fireUpdateSet() {
	doors[0] = doors[1];
	gears[0] = gears[1];
	generalElectroValve[0] = generalElectroValve[1];
	openDoorsElectroValve[0] = openDoorsElectroValve[1];
	closeDoorsElectroValve[0] = closeDoorsElectroValve[1];
	retractGearsElectroValve[0] = retractGearsElectroValve[1];
	extendGearsElectroValve[0] = extendGearsElectroValve[1];
	anomaly[0] = anomaly[1];
}

//init static functions and elements of abstract domains


