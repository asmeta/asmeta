asm caseIsComplete

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled fooA : MyDomain
	dynamic controlled fooB : MyDomain
	dynamic controlled fooC : MyDomain
	dynamic controlled fooD : MyDomain
	dynamic controlled fooE : MyDomain
	dynamic controlled fooF : MyDomain
	dynamic controlled fooG : MyDomain -> MyDomain
	dynamic monitored mon: Boolean
	dynamic monitored mon2: Boolean
	dynamic monitored mon3: Boolean


definitions:
	domain MyDomain = {1..4}

	main rule r_Main =
		par
			switch(mon) //complete
				case true:
					fooA := 2
				case false:
					fooA := 3
			endswitch

			switch(mon2) //complete
				case true:
					fooB := 2
				otherwise
					fooB := 3
			endswitch
			
			fooC := 1
			switch(fooC) //complete
				case 1:
					fooD := 2
			endswitch

			switch(mon and mon) //not complete
				case true:
					fooE := 2
			endswitch


			if(fooC = 2) then//sempre false 
				switch(mon3) //complete
					case true:
						fooF := 2
					case false:
						fooF := 3
				endswitch
			endif
		endpar


default init s0:
	function fooA = 4
	function fooB = 4
	function fooC = 1
	function fooD = 1
	function fooE = 1
	function fooF = 1
	function fooG($x in MyDomain) = 1
