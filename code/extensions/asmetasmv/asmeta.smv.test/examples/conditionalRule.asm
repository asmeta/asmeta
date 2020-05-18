asm conditionalRule
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled guard: EnumDom
	dynamic controlled foo: EnumDom
	
definitions:
	
	main rule r_Main = 
		if(guard = CC) then
			foo := AA
		else
			foo := BB
		endif

default init s0:
	function guard = CC
