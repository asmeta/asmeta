// this is with true OK NO CONDITION
asm ChooseNoCond
import ../../STDL/StandardLibrary
	
signature:
	controlled myInt: Integer
definitions:
	main rule r_Main =
		choose $i in Integer do
			myInt := $i
default init s0:
	function myInt = 0
