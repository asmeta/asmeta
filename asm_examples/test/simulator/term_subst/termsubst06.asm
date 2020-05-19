asm termsubst06

import ../../../STDL/StandardLibrary

signature:
	controlled f: Boolean

definitions:

macro rule r_bar($a in Integer, $b in Integer, $c in Integer) =
	f := 
		(exist $x in Natural with $x = $a)

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[$y + $z, $y, $z]

main rule r_main =
	r_foo[0, 0, 0]
