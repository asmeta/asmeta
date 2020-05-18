asm free01

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer

definitions:

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	f := $x

main rule r_main =
	r_foo[0, 0, 0]
