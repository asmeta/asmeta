asm axiomInitialStateWithMon
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled foo1: Boolean
	dynamic controlled foo2: Boolean
	dynamic monitored mon: Boolean

definitions:
	
	invariant over foo2: foo2 //verificato. ma lo stato iniziale?
	//CTLSPEC ag(foo2) //in NuSMV controesempio nello stato iniziale
	
	main rule r_Main =
		par
			foo1 := not(foo1)
			foo2 := true
		endpar
		
default init s0:
	function foo1 = mon
	function foo2 = mon