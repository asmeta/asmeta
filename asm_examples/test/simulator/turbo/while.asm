asm while
import ../../../STDL/StandardLibrary
	
signature:
	controlled x: Integer
		
definitions:

	invariant over x: x = 0 or x = 10

	main rule r_main =
		while x < 10 do
			x := x + 1

default init s0:
	function x = 0