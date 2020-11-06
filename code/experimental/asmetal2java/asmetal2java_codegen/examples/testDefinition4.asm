// a simple example with a tic tac toe game

asm testDefinition4

import STDL/StandardLibrary

signature:

	domain WaterpressureType subsetof Integer
	domain Delta subsetof Integer
	enum domain Switch = {ON | OFF}
	enum domain Pressure = { TOOLOW |NORMAL |HIGH }
	enum domain PhaseDomain = { FULLYCLOSED | FULLYOPEN }


	domain VectDomain subsetof Integer
	controlled vect: Integer -> Integer
	controlled vect2: VectDomain -> Integer

	controlled waterpressure: WaterpressureType
	controlled pressure : Pressure
	controlled overridden : Boolean
	controlled safetyInjection : Switch
	dynamic controlled phase: PhaseDomain

	monitored reset : Switch
	monitored block : Switch
	monitored delta : Delta

	static low : WaterpressureType
	static permit : WaterpressureType
	static maxwp : WaterpressureType
	static minwp : WaterpressureType

	static n: Integer

definitions:

	domain WaterpressureType = {0 ..100}
	domain Delta = {-5 .. +5}

	domain VectDomain={0..9}


	function low = 9
	function permit = 10
	function maxwp = 20
	function minwp = 0

	function n = 10

	// modify the water pressure: if it is < then 0 take 0
	// it it is > max, take max

	main rule r_Main =
		skip

default init s0:

  function waterpressure = 3
  function pressure = TOOLOW
  function reset = OFF
  function block = OFF
  function overridden = false
  function safetyInjection = OFF

  	function phase = FULLYCLOSED

  		function vect($x in Integer) =
		switch $x
			case 0: 5
			case 1: 7
			case 2: 1
			case 3: 8
			case 4: 3
			case 5: 2
			case 6: 6
			case 7: 4
			case 8: 8
			case 9: 9
		endswitch
	function vect2($x in VectDomain) = //$x in Integer is not working when translating into c++
		switch $x
			case 0: 5
			case 1: 7
			case 2: 1
			case 3: 8
			case 4: 3
			case 5: 2
			case 6: 6
			case 7: 4
			case 8: 8
			case 9: 9
		endswitch


