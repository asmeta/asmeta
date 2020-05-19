asm contrStateViolation

//This model is used in the test testContrStateViolation()
//of the class interpreter/AxiomsTest.java

//The controlled function fooA is just used in the test to detect that an invariant
//violation has occurred.
//Indeed the test executes the ASM for 10 steps and the env file contrStateViolation.env
//sets the values of mon incrementally: 1, 2, 3, ..., 11
//The value of mon, at each step,  is put in fooA.
//Since the execution is blocked after two steps because of the invariant violation,
//at the end of the computation fooA will have value 2.

import ../../../STDL/StandardLibrary

signature:
	controlled foo: Integer
	controlled fooA: Integer
	monitored mon: Integer

definitions:

	//After 2 steps the invariant is violated, because foo becomes 3.
	//you must see the values of mon in asm contrStateViolation.env
	invariant over foo: foo < 3

	main rule r_main =
		par
			foo := foo + 1
			fooA := mon
		endpar

default init s0:
	function foo = 1
	function fooA = mon