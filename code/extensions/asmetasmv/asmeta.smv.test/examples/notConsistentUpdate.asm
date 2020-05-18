asm notConsistentUpdate
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled foo: Boolean
	
definitions:
	main  rule r_Main = 
		par
			foo := true
			foo := false
		endpar
