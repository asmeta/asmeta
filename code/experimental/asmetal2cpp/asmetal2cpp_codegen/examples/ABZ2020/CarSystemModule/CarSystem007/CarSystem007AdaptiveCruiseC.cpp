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
RangeRadarSensor varRangeRadarSensor_0 = 0;
RangeRadarSensor varRangeRadarSensor_1 = 1;
RangeRadarSensor varRangeRadarSensor_2 = 2;
RangeRadarSensor varRangeRadarSensor_3 = 3;
RangeRadarSensor varRangeRadarSensor_4 = 4;
RangeRadarSensor varRangeRadarSensor_5 = 5;
RangeRadarSensor varRangeRadarSensor_6 = 6;
RangeRadarSensor varRangeRadarSensor_7 = 7;
RangeRadarSensor varRangeRadarSensor_8 = 8;
RangeRadarSensor varRangeRadarSensor_9 = 9;
RangeRadarSensor varRangeRadarSensor_10 = 10;
RangeRadarSensor varRangeRadarSensor_11 = 11;
RangeRadarSensor varRangeRadarSensor_12 = 12;
RangeRadarSensor varRangeRadarSensor_13 = 13;
RangeRadarSensor varRangeRadarSensor_14 = 14;
RangeRadarSensor varRangeRadarSensor_15 = 15;
RangeRadarSensor varRangeRadarSensor_16 = 16;
RangeRadarSensor varRangeRadarSensor_17 = 17;
RangeRadarSensor varRangeRadarSensor_18 = 18;
RangeRadarSensor varRangeRadarSensor_19 = 19;
RangeRadarSensor varRangeRadarSensor_20 = 20;
RangeRadarSensor varRangeRadarSensor_21 = 21;
RangeRadarSensor varRangeRadarSensor_22 = 22;
RangeRadarSensor varRangeRadarSensor_23 = 23;
RangeRadarSensor varRangeRadarSensor_24 = 24;
RangeRadarSensor varRangeRadarSensor_25 = 25;
RangeRadarSensor varRangeRadarSensor_26 = 26;
RangeRadarSensor varRangeRadarSensor_27 = 27;
RangeRadarSensor varRangeRadarSensor_28 = 28;
RangeRadarSensor varRangeRadarSensor_29 = 29;
RangeRadarSensor varRangeRadarSensor_30 = 30;
RangeRadarSensor varRangeRadarSensor_31 = 31;
RangeRadarSensor varRangeRadarSensor_32 = 32;
RangeRadarSensor varRangeRadarSensor_33 = 33;
RangeRadarSensor varRangeRadarSensor_34 = 34;
RangeRadarSensor varRangeRadarSensor_35 = 35;
RangeRadarSensor varRangeRadarSensor_36 = 36;
RangeRadarSensor varRangeRadarSensor_37 = 37;
RangeRadarSensor varRangeRadarSensor_38 = 38;
RangeRadarSensor varRangeRadarSensor_39 = 39;
RangeRadarSensor varRangeRadarSensor_40 = 40;
RangeRadarSensor varRangeRadarSensor_41 = 41;
RangeRadarSensor varRangeRadarSensor_42 = 42;
RangeRadarSensor varRangeRadarSensor_43 = 43;
RangeRadarSensor varRangeRadarSensor_44 = 44;
RangeRadarSensor varRangeRadarSensor_45 = 45;
RangeRadarSensor varRangeRadarSensor_46 = 46;
RangeRadarSensor varRangeRadarSensor_47 = 47;
RangeRadarSensor varRangeRadarSensor_48 = 48;
RangeRadarSensor varRangeRadarSensor_49 = 49;
RangeRadarSensor varRangeRadarSensor_50 = 50;
RangeRadarSensor varRangeRadarSensor_51 = 51;
RangeRadarSensor varRangeRadarSensor_52 = 52;
RangeRadarSensor varRangeRadarSensor_53 = 53;
RangeRadarSensor varRangeRadarSensor_54 = 54;
RangeRadarSensor varRangeRadarSensor_55 = 55;
RangeRadarSensor varRangeRadarSensor_56 = 56;
RangeRadarSensor varRangeRadarSensor_57 = 57;
RangeRadarSensor varRangeRadarSensor_58 = 58;
RangeRadarSensor varRangeRadarSensor_59 = 59;
RangeRadarSensor varRangeRadarSensor_60 = 60;
RangeRadarSensor varRangeRadarSensor_61 = 61;
RangeRadarSensor varRangeRadarSensor_62 = 62;
RangeRadarSensor varRangeRadarSensor_63 = 63;
RangeRadarSensor varRangeRadarSensor_64 = 64;
RangeRadarSensor varRangeRadarSensor_65 = 65;
RangeRadarSensor varRangeRadarSensor_66 = 66;
RangeRadarSensor varRangeRadarSensor_67 = 67;
RangeRadarSensor varRangeRadarSensor_68 = 68;
RangeRadarSensor varRangeRadarSensor_69 = 69;
RangeRadarSensor varRangeRadarSensor_70 = 70;
RangeRadarSensor varRangeRadarSensor_71 = 71;
RangeRadarSensor varRangeRadarSensor_72 = 72;
RangeRadarSensor varRangeRadarSensor_73 = 73;
RangeRadarSensor varRangeRadarSensor_74 = 74;
RangeRadarSensor varRangeRadarSensor_75 = 75;
RangeRadarSensor varRangeRadarSensor_76 = 76;
RangeRadarSensor varRangeRadarSensor_77 = 77;
RangeRadarSensor varRangeRadarSensor_78 = 78;
RangeRadarSensor varRangeRadarSensor_79 = 79;
RangeRadarSensor varRangeRadarSensor_80 = 80;
RangeRadarSensor varRangeRadarSensor_81 = 81;
RangeRadarSensor varRangeRadarSensor_82 = 82;
RangeRadarSensor varRangeRadarSensor_83 = 83;
RangeRadarSensor varRangeRadarSensor_84 = 84;
RangeRadarSensor varRangeRadarSensor_85 = 85;
RangeRadarSensor varRangeRadarSensor_86 = 86;
RangeRadarSensor varRangeRadarSensor_87 = 87;
RangeRadarSensor varRangeRadarSensor_88 = 88;
RangeRadarSensor varRangeRadarSensor_89 = 89;
RangeRadarSensor varRangeRadarSensor_90 = 90;
RangeRadarSensor varRangeRadarSensor_91 = 91;
RangeRadarSensor varRangeRadarSensor_92 = 92;
RangeRadarSensor varRangeRadarSensor_93 = 93;
RangeRadarSensor varRangeRadarSensor_94 = 94;
RangeRadarSensor varRangeRadarSensor_95 = 95;
RangeRadarSensor varRangeRadarSensor_96 = 96;
RangeRadarSensor varRangeRadarSensor_97 = 97;
RangeRadarSensor varRangeRadarSensor_98 = 98;
RangeRadarSensor varRangeRadarSensor_99 = 99;
RangeRadarSensor varRangeRadarSensor_100 = 100;
RangeRadarSensor varRangeRadarSensor_101 = 101;
RangeRadarSensor varRangeRadarSensor_102 = 102;
RangeRadarSensor varRangeRadarSensor_103 = 103;
RangeRadarSensor varRangeRadarSensor_104 = 104;
RangeRadarSensor varRangeRadarSensor_105 = 105;
RangeRadarSensor varRangeRadarSensor_106 = 106;
RangeRadarSensor varRangeRadarSensor_107 = 107;
RangeRadarSensor varRangeRadarSensor_108 = 108;
RangeRadarSensor varRangeRadarSensor_109 = 109;
RangeRadarSensor varRangeRadarSensor_110 = 110;
RangeRadarSensor varRangeRadarSensor_111 = 111;
RangeRadarSensor varRangeRadarSensor_112 = 112;
RangeRadarSensor varRangeRadarSensor_113 = 113;
RangeRadarSensor varRangeRadarSensor_114 = 114;
RangeRadarSensor varRangeRadarSensor_115 = 115;
RangeRadarSensor varRangeRadarSensor_116 = 116;
RangeRadarSensor varRangeRadarSensor_117 = 117;
RangeRadarSensor varRangeRadarSensor_118 = 118;
RangeRadarSensor varRangeRadarSensor_119 = 119;
RangeRadarSensor varRangeRadarSensor_120 = 120;
RangeRadarSensor varRangeRadarSensor_121 = 121;
RangeRadarSensor varRangeRadarSensor_122 = 122;
RangeRadarSensor varRangeRadarSensor_123 = 123;
RangeRadarSensor varRangeRadarSensor_124 = 124;
RangeRadarSensor varRangeRadarSensor_125 = 125;
RangeRadarSensor varRangeRadarSensor_126 = 126;
RangeRadarSensor varRangeRadarSensor_127 = 127;
RangeRadarSensor varRangeRadarSensor_128 = 128;
RangeRadarSensor varRangeRadarSensor_129 = 129;
RangeRadarSensor varRangeRadarSensor_130 = 130;
RangeRadarSensor varRangeRadarSensor_131 = 131;
RangeRadarSensor varRangeRadarSensor_132 = 132;
RangeRadarSensor varRangeRadarSensor_133 = 133;
RangeRadarSensor varRangeRadarSensor_134 = 134;
RangeRadarSensor varRangeRadarSensor_135 = 135;
RangeRadarSensor varRangeRadarSensor_136 = 136;
RangeRadarSensor varRangeRadarSensor_137 = 137;
RangeRadarSensor varRangeRadarSensor_138 = 138;
RangeRadarSensor varRangeRadarSensor_139 = 139;
RangeRadarSensor varRangeRadarSensor_140 = 140;
RangeRadarSensor varRangeRadarSensor_141 = 141;
RangeRadarSensor varRangeRadarSensor_142 = 142;
RangeRadarSensor varRangeRadarSensor_143 = 143;
RangeRadarSensor varRangeRadarSensor_144 = 144;
RangeRadarSensor varRangeRadarSensor_145 = 145;
RangeRadarSensor varRangeRadarSensor_146 = 146;
RangeRadarSensor varRangeRadarSensor_147 = 147;
RangeRadarSensor varRangeRadarSensor_148 = 148;
RangeRadarSensor varRangeRadarSensor_149 = 149;
RangeRadarSensor varRangeRadarSensor_150 = 150;
RangeRadarSensor varRangeRadarSensor_151 = 151;
RangeRadarSensor varRangeRadarSensor_152 = 152;
RangeRadarSensor varRangeRadarSensor_153 = 153;
RangeRadarSensor varRangeRadarSensor_154 = 154;
RangeRadarSensor varRangeRadarSensor_155 = 155;
RangeRadarSensor varRangeRadarSensor_156 = 156;
RangeRadarSensor varRangeRadarSensor_157 = 157;
RangeRadarSensor varRangeRadarSensor_158 = 158;
RangeRadarSensor varRangeRadarSensor_159 = 159;
RangeRadarSensor varRangeRadarSensor_160 = 160;
RangeRadarSensor varRangeRadarSensor_161 = 161;
RangeRadarSensor varRangeRadarSensor_162 = 162;
RangeRadarSensor varRangeRadarSensor_163 = 163;
RangeRadarSensor varRangeRadarSensor_164 = 164;
RangeRadarSensor varRangeRadarSensor_165 = 165;
RangeRadarSensor varRangeRadarSensor_166 = 166;
RangeRadarSensor varRangeRadarSensor_167 = 167;
RangeRadarSensor varRangeRadarSensor_168 = 168;
RangeRadarSensor varRangeRadarSensor_169 = 169;
RangeRadarSensor varRangeRadarSensor_170 = 170;
RangeRadarSensor varRangeRadarSensor_171 = 171;
RangeRadarSensor varRangeRadarSensor_172 = 172;
RangeRadarSensor varRangeRadarSensor_173 = 173;
RangeRadarSensor varRangeRadarSensor_174 = 174;
RangeRadarSensor varRangeRadarSensor_175 = 175;
RangeRadarSensor varRangeRadarSensor_176 = 176;
RangeRadarSensor varRangeRadarSensor_177 = 177;
RangeRadarSensor varRangeRadarSensor_178 = 178;
RangeRadarSensor varRangeRadarSensor_179 = 179;
RangeRadarSensor varRangeRadarSensor_180 = 180;
RangeRadarSensor varRangeRadarSensor_181 = 181;
RangeRadarSensor varRangeRadarSensor_182 = 182;
RangeRadarSensor varRangeRadarSensor_183 = 183;
RangeRadarSensor varRangeRadarSensor_184 = 184;
RangeRadarSensor varRangeRadarSensor_185 = 185;
RangeRadarSensor varRangeRadarSensor_186 = 186;
RangeRadarSensor varRangeRadarSensor_187 = 187;
RangeRadarSensor varRangeRadarSensor_188 = 188;
RangeRadarSensor varRangeRadarSensor_189 = 189;
RangeRadarSensor varRangeRadarSensor_190 = 190;
RangeRadarSensor varRangeRadarSensor_191 = 191;
RangeRadarSensor varRangeRadarSensor_192 = 192;
RangeRadarSensor varRangeRadarSensor_193 = 193;
RangeRadarSensor varRangeRadarSensor_194 = 194;
RangeRadarSensor varRangeRadarSensor_195 = 195;
RangeRadarSensor varRangeRadarSensor_196 = 196;
RangeRadarSensor varRangeRadarSensor_197 = 197;
RangeRadarSensor varRangeRadarSensor_198 = 198;
RangeRadarSensor varRangeRadarSensor_199 = 199;
RangeRadarSensor varRangeRadarSensor_200 = 200;
RangeRadarState varRangeRadarState_READY = READY;
RangeRadarState varRangeRadarState_DIRTY = DIRTY;
RangeRadarState varRangeRadarState_NOTREADY = NOTREADY;
Aceleration varAceleration_ACCP5 = ACCP5;
Aceleration varAceleration_DECM2 = DECM2;
Aceleration varAceleration_NOACC = NOACC;

/* Function and domain initialization */
CarSystem007AdaptiveCruiseC::CarSystem007AdaptiveCruiseC() :
// Static domain initialization 
RangeRadarSensor_elems(set<int> { &varRangeRadarSensor_0,
		&varRangeRadarSensor_1, &varRangeRadarSensor_2, &varRangeRadarSensor_3,
		&varRangeRadarSensor_4, &varRangeRadarSensor_5, &varRangeRadarSensor_6,
		&varRangeRadarSensor_7, &varRangeRadarSensor_8, &varRangeRadarSensor_9,
		&varRangeRadarSensor_10, &varRangeRadarSensor_11,
		&varRangeRadarSensor_12, &varRangeRadarSensor_13,
		&varRangeRadarSensor_14, &varRangeRadarSensor_15,
		&varRangeRadarSensor_16, &varRangeRadarSensor_17,
		&varRangeRadarSensor_18, &varRangeRadarSensor_19,
		&varRangeRadarSensor_20, &varRangeRadarSensor_21,
		&varRangeRadarSensor_22, &varRangeRadarSensor_23,
		&varRangeRadarSensor_24, &varRangeRadarSensor_25,
		&varRangeRadarSensor_26, &varRangeRadarSensor_27,
		&varRangeRadarSensor_28, &varRangeRadarSensor_29,
		&varRangeRadarSensor_30, &varRangeRadarSensor_31,
		&varRangeRadarSensor_32, &varRangeRadarSensor_33,
		&varRangeRadarSensor_34, &varRangeRadarSensor_35,
		&varRangeRadarSensor_36, &varRangeRadarSensor_37,
		&varRangeRadarSensor_38, &varRangeRadarSensor_39,
		&varRangeRadarSensor_40, &varRangeRadarSensor_41,
		&varRangeRadarSensor_42, &varRangeRadarSensor_43,
		&varRangeRadarSensor_44, &varRangeRadarSensor_45,
		&varRangeRadarSensor_46, &varRangeRadarSensor_47,
		&varRangeRadarSensor_48, &varRangeRadarSensor_49,
		&varRangeRadarSensor_50, &varRangeRadarSensor_51,
		&varRangeRadarSensor_52, &varRangeRadarSensor_53,
		&varRangeRadarSensor_54, &varRangeRadarSensor_55,
		&varRangeRadarSensor_56, &varRangeRadarSensor_57,
		&varRangeRadarSensor_58, &varRangeRadarSensor_59,
		&varRangeRadarSensor_60, &varRangeRadarSensor_61,
		&varRangeRadarSensor_62, &varRangeRadarSensor_63,
		&varRangeRadarSensor_64, &varRangeRadarSensor_65,
		&varRangeRadarSensor_66, &varRangeRadarSensor_67,
		&varRangeRadarSensor_68, &varRangeRadarSensor_69,
		&varRangeRadarSensor_70, &varRangeRadarSensor_71,
		&varRangeRadarSensor_72, &varRangeRadarSensor_73,
		&varRangeRadarSensor_74, &varRangeRadarSensor_75,
		&varRangeRadarSensor_76, &varRangeRadarSensor_77,
		&varRangeRadarSensor_78, &varRangeRadarSensor_79,
		&varRangeRadarSensor_80, &varRangeRadarSensor_81,
		&varRangeRadarSensor_82, &varRangeRadarSensor_83,
		&varRangeRadarSensor_84, &varRangeRadarSensor_85,
		&varRangeRadarSensor_86, &varRangeRadarSensor_87,
		&varRangeRadarSensor_88, &varRangeRadarSensor_89,
		&varRangeRadarSensor_90, &varRangeRadarSensor_91,
		&varRangeRadarSensor_92, &varRangeRadarSensor_93,
		&varRangeRadarSensor_94, &varRangeRadarSensor_95,
		&varRangeRadarSensor_96, &varRangeRadarSensor_97,
		&varRangeRadarSensor_98, &varRangeRadarSensor_99,
		&varRangeRadarSensor_100, &varRangeRadarSensor_101,
		&varRangeRadarSensor_102, &varRangeRadarSensor_103,
		&varRangeRadarSensor_104, &varRangeRadarSensor_105,
		&varRangeRadarSensor_106, &varRangeRadarSensor_107,
		&varRangeRadarSensor_108, &varRangeRadarSensor_109,
		&varRangeRadarSensor_110, &varRangeRadarSensor_111,
		&varRangeRadarSensor_112, &varRangeRadarSensor_113,
		&varRangeRadarSensor_114, &varRangeRadarSensor_115,
		&varRangeRadarSensor_116, &varRangeRadarSensor_117,
		&varRangeRadarSensor_118, &varRangeRadarSensor_119,
		&varRangeRadarSensor_120, &varRangeRadarSensor_121,
		&varRangeRadarSensor_122, &varRangeRadarSensor_123,
		&varRangeRadarSensor_124, &varRangeRadarSensor_125,
		&varRangeRadarSensor_126, &varRangeRadarSensor_127,
		&varRangeRadarSensor_128, &varRangeRadarSensor_129,
		&varRangeRadarSensor_130, &varRangeRadarSensor_131,
		&varRangeRadarSensor_132, &varRangeRadarSensor_133,
		&varRangeRadarSensor_134, &varRangeRadarSensor_135,
		&varRangeRadarSensor_136, &varRangeRadarSensor_137,
		&varRangeRadarSensor_138, &varRangeRadarSensor_139,
		&varRangeRadarSensor_140, &varRangeRadarSensor_141,
		&varRangeRadarSensor_142, &varRangeRadarSensor_143,
		&varRangeRadarSensor_144, &varRangeRadarSensor_145,
		&varRangeRadarSensor_146, &varRangeRadarSensor_147,
		&varRangeRadarSensor_148, &varRangeRadarSensor_149,
		&varRangeRadarSensor_150, &varRangeRadarSensor_151,
		&varRangeRadarSensor_152, &varRangeRadarSensor_153,
		&varRangeRadarSensor_154, &varRangeRadarSensor_155,
		&varRangeRadarSensor_156, &varRangeRadarSensor_157,
		&varRangeRadarSensor_158, &varRangeRadarSensor_159,
		&varRangeRadarSensor_160, &varRangeRadarSensor_161,
		&varRangeRadarSensor_162, &varRangeRadarSensor_163,
		&varRangeRadarSensor_164, &varRangeRadarSensor_165,
		&varRangeRadarSensor_166, &varRangeRadarSensor_167,
		&varRangeRadarSensor_168, &varRangeRadarSensor_169,
		&varRangeRadarSensor_170, &varRangeRadarSensor_171,
		&varRangeRadarSensor_172, &varRangeRadarSensor_173,
		&varRangeRadarSensor_174, &varRangeRadarSensor_175,
		&varRangeRadarSensor_176, &varRangeRadarSensor_177,
		&varRangeRadarSensor_178, &varRangeRadarSensor_179,
		&varRangeRadarSensor_180, &varRangeRadarSensor_181,
		&varRangeRadarSensor_182, &varRangeRadarSensor_183,
		&varRangeRadarSensor_184, &varRangeRadarSensor_185,
		&varRangeRadarSensor_186, &varRangeRadarSensor_187,
		&varRangeRadarSensor_188, &varRangeRadarSensor_189,
		&varRangeRadarSensor_190, &varRangeRadarSensor_191,
		&varRangeRadarSensor_192, &varRangeRadarSensor_193,
		&varRangeRadarSensor_194, &varRangeRadarSensor_195,
		&varRangeRadarSensor_196, &varRangeRadarSensor_197,
		&varRangeRadarSensor_198, &varRangeRadarSensor_199,
		&varRangeRadarSensor_200 }), RangeRadarState_elems( {
		&varRangeRadarState_READY, &var_RangeRadarState_DIRTY,
		&var_RangeRadarState_NOTREADY }), Aceleration_elems( {
		&varAceleration_ACCP5, &var_Aceleration_DECM2, &var_Aceleration_NOACC }) {
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
}

/* init static functions and elements of abstract domains */

