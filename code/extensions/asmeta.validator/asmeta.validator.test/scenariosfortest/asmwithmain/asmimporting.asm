asm asmimporting
import ../StandardLibrary
import asmimported

signature:
	// FUNCTIONS
	controlled c_2: Integer

definitions:
    main rule r_main =   c_2 := c_1*2 
    
default init s0:
	function c_2 = 0
	// this needs to be repeated since it is ignored
	function c_1 = 0