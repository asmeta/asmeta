asm ctlExample
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	dynamic controlled fooA: Boolean
	dynamic controlled fooB: Boolean
	dynamic monitored mon: Boolean

definitions:
	//true
   CTLSPEC ag(fooA iff ax(not(fooA)))
   CTLSPEC ag(not(fooA) iff ax(fooA))

    //false. Gives counterexample.
   CTLSPEC not(ef(fooA != fooB))
	

	main rule r_Main = 
		par
			fooA := not(fooA)
			if(mon) then
				fooB := not(fooB)
			endif
		endpar

default init s0:
	function fooA = true
	function fooB = true