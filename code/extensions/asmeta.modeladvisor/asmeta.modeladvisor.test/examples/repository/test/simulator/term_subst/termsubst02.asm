asm termsubst02

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer
	controlled g: Prod(Integer, Integer) -> Integer

definitions:

macro rule r_bar($x in Integer, $y in Integer, $z in Integer) =
	f := g($x, $y)

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[$x + $y, 0, 0]

main rule r_main =
	r_foo[0, 0, 0]
