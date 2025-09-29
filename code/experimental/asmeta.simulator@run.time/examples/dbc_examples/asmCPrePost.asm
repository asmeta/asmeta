/** at every step increments the seconds 
*/
asm asmCPrePost

import ../StandardLibrary

signature:
	monitored funcS: Integer
	monitored funcH: Integer
	monitored myinput: Integer //unbound input
	out funcC: Integer

definitions:

invariant inv_0 over funcS: funcS>=0
invariant inv_1 over funcC: funcC>=0

	main rule r_Main = 
		funcC := (funcS + funcH + myinput) *2


default init s0:
	function funcC = 0
	
	//asmH.asm <|> (asmC.asm <|> asmS.asm)
