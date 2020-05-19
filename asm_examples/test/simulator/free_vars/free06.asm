asm free06

import ../../../STDL/StandardLibrary

signature:
	controlled f: Boolean

definitions:

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	f := (exist $v1 in Natural, $v2 in {1, 2} with $v1 = $v2)

main rule r_main =
	r_foo[0, 0, 0]
