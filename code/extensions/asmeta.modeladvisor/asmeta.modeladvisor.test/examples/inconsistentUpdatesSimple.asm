asm inconsistentUpdatesSimple

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain SubDom subsetof Integer
	dynamic controlled fooG: SubDom
	dynamic monitored mon: Boolean
	dynamic monitored mon2: Boolean

definitions:
	domain SubDom = {1 : 2}
	
	main  rule r_Main =
			//inconsistent update
			if mon then 
				 if not mon then fooG := 1 endif
			endif
			
default init s0:
	function fooG = 1
