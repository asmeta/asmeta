asm forallRule3

import ../../../../asm_examples/STDL/StandardLibrary

signature:
//TODO problema altri domini
	enum domain EnumDom = {AA | BB | CC | DD}
	
	domain ConcrDom subsetof Integer
	dynamic controlled foo: ConcrDom -> ConcrDom
	dynamic controlled foo2: ConcrDom -> EnumDom
	dynamic controlled foo3: EnumDom
	
definitions:
	domain ConcrDom = {1..4}
	
	main rule r_Main = 
		par
			if true then
				forall $a in ConcrDom, $b in ConcrDom, $c in EnumDom with (($b < $a) and ($c = BB)) do
					par
						foo($b) := $a
						foo2($a) := $c
					endpar
			endif
			foo3 := AA
		endpar

default init s0:
	function foo($x in ConcrDom) = 4