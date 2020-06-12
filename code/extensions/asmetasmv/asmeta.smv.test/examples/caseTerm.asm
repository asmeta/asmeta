asm caseTerm
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	enum domain EnumDom = {AA | BB | CC | DD}
	dynamic controlled foo: EnumDom -> EnumDom
	dynamic controlled foo2: EnumDom -> EnumDom
	dynamic controlled foo3: EnumDom -> EnumDom
	
definitions:
	
	CTLSPEC foo(AA) = BB and foo(BB) = CC and isUndef(foo(CC)) and isUndef(foo(DD))
	CTLSPEC ag(foo(AA) = BB)
	CTLSPEC ax(ag(foo(BB) = BB))
	CTLSPEC ax(ag(foo(CC) = BB))
	CTLSPEC ax(ag(foo(DD) = BB))
	CTLSPEC ag((forall $e in EnumDom with $e !=DD implies foo2($e) = $e))
	CTLSPEC ag(isUndef(foo2(DD)))
	CTLSPEC ag(foo3(AA) = AA and foo3(BB) = BB and foo3(CC) = DD and foo3(DD) = DD)

	main rule r_Main =
		par
			forall $e in EnumDom with true do
				foo($e) := BB
			forall $a in EnumDom with $a != DD do
				foo2($a) := $a
			forall $b in EnumDom with true do
				foo3($b) := foo3($b)
		endpar

default init s0:
	function foo($a in EnumDom) = switch $a
										case AA: BB
										case BB: CC
									endswitch
	function foo2($e in EnumDom) = switch $e
										case AA: AA
										case BB: BB
										case CC: CC
									endswitch
	function foo3($b in EnumDom) = switch $b
										case AA: AA
										case BB: BB
										otherwise DD
									endswitch