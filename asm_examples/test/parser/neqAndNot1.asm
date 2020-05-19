asm neqAndNot1

import ../../STDL/StandardLibrary

signature:
	controlled foo: Boolean

definitions:

	//It is parsed correctly.
	invariant over foo: foo != not(foo)

	main rule r_Main = skip
       
default init s0:
