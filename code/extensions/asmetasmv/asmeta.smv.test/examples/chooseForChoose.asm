asm chooseForChoose

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain Dom subsetof Integer
	dynamic controlled foo: Dom
	dynamic controlled foo2: Dom
	dynamic controlled foo3: Dom -> Dom

definitions:
	domain Dom ={1:3}

	CTLSPEC ag(foo=3)
	CTLSPEC ag(foo2!=3)
	CTLSPEC ag(foo3(1)=1 and foo3(2)=1 and foo3(3)=1)

	main rule r_Main =
		choose $x in Dom with $x<2 do
			forall $y in Dom with $y<=$x do
				par
					choose $k in Dom with $k> $x +  $y do
						foo := $k
					choose $t in Dom with $t <= $x +  $y do
						foo2 := $t
					forall $e in Dom with true do
						choose $w in Dom with $w > 2 do
							foo3($e) := $w - $x  - $y
				endpar
		
default init s0:
	function foo = 3
	function foo2 = 2
	function foo3($n in Dom) = 1
