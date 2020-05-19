asm print

import  ../../../STDL/StandardLibrary
	
signature:
	controlled output: Integer 

definitions:

	main rule r_main =
		seq
			output := print("a")
			output := print("b")
			output := print("c")
		endseq

default init s0:
