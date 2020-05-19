asm testRanges

import ../STDL/StandardLibrary

signature:
	
	domain DiceValue2 subsetof Real
	domain DiceValue3 subsetof Integer
	controlled number: Real 
	controlled numberOfDice : DiceValue2
	
definitions:
	
	domain DiceValue2 = {0.6:6.4} 
	domain DiceValue3 = {0:6}
	
	main rule r_Main = skip

default init s0:

	function number = 5.0
	function numberOfDice = 5.2