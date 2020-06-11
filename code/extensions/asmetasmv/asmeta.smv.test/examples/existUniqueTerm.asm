asm existUniqueTerm

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a: MyDomain
	dynamic controlled foo: MyDomain -> Boolean
	dynamic controlled fooA: Boolean
	dynamic controlled fooB: Boolean

definitions:
	domain MyDomain = {1..3}

	CTLSPEC var_a = 2 and ax(ag(var_a=3))
	CTLSPEC ef(fooA)
	CTLSPEC ef(fooB)
	CTLSPEC ef(not(fooA))
	CTLSPEC ef(not(fooB))

	main rule r_Main =
		par 
			if(exist unique $x in MyDomain with $x=2) then
				var_a := 3
			else
				var_a := 1
			endif
			choose $y in MyDomain with true do
				foo($y) := not(foo($y))
			fooA := (exist unique $z in MyDomain with foo($z))
			fooB := (exist unique $k in MyDomain with not(foo($k)))
		endpar

default init s0:
	function var_a = 2
	function foo($x in MyDomain) = false
	function fooA = false
	function fooB = false