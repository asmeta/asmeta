asm conditionalTerm
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	enum domain EnumDom = {AA | BB | CC | DD}
	dynamic controlled foo: EnumDom -> EnumDom
	
definitions:
	
	CTLSPEC ag(foo(AA) = BB)
	CTLSPEC isUndef(foo(BB)) and isUndef(foo(CC)) and isUndef(foo(DD))
	CTLSPEC ax(ag(foo(BB) = BB and foo(CC) = BB and foo(DD) = BB))

	main rule r_Main = 
		forall $a in EnumDom with true do
			foo($a) := BB

default init s0:
	function foo($a in EnumDom) = if $a = AA then
										BB
									endif