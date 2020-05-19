asm argsEnumDom

import ../../STDL/StandardLibrary

signature:
	enum domain Coord = {AA |BB}
	dynamic controlled board: Prod(Coord, Coord) -> Boolean

definitions:

	main rule r_Main =
		forall $x in Coord with true do
			board($x, $x) := false

default init s0:
	function board($r in Coord, $c in Coord) = true
