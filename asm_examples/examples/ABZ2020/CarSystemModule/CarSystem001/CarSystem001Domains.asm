//Ground model of Adaptive Exterior Light and Speed Control System
//Direction Blinking
//Hazard warning light
//from ELS-1 to ELS-13

module CarSystem001Domains
import ../../StandardLibrary
import ../CarSystem000/CarSystem000CommonDomains
export *
//export MarketCode, PitmanArmUpDown, LightPercentage,PulseRatio,TailLampStatus

signature:

	// DOMAINS
	enum domain MarketCode = {USA | CANADA | EU} // Name of the market for which the car is to be built
	enum domain PitmanArmUpDown = {DOWNWARD5 | DOWNWARD5_LONG| UPWARD5 | UPWARD5_LONG | DOWNWARD7 | UPWARD7 | NEUTRAL_UD} // Pitman arm positions - vertical position
	// DOWNWARD5 -> move pitman arm for less than 0.5 seconds DOWNWARD5_LONG -> move pitman arm for more than 0.5 seconds 
	//enum domain KeyPosition = {NOKEYINSERTED | KEYINSERTED | KEYINIGNITIONONPOSITION} // Key state
	enum domain PulseRatio = {NOPULSE | PULSE11 | PULSE12} //* Pulse ratio of direction lights
	enum domain TailLampStatus = {FIX | BLINK} //* Tail lamp status, blink if it is USA or CANADA car and turns left/right, is fixed otherwise
	
	domain LightPercentage subsetof Integer // Light percentage
	// FUNCTIONS
	
definitions:
	// DOMAIN DEFINITIONS
	domain LightPercentage = {0 : 100}
	// {0, 50, 100} //0 :100 -> SEMPLIFICATO CON I SOLI VALORI UTILIZZATI NELLA SPECIFICA

