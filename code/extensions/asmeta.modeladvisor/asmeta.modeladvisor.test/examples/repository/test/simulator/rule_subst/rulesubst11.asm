asm rulesubst11

import ../../../STDL/StandardLibrary

signature:
	dynamic abstract domain A

	controlled f: Integer
	controlled g: Integer -> Integer

definitions:

macro rule r_bar($a in Integer, $b in Integer, $c in Integer) =
	extend A with $l do extend A with $z do skip

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[$x, $y, $z]

main rule r_main =
	r_foo[0, 0, 0]
