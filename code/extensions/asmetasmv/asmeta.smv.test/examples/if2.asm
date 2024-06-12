asm if2

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a : MyDomain
	dynamic monitored mon: Boolean
	dynamic monitored mon2: Boolean
	
definitions:
	domain MyDomain = {1:10}

	//proprieta' CTL con la vecchia interpretazione delle monitorate: le monitorate
	//appartengono al nuovo updateSet
	/*CTLSPEC ax(ag(
			     ((mon and mon2) iff (var_a=1)) and ((mon and not(mon2)) iff (var_a=2)) and 
			     ((not(mon) and mon2) iff (var_a=3)) and ((not(mon) and not(mon2)) iff (var_a=4))
			    ))*/

	//proprieta' CTL con la nuova interpretazione delle monitorate: le monitorate
	//appartengono al vecchio updateSet
	CTLSPEC ag((mon and mon2) iff ax(var_a=1))
	CTLSPEC ag((mon and not(mon2)) iff ax(var_a=2))
	CTLSPEC ag((not(mon) and mon2) iff ax(var_a=3))
	CTLSPEC ag((not(mon) and not(mon2)) iff ax(var_a=4))

	main rule r_Main = 
		if(mon) then
			if(mon2) then
				var_a := 1
			else
				var_a := 2
			endif
		else
			if(mon2) then
				var_a := 3
			else
				var_a := 4
			endif
		endif

default init s0:
