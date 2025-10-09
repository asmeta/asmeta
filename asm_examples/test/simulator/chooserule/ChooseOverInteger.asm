// integer with a condition
asm ChooseOverInteger
import  ../../../STDL/StandardLibrary
	
signature:
	controlled myInt: Integer
definitions:
	main rule r_Main =
		choose $i in Integer with $i > 0 do
			myInt := $i
default init s0:
	function myInt = 0
