asm rangeFunction

import ../../STDL/StandardLibrary

signature:
	domain ConcrDom subsetof Natural
	dynamic controlled foo: ConcrDom -> ConcrDom  
	
definitions:
	domain ConcrDom = {1n : 10n}

	//invariant over foo: (foo < 6n)

	main rule r_Main =
		forall $x in range(1n, 5n) with true do
			foo($x) := foo($x) + 1n

default init s0:
	function foo($x in ConcrDom) = $x
