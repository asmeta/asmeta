asm caseTerm5
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	enum domain EnumDom = {AA | BB }
	derived foo: EnumDom -> EnumDom
	derived foo3: EnumDom -> EnumDom
	controlled cvar : EnumDom -> EnumDom
	controlled cvar3 : EnumDom -> EnumDom
	
definitions:

	function foo($a in EnumDom) = switch $a
										case AA: BB
										case BB: AA
									endswitch
	function foo3($b in EnumDom) = switch $b
										case AA: AA
										otherwise BB
									endswitch

	
/*	CTLSPEC foo(AA) = BB and foo(BB) = CC and isUndef(foo(CC)) and isUndef(foo(DD))
	CTLSPEC ag(foo(AA) = BB)
	CTLSPEC ax(ag(foo(BB) = BB))
	CTLSPEC ax(ag(foo(CC) = BB))
	CTLSPEC ax(ag(foo(DD) = BB))
	CTLSPEC ag((forall $e in EnumDom with $e !=DD implies foo2($e) = $e))
	CTLSPEC ag(isUndef(foo2(DD)))
	CTLSPEC ag(foo3(AA) = AA and foo3(BB) = BB and foo3(CC) = DD and foo3(DD) = DD)
*/
	main rule r_Main =
		forall $b in EnumDom with true do
		par
			cvar($b) := foo($b) 
			cvar3($b) := foo3($b) 
		endpar
		

default init s0:
   function cvar($x in EnumDom) = AA
   function cvar3($x in EnumDom) = AA
