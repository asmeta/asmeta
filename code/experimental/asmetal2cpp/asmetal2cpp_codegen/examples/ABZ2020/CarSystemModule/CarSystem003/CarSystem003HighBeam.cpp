	/* CarSystem003HighBeam.cpp automatically generated from ASM2CODE */
	#include "CarSystem003HighBeam.h"
	
	using namespace CarSystem003HighBeamnamespace;
	
	/* Conversion of ASM rules in C++ methods */
	void CarSystem003HighBeam::r_set_high_beam_headlights (bool _v, HighBeamMotor _d, HighBeamRange _l){
		{ //par
			highBeamOn[1] = _v;
			highBeamMotor[1] = _d;
			highBeamRange[1] = _l;
		}//endpar
	}
	void CarSystem003HighBeam::r_Manual_high_beam_headlights(){
		{ //par
			if (headlampFlasherActivated()){ 
				r_set_high_beam_headlights(true, 14, 100);
			}
			if (headlampFlasherDeactivated() | headlampFixedDeactivated()){ 
				highBeamOn[1] = false;
			}
			if (headlampFixedActivated()){ 
				r_set_high_beam_headlights(true, 7, 100);
			}
		}//endpar
	}
	void CarSystem003HighBeam::r_Execute_HBH (bool _setHighBeam, HighBeamMotor _setHighBeamMotor, HighBeamRange _setHighBeamRange){
		r_set_high_beam_headlights(_setHighBeam, _setHighBeamMotor, _setHighBeamRange);
	}
	void CarSystem003HighBeam::r_IncreasingPlan_HBH(){
		{
		auto _d = lightIlluminationDistance(calculateSpeed());
		auto _l = luminousStrength(calculateSpeed());
		r_Execute_HBH(true, _d, _l);
		}
	}
	void CarSystem003HighBeam::r_DecreasingPlan_HBH(){
		r_Execute_HBH(true, 30, 0);
	}
	void CarSystem003HighBeam::r_Monitor_Analyze_HBH(){
		if (adaptiveHighBeamActivated()){ 
			{ //par
				if (drivesFasterThan(currentSpeed, 300) & ! oncomingTraffic){ 
					r_IncreasingPlan_HBH();
				}
				if (oncomingTraffic){ 
					r_DecreasingPlan_HBH();
				}
			}//endpar
		}
	}
	void CarSystem003HighBeam::r_MAPE_HBH(){
		{ //par
			r_Monitor_Analyze_HBH();
			if (adaptiveHighBeamDeactivated()){ 
				highBeamOn[1] = false;
			}
		}//endpar
	}
	void CarSystem003HighBeam::r_HighBeam(){
		{ //par
			pitmanArmForthBackPrevious[1] = pitmanArmForthBack;
			r_Manual_high_beam_headlights();
			r_MAPE_HBH();
		}//endpar
	}
	
	/* Static function definition */
	HighBeamMotor CarSystem003HighBeam::percentageHBM(int _x){return /*conditionalTerm*/
		((_x <= 65))
		?
			0
		:
			/*conditionalTerm*/
				((_x <= 100))
				?
					1
				:
					/*conditionalTerm*/
						((_x <= 120))
						?
							2
						:
							/*conditionalTerm*/
								((_x <= 140))
								?
									3
								:
									/*conditionalTerm*/
										((_x <= 160))
										?
											4
										:
											/*conditionalTerm*/
												((_x <= 180))
												?
													5
												:
													/*conditionalTerm*/
														((_x <= 200))
														?
															6
														:
															/*conditionalTerm*/
																((_x <= 220))
																?
																	7
																:
																	/*conditionalTerm*/
																		((_x <= 240))
																		?
																			8
																		:
																			/*conditionalTerm*/
																				((_x <= 260))
																				?
																					9
																				:
																					/*conditionalTerm*/
																						((_x <= 280))
																						?
																							10
																						:
																							/*conditionalTerm*/
																								((_x <= 300))
																								?
																									11
																								:
																									/*conditionalTerm*/
																										((_x <= 320))
																										?
																											12
																										:
																											/*conditionalTerm*/
																												((_x <= 340))
																												?
																													13
																												:
																													/*conditionalTerm*/
																														((_x <= 360))
																														?
																															14
																														:
	;}
	HighBeamMotor CarSystem003HighBeam::lightIlluminationDistance(CurrentSpeed _y){return //  use a local variable 
	int _x
	/*conditionalTerm*/
		((_x <= 171.0))
		?
			percentageHBM(rtoi(((((_x * _x) * 2.0) * 0.00025) + 95.0)))
		:
			11
	;}
	HighBeamRange CarSystem003HighBeam::luminousStrength(CurrentSpeed _x){return /*conditionalTerm*/
		((_x <= 1200))
		?
			rtoi()
		:
			100
	;}
	CurrentSpeed CarSystem003HighBeam::calculateSpeed(){return /*conditionalTerm*/
		((cruiseControlMode == CCM2))
		?
			setVehicleSpeed[0]
		:
			currentSpeed
	;}
	bool CarSystem003HighBeam::drivesFasterThan(CurrentSpeed _speed, int _x){return (_speed >= _x);}
	bool CarSystem003HighBeam::adaptiveHighBeamActivated(){return (lightRotarySwitch == AUTO) & engineOn(keyState) & (pitmanArmForthBack == BACKWARD);}
	bool CarSystem003HighBeam::adaptiveHighBeamDeactivated(){return (lightRotarySwitch == AUTO) & (pitmanArmForthBack == NEUTRAL_FB) & (pitmanArmForthBackPrevious[0] == BACKWARD);}
	bool CarSystem003HighBeam::headlampFlasherActivated(){return (pitmanArmForthBack == FORWARD) & (pitmanArmForthBackPrevious[0] == NEUTRAL_FB);}
	bool CarSystem003HighBeam::headlampFlasherDeactivated(){return (pitmanArmForthBack == NEUTRAL_FB) & (pitmanArmForthBackPrevious[0] == FORWARD);}
	bool CarSystem003HighBeam::headlampFixedActivated(){return (pitmanArmForthBack == BACKWARD) & (lightRotarySwitch == ON) & (keyState == KEYINSERTED) | engineOn(keyState);}
	bool CarSystem003HighBeam::headlampFixedDeactivated(){return (pitmanArmForthBack == NEUTRAL_FB) & (pitmanArmForthBackPrevious[0] == BACKWARD) & (lightRotarySwitch == ON) | (lightRotarySwitch == OFF) | (keyState == NOKEYINSERTED) & ! headlampFlasherActivated();}
	HighBeamMotor var_0 = 0;
	HighBeamMotor var_1 =  1;
	HighBeamMotor var_2 =  2;
	HighBeamMotor var_3 =  3;
	HighBeamMotor var_4 =  4;
	HighBeamMotor var_5 =  5;
	HighBeamMotor var_6 =  6;
	HighBeamMotor var_7 =  7;
	HighBeamMotor var_8 =  8;
	HighBeamMotor var_9 =  9;
	HighBeamMotor var_10 =  10;
	HighBeamMotor var_11 =  11;
	HighBeamMotor var_12 =  12;
	HighBeamMotor var_13 =  13;
	HighBeamMotor var_14 =  14;
	CameraState var_CAMERA_READY = CAMERA_READY;
	CameraState var_CAMERA_DIRTY = CAMERA_DIRTY;
	CameraState var_CAMERA_NOTREADY = CAMERA_NOTREADY;
	CruiseControlMode var_CCM0 = CCM0;
	CruiseControlMode var_CCM1 = CCM1;
	CruiseControlMode var_CCM2 = CCM2;
	PitmanArmForthBack var_BACKWARD = BACKWARD;
	PitmanArmForthBack var_FORWARD = FORWARD;
	PitmanArmForthBack var_NEUTRAL_FB = NEUTRAL_FB;
	
	/* Function and domain initialization */
	CarSystem003HighBeam::CarSystem003HighBeam()/*: 
	// Static domain initialization 
	HighBeamMotor_elems(set<int>{var_0,var_ 1,var_ 2,var_ 3,var_ 4,var_ 5,var_ 6,var_ 7,var_ 8,var_ 9,var_ 10,var_ 11,var_ 12,var_ 13,var_ 14}), 
	CameraState_elems({var_CAMERA_READY,var_CAMERA_DIRTY,var_CAMERA_NOTREADY}
						), 
	CruiseControlMode_elems({var_CCM0,var_CCM1,var_CCM2}
						), 
	PitmanArmForthBack_elems({var_BACKWARD,var_FORWARD,var_NEUTRAL_FB}
						)
	*/{
	/* Init static functions Abstract domain */
	/* Function initialization */
	}
	
	/* initialize controlled functions that contains monitored functions in the init term */
	void CarSystem003HighBeam::initControlledWithMonitored(){
	}
	

	/* Apply the update set */
	void CarSystem003HighBeam::fireUpdateSet(){
		highBeamOn[0] = highBeamOn[1];
		highBeamRange[0] = highBeamRange[1];
		highBeamMotor[0] = highBeamMotor[1];
		pitmanArmForthBackPrevious[0] = pitmanArmForthBackPrevious[1];
		setVehicleSpeed[0] = setVehicleSpeed[1];
	}
	
	/* init static functions and elements of abstract domains */
	
