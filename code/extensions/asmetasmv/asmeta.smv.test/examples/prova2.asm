asm prova2

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	enum domain State = {AA | BB | CC}
	//enum domain State = {AA}
	dynamic controlled var_a : MyDomain
	//dynamic controlled var_d : Prod(MyDomain, MyDomain) -> MyDomain
	//dynamic monitored var_b : Boolean
	//dynamic monitored var_c : State -> Boolean
	//dynamic monitored var_d : Prod(MyDomain, MyDomain) -> MyDomain
	dynamic controlled var_e : State
	dynamic controlled var_f : State
	//dynamic monitored var_g : Prod(State, MyDomain) -> Boolean
	//dynamic monitored var_h : Prod(State, Boolean) -> Boolean
	//dynamic monitored var_i : Boolean -> State

definitions:
	domain MyDomain = {1..4}

	rule r_a($x in MyDomain) =
		var_a := $x + 2


	main rule r_Main = 
		r_a[2]
		/*switch(var_f)
			case AA : r_a[2]
			case BB : var_e := BB
			case CC : var_e := CC
		endswitch*/
		
	/*forall $x in MyDomain with $x < 3 do
			if(var_i(true) = AA) then
				var_a($x) := 3 - $x
			else
				var_a($x) := 2
			endif*/
		//if(var_i(true) = AA) then
		//	if(not(var_b)) then
		//		var_a(1) := 4
		//	endif
		//endif

		
default init s0:
	//function var_a($x in  MyDomain) = $x
