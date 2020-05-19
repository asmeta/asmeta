asm monStateViolation

//This model is used in the test testInitStateMonAxiomViolation()
//of the class interpreter/AxiomsTest.java

import ../../../STDL/StandardLibrary

signature:
	controlled foo: Boolean
	controlled fooA: Integer
	monitored mon: Boolean

definitions:

	//After 3 steps the invariant is violated (based on the values of mon in monStateViolation.env).
	//Since the invariant contains monitored functions, it must be evaluated
	//after the execution of the update set and the evaluation of the monitored.
	//The invariant is checked in an actual state, that is in a complete state, where
	//the update set has been applied and the monitored functions have been evaluated.
	invariant over foo, mon: foo = mon

	main rule r_main =
		par
			foo := mon
			fooA := fooA + 1
		endpar

default init s0:
	function foo = mon
	function fooA = 1