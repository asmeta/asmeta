// ntor applied to an integer
//@parser: error ntor cannot be used for iteger
asm ntor

import ../../../STDL/StandardLibrary

signature:

	monitored m1: Integer
	controlled foo1: Real

definitions:

	main rule r_Main = foo1 := ntor(m1)   

default init s0:
