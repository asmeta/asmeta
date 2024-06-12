asm noJusticeConstraint

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain
	
definitions:
	domain MyDomain = {1:3}

	CTLSPEC ag(af(foo = 2)) //it should be false

	main rule r_Main =
		choose $x in MyDomain with true do
			foo := $x

default init s0:
	function foo = 1