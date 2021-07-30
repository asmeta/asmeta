asm chooseRule

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	dynamic controlled foo: ConcrDom -> ConcrDom
	
definitions:
	domain ConcrDom = {1 : 5}

	main rule r_Main = 
		choose $a in ConcrDom with $a < foo($a) do
			foo($a) := $a

default init s0:
	function foo($x in ConcrDom) = 5
