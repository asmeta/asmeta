asm notConsistent
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic monitored mon: Boolean
	dynamic monitored mon2: Boolean
	dynamic controlled foo: EnumDom

definitions:
	main  rule r_Main =
		par
			if(mon != mon2) then
				foo := AA
			endif
			if(mon2 != mon) then
				foo := BB
			endif
		endpar
