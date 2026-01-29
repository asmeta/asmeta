asm coffeeVendingMachine

import ../StandardLibrary

signature:
	enum domain CoinType = {HALF | ONE}
	enum domain Product = {COFFEE | TEA | MILK}
	domain QuantityDomain subsetof Integer	
	domain CoinDomain subsetof Integer 
	controlled available: Product -> QuantityDomain
	controlled coins: CoinDomain
	monitored insertedCoin: CoinType

definitions:
	domain QuantityDomain = {0 : 10}
	domain CoinDomain = {0 : 25}

	rule r_serveProduct($p in Product) =
		par
			available($p) := available($p) - 1
			coins := coins + 1
		endpar

	main rule r_Main =
		if(coins < 25) then
			if(insertedCoin = HALF) then
				if(available(MILK) > 0) then
					r_serveProduct[MILK]
				endif
			else
				choose $p in Product with $p != MILK and available($p) > 0 do
					r_serveProduct[$p]
			endif
		endif

default init s0:
	function coins = 0
	function available($p in Product) = 10
