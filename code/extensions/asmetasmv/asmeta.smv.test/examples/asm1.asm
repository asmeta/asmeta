asm asm1

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled var_a : Integer
	dynamic controlled var_b : Integer
	dynamic controlled var_c : Integer
	dynamic controlled var_d : Integer
	dynamic monitored b: Boolean

definitions:

	rule r_a =
		var_b := var_a + 1

	main rule r_Main =  
		r_a[]
	

default init s0:
	function var_a = 5
	function var_b = 5
