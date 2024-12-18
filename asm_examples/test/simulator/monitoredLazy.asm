// test for lazy evaluation
asm monitoredLazy

import ../../STDL/StandardLibrary

signature:
	monitored f1: Boolean
	monitored f2: Boolean
	controlled g1: Boolean


definitions:    

// f1 --> TRUE
main rule r_main =	g1 := f1 and f2
