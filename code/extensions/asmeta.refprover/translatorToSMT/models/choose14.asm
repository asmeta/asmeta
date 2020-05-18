asm choose14

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB}
	dynamic controlled fooA: Boolean
	dynamic controlled fooB: Boolean

definitions:

	main rule r_Main =
		choose $x in EnumDom with true do
			if $x = AA then
				fooA := true
			else
				fooB := true
			endif

default init s0:
	function fooA = false
	function fooB = false