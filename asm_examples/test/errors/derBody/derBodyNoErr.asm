asm derBodyNoErr

import ../../../STDL/StandardLibrary

signature:
	derived fooA: Integer -> Boolean
	derived fooB: Integer -> Boolean

definitions:
	function fooA($a in Integer) =
		$a=$a

	function fooB($b in Integer) =
		true//$b=$b

	main rule r_mainRule =
		if fooA(2) then
			skip
		endif

default init s0:
