asm termsubst05

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer

definitions:

macro rule r_bar($a in Integer, $b in Integer, $c in Integer) =
	f := 
		let ($x = $a, $y = $b, $z = $c) in
			$x + $c
		endlet

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[$x, $y, $z]

main rule r_main =
	r_foo[0, 0, 0]
