asm free03

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer
	controlled g: Prod(Integer, Integer, Integer) -> Integer

definitions:

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	f := if $x = 2 * $y then $z else g(7, 7, $z) endif

main rule r_main =
	r_foo[0, 0, 0]
