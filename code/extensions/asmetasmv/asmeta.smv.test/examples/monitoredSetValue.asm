asm monitoredSetValue

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB}
	dynamic monitored mon: EnumDom
	dynamic monitored monA: Boolean
	dynamic monitored monB: Boolean
	
	
definitions:
	
	rule r_AA =
		if(monA) then
			skip
		endif

	rule r_BB =
		if(monB) then
			skip
		endif

	main rule r_Main =
		switch(mon)
			case AA:
				r_AA[]
			case BB:
				r_BB[]
		endswitch

default init s0: