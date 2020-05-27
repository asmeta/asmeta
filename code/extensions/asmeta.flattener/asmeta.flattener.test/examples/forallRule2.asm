asm forallRule2

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	dynamic controlled foo: ConcrDom -> ConcrDom
	
definitions:
	domain ConcrDom = {1:4}
	
	main rule r_Main = 
		par
			if true then
				forall $a in ConcrDom, $b in ConcrDom with $b < $a do
					foo($b) := $a
			endif
			forall $x in ConcrDom with $x < 3 do
				foo($x) := 4 - $x
			forall $y in ConcrDom with foo(4) - 1 = $y do
				foo($y) := $y - 1
			forall $m in ConcrDom with $m < 3 do
				forall $n in ConcrDom with $n < 2 do
					forall $h in ConcrDom with $h = 1 do
						foo($m) := $n
		endpar

default init s0:
	function foo($x in ConcrDom) = 4