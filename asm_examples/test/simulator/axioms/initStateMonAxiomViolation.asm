asm initStateMonAxiomViolation

//This model is used in the test testInitStateMonAxiomViolation()
//of the class interpreter/AxiomsTest.java

import ../../../STDL/StandardLibrary

signature:
	controlled foo: Boolean
	controlled fooA: Boolean
	monitored mon: Boolean

definitions:

	//the invariant is violated in the initial state
	invariant over foo, mon: foo

	//No step is executed if in the initial state mon is set to false
	main rule r_main =
		par
			foo := mon
			fooA := false
		endpar

default init s0:
	function foo = mon //the invariant is violated here if mon is set to false
	function fooA = true