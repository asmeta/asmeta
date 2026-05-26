asm existTerm2

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
	domain MyDomain = {1:2}

	main rule r_Main =
		par
			if(exists $x in MyDomain with foo(foo($x))=foo(2)) then
				var_a := 3
			else
				var_a := 1
			endif
			forall $k in MyDomain with true do
				choose $j in MyDomain with true do
					foo($k) := $j
			foo1 := (exists $q in MyDomain with foo(foo($x))=1)
			foo2 := (exists $w in MyDomain with foo(foo($w))=foo(2))
			foo3 := (exists $e in MyDomain with foo(foo($e))=foo($e))
		endpar

default init s0:
	function var_a = 3
	function foo($i in MyDomain) = 1
	function foo1 = true
	function foo2 = false
	function foo3 = false
