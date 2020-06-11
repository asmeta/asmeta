asm seqRule5

//questo modello deve generare un update inconsistente

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled fooA: MyDomain
	dynamic controlled fooB: MyDomain

definitions:
	domain MyDomain = {1..4}

	main rule r_Main =
		par
			fooB := 1
			seq
				fooB := 2
				fooA := 2
			endseq
		endpar

default init s0:
	function fooA = 2
	function fooB = 1
