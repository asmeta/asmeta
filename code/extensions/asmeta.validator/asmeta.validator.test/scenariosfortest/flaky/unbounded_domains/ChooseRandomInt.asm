// this is with true OK
asm ChooseRandomInt
import ../StandardLibrary
	
signature:
	controlled myInt: Integer
definitions:
	main rule r_Main =
		choose $i in Integer with true do
			myInt := $i
		ifnone
			myInt := undef
			
default init s0:
	function myInt = 0
