// No guard -> equivalent to "with true"
asm ChooseRandomReal
import ../StandardLibrary
	
signature:
	controlled myR: Real
definitions:
	main rule r_Main =
		choose $i in Real do
			myR := $i
		ifnone
			myR := undef	
			
default init s0:
	function myR = 0.0
