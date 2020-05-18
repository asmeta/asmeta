asm free08

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer

definitions:

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	f := let ($v1 = 0) in let ($v2 = $x) in $x endlet endlet

main rule r_main =
	r_foo[0, 0, 0]
