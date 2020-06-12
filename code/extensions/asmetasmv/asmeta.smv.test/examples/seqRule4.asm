asm seqRule4

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled fooA: MyDomain
	dynamic controlled fooB: MyDomain
	dynamic controlled fooC: MyDomain
	dynamic controlled fooD: MyDomain
	dynamic controlled fooE: MyDomain
	dynamic controlled fooF: MyDomain

definitions:
	domain MyDomain = {1..4}
	
	CTLSPEC fooB = 1 and ax(fooB=1 and ax(ag(fooB=3)))
	CTLSPEC fooC = 2 and ax(fooC=4 and ax(ag(fooC=3)))
	CTLSPEC fooE = 2 and ax(fooE=4 and ax(ag(fooE=3)))

	main rule r_Main =
		par
			fooB := fooA
			seq
				fooA := 1
				fooA := 2
				fooA := 3
			endseq
			
			fooD := 1
			seq
				fooC := fooD + 1
				fooC := fooC + 1
			endseq
			
			fooF := 1
			seq
				fooE := 1
				fooE := fooF + 1
				fooE := fooE + 1
			endseq
		endpar

default init s0:
	function fooA = 1
	function fooB = 1
	function fooC = 2
	function fooD = 2
	function fooE = 2
	function fooF = 2
