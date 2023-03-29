asm forallEmpty

import ../../../../../asm_examples/STDL/StandardLibrary

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
			forall $x in MyDomain with $x < 2 do //always not empty
				fooA($x) := $x

			if(fooA(1) = 2) then
				//la forall e' always not empty, pero' non e' raggiungibile
				//bisogna anche considerare la condizione di arrivo alla forall?
				//ora consideriamo la condizione d'arrivo e quindi e' always empty.
				forall $y in MyDomain with $y > 2 do
					fooA($y) := $y
			endif
			forall $q in MyDomain with $q > 2 do //always not empty
				forall $w in MyDomain with $w > $q do //always not empty
					fooB($w) := $w
			forall $f in MyDomain with $f > mon do //sometimes empty (but not always)
				fooC($f) := $f
			forall $s in MyDomain with $s > 3 do //always not empty
				forall $d in MyDomain with $d > $s do //always empty. Consideriamo la condizione d'arrivo
					fooB($d) := $d
			choose $u in MyDomain with true do
				forall $i in MyDomain with $i>$u do //sometimes empty (but not always)
					fooC($i) := $i
		endpar


default init s0:
	function fooA($x in MyDomain) = 4
	function fooB($x in MyDomain) = 4
	function fooC($x in MyDomain) = 1
	function fooD($x in MyDomain) = 1
	function fooE($x in MyDomain) = 1
