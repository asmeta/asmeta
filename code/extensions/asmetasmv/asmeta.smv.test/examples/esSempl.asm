asm esSempl
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain
	dynamic monitored mon: Boolean
	
definitions:
	domain MyDomain = {1..4}
	
	main  rule r_Main = 
		if(1 < 2) then
			if(mon) then
				foo := 2
			else
				foo := 3
			endif
		else
			foo := 1
		endif
