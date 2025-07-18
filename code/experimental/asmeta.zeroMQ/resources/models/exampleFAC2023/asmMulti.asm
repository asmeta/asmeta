/** at every step multiply the sum of functions by 2
*/
asm asmMulti

import StandardLibrary

signature:
	monitored myinput: Integer //unbound input
	out funcMulti: Integer

definitions:

	main rule r_Main = 
		funcMulti := myinput *2


default init s0:
	
	// asmMulti.asm | (asmInc.asm <|> asmDec.asm)
