asm termsubst08

import ../../../STDL/StandardLibrary

signature:
	controlled f: Powerset(Integer)

definitions:

macro rule r_bar($a in Integer, $b in Integer, $c in Integer) =
	f := 
		let ($x = $b, $y = $a) in
		   {$z in Integer | $x = $z iff $a = $c : $z}
		endlet

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[$x + $y, $y, $z]

main rule r_main =
	r_foo[0, 0, 0]
