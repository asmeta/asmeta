asm seqRule2

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain
	dynamic monitored mon: Boolean

definitions:
	domain MyDomain = {1:4}
	
	CTLSPEC ag(foo=1)
	
	main rule r_Main = 
		seq
			if(mon) then
				foo := 2
			else
				foo := 3
			endif
			foo := 1
		endseq

default init s0:
	function foo = 1