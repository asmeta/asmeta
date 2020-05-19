asm rulesubst12

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer
	controlled g: Integer -> Integer
	static run: Integer -> Rule(Integer)
	
definitions:

macro rule r_bar($a in Integer, $b in Integer, $c in Integer) =
	let ($x = 123) in
		run($x)[$a]
	endlet

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[$x, g(0), $z]

main rule r_main =
	r_foo[0, 0, 0]
