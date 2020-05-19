asm rulesubst07

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer
	controlled g: Integer -> Integer

definitions:

macro rule r_bar($a in Integer, $b in Integer, $c in Integer) =
	let ($x = $a, $l = 77) in
		par
			$b := $x + $a
			skip
		endpar
	endlet

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[$x, g(0), $z]

main rule r_main =
	r_foo[0, 0, 0]
