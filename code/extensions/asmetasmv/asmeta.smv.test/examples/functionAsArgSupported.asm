asm functionAsArgSupported
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic monitored monArg: EnumDom
	dynamic controlled contrArg: EnumDom
	dynamic controlled foo: EnumDom -> Boolean
	dynamic controlled foo2: EnumDom -> Boolean

definitions:

	CTLSPEC (forall $x in EnumDom with ag(monArg = $x implies ax(foo($x))))
	CTLSPEC (forall $x in EnumDom with ag(contrArg = $x implies ax(foo2($x))))

	main  rule r_Main =
		par
			contrArg := AA
			let ($x = monArg, $y = contrArg) in
				par
					foo($x) := true //Supported by AsmetaSMV
					foo2($y) := true //Supported by AsmetaSMV
				endpar
			endlet
		endpar

default init s0:
	function contrArg = BB
	function foo($x in EnumDom) = false
	function foo2($x in EnumDom) = false