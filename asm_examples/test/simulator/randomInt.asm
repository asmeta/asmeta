asm randomInt
import  ../../STDL/StandardLibrary
	
signature:
	controlled myInt: Integer
definitions:
	main rule r_Main =
		choose $i in Integer with true do
			myInt := $i
default init s0:
	function myInt = 0
