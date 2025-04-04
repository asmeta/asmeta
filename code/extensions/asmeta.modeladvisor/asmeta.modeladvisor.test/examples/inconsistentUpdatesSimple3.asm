// no error in this case
asm inconsistentUpdatesSimple3

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain SubDom subsetof Integer
	dynamic controlled fooG: SubDom
	dynamic monitored mon: Boolean

definitions:
	domain SubDom = {1 : 2}
	
	main  rule r_Main =
			//inconsistent update
			if mon then 
				 par
				  fooG := 1
				  fooG := 1
				endpar
			endif
			
default init s0:
	function fooG = 1
