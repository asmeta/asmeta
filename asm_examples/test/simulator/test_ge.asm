asm test_ge
	
import ../../STDL/StandardLibrary
	
signature:

controlled geval : Boolean

definitions:

main rule r_test_ge = if ( 10 > 3) then geval:= true endif

default init s0:

  function geval = false
 



