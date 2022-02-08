/** at every step increments the seconds 
*/
asm asmC

import StandardLibrary

signature:
	monitored funcS: Integer
	monitored funcH: Integer
	monitored myinput: Integer //unbound input
	out funcC: Integer

definitions:

	main rule r_Main = 
		funcC := (funcS + funcH + myinput) *2


default init s0:
	function funcS = 0
	function funcH = 0
	
	//asmH <|> (asmC | asmS)
