asm rulesubst10

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer
	controlled g: Integer -> Integer

definitions:

macro rule r_wer($l in Integer, $m in Integer) =
	skip

macro rule r_bar($a in Integer, $b in Integer, $c in Integer) =
	r_wer[$a, $b]

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[$x, $y, $z]

main rule r_main =
	r_foo[0, 0, 0]
