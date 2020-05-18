asm stackConds
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic monitored mon: Boolean
	dynamic monitored mon2: Boolean
	dynamic controlled contr: EnumDom
	dynamic controlled contr1: EnumDom

definitions:
	main  rule r_Main =
		par
			contr1 := AA
			if(mon) then
				if(mon2) then
					contr := BB
				else
					contr := AA
				endif
			endif
		endpar
