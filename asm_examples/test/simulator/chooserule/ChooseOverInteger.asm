// integer with a condition -> errore
asm ChooseOverInteger
import  ../../../STDL/StandardLibrary
	
signature:
	controlled myi: Integer
definitions:
	main rule r_Main =
		choose $i in Integer with $i > 10 do
			myi := $i
default init s0:
	function myi = 0
