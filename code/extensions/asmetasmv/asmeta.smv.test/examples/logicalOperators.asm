asm logicalOperators
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	dynamic controlled foo: ConcrDom -> Boolean

definitions:
	domain ConcrDom = {0 : 14}
	
	main rule r_main =
		par
			foo(0) := foo(1) and foo(2)
			foo(3) := foo(4) or foo(5)
			foo(6) := foo(7) xor foo(8)
			foo(9) := foo(10) implies foo(11)
			foo(12) := foo(13) iff foo(14)
		endpar

default init s0:
	function foo($i in ConcrDom) = false 