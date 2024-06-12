asm preFunction

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

//come fare la funzione pre?

signature:
	dynamic controlled foo: Boolean
	dynamic controlled fooA: Boolean
	dynamic monitored mon: Boolean

definitions:

	main rule r_Main =
		par
			foo := not(foo)
			if(mon) then
				fooA := pre(foo)
			endif
		endpar

default init s0:
	function foo = true
	function fooA = false
