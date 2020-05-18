asm free09

import ../../../STDL/StandardLibrary

signature:
	controlled f: Seq(Integer)

definitions:

macro rule r_foo($x in Integer, $y in Integer, $z in Integer) =
	f := 
		let ($v1 = 0) in  
			[ $v2 in [] | (forall $v3 in Integer with $z = 0) : $v2]
		endlet

main rule r_main =
	r_foo[0, 0, 0]
