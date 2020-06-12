asm asm2

import ../../../../../asm_examples/STDL/StandardLibrary

signature:

	abstract domain Lift
	domain MyDomain subsetof Integer
	domain SmallDomain subsetof Integer	
	enum domain PhaseDomain = { FULLYCLOSED | FULLYOPEN }
	dynamic controlled g : Prod(SmallDomain,SmallDomain) -> SmallDomain
	dynamic controlled h : Prod(SmallDomain, SmallDomain) -> SmallDomain
	dynamic controlled var_a : MyDomain
	dynamic controlled var_b : MyDomain -> PhaseDomain
	dynamic controlled var_c : MyDomain
	dynamic controlled var_d : MyDomain
	dynamic controlled var_e : MyDomain
	dynamic monitored b: Boolean
	dynamic monitored c: Boolean
	dynamic monitored d: Boolean
	dynamic controlled ff : MyDomain
	dynamic controlled r: Lift -> Boolean
	
definitions:
	domain MyDomain = {1..10}
	domain SmallDomain = {1..2}

	rule r_b($y in MyDomain) =
		var_a := $y

	rule r_a($x in MyDomain) =
		r_b[$x]
	
	main rule r_Main = 
	forall $x in MyDomain with $x = 1 do
		r_a[$x]
		

default init s0:
	function var_a = 5
	function var_b($x in MyDomain) = FULLYOPEN
