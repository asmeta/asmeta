asm free05

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer

definitions:

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	f := let ($v1 = $x, $v2 = $v1 + 1, $v3 = 0) in $v3 * $y endlet

main rule r_main =
	r_foo[0, 0, 0]
