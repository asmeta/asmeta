asm division
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	dynamic monitored mon: Integer
	dynamic controlled fooA: Real

definitions:
	CTLSPEC ag(fooA<5)

	main rule r_main =
	  	fooA := mon/3

default init s0:
	function fooA = 2.9
