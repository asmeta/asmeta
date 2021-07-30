asm seq1

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a: MyDomain
	dynamic controlled var_b: MyDomain
	dynamic monitored mon: Boolean
definitions:
	domain MyDomain = {1:4}

	CTLSPEC ag(var_b = 3)	
	
	main rule r_Main = 
		seq
			var_b := 2
			var_b := var_b + 1
		endseq
		
default init s0:
	function var_b = 3
