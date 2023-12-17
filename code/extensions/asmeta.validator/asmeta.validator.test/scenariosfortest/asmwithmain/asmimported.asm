//
// this is an ASM (not MODULE) and it is imported
// it has also a main rule
//
asm asmimported
import ../StandardLibrary
export *

signature:
	// FUNCTIONS
	controlled c_1: Integer

definitions:
    main rule r_main =   c_1 := c_1+1 
    
default init s0:
	function c_1 = 0