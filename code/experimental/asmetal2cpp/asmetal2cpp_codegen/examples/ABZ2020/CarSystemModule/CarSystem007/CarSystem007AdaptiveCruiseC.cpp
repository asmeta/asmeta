	/* CarSystem007AdaptiveCruiseC.cpp automatically generated from ASM2CODE */
	#include "CarSystem007AdaptiveCruiseC.h"
	
	using namespace CarSystem007AdaptiveCruiseCnamespace;
	
	/* Conversion of ASM rules in C++ methods */
	void CarSystem007AdaptiveCruiseC::r_CalculateSafetyDistancePlan_CC(){
		if ((currentSpeed < 200)){ 
			{ //par
				speedVehicleAhead_Prec[1] = speedVehicleAhead;
				if ((speedVehicleAhead < 200) & (speedVehicleAhead > 0)){ 
					setSafetyDistance[1] = rtoi((2.5 * ));
				}
				if (bothVehicleStanding()){ 
					setSafetyDistance[1] = 2;
				}
				if ((speedVehicleAhead > speedVehicleAhead_Prec[0]) & (currentSpeed != 0)){ 
					setSafetyDistance[1] = rtoi((3.0 * ));
				}
			}//endpar
		}
	}
	void CarSystem007AdaptiveCruiseC::r_SafetyDistanceByUser(){
		if (! adaptiveCruiseControlActivated()){ 
			if ((currentSpeed > 200)){ 
				if ((sCSLever == HEAD)){ 
					setSafetyDistance[1] = rtoi(( * safetyDistance));
				}
			}
		}
	}
	void CarSystem007AdaptiveCruiseC::r_CollisionDetection(){
		{ //par
			if ((rangeRadarSensor < brakingDistance())){ 
				acousticCollisionSignals[1] = true;
			}
			if ((rangeRadarSensor > brakingDistance()) & (acousticCollisionSignals[0] == true)){ 
				acousticCollisionSignals[1] = false;
			}
		}//endpar
	}
	void CarSystem007AdaptiveCruiseC::r_AcceleratePlan_CC(){
			if ((currentSpeed < setVehicleSpeed[0])){ 
			{ //par
				acceleration[1] = 2;
				if (! manualSpeed()){ 
					if ((desiredSpeed[0] > setVehicleSpeed[0])){ 
							if ((setTargetFromAccDec > speedVehicleAhead)){ 
							setVehicleSpeed[1] = speedVehicleAhead;
							}else{
							setVehicleSpeed[1] = setTargetFromAccDec;
						}
					}
				}
			}//endpar
			}else{
			acceleration[1] = 0;
		}
	}
	void CarSystem007AdaptiveCruiseC::r_DeceleratePlan_CC(){
			if ((currentSpeed != 0)){ 
			{ //par
				acceleration[1] = -5;
				if (! manualSpeed()){ 
					setVehicleSpeed[1] = setTargetFromAccDec;
				}
			}//endpar
			}else{
			acceleration[1] = 0;
		}
	}
	void CarSystem007AdaptiveCruiseC::r_WarningPlan_CC(){
		{ //par
			if ((itor(rangeRadarSensor) < ( * 1.5))){ 
					visualWarningOn[1] = true;
			} else if (visualWarningOn[0]){ 
				visualWarningOn[1] = false;
			}
			if ((itor(rangeRadarSensor) < ( * 0.8))){ 
					acousticWarningOn[1] = true;
			} else if (acousticWarningOn[0]){ 
				acousticWarningOn[1] = false;
			}
		}//endpar
	}
	void CarSystem007AdaptiveCruiseC::r_Monitor_Analyze_CC(){
		if (adaptiveCruiseControlActivated()){ 
			{ //par
				if (obstacleAhead() & (rangeRadarSensor < setSafetyDistance[0])){ 
					r_AcceleratePlan_CC();
				}
				if (obstacleAhead() & (rangeRadarSensor > setSafetyDistance[0])){ 
					r_DeceleratePlan_CC();
				}
				r_CollisionDetection();
				if (obstacleAhead()){ 
					r_WarningPlan_CC();
				}
				r_CalculateSafetyDistancePlan_CC();
			}//endpar
		}
	}
	void CarSystem007AdaptiveCruiseC::r_MAPE_CC(){
		r_Monitor_Analyze_CC();
	}
	
	/* Static function definition */
	bool CarSystem007AdaptiveCruiseC::adaptiveCruiseControlActivated(){return (cruiseControlMode == CCM2);}
	bool CarSystem007AdaptiveCruiseC::obstacleAhead(){return (rangeRadarState == READY) & (rangeRadarSensor != 0);}
	bool CarSystem007AdaptiveCruiseC::bothVehicleStanding(){return (currentSpeed == 0) & (speedVehicleAhead == 0);}
	int CarSystem007AdaptiveCruiseC::brakingDistance(){return rtoi();}
	bool CarSystem007AdaptiveCruiseC::manualSpeed(){return (sCSLever == UPWARD5_SCS) | (sCSLever == UPWARD7_SCS) | (sCSLever == DOWNWARD5_SCS) | (sCSLever == DOWNWARD7_SCS);}
	RangeRadarSensor var_0 = 0;
	RangeRadarSensor var_1 =  1;
	RangeRadarSensor var_2 =  2;
	RangeRadarSensor var_3 =  3;
	RangeRadarSensor var_4 =  4;
	RangeRadarSensor var_5 =  5;
	RangeRadarSensor var_6 =  6;
	RangeRadarSensor var_7 =  7;
	RangeRadarSensor var_8 =  8;
	RangeRadarSensor var_9 =  9;
	RangeRadarSensor var_10 =  10;
	RangeRadarSensor var_11 =  11;
	RangeRadarSensor var_12 =  12;
	RangeRadarSensor var_13 =  13;
	RangeRadarSensor var_14 =  14;
	RangeRadarSensor var_15 =  15;
	RangeRadarSensor var_16 =  16;
	RangeRadarSensor var_17 =  17;
	RangeRadarSensor var_18 =  18;
	RangeRadarSensor var_19 =  19;
	RangeRadarSensor var_20 =  20;
	RangeRadarSensor var_21 =  21;
	RangeRadarSensor var_22 =  22;
	RangeRadarSensor var_23 =  23;
	RangeRadarSensor var_24 =  24;
	RangeRadarSensor var_25 =  25;
	RangeRadarSensor var_26 =  26;
	RangeRadarSensor var_27 =  27;
	RangeRadarSensor var_28 =  28;
	RangeRadarSensor var_29 =  29;
	RangeRadarSensor var_30 =  30;
	RangeRadarSensor var_31 =  31;
	RangeRadarSensor var_32 =  32;
	RangeRadarSensor var_33 =  33;
	RangeRadarSensor var_34 =  34;
	RangeRadarSensor var_35 =  35;
	RangeRadarSensor var_36 =  36;
	RangeRadarSensor var_37 =  37;
	RangeRadarSensor var_38 =  38;
	RangeRadarSensor var_39 =  39;
	RangeRadarSensor var_40 =  40;
	RangeRadarSensor var_41 =  41;
	RangeRadarSensor var_42 =  42;
	RangeRadarSensor var_43 =  43;
	RangeRadarSensor var_44 =  44;
	RangeRadarSensor var_45 =  45;
	RangeRadarSensor var_46 =  46;
	RangeRadarSensor var_47 =  47;
	RangeRadarSensor var_48 =  48;
	RangeRadarSensor var_49 =  49;
	RangeRadarSensor var_50 =  50;
	RangeRadarSensor var_51 =  51;
	RangeRadarSensor var_52 =  52;
	RangeRadarSensor var_53 =  53;
	RangeRadarSensor var_54 =  54;
	RangeRadarSensor var_55 =  55;
	RangeRadarSensor var_56 =  56;
	RangeRadarSensor var_57 =  57;
	RangeRadarSensor var_58 =  58;
	RangeRadarSensor var_59 =  59;
	RangeRadarSensor var_60 =  60;
	RangeRadarSensor var_61 =  61;
	RangeRadarSensor var_62 =  62;
	RangeRadarSensor var_63 =  63;
	RangeRadarSensor var_64 =  64;
	RangeRadarSensor var_65 =  65;
	RangeRadarSensor var_66 =  66;
	RangeRadarSensor var_67 =  67;
	RangeRadarSensor var_68 =  68;
	RangeRadarSensor var_69 =  69;
	RangeRadarSensor var_70 =  70;
	RangeRadarSensor var_71 =  71;
	RangeRadarSensor var_72 =  72;
	RangeRadarSensor var_73 =  73;
	RangeRadarSensor var_74 =  74;
	RangeRadarSensor var_75 =  75;
	RangeRadarSensor var_76 =  76;
	RangeRadarSensor var_77 =  77;
	RangeRadarSensor var_78 =  78;
	RangeRadarSensor var_79 =  79;
	RangeRadarSensor var_80 =  80;
	RangeRadarSensor var_81 =  81;
	RangeRadarSensor var_82 =  82;
	RangeRadarSensor var_83 =  83;
	RangeRadarSensor var_84 =  84;
	RangeRadarSensor var_85 =  85;
	RangeRadarSensor var_86 =  86;
	RangeRadarSensor var_87 =  87;
	RangeRadarSensor var_88 =  88;
	RangeRadarSensor var_89 =  89;
	RangeRadarSensor var_90 =  90;
	RangeRadarSensor var_91 =  91;
	RangeRadarSensor var_92 =  92;
	RangeRadarSensor var_93 =  93;
	RangeRadarSensor var_94 =  94;
	RangeRadarSensor var_95 =  95;
	RangeRadarSensor var_96 =  96;
	RangeRadarSensor var_97 =  97;
	RangeRadarSensor var_98 =  98;
	RangeRadarSensor var_99 =  99;
	RangeRadarSensor var_100 =  100;
	RangeRadarSensor var_101 =  101;
	RangeRadarSensor var_102 =  102;
	RangeRadarSensor var_103 =  103;
	RangeRadarSensor var_104 =  104;
	RangeRadarSensor var_105 =  105;
	RangeRadarSensor var_106 =  106;
	RangeRadarSensor var_107 =  107;
	RangeRadarSensor var_108 =  108;
	RangeRadarSensor var_109 =  109;
	RangeRadarSensor var_110 =  110;
	RangeRadarSensor var_111 =  111;
	RangeRadarSensor var_112 =  112;
	RangeRadarSensor var_113 =  113;
	RangeRadarSensor var_114 =  114;
	RangeRadarSensor var_115 =  115;
	RangeRadarSensor var_116 =  116;
	RangeRadarSensor var_117 =  117;
	RangeRadarSensor var_118 =  118;
	RangeRadarSensor var_119 =  119;
	RangeRadarSensor var_120 =  120;
	RangeRadarSensor var_121 =  121;
	RangeRadarSensor var_122 =  122;
	RangeRadarSensor var_123 =  123;
	RangeRadarSensor var_124 =  124;
	RangeRadarSensor var_125 =  125;
	RangeRadarSensor var_126 =  126;
	RangeRadarSensor var_127 =  127;
	RangeRadarSensor var_128 =  128;
	RangeRadarSensor var_129 =  129;
	RangeRadarSensor var_130 =  130;
	RangeRadarSensor var_131 =  131;
	RangeRadarSensor var_132 =  132;
	RangeRadarSensor var_133 =  133;
	RangeRadarSensor var_134 =  134;
	RangeRadarSensor var_135 =  135;
	RangeRadarSensor var_136 =  136;
	RangeRadarSensor var_137 =  137;
	RangeRadarSensor var_138 =  138;
	RangeRadarSensor var_139 =  139;
	RangeRadarSensor var_140 =  140;
	RangeRadarSensor var_141 =  141;
	RangeRadarSensor var_142 =  142;
	RangeRadarSensor var_143 =  143;
	RangeRadarSensor var_144 =  144;
	RangeRadarSensor var_145 =  145;
	RangeRadarSensor var_146 =  146;
	RangeRadarSensor var_147 =  147;
	RangeRadarSensor var_148 =  148;
	RangeRadarSensor var_149 =  149;
	RangeRadarSensor var_150 =  150;
	RangeRadarSensor var_151 =  151;
	RangeRadarSensor var_152 =  152;
	RangeRadarSensor var_153 =  153;
	RangeRadarSensor var_154 =  154;
	RangeRadarSensor var_155 =  155;
	RangeRadarSensor var_156 =  156;
	RangeRadarSensor var_157 =  157;
	RangeRadarSensor var_158 =  158;
	RangeRadarSensor var_159 =  159;
	RangeRadarSensor var_160 =  160;
	RangeRadarSensor var_161 =  161;
	RangeRadarSensor var_162 =  162;
	RangeRadarSensor var_163 =  163;
	RangeRadarSensor var_164 =  164;
	RangeRadarSensor var_165 =  165;
	RangeRadarSensor var_166 =  166;
	RangeRadarSensor var_167 =  167;
	RangeRadarSensor var_168 =  168;
	RangeRadarSensor var_169 =  169;
	RangeRadarSensor var_170 =  170;
	RangeRadarSensor var_171 =  171;
	RangeRadarSensor var_172 =  172;
	RangeRadarSensor var_173 =  173;
	RangeRadarSensor var_174 =  174;
	RangeRadarSensor var_175 =  175;
	RangeRadarSensor var_176 =  176;
	RangeRadarSensor var_177 =  177;
	RangeRadarSensor var_178 =  178;
	RangeRadarSensor var_179 =  179;
	RangeRadarSensor var_180 =  180;
	RangeRadarSensor var_181 =  181;
	RangeRadarSensor var_182 =  182;
	RangeRadarSensor var_183 =  183;
	RangeRadarSensor var_184 =  184;
	RangeRadarSensor var_185 =  185;
	RangeRadarSensor var_186 =  186;
	RangeRadarSensor var_187 =  187;
	RangeRadarSensor var_188 =  188;
	RangeRadarSensor var_189 =  189;
	RangeRadarSensor var_190 =  190;
	RangeRadarSensor var_191 =  191;
	RangeRadarSensor var_192 =  192;
	RangeRadarSensor var_193 =  193;
	RangeRadarSensor var_194 =  194;
	RangeRadarSensor var_195 =  195;
	RangeRadarSensor var_196 =  196;
	RangeRadarSensor var_197 =  197;
	RangeRadarSensor var_198 =  198;
	RangeRadarSensor var_199 =  199;
	RangeRadarSensor var_200 =  200;
	RangeRadarState var_READY = READY;
	RangeRadarState var_DIRTY = DIRTY;
	RangeRadarState var_NOTREADY = NOTREADY;
	Aceleration var_ACCP5 = ACCP5;
	Aceleration var_DECM2 = DECM2;
	Aceleration var_NOACC = NOACC;
	
	/* Function and domain initialization */
	CarSystem007AdaptiveCruiseC::CarSystem007AdaptiveCruiseC()/*: 
	// Static domain initialization 
	RangeRadarSensor_elems(set<int>{var_0,var_ 1,var_ 2,var_ 3,var_ 4,var_ 5,var_ 6,var_ 7,var_ 8,var_ 9,var_ 10,var_ 11,var_ 12,var_ 13,var_ 14,var_ 15,var_ 16,var_ 17,var_ 18,var_ 19,var_ 20,var_ 21,var_ 22,var_ 23,var_ 24,var_ 25,var_ 26,var_ 27,var_ 28,var_ 29,var_ 30,var_ 31,var_ 32,var_ 33,var_ 34,var_ 35,var_ 36,var_ 37,var_ 38,var_ 39,var_ 40,var_ 41,var_ 42,var_ 43,var_ 44,var_ 45,var_ 46,var_ 47,var_ 48,var_ 49,var_ 50,var_ 51,var_ 52,var_ 53,var_ 54,var_ 55,var_ 56,var_ 57,var_ 58,var_ 59,var_ 60,var_ 61,var_ 62,var_ 63,var_ 64,var_ 65,var_ 66,var_ 67,var_ 68,var_ 69,var_ 70,var_ 71,var_ 72,var_ 73,var_ 74,var_ 75,var_ 76,var_ 77,var_ 78,var_ 79,var_ 80,var_ 81,var_ 82,var_ 83,var_ 84,var_ 85,var_ 86,var_ 87,var_ 88,var_ 89,var_ 90,var_ 91,var_ 92,var_ 93,var_ 94,var_ 95,var_ 96,var_ 97,var_ 98,var_ 99,var_ 100,var_ 101,var_ 102,var_ 103,var_ 104,var_ 105,var_ 106,var_ 107,var_ 108,var_ 109,var_ 110,var_ 111,var_ 112,var_ 113,var_ 114,var_ 115,var_ 116,var_ 117,var_ 118,var_ 119,var_ 120,var_ 121,var_ 122,var_ 123,var_ 124,var_ 125,var_ 126,var_ 127,var_ 128,var_ 129,var_ 130,var_ 131,var_ 132,var_ 133,var_ 134,var_ 135,var_ 136,var_ 137,var_ 138,var_ 139,var_ 140,var_ 141,var_ 142,var_ 143,var_ 144,var_ 145,var_ 146,var_ 147,var_ 148,var_ 149,var_ 150,var_ 151,var_ 152,var_ 153,var_ 154,var_ 155,var_ 156,var_ 157,var_ 158,var_ 159,var_ 160,var_ 161,var_ 162,var_ 163,var_ 164,var_ 165,var_ 166,var_ 167,var_ 168,var_ 169,var_ 170,var_ 171,var_ 172,var_ 173,var_ 174,var_ 175,var_ 176,var_ 177,var_ 178,var_ 179,var_ 180,var_ 181,var_ 182,var_ 183,var_ 184,var_ 185,var_ 186,var_ 187,var_ 188,var_ 189,var_ 190,var_ 191,var_ 192,var_ 193,var_ 194,var_ 195,var_ 196,var_ 197,var_ 198,var_ 199,var_ 200}), 
	RangeRadarState_elems({var_READY,var_DIRTY,var_NOTREADY}
						), 
	Aceleration_elems({var_ACCP5,var_DECM2,var_NOACC}
						)
	*/{
	/* Init static functions Abstract domain */
	/* Function initialization */
	}
	
	/* initialize controlled functions that contains monitored functions in the init term */
	void CarSystem007AdaptiveCruiseC::initControlledWithMonitored(){
	}
	

	/* Apply the update set */
	void CarSystem007AdaptiveCruiseC::fireUpdateSet(){
		acousticWarningOn[0] = acousticWarningOn[1];
		visualWarningOn[0] = visualWarningOn[1];
		speedVehicleAhead_Prec[0] = speedVehicleAhead_Prec[1];
		setSafetyDistance[0] = setSafetyDistance[1];
		brakePressure[0] = brakePressure[1];
		acceleration[0] = acceleration[1];
		acousticCollisionSignals[0] = acousticCollisionSignals[1];
	}
	
	/* init static functions and elements of abstract domains */
	
