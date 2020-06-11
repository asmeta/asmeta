asm enumDom

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {EL_A|EL_B|EL_C}
	dynamic controlled foo1: EnumDom
	dynamic controlled foo2: EnumDom
	
definitions:
	
	main rule r_Main = 
		par
			foo1 := EL_C
			foo2 := EL_A
		endpar
