asm letRule3

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic monitored foo: Boolean
	dynamic monitored fooA: MyDomain
	dynamic controlled fooB: Boolean
	dynamic controlled fooC: MyDomain

definitions:
	 domain MyDomain = {1,2,3,4}

	main rule r_main =
		let ($x = foo, $y = fooA) in
			par
				fooB := $x
				fooC := $y
			endpar
		endlet

default init s0: