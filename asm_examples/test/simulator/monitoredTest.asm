asm monitoredTest

import ../../STDL/StandardLibrary

signature:
	monitored f1: Integer
	monitored f2: Boolean
	monitored f3: Seq(Integer)
	monitored f4: Seq(Prod(Integer, Boolean))
	controlled g1: Integer
	controlled g2: Boolean
	controlled g3: Seq(Integer)
	controlled g4: Seq(Prod(Integer, Boolean))


definitions:    


main rule r_main = 
	par
		g1 := f1
		g2 := f2
		g3 := f3
		g4 := f4
	endpar
