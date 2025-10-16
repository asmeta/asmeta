asm CoffeeVendingMachineRefined
import ../StandardLibrary

signature:
	enum domain Product = {COFFEE | TEA | MILK}
	domain QuantityDomain subsetof Integer	
	domain CoinDomain subsetof Integer 
	domain InputCoinDomain subsetof Integer
	controlled available: Product -> QuantityDomain
	controlled coins: CoinDomain
	controlled dispensed: Product
	monitored insertedCoin: InputCoinDomain
	static price: Product -> InputCoinDomain

definitions:
	domain QuantityDomain = {0 : 10}
	domain InputCoinDomain = {1 : 2}
	domain CoinDomain = {0 : 25}

	function price($prod in Product) = switch $prod
			case COFFEE: 2
			case TEA: 2
			case MILK: 1
		endswitch

	rule r_serveProduct($p in Product) =
		par
			dispensed := $p
			available($p) := available($p) - 1
			coins := coins + price($p)
		endpar

	main rule r_Main =
		if(coins < 25) then
			choose $p in Product with price($p) = insertedCoin and available($p) > 0 do
				r_serveProduct[$p]
			ifnone
				dispensed := undef
		endif

default init s0:
	function coins = 0
	function dispensed = undef
	function available($p in Product) = 10
