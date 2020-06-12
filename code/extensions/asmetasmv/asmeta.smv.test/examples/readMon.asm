asm readMon
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB}
	dynamic monitored monB: Boolean
	dynamic monitored monE: EnumDom

definitions:

	main rule r_main =
			if(monB) then
				if(monE = AA) then
					skip
				endif
			endif