asm runUntilEmptyAxiomViolation

import ../../../STDL/StandardLibrary

signature:
	controlled foo: Integer

definitions:

	invariant over foo: foo < 10

	main rule r_main =
		foo := foo + 1

default init s0:
	function foo = 1