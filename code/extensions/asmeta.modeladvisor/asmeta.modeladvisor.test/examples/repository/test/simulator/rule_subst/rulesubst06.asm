asm rulesubst06

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer
	controlled g: Integer -> Integer

definitions:

macro rule r_bar($a in Integer, $b in Integer, $c in Integer) =
	switch $a
		case $b: skip
		otherwise skip
	endswitch

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[$x, f, $z]

main rule r_main =
	r_foo[0, 0, 0]
