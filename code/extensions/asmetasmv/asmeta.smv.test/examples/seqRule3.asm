asm seqRule3

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
	dynamic monitored mon: Boolean

definitions:
	domain MyDomain = {1..4}

	CTLSPEC (fooA = 1) and (mon implies ax(ag(fooA = 3)))
	CTLSPEC ag(mon implies ax(ag(fooA = 3)))
	CTLSPEC ag(mon implies ax(ag(fooA = 3)))
	CTLSPEC mon implies ax(fooC=4 and ag(mon implies ax(fooC=3)))
	CTLSPEC mon implies ax(fooE=4 and ag(mon implies ax(fooE=3)))
	CTLSPEC e(fooE = 2, ex(fooE = 4))

	main rule r_Main =
		if(mon) then
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
		endif

default init s0:
	function fooA = 1
	function fooB = 1
	function fooC = 2
	function fooD = 2
	function fooE = 2
	function fooF = 2
