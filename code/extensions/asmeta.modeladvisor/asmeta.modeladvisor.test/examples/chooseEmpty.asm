asm chooseEmpty

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled fooA : MyDomain -> MyDomain
	dynamic controlled fooB : MyDomain -> MyDomain
	dynamic controlled fooC : MyDomain -> MyDomain
	dynamic controlled fooD : MyDomain -> MyDomain
	dynamic controlled fooE : MyDomain -> MyDomain
	dynamic monitored mon: MyDomain

definitions:
	domain MyDomain = {1 : 4}

	main rule r_Main =
		par
			//always not empty. $x is always 1.
			choose $x in MyDomain with $x < 2 do
					fooA($x) := $x

			//fooA(1) is always different from 2
			if(fooA(1) = 2) then
				//choose rule is never executed
				choose $y in MyDomain with $y > 2 do
					fooA($y) := $y
			endif
			//always empty
			choose $z in MyDomain with $z > 2 and $z < 2 do
				fooA($z) := $z
			//sometimes (but not always) empty
			choose $k in MyDomain with $k > mon do
				fooB($k) := $k
			forall $g in MyDomain with $g > 2 and $g < 4 do
				choose $h in MyDomain with $h > $g do //always not empty
					fooC($h) := $g
			forall $q in MyDomain with $q > 3 do
				choose $w in MyDomain with $w > $q do //always empty se considero la condizione d'arrivo
					fooD($w) := $q
			choose $o in MyDomain with $o > 3 do //always not empty
				choose $p in MyDomain with $p > $o do //always empty se considero la condizione d'arrivo
					fooE($p) := $o
		endpar


default init s0:
	function fooA($x in MyDomain) = 4
	function fooB($x in MyDomain) = 4
	function fooC($x in MyDomain) = 1
	function fooD($x in MyDomain) = 1
	function fooE($x in MyDomain) = 1
