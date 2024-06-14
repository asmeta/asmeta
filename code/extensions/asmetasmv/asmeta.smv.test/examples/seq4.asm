asm seq4

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a: MyDomain
	dynamic controlled var_b: MyDomain
	dynamic monitored mon: Boolean

definitions:
	domain MyDomain = {1:4}

	CTLSPEC ax(ag(var_b=3 iff mon))
	CTLSPEC ax(ag(var_b=4 iff not(mon)))
	CTLSPEC var_b=undef and ax(ag(var_b!=1))
	CTLSPEC ag(var_a=1)

	main rule r_Main =
		seq
			var_b := 1
			if(mon) then
				var_b := 2
			else
				var_b := var_a + 2
			endif
			var_b := var_b + 1
		endseq

default init s0:
	function var_a = 1
