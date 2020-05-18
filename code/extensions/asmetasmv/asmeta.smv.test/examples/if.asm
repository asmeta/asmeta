asm if

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:

	domain MyDomain subsetof Integer
	dynamic controlled var_a : MyDomain
	dynamic monitored b: Boolean
	
definitions:
	domain MyDomain = {1..10}
	
	//per la vecchia interpretazione delle monitorate: le monitorate
	//appartengono al nuovo updateSet
	//CTLSPEC ag((var_a=3 implies b) and (var_a=4 implies not(b)) )

	//nuova interpretazione delle monitorate
	CTLSPEC ag( (b implies ax(var_a=3)) and (not(b) implies ax(var_a=4)))
	
	main rule r_Main =
		if(b) then
			var_a := 3
		else
			var_a := 4
		endif

default init s0:
	function var_a = 5
