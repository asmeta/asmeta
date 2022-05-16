asm chooseRule2
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain

definitions:
	domain MyDomain = {1:4}

	CTLSPEC ag(foo=4 iff ax(foo=3))
	CTLSPEC ag(foo=3 iff ax(foo=2))
	CTLSPEC ag(foo=2 implies ax(foo=1))
	CTLSPEC ag(foo=1 iff ag(foo=1))

	main rule r_Main = 
		choose $x in MyDomain with $x = foo - 1 do
				foo := $x
				
default init s0:
	function foo = 4