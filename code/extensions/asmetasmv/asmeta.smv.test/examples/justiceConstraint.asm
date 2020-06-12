asm justiceConstraint

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain
	
definitions:
	domain MyDomain = {1..3}

	JUSTICE foo = 2

	CTLSPEC ag(af(foo = 2)) //true because of the justice constraint

	main rule r_Main =
		choose $x in MyDomain with true do
			foo := $x

default init s0:
	function foo = 1