asm forallRuleChoose

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	dynamic controlled foo: ConcrDom -> ConcrDom
	
definitions:
	domain ConcrDom = {1 : 10}
	
	rule r_rule($a in ConcrDom, $x in ConcrDom) =
		foo($a) := $x

	main rule r_Main = 
		forall $a in ConcrDom with $a < 6 do
			choose $x in ConcrDom with $x < $a do
				r_rule[$a, $x]

default init s0:
	function foo($x in ConcrDom) = 1
