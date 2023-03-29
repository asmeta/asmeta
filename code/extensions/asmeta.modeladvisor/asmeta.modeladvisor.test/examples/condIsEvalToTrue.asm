asm condIsEvalToTrue

import ../../../../../asm_examples/STDL/StandardLibrary

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
	dynamic monitored mon: Boolean

definitions:
	domain MyDomain = {1 : 4}

	main rule r_Main =
		par
			if(mon or not(mon)) then //executed
				fooA := 2
			endif

			if(mon) then //executed
				fooB := 2
			endif

			if(mon or mon) then //executed
				fooB := 2
			else //executed
				fooB := 3
			endif

			if(mon or not(mon) or not(mon)) then //executed
				fooB := 2
			else //never executed
				fooB := 3
			endif


			forall $x in MyDomain with $x < 3 do
				if(mon and mon and mon) then //executed
					fooC($x) := 2
				else //executed
					fooC($x) := 3
				endif

			forall $y in MyDomain with $y < 1 do
				if(mon or mon or mon) then //never executed
					fooD($y) := 2
				else //not executed
					fooD($y) := 3
				endif
			
			forall $k in MyDomain with $k < 1 do
				if(mon and mon) then //never executed
					fooE($k) := 2
				endif

			forall $h in MyDomain with $h < 2 do
				if($h<2) then// executed
					fooF($h) := 2
				endif
			forall $g in MyDomain with $g < 1 do
				if($g<2) then// never executed
					fooG($h) := 2
				endif

			forall $f in MyDomain with $f < 1 do
				if(1=1) then// never executed
					fooH($h) := 2
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
