asm condterm

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLLibrary

signature:
    	domain MyInt subsetof Integer

    	controlled my_int: MyInt
    	monitored my_bool: Boolean

definitions:
    	domain MyInt = {-1:1}
	
		CTLSPEC ag ((my_int >= -1) and (my_int <= 1))
	
    	main rule r_main =
    		my_int :=
    			if (my_bool) then
    				1
    			else
    				-1
    			endif

default init s0:
    	function my_int = 0
