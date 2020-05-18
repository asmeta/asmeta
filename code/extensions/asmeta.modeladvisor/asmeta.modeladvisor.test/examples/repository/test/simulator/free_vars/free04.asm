asm free04

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer
	controlled g: Prod(Integer, Integer, Integer) -> Integer

definitions:

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	f := switch $z case $z: 0 otherwise 1 endswitch

main rule r_main =
	r_foo[0, 0, 0]
