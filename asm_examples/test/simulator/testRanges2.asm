asm testRanges2

import ../../STDL/StandardLibrary

signature:
	
	domain DiceValue2 subsetof Real
	domain DiceValue3 subsetof Integer
	domain DiceValue4 subsetof Integer
	domain DiceValue5 subsetof Real
	controlled number: Real 
	controlled numberOfDice : DiceValue2
	
definitions:
	// step with 0.1
	domain DiceValue2 = {0.6: 0.9, 0.1}
	domain DiceValue3 = {0:6, 2}
	domain DiceValue4 = {0:7, 2}
	domain DiceValue5 = {0.6: 0.6, 0.1}
	
	// correct
	main rule r_Main = numberOfDice := 0.9

default init s0:

	function number = 5.0
	function numberOfDice = 1.6 // never used, it could be an error
