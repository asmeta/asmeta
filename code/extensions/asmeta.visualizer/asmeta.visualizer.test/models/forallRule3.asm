asm forallRule3

import StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	controlled foo: Prod(ConcrDom, ConcrDom) -> ConcrDom

definitions:
	domain ConcrDom = {0 .. 5}

	rule r_rule($x in ConcrDom) =
		forall $y in ConcrDom with true do
			foo($x, $y) := 1

	main rule r_Main =
		forall $x in ConcrDom with true do
			r_rule[$x]

default init s0:
	function foo($x in ConcrDom, $y in ConcrDom) = 0