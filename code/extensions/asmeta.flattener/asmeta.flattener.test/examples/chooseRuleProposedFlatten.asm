asm chooseRuleProposedFlatten

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	dynamic controlled foo: ConcrDom -> ConcrDom
	derived chooseVar: ConcrDom//to generate

definitions:
	domain ConcrDom = {1 .. 5}
	
	//to generate
	function chooseVar =
		chooseone({$a in ConcrDom | $a < foo($a): $a})

	main rule r_Main =
		if isDef(chooseVar) then
			foo(chooseVar) := chooseVar
		endif

default init s0:
	function foo($x in ConcrDom) = 5
