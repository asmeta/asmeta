// itor applied to a natural
//@parser: error itor cannot be used for naturale
asm itor

import ../../STDL/StandardLibrary

signature:

	monitored m1: Natural
	controlled foo1: Real

definitions:

	main rule r_Main = foo1 := itor(m1)   

default init s0:
