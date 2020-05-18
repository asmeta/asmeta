asm termsubst04

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer

definitions:

macro rule r_bar($x in Integer, $y in Integer, $z in Integer) =
	f := switch $y case $x: 0 case $z: 1 otherwise 2 endswitch

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[$z, $y, $x]

main rule r_main =
	r_foo[0, 0, 0]
