asm initStateViolation

//This model is used in the test testInitStateViolation()
//of the class interpreter/AxiomsTest.java

import ../../../STDL/StandardLibrary

signature:
	controlled foo: Boolean

definitions:

	//the invariant is violated in the initial state
	invariant over foo: foo = true

	//No step is executed because the invariant is violated in the initial state
	main rule r_main = 
		foo := true

default init s0:
	function foo = false //the invariant is violated here
