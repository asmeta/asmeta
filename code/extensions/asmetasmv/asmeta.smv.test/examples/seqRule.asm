asm seqRule

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain

definitions:
	domain MyDomain = {1..4}
	
	CTLSPEC isUndef(foo) and ax(ag(foo=3))

	main rule r_Main = 
		seq
			foo := 2
			foo := foo + 1
		endseq
