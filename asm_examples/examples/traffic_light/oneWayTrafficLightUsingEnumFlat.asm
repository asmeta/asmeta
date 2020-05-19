//for Festschrift for KD Schewe

asm oneWayTrafficLightUsingEnumFlat
//One-Way Traffic Light
//from the paper "The Abstract State Machines Method for High-Level System Design and Analysis" by Egon Boerger

/*
...the traffic is controlled by a pair of simple portable traffic light
units... one unit at each end of the one-way section...connect(ed)...to
a small computer that controls the sequence of lights
Each unit has a Stop light and a Go light.
The computer controls the lights by emitting RPulses and GPulses,
to which the units respond by turning the light on and off.
The regime for the lights repeats a fixed cycle of four phases. First,
for 50 seconds, both units show Stop; then, for 120 seconds, one unit
shows Stop and the other Go; then for 50 seconds both show Stop
again; then for 120 seconds the unit that previously showed Go shows
Stop, and the other shows Go. Then the cycle is repeated.
*/

//ground model

import ../../STDL/StandardLibrary

signature:
	enum domain LightUnit = {LIGHTUNIT1 | LIGHTUNIT2}
	enum domain PhaseDomain = { STOP1STOP2 | GO2STOP1 | STOP2STOP1 | GO1STOP2 }
	enum domain Time = {FIFTY | ONEHUNDREDTWENTY | LESS}
	dynamic controlled phase: PhaseDomain
	dynamic controlled stopLight: LightUnit -> Boolean
	dynamic controlled goLight: LightUnit -> Boolean
	dynamic monitored passed: Time

definitions:

   rule r_stop1stop2_to_go2stop1 =
   		if passed = FIFTY then
			par
				goLight(LIGHTUNIT2) := not(goLight(LIGHTUNIT2))
				stopLight(LIGHTUNIT2) := not(stopLight(LIGHTUNIT2))
				phase := GO2STOP1
			endpar
		endif

	rule r_go2stop1_to_stop2stop1 =
		if passed = ONEHUNDREDTWENTY then
			par
				goLight(LIGHTUNIT2) := not(goLight(LIGHTUNIT2))
				stopLight(LIGHTUNIT2) := not(stopLight(LIGHTUNIT2))
				phase := STOP2STOP1
			endpar
		endif

	rule r_stop2stop1_to_go1stop2 =
		if passed = FIFTY then
			par
				goLight(LIGHTUNIT1) := not(goLight(LIGHTUNIT1))
				stopLight(LIGHTUNIT1) := not(stopLight(LIGHTUNIT1))
				phase := GO1STOP2
			endpar
		endif

	rule r_go1stop2_to_stop1stop2 =
		if passed = ONEHUNDREDTWENTY then
			par
				goLight(LIGHTUNIT1) := not(goLight(LIGHTUNIT1))
				stopLight(LIGHTUNIT1) := not(stopLight(LIGHTUNIT1))
				phase := STOP1STOP2
			endpar
		endif

	main rule r_Main =
		par
			if phase=GO1STOP2 then
				r_go1stop2_to_stop1stop2[]
			endif
			if phase=STOP2STOP1 then
				r_stop2stop1_to_go1stop2[]
			endif
			if phase=GO2STOP1 then
				r_go2stop1_to_stop2stop1[]
			endif
			if phase=STOP1STOP2 then
				r_stop1stop2_to_go2stop1[]
			endif
		endpar

default init s0:
	function stopLight($l in LightUnit) = true
	function goLight($l in LightUnit) = false
	function phase = STOP1STOP2
