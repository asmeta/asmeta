asm chooseBoolean

import  ../../../STDL/StandardLibrary

signature:
	dynamic controlled fooA: Natural
	dynamic controlled fooB: Natural

definitions:

	main rule r_main =
		par
			choose $a in Boolean with true do
				if($a) then
					fooA := fooA + 1n
				endif
			choose $b in Boolean with true do
				if(not($b)) then
					fooB := fooB + 1n
				endif
		endpar

default init s0:
	function fooA = 0n
	function fooB = 0n
