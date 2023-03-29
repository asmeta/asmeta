asm locationCouldBeRemoved

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled fooA: EnumDom //init but never used
	dynamic controlled fooB: EnumDom //used
	dynamic controlled fooC: EnumDom //never used
	dynamic controlled fooD: EnumDom //init and unreachable code
	dynamic controlled fooE: EnumDom //unreachable code
	dynamic controlled fooF: EnumDom //used
	dynamic controlled fooG: EnumDom //used
	dynamic controlled fooH: EnumDom -> Boolean //fooH(CC) init and unreachable code
	dynamic monitored mon: Boolean //used
	dynamic monitored mon2: EnumDom //never read
	dynamic monitored mon3: Boolean //unreachable code
	dynamic monitored mon4: Boolean //used
	derived der: Boolean
	dynamic monitored inDer: Boolean

definitions:
	function der = inDer
	
	main  rule r_Main =
		par
			fooB := BB
			if(fooB=CC and mon) then
				par
					fooD := BB//init and unreachable code
					fooE := BB//unreachable code
					if(mon3 and mon4) then //unreachable code
						par
							fooF := BB
							fooG := CC
						endpar
					endif
				endpar
			else
				if(mon4) then
					par
						fooF := CC
						fooG := BB
					endpar
				endif
				
			endif
			if(fooH(BB)) then
				fooH(AA) := false
			endif
			if(not(fooH(BB))) then
				fooH(CC) := false
			endif
		endpar

default init s0:
	function fooA = AA 
	function fooB = AA
	function fooD = AA
	function fooF = AA
	function fooH($x in EnumDom) = true
