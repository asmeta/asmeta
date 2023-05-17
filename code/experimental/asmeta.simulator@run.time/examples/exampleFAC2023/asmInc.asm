/** at every step increment the sum of monitored function by 2 and return the maximum among funcDec and funcMulti, if functions are equals assign 0
*/
asm asmInc

import StandardLibrary

signature:
	monitored funcMulti: Integer
	monitored funcDec: Integer
	controlled maxFunction: Integer
	out funcInc: Integer

definitions:

	macro rule r_max = 
		par
			if (funcMulti > funcDec) then maxFunction := funcMulti endif
			if (funcDec > funcMulti) then maxFunction := funcDec endif
			if (funcDec = funcMulti) then maxFunction := 0 endif
		endpar
		
	
	main rule r_Main = 
		par
			funcInc := (funcMulti + funcDec) + 2
			r_max[]
		endpar

default init s0:
	function funcMulti = 0
	function funcDec = 0
	function maxFunction = 0
