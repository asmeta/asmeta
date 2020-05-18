asm termsubst03

import ../../../STDL/StandardLibrary

signature:
	controlled f: Integer

definitions:

macro rule r_bar($x in Integer, $y in Integer, $z in Integer) =
	f := if $x = 7 then 2 * $x else $y endif

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	r_bar[$z, 77, 0]

main rule r_main =
	r_foo[0, 0, 0]
