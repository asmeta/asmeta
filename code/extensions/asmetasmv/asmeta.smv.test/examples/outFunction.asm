//utilizzo di out functions
asm outFunction
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic monitored mon: Boolean
	dynamic monitored mon2: Boolean
	dynamic out outFoo: EnumDom
	dynamic out outFoo1: EnumDom
	dynamic out outFoo2: EnumDom
	dynamic out outFoo3: EnumDom

definitions:

	main  rule r_Main =
		par
			outFoo := AA
			if(mon) then
				outFoo1 := AA
			else
				outFoo1 := BB
			endif
			if(outFoo1 = BB) then
				outFoo2 := CC
			else
				outFoo2 := AA
			endif
			outFoo3 := outFoo2
		endpar

default init s0:
	function outFoo = CC
	function outFoo1 = CC
	function outFoo2 = CC
	function outFoo3 = outFoo2