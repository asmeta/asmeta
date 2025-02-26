asm derBodyErr

import ../../../STDL/StandardLibrary

signature:
	static fooA: Integer -> Boolean
	static fooB: Integer -> Boolean

definitions:
	function fooA($a in Integer) =
		true//$a=$a

	function fooB($b in Integer) =
		true//$b=$b

	main rule r_mainRule =
		if fooA(2) then
			skip
		endif

default init s0:
