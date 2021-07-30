asm forallRule4

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	dynamic controlled foo: ConcrDom -> ConcrDom
	dynamic controlled foo2: ConcrDom
	// par con forall e altro
definitions:
	domain ConcrDom = {1:4}
	
	main rule r_Main = 
		par
			if true then
				forall $a in ConcrDom, $b in ConcrDom with ($b < $a) do
					foo($b) := $a
			endif
			foo2 := 2
		endpar

default init s0:
	function foo($x in ConcrDom) = 4
