asm chooseRule3
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled fooA: MyDomain
	dynamic controlled fooB: MyDomain

definitions:
	domain MyDomain = {1:4}

	rule r_a =
		choose $x in MyDomain with $x = fooA - 1 do
			fooA := $x

	rule r_b =
		choose $x in MyDomain with $x = fooB - 1 do
			fooB := $x

	CTLSPEC ag(fooA=4 iff ax(fooA=3))
	CTLSPEC ag(fooA=3 iff ax(fooA=2))
	CTLSPEC ag(fooA=2 implies ax(fooA=1))
	CTLSPEC ag(fooA=1 iff ag(fooA=1))
	CTLSPEC ag(fooB=4 iff ax(fooB=3))
	CTLSPEC ag(fooB=3 iff ax(fooB=2))
	CTLSPEC ag(fooB=2 implies ax(fooB=1))
	CTLSPEC ag(fooB=1 iff ag(fooB=1))

	main rule r_Main = 
		par
			r_a[]
			r_b[]
		endpar

default init s0:
	function fooA = 4
	function fooB = 4