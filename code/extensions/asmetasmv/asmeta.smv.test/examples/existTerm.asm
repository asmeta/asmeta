asm existTerm

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a : MyDomain
	dynamic controlled foo: MyDomain -> MyDomain
	dynamic controlled foo1: Boolean
	dynamic controlled foo2: Boolean
	dynamic controlled foo3: Boolean

definitions:
	domain MyDomain = {1:3}

	CTLSPEC ag(var_a = 3)
	CTLSPEC ag((exists $x in MyDomain with foo($x)=1) iff ax(foo1))
	CTLSPEC ag((exists $x in MyDomain with foo($x)=2) iff ax(foo2))
	CTLSPEC ag((exists $x in MyDomain with foo($x)=3) iff ax(foo3))

	main rule r_Main =
		par
			if(exists $x in MyDomain with $x=2) then
				var_a := 3
			else
				var_a := 1
			endif
			forall $k in MyDomain with true do
				choose $j in MyDomain with true do
					foo($k) := $j
			foo1 := (exists $q in MyDomain with foo($q)=1)
			foo2 := (exists $w in MyDomain with foo($w)=2)
			foo3 := (exists $e in MyDomain with foo($e)=3)
		endpar

default init s0:
	function var_a = 3
	function foo($i in MyDomain) = 1
	function foo1 = true
	function foo2 = false
	function foo3 = false
