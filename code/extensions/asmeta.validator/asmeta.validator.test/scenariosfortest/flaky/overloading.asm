asm overloading

import StandardLibrary

signature:
	enum domain Prova_Enum = {ENUM1 | ENUM2} 
	domain CoinDomain subsetof Integer 
	controlled coins1: CoinDomain
	controlled coins2: CoinDomain
	controlled coins3: CoinDomain

definitions:
	domain CoinDomain = {0 : 20}
	
	rule r_chooseRandom($b in Boolean) = 
		choose $i1 in CoinDomain with $i1 > 0 do
					coins1 := $i1
					
	rule r_chooseRandom($i in Integer, $en in Prova_Enum) = 
		choose $i1 in CoinDomain with $i1 > 0 do
					coins2 := $i1
					
	rule r_chooseRandom = 
		choose $i1 in CoinDomain with $i1 > 0 do
					coins3 := $i1
					
	main rule r_Main =
		par
			r_chooseRandom[true]
			r_chooseRandom[5, ENUM1]
			r_chooseRandom[]
		endpar
		
default init s0:
	function coins1 = 0
	function coins2 = 0
	function coins3 = 0
