// derivata and function
asm SensorVotingFuncWrong

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
    enum domain Channel = {ONE | TWO | THREE}

	dynamic controlled sensor: Boolean
	dynamic controlled validCh: Channel -> Boolean
	derived valid: Boolean

	dynamic monitored sensorMon:  Channel -> Boolean

definitions:
	function valid = (exist $c1 in Channel, $c2 in Channel with $c1!=$c2
	           and validCh($c1) and  validCh($c2))

    // check $vc1 equal $vc2 and then $vc3
	rule r_threeValidSensors($vc1 in Channel, $vc2 in Channel, $vc3 in Channel) =
			if sensorMon($vc1) = sensorMon($vc2) then
				par
					sensor := sensorMon($vc1)
					if sensorMon($vc1) != sensorMon($vc3) then
						validCh($vc3) := false
					endif
				endpar
			endif

	rule r_allValidSensors =
		if (forall $c in Channel with validCh($c)) then
			par
				r_threeValidSensors[ONE,TWO,THREE] // check 3
				r_threeValidSensors[TWO,THREE,ONE]
				r_threeValidSensors[THREE,ONE,TWO]
			endpar
		endif

	rule r_twoValidSensors($vc1 in Channel, $vc2 in Channel, $vc3 in Channel) =
		if not(validCh($vc1)) then
			if sensorMon($vc2) = sensorMon($vc3) then
				sensor := sensorMon($vc2)
			else
				par
					validCh($vc1) := false
					validCh($vc2) := false
				endpar
			endif
		endif

	rule r_sensors =
		par
			r_allValidSensors[]
			r_twoValidSensors[ONE, TWO, THREE]
			r_twoValidSensors[TWO, THREE, ONE]
			r_twoValidSensors[THREE, ONE, TWO]
		endpar

	invariant over validCh: (not(validCh(ONE)) and not(validCh(TWO)) and not(validCh(THREE))) or (validCh(ONE) and validCh(TWO) and validCh(THREE)) or (validCh(ONE) and validCh(TWO) and not(validCh(THREE))) or (validCh(ONE) and not(validCh(TWO)) and validCh(THREE)) or (not(validCh(ONE)) and validCh(TWO) and validCh(THREE))
	//in modo equivalente
	invariant over validCh: not((validCh(ONE) and not(validCh(TWO)) and not(validCh(THREE))) or (not(validCh(ONE)) and validCh(TWO) and not(validCh(THREE))) or (not(validCh(ONE)) and not(validCh(TWO)) and validCh(THREE)))
	//in modo equivalente
	invariant over validCh: not(exist $c1 in Channel, $c2 in Channel, $c3 in Channel with $c1 != $c2 and $c1 != $c3 and $c2 != $c3 and validCh($c1) and not(validCh($c2)) and not(validCh($c3)))
	//in modo equivalente
	//invariant over validCh: size({$c in Channel | validCh($c) : $c}) !=1

	CTLSPEC ag(not(valid) implies ag(not(valid)))
	CTLSPEC ef(not(valid))
	CTLSPEC eg(valid)

	//- If at t the three channels are considered as valid and are equal, then the
	//value considered by the control software is this common value.
	//- If at t one channel is different from the two others for the first time (i.e.,
	//the three channels were considered as valid up to t), then this channel is
	//considered as invalid and is definitely eliminated. Only the two remaining
	//channels are considered in the future. At time t, the value considered by the
	//control software is the common value of the two remaining channels.
	//- If a channel has been eliminated previously, and if at t the two remaining
	//channels are not equal, then the sensor is definitely considered as invalid.
	main rule r_Main =
		if valid then
			r_sensors[]
		endif

default init s0:
	function validCh($c in Channel) = true