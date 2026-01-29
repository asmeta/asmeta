//
asm SpecWithChoose
import StandardLibrary
	
signature:
	domain SubSet subsetof Integer
	controlled myInt: SubSet

definitions:
    domain SubSet = {0 : 4}

	main rule r_Main =
		choose $i in SubSet do
			if $i = 0 then myInt := $i endif

default init s0:
	function myInt = 0
