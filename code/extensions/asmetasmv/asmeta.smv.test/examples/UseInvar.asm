asm UseInvar

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	dynamic controlled foo: Boolean
	dynamic monitored mon: Boolean

definitions:

	//AsmetaL invariants:
	// over monitored
	invariant over mon: mon = false
	// over controlled
	invariant over foo: foo = false

	main rule r_Main =		foo := mon

default init s0:
	function foo = mon
