asm CoffeeVendingMachine

import ./STDL/StandardLibrary
import ./STDL/CTLLibrary

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

	CTLSPEC ef(available(MILK) = 0 and available(COFFEE) = 9 and available(TEA) = 0)

	CTLSPEC ag(available(MILK) = 0 implies ag(available(MILK) = 0))
	CTLSPEC ag(available(TEA) = 0 implies ag(available(TEA) = 0))
	CTLSPEC ag(available(COFFEE) = 0 implies ag(available(COFFEE) = 0))
	CTLSPEC (forall $p in Product with ag(available($p) = 0 implies ag(available($p) = 0)))

	CTLSPEC ag(available(COFFEE) + available(TEA) + available(MILK) >= 5)

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