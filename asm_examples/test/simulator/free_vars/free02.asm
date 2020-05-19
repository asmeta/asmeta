asm free02

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer
	controlled g: Prod(Integer, Integer, Integer) -> Integer

definitions:

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	f := g($x, $y, 7)

main rule r_main =
	r_foo[0, 0, 0]
