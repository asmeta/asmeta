asm rulesubst04

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer
	controlled g: Integer -> Integer

definitions:

macro rule r_bar($a in Integer, $b in Integer, $c in Integer) =
	seq
		$a := $b + $c
		$b := $b
	endseq

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[g($x), f, $z]

main rule r_main =
	r_foo[0, 0, 0]
