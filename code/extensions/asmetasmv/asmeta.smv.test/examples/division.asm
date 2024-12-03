asm division
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	dynamic monitored mon: Integer
	dynamic controlled fooA: Integer

definitions:
	//CTLSPEC ag(fooA<5)

	main rule r_main =
	  	if (mon/3 > (10.0 + 5.0)) then fooA := 3 endif

default init s0:
	function fooA = 2
