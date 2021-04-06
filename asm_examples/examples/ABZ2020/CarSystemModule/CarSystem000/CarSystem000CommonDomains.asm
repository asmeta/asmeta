module CarSystem000CommonDomains
import ../../StandardLibrary
export *
//export KeyPosition, CruiseControlMode, CurrentSpeed

signature:
	enum domain KeyPosition = {NOKEYINSERTED | KEYINSERTED | KEYINIGNITIONONPOSITION} // Key state
	enum domain CruiseControlMode = {CCM0 | CCM1 | CCM2}
	//CCM0: cruise control disabled
	//CCM1: cruise control active
	//CCM2: adaptive cruise control active
	domain CurrentSpeed subsetof Integer // Speed

definitions:
	domain CurrentSpeed = {0 : 2000}

