asm seq3

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a: MyDomain
	dynamic controlled var_b: MyDomain
	dynamic monitored mon: Boolean

definitions:
	domain MyDomain = {1:4}

	//proprieta' CTL con la vecchia interpretazione delle monitorate: le monitorate
	//appartengono al nuovo updateSet
	/*CTLSPEC ax(ag(var_b=3 iff mon))
	CTLSPEC ax(ag(var_b=2 iff not(mon)))
	CTLSPEC var_b=1 and ax(ag(var_b!=1))*/

	//proprieta' CTL con la nuova interpretazione delle monitorate: le monitorate
	//appartengono al vecchio updateSet
	CTLSPEC ag(var_b != 4)
	CTLSPEC ag(mon iff ax(var_b=3))
	CTLSPEC ag(not(mon) iff ax(var_b=2))

	main rule r_Main = 
		seq
			var_b := 4
			if(mon) then
				var_b := 2
			else
				var_b := 1
			endif
			var_b := var_b + 1
		endseq

default init s0:
	function var_b = 1
