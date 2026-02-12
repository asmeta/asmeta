asm testRanges2

import ../../STDL/StandardLibrary

signature:
	
	domain DiceValue2 subsetof Real
	domain DiceValue3 subsetof Integer
	controlled number: Real 
	controlled numberOfDice : DiceValue2
	
definitions:
	
	domain DiceValue2 = [0.6:6.4, 0.1]
	domain DiceValue3 = [0:6]
	
	// correct
	main rule r_Main = numberOfDice := 5.6

default init s0:

	function number = 5.0
	function numberOfDice = 1.6
