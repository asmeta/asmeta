asm initExample

import ../../../STDL/StandardLibrary

signature:
	abstract domain AbDom
	dynamic controlled foo: AbDom -> Boolean
	static ab1: AbDom
	static ab2: AbDom

definitions:

	main rule r_Main =
		forall $a in AbDom with true do
		 	foo($a) := not(foo($a))
		
default init s0:
	function foo($a in AbDom) =
		if($a = ab1) then
			true
		else
			false
		endif