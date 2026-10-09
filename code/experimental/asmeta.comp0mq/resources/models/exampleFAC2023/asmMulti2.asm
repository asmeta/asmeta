/** at every step multiply the sum of functions by 2
*/
asm asmMulti2

import StandardLibrary

signature:
	monitored funcInc: Integer
	monitored funcDec: Integer
	monitored myinput: Integer //unbound input
	out funcMulti: Integer

definitions:

	main rule r_Main = 
		funcMulti := (funcInc + funcDec + myinput) *2


default init s0:
	function funcInc = 0
	function funcDec = 0
	
	//asmDec.asm <|> (asmMulti.asm <|> asmInc.asm)
