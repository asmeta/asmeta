asm axiomInitialState
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled foo: Boolean

definitions:

	invariant over foo: foo //deve essere verificato anche nello stato iniziale?
	//invariant over foo: ag(foo)
	
	main rule r_Main = 
		foo := true
		
default init s0:
	function foo = false