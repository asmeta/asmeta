/** at every step increments the seconds 
*/
asm asmH

import StandardLibrary

signature:
	monitored funcS: Integer
	monitored funcC: Integer
	out funcH: Integer

definitions:

	main rule r_Main = 
		funcH := funcS + funcC + 1

default init s0:
	function funcS = 0
	function funcC = 0
