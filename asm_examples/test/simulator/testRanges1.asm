asm testRanges1

import ../../STDL/StandardLibrary

signature:
	
	domain DiceValue2 subsetof Real
	domain DiceValue3 subsetof Integer

	controlled number: Real 
	controlled numberOfDice : DiceValue2
	
definitions:
	// implicit step 1
	ma uso {} o uso [] ????

	domain DiceValue2 = {0.6:6.4}
	///	domain DiceValue2 = {$c in Real | $c > 0.6 and $c< 6.4 : $c}
	domain DiceValue3 = {0:6}

	domain DiceValue2 = [0.6:6.4]
	domain DiceValue3 = [0:6]
	
	// correct
	main rule r_Main = numberOfDice := 5.6

default init s0:

	function number = 5.0
	function numberOfDice = 1.6
