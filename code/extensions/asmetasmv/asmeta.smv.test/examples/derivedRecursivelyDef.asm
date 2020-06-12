asm derivedRecursivelyDef
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	derived fooG: Boolean
	dynamic controlled fooH: Boolean
	
definitions:
	function fooG = fooH

	//AsmetaL invariant
	invariant over fooG: fooG = fooH

	CTLSPEC ag(fooG=fooH)	

	main rule r_Main =
		if(fooG) then
			fooH := false
		else
			fooH := true
		endif

default init s0:
	function fooH = true