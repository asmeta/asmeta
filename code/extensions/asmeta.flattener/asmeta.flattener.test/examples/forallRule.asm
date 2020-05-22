asm forallRule

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	dynamic controlled foo: ConcrDom -> ConcrDom
	dynamic controlled foo2: ConcrDom
	
definitions:
	domain ConcrDom = {1}
	// test con un singolo elemento per vedere come crea il par di if
	main rule r_Main = 
		par
			if true then
				forall $a in ConcrDom, $b in ConcrDom with ($b < $a) do
					foo($b) := $a
			endif
			foo2 := 1
		endpar

default init s0:
	function foo($x in ConcrDom) = 1