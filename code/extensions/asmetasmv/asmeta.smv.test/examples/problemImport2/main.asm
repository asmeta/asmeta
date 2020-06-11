asm main

//il problema dovrebbe essere stato risolto. Rimangono gli esempi per documentazione

import ../../../../../../../asm_examples/STDL/StandardLibrary

//modo 1: il foo di moduleA sovrascrive quello di moduleB
import moduleA
import moduleB

//modo 2: il foo di moduleB sovrascrive quello di moduleA
//import moduleB
//import moduleA

signature:
	
definitions:

	main rule r_Main =
		par
			r_a[]
			r_b[]
		endpar
default init s0:
