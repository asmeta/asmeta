asm UseInvar2

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	dynamic controlled foo: Boolean
	dynamic monitored mon: Boolean

definitions:

	//AsmetaL invariants:
	// over monitored
	invariant over mon: mon = false
	// those with names are traslated to INVARSPEC
	// over controlled
	invariant inv_1 over foo: foo = false
	// mixed --> INVARSPEC
	invariant inv_2 over foo: foo = false and mon = false

	main rule r_Main =		foo := mon

default init s0:
	function foo = mon
