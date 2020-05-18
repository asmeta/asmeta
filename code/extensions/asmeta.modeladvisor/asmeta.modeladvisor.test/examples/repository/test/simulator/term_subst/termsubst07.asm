asm termsubst07

import ../../../STDL/StandardLibrary

signature:
	controlled f: Powerset(Integer)

definitions:

macro rule r_bar($a in Integer, $b in Integer, $c in Integer) =
	f := 
		{$x in {1..2}, $d in {3, 4} | $x != $d : $x * $d}

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[$x, $y, $z]

main rule r_main =
	r_foo[0, 0, 0]
