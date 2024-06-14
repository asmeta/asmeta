asm switch

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a: MyDomain
	dynamic controlled var_b: MyDomain

definitions:
	domain MyDomain = {1:4}
	
	CTLSPEC ag((var_a = 1 iff ax(var_a = 2)) and (var_a = 2 iff ax(var_a = 3)) and 
			     (var_a = 3 iff ax(var_a = 4)) and (var_a = 4 iff ax(var_a = 1)))

	CTLSPEC var_b=1 and ax(var_b=1 and ax(var_b=1 and ax(var_b=1 and ax(var_b=4))))
	CTLSPEC ag(var_a!=4 iff ax(var_b=1))

	main rule r_Main =
		par
			switch(var_a)
				case 1: 
					var_a := 2
				case 2: 
					var_a := 3
				case 3: 
					var_a := 4
				case 4: 
					var_a := 1
			endswitch
			
			switch(var_a)
				case 1: 
					var_b := 1
				case 2: 
					var_b := 1
				case 3: 
					var_b := 1
				otherwise
					var_b := 4
			endswitch
		endpar

default init s0:
	function var_a = 1
	function var_b = 1