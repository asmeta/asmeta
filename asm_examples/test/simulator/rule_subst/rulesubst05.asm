asm rulesubst05

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer
	controlled g: Integer -> Integer

definitions:

macro rule r_bar($a in Boolean, $b in Integer, $c in Integer) =
	if $a then $b := $c else skip endif

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[0 = 0, f, $z]

main rule r_main =
	r_foo[0, 0, 0]
