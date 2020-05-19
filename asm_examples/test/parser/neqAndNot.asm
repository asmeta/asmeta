asm neqAndNot

import ../../STDL/StandardLibrary

signature:
	controlled foo: Boolean

definitions:

	//It gives error when it encounters "not".
	invariant over foo: foo != not foo

	main rule r_Main = skip
       
default init s0:
