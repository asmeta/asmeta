asm nestedChoose

import StandardLibrary

signature:
	domain CoinDomain subsetof Integer 
	controlled coins1: CoinDomain

definitions:
	domain CoinDomain = {0 : 20}
		
	main rule r_Main =
		choose $i1 in {-10:10} with $i1 <= 0 do
			choose $i2 in CoinDomain with $i2 >= 5 and $i2 <= 10 do
				choose $i3 in CoinDomain with $i3 >= 5 and $i3 <= 10 do
					coins1 := $i1 + $i2 + $i3
	
default init s0:
	function coins1 = 0
