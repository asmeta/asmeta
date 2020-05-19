asm argsConcrDom

import ../../STDL/StandardLibrary

signature:
	domain Coord subsetof Integer
	dynamic controlled board: Prod(Coord, Coord) -> Boolean

definitions:
	domain Coord = {1 : 3}

	main rule r_Main =
		forall $x in Coord with true do
			board($x, $x) := false

default init s0:
	function board($r in Coord, $c in Coord) = true
