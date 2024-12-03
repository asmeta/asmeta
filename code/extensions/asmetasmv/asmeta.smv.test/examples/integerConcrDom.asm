asm integerConcrDom
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain ConcrDom subsetof Integer
	controlled foo: Boolean
	controlled fooA: ConcrDom
	controlled fooB: ConcrDom
	derived der: ConcrDom -> ConcrDom
	derived der2: Prod(ConcrDom, ConcrDom) -> ConcrDom

definitions:
	domain ConcrDom = {-10 : 10}

	function der($c in ConcrDom) = ($c + 1) mod 10

	function der2($c in ConcrDom, $g in ConcrDom) = ($c * $g) mod 10

	CTLSPEC ag(foo)

	main rule r_main =
		par
			foo := (exist $c in ConcrDom with $c > 5)
			fooA := der(5)
			fooB := der2(-5, 5)
		endpar

default init s0:
	function foo = true