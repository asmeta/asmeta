asm forallChooseRule

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	dynamic controlled foo: ConcrDom -> ConcrDom
	
definitions:
	domain ConcrDom = {1 : 5}

	main rule r_Main =
		forall $a in ConcrDom with $a < foo($a) do
			choose $b in ConcrDom with $b < $a do
				foo($b) := $b

default init s0:
	function foo($x in ConcrDom) = 5
