asm seq5

//questo modello non puo' essere tradotto perche', per ora, non e' possibile
//usare una choose rule in una seq rule

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain
	dynamic controlled fooA: MyDomain

definitions:
	domain MyDomain = {1:4}

	//CTLSPEC ag(fooA<=4)
	//CTLSPEC foo=1 and ax(ag(foo = 1 + fooA))

	main rule r_Main =
		seq
			foo := 1
			choose $x in MyDomain with $x!=4 do
				fooA := $x
			foo := fooA + 1
		endseq

default init s0:
	function foo = 1
	function fooA = 1