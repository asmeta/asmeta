//
// this ASM USES THE TIME LIBRARY
//
asm asmwithtime
import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/TimeLibrarySimple

signature:
	// FUNCTIONS
	controlled count5Secs: Integer

definitions:
    main rule r_main =   
    	if (mCurrTimeSecs mod 5) = 0 
    	then count5Secs := count5Secs +1
    	endif 
    
default init s0:
	function count5Secs = 0