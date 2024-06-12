asm choose3

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a : MyDomain
	dynamic controlled var_b : MyDomain
	dynamic controlled var_c : MyDomain

definitions:
	domain MyDomain = {1:4}

	rule r_a($x in MyDomain, $y in MyDomain) =
		var_a := $y - $x

	rule r_b($x in MyDomain, $y in MyDomain) =
		var_b := $y - $x

	rule r_c($x in MyDomain, $y in MyDomain) =
		var_c := $y - $x - 1
	
	CTLSPEC ag(var_a = 1 and var_b = 3 and var_c = 2)

	main rule r_Main = 
		par
			choose $z in MyDomain with $z < 1 do
				choose $k in MyDomain with $k > $z + 2 do
					r_a[$z, $k]
				ifnone
					var_a := 3
			ifnone
				var_a := 1
			choose $x in MyDomain with $x < 2 do
				choose $y in MyDomain with $y > $x + 3 do
					r_b[$x, $y]
				ifnone
					var_b := 3
			ifnone
				var_b := 1
			choose $n in MyDomain with $n < 2 do
				choose $m in MyDomain with $m > $n + 2 do
					r_c[$n, $m]
				ifnone
					var_c := 3
			ifnone
				var_c := 1
		endpar
default init s0:
	function var_a = 1
	function var_b = 3
	function var_c = 2	
