asm allRules

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	enum domain EnumDom = { AA | BB | CC | DD | EE | FF | GG | HH }
	
	dynamic monitored f1: EnumDom
	dynamic monitored f2: EnumDom
	dynamic controlled dc1: MyDomain
	dynamic controlled dc2: MyDomain
	dynamic controlled dc3: MyDomain
	dynamic monitored dm1: MyDomain
	dynamic monitored dm2: MyDomain
	dynamic monitored dm3: MyDomain
	dynamic monitored bm1: Boolean
	dynamic monitored bm2: Boolean
	dynamic monitored bm3: Boolean
	
definitions:
domain MyDomain = {1:50}
	
	main rule r_Main = 
		par
			if (bm2 and f1 != BB) then 
				dc2 := 21
			endif
			dc3 := 4
			switch(f1)
				case AA: 
					if (bm1) then
						switch(f2)
							case AA: 
								par
									if (not(bm2))then
										if (bm3)then
											par
												dc2 := 31
												dc1 := 50
											endpar
										endif
									else
										dc2 := 21
									endif
									dc3 := 4
								endpar
						endswitch
					endif
				case BB: 
					par
						dc1 := 12
						dc2 := 12
						dc3 := 4
					endpar
				case DD: 
					par
						dc1 := 14
						dc3 := 4
					endpar
				case EE: 
					if (bm1) then
						switch(f2)
							case AA: 
								par
									if (not(bm2))then
										if (bm3)then
											par
												dc2 := 31
												dc1 := 49
											endpar
										endif
									else
										dc2 := 21
									endif
									dc3 := 4
								endpar
						endswitch
					endif
				otherwise
					par
						if (bm3) then
							dc1 :=48
						endif
						dc3 := 4
					endpar
			endswitch
		endpar

default init s0:
	function dc1 = 10
	function dc2 = 30
	function dc3 = 20
	
	
	
	
//bm2 false
//f1: EE
//bm1: true
//f2: AA
//bm3: true
