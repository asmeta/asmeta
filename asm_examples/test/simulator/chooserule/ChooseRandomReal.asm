// this is with true OK
asm ChooseRandomReal
import  ../../../STDL/StandardLibrary
	
signature:
	controlled myR: Real
definitions:
	main rule r_Main =
		choose $i in Real with true do
			myR := $i
default init s0:
	function myR = 0.0
