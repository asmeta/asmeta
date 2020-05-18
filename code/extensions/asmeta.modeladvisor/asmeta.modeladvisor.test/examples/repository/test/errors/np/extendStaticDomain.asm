// domain A is not dynamic and is extended

asm extendStaticDomain

import ../../../STDL/StandardLibrary

signature:

 abstract domain A

definitions:

main rule r_main =
	extend A with $x do skip
		