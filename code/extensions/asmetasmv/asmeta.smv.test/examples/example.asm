asm example
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	enum domain EnumDom = {AA | BB}
	dynamic controlled foo: EnumDom -> Boolean
	dynamic controlled foo2: Prod(Boolean, EnumDom) -> Boolean

definitions:

	CTLSPEC ag(foo(AA))
	CTLSPEC ag(foo2(true, BB))

	main rule r_Main =
		foo(AA) := true

default init s0:
	function foo($e in EnumDom) = true