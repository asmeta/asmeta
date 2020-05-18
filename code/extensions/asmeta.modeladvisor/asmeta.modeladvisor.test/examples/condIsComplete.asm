asm condIsComplete

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled fooA: MyDomain
	dynamic controlled fooB: MyDomain
	dynamic controlled fooC: MyDomain -> MyDomain
	dynamic controlled fooD: MyDomain -> MyDomain
	dynamic controlled fooE: MyDomain -> MyDomain
	dynamic controlled fooF: MyDomain -> MyDomain
	dynamic controlled fooG: MyDomain -> MyDomain
	dynamic controlled fooH: MyDomain -> MyDomain
	dynamic controlled fooI: MyDomain -> MyDomain
	dynamic monitored mon: Boolean

definitions:
	domain MyDomain = {1..4}

	main rule r_Main =
		par
			if(mon or not(mon)) then //complete
				fooA := 2
			endif

			if(mon) then //not complete
				fooB := 2
			endif

			if(mon) then //complete
				fooB := 2
			else
				fooB := 3
			endif

			forall $x in MyDomain with $x < 3 do
				if(mon and mon and mon) then//complete perche' se c'e' l'else non lo valuta.
					fooC($x) := 2
				else
					fooC($x) := 3
				endif

			forall $y in MyDomain with $y < 1 do
				if(mon) then//complete perche' se c'e' l'else non lo valuta. e' complete anche perche' non ci arriva mai.
					fooD($y) := 2
				else
					fooD($y) := 3
				endif
			
			forall $k in MyDomain with $k < 1 do
				if(mon and mon) then// complete, perche' non ci arriva mai.
					fooE($k) := 2
				endif

			forall $h in MyDomain with $h < 2 do
				if($h<2) then// complete.
					fooF($h) := 2
				endif
			forall $g in MyDomain with $g < 1 do
				if($g<2) then// complete, perche' non ci arriva mai.
					fooG($h) := 2
				endif

			forall $f in MyDomain with $f < 1 do
				if(1=1) then// complete, perche' non ci arriva mai.
					fooH($h) := 2
				endif

			forall $i in MyDomain with $i < 2 do
				if($i<2 and mon) then// not complete.
					fooI($i) := 2
				endif
		endpar


default init s0:
	function fooA = 4
	function fooB = 4
	function fooC($x in MyDomain) = 1
	function fooD($x in MyDomain) = 1
	function fooE($x in MyDomain) = 1
	function fooF($x in MyDomain) = 1
	function fooG($x in MyDomain) = 1
	function fooH($x in MyDomain) = 1
	function fooI($x in MyDomain) = 1
