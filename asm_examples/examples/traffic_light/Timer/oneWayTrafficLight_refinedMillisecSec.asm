asm oneWayTrafficLight_refinedMillisecSec

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

import ../../../STDL/CTLlibrary
import ../../../STDL/TimeLibrary

signature:
	enum domain LightUnit = {LIGHTUNIT1 | LIGHTUNIT2}
	enum domain PhaseDomain = { STOP1STOP2 | GO2STOP1 | STOP2STOP1 | GO1STOP2}
	dynamic controlled phase: PhaseDomain
	dynamic controlled stopLight: LightUnit -> Boolean
	dynamic controlled goLight: LightUnit -> Boolean
	dynamic controlled rPulse: LightUnit -> Boolean
	dynamic controlled gPulse: LightUnit -> Boolean
	
	static timer50Passed: Timer
	static timer120Passed: Timer
	
definitions:

	rule r_switch($l in Boolean) =
		$l := not($l)

	rule r_switchLightUnit($l in LightUnit) =
		par
		    rPulse($l) := true
		    gPulse($l) := true
		endpar

	rule r_stop1stop2_to_go2stop1 =
		if(phase=STOP1STOP2) then
			if expired(timer50Passed) then
				par
					r_switchLightUnit[LIGHTUNIT2]
					phase:=GO2STOP1
					r_reset_timer[timer120Passed]
				endpar
			endif
		endif

	rule r_go2stop1_to_stop2stop1 =
		if(phase=GO2STOP1) then
			if expired(timer120Passed) then
				par
					r_switchLightUnit[LIGHTUNIT2]
					phase:=STOP2STOP1
					r_reset_timer[timer50Passed]
				endpar
			endif
		endif

	rule r_stop2stop1_to_go1stop2 =
		if(phase=STOP2STOP1) then
			if expired(timer50Passed) then
				par
					r_switchLightUnit[LIGHTUNIT1]
					phase:=GO1STOP2
					r_reset_timer[timer120Passed]
				endpar
			endif
		endif

	rule r_go1stop2_to_stop1stop2 =
		if(phase=GO1STOP2) then
			if expired(timer120Passed) then
				par
					r_switchLightUnit[LIGHTUNIT1]
					phase:=STOP1STOP2
					r_reset_timer[timer50Passed]
				endpar
			endif
		endif

	rule r_pulses =
		forall $l in LightUnit with true do
			par
				if(gPulse($l)) then
					par
						r_switch[goLight($l)]
						gPulse($l) := false
					endpar
				endif
				if(rPulse($l)) then
					par
						r_switch[stopLight($l)]
						rPulse($l) := false
					endpar
				endif
			endpar

	//i segnali rPulse e gPulse sono sempre letti ed azzerati in un solo passo
	//the rPulse e gPulse signals are always read and resetted in a single step
	CTLSPEC ag(rPulse(LIGHTUNIT1) implies ax(not(rPulse(LIGHTUNIT1))))
	CTLSPEC ag(rPulse(LIGHTUNIT2) implies ax(not(rPulse(LIGHTUNIT2))))
	CTLSPEC ag(gPulse(LIGHTUNIT1) implies ax(not(gPulse(LIGHTUNIT1))))
	CTLSPEC ag(gPulse(LIGHTUNIT2) implies ax(not(gPulse(LIGHTUNIT2))))

	main rule r_Main =
		par
			r_stop1stop2_to_go2stop1[]
			r_go2stop1_to_stop2stop1[]
			r_stop2stop1_to_go1stop2[]
			r_go1stop2_to_stop1stop2[]
			r_pulses[]
		endpar
				

default init s0:
	function stopLight($l in LightUnit) = true
	function goLight($l in LightUnit) = false
	function phase = STOP1STOP2
	function rPulse($l in LightUnit) = false
	function gPulse($l in LightUnit) = false
	
	function duration($t in Timer) = if $t = timer50Passed 	then 50000 //50000
    									else 
    										if $t = timer120Passed	then 120 endif //120000
   									endif
   									
	function start($t in Timer) = currentTime($t)
	
	function timerUnit($t in Timer) = if $t = timer50Passed 	then MILLISEC //50
    									else 
    										if $t = timer120Passed	then SEC endif //120
   									endif