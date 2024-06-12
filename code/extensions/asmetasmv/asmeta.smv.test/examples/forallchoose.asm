asm forallchoose

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a: Prod(MyDomain, MyDomain) -> MyDomain
	//static check: Prod(MyDomain, MyDomain) -> Boolean
	//static prop: Boolean
	dynamic monitored mon: MyDomain
definitions:
	domain MyDomain = {1:4}
	
	//function check($a in MyDomain, $b in MyDomain) =
	//	$a<3 and ($b> $a +1)
		
	//function prop =
	//	if(forall $a in MyDomain, $b in MyDomain with (check($a,$b) iff var_a($a,$b)=4)) then true else false endif
		
		
		rule r_a($x in MyDomain, $y in MyDomain) =
			var_a($x, $y) := 4
	
	//CTLSPEC af(prop)
	CTLSPEC ag(var_a(1,1)!=4)
	
	main rule r_Main =
		par
			forall $x in MyDomain with $x < 3 do
				//questa choose deve essere always not empty
				choose $y in MyDomain with $y > $x + 1 do
					r_a[$x, $y]
			forall $c in MyDomain with $c < 3 do
				//questa choose sometimes is empty
				choose $v in MyDomain with $v > mon + 1 do
					r_a[$c, $v]
 
		endpar
		
default init s0:
	function var_a($a in MyDomain, $b in MyDomain) = 1
