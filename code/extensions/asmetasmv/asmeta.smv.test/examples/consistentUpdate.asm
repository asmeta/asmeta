asm consistentUpdate
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled foo: Boolean
	dynamic controlled foo1: Boolean
	
definitions:
	main  rule r_Main = 
		par
			foo := true
			foo := true
			foo1 := false
		endpar
