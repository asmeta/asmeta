asm chooseChoose

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled fooA: Boolean
	dynamic controlled fooB: Boolean
	dynamic controlled fooC: Boolean

definitions:
	domain MyDomain = {1:4}

	CTLSPEC not(fooA) and ax(ag(fooA))  
	CTLSPEC ag(not(fooB))
	CTLSPEC ag(not(fooC))

	main rule r_Main =
		par
			choose $x in MyDomain with $x < 2 do
				choose $y in MyDomain with $y <= $x do
					fooA := true
				ifnone
					fooA := false
			choose $k in MyDomain with $k < 2 do
				choose $i in MyDomain with $i < $k do
					fooB := true
				ifnone
					fooB := false
			choose $c in MyDomain with $c < 2 do
				choose $z in MyDomain with $z < $c do
					fooC := true
		endpar

default init s0:
	function fooA = false
	function fooB = false
	function fooC = false