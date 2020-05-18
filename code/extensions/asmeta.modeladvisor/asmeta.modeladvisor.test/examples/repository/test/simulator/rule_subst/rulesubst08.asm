asm rulesubst08

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer
	controlled g: Integer -> Integer

definitions:

macro rule r_bar($a in Integer, $b in Integer, $c in Integer) =
	forall $z in Integer with true do
		f := $a

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[$y, g(0), $z]

main rule r_main =
	r_foo[0, 0, 0]
