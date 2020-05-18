//let with a logicalVar is updated to a locationVar
asm logVarUpdate

import ../../../STDL/StandardLibrary

signature:

definitions:

main rule r_main =
	let ($x = 0) in
		$x := 1
	endlet
