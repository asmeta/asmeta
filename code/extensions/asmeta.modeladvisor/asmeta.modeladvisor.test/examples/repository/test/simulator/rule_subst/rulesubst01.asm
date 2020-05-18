asm rulesubst01

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer

definitions:

macro rule r_bar($a in Integer, $b in Integer, $c in Integer) =
	$a := $b

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[f, $y, $z]

main rule r_main =
	r_foo[0, 0, 0]
