asm coffeeVendingMachine

import ../../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain CoinType = {HALF | ONE}
	enum domain Product = {COFFEE | TEA | MILK}
	domain QuantityDomain subsetof Integer	
	domain CoinDomain subsetof Integer 
	controlled available: Product -> QuantityDomain
	controlled coins: CoinDomain
	controlled nCall: Integer
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
				choose $p in {COFFEE, MILK, TEA} with $p != MILK and available($p) > 0 do
					par
						r_serveProduct[$p]
						nCall := nCall + 1 // Added to properly test update rule coverage
					endpar
			endif
		endif

default init s0:
	function nCall = 0
	function coins = 0
	function available($p in Product) = 10
