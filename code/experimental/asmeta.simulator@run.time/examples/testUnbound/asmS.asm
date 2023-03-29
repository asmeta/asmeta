/** at every step increments the seconds 
*/
asm asmS

import StandardLibrary

signature:
	monitored funcC: Integer
	monitored funcH: Integer
	out funcS: Integer

definitions:

	main rule r_Main = 
		funcS := (funcC + funcH) + 2

default init s0:
	function funcC = 0
	function funcH = 0
