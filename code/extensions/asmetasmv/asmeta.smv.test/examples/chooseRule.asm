asm chooseRule
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain

definitions:
	domain MyDomain = {1:4}

	CTLSPEC ag(foo=3)

	main rule r_Main = 
		choose $x in MyDomain with $x < 2 do
				foo := $x + 2
			ifnone
				foo := 4

default init s0:
	function foo = 3
