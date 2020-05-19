// SIS.ino automatically generated from UASM2CODE
//after generation this code was manually modified (by mcarissoni) to run on arduino

int blockButton = 2;
int resetButton = 3;
int sisButton = 13;
/////////////////////////////////////////////////
/// DOMAIN DEFINITIONS
/////////////////////////////////////////////////
/* Domain definitions here */
typedef int WaterpressureType;

typedef int Delta;

enum Switch {
	ON, OFF
};
enum Pressure {
	P_TOOLOW, P_NORMAL, P_HIGH
};

class SIS {
public:
/////////////////////////////////////////////////
/// FUNCTIONS
/////////////////////////////////////////////////
	WaterpressureType waterpressure[2];
	Pressure pressure[2];
	bool overridden[2];
	Switch safetyInjection[2];
	Switch reset;
	Switch block;
	Delta delta;
	WaterpressureType const low = 9;
	WaterpressureType const permit = 10;
	WaterpressureType const maxwp = 20;
	WaterpressureType const minwp = 0;
////////////////////////////////////////////////
/// RULE DEFINITION
/////////////////////////////////////////////////
	/* Rule definition here */

	void r_wp() {
		if (waterpressure[0] + delta < minwp) {
			waterpressure[1] = minwp;
		} else {
			if (waterpressure[0] + delta > maxwp) {
				waterpressure[1] = maxwp;
			} else {
				waterpressure[1] = waterpressure[0] + delta;
			}
		}
	}
	void r_1() {
		if (waterpressure[0] >= low && pressure[0] == P_TOOLOW) {
			pressure[1] = P_NORMAL;
		}
	}
	void r_2() {
		if (waterpressure[0] >= permit && pressure[0] == P_NORMAL) {
			{ //par
				pressure[1] = P_HIGH;
				overridden[1] = false;
			} //endpar
		}
	}
	void r_3() {
		if (waterpressure[0] < low && pressure[0] == P_NORMAL) {
			pressure[1] = P_TOOLOW;
		}
	}
	void r_4() {
		if (waterpressure[0] < permit && pressure[0] == P_HIGH) {
			{ //par
				pressure[1] = P_NORMAL;
				overridden[1] = false;
			} //endpar
		}
	}
	void r_5() {
		if (reset == ON && pressure[0] == P_TOOLOW || pressure[0] == P_NORMAL) {
			overridden[1] = false;
		}
	}
	void r_6() {
		if (block == ON && reset == OFF && pressure[0] == P_TOOLOW) {
			overridden[1] = true;
		}
	}
	void r_7() {
		if (pressure[0] == P_TOOLOW) {
			if (overridden[0]) {
				safetyInjection[1] = OFF;
			} else {
				safetyInjection[1] = ON;
			}
		}
	}
	void r_8() {
		if (pressure[0] != P_TOOLOW) {
			safetyInjection[1] = OFF;
		}
	}
	void r_Main() {
		{ //par
			r_wp();
			r_1();
			r_2();
			r_3();
			r_4();
			r_5();
			r_6();
			r_7();
			r_8();
		} //endpar
	}

	SIS() {
		waterpressure[0] = waterpressure[1] = 3;
		pressure[0] = pressure[1] = P_TOOLOW;
		overridden[0] = overridden[1] = false;
		safetyInjection[0] = safetyInjection[1] = OFF;
	}
	void getInput() {
		if (digitalRead(blockButton) != 0)
			block = ON;
		else
			block = OFF;

		if (digitalRead(resetButton) != 0)
			reset = ON;
		else
			reset = OFF;

		int deltaAnalog = analogRead(A0);
		delta = (deltaAnalog / 33) - 15;
	}
	void setOutput() {
		digitalWrite(sisButton, safetyInjection[0] == ON ? HIGH : LOW);
	}
	void updateSet() {
		waterpressure[0] = waterpressure[1];
		pressure[0] = pressure[1];
		overridden[0] = overridden[1];
		safetyInjection[0] = safetyInjection[1];
	}
};

//INSTANCE OF THE ASM
SIS sIS;

void setup() {
	/* HW initial setup here */

	pinMode(blockButton, INPUT);
	pinMode(resetButton, INPUT);
	pinMode(sisButton, OUTPUT);

}
void loop() {
	sIS.getInput();
	sIS.r_Main();
	sIS.updateSet();
	sIS.setOutput();
}

