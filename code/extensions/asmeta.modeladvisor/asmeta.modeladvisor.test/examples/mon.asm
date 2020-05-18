asm mon

import ../../../../asm_examples/STDL/StandardLibrary
import ../../../../asm_examples/STDL/CTLlibrary

signature:
	dynamic controlled fooA : Boolean
	dynamic controlled fooB : Boolean
	dynamic controlled fooC : Boolean
	dynamic monitored mon: Boolean
	dynamic monitored mon2: Boolean

definitions:
	CTLSPEC ag(fooA = mon)
	CTLSPEC ag(fooB = not(mon))
	CTLSPEC ag(fooC = not(mon))

	main rule r_Main =
		par
			fooA := mon
			fooB := not(mon)
			if(mon) then
				fooC := false
			else
				fooC := true
			endif
		endpar


default init s0:
	function fooA = mon
	function fooB = not(mon)
	function fooC = not(mon)
