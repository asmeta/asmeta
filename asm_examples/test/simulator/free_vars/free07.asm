asm free07

import ../../../STDL/StandardLibrary

signature:
	controlled f: Powerset(Integer)

definitions:

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	f := { $v1 in Integer | $v1 = $z : $y * $v1}

main rule r_main =
	r_foo[0, 0, 0]
