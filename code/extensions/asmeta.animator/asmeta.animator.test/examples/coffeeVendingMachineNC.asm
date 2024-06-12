asm coffeeVendingMachineNC

//Una macchinetta automatica dispensa caffe', te' e latte. La macchinetta accetta solo
//monete da 50 centesimi e da 1 euro. Se viene inserita una moneta da 50 centesimi, la
//macchinetta dispensa latte (se disponibile); se viene inserita una moneta da 1 euro,
//invece, la macchinetta decide in modo casuale di dispensare caffe' o te' (se disponibili).
//Se viene dispensata una bevanda, la sua disponibilita' viene decrementata e la moneta
//viene conservata nella macchinetta.
//Nel modello ASM ogni passo di macchina corrisponde all'inserimento di una moneta
//e all'erogazione di una bevanda corrispondente.
//La macchina all'inizio contiene 10 unita' per ogni bevanda; l'atto di erogazione di
//una bevanda corrisponde alla diminuzione di un'unita' della disponibilita' della stessa
//e alla conservazione della moneta (nelle monete conservate, non bisogna distinguere
//tra monete da 50 centesimi ed 1 euro). Se la bevanda non e' disponibile, non viene
//erogata e la moneta non viene conservata.
//La macchina puo' contenere al massimo 25 monete; quando la macchina e' piena di
//monete, non accetta altre monete e, quindi, non eroga pie' alcuna bevanda. All'inizio
//la macchinetta non contiene alcuna moneta.
//L'utente del sistema decide ad ogni passo di simulazione il tipo di moneta da inserire.


import STDL/StandardLibrary

import STDL/CTLLibrary


signature:
	enum domain CoinType = {HALF | ONE}
	enum domain Product = {COFFEE | TEA | MILK}
	domain QuantityDomain subsetof Integer	
	domain CoinDomain subsetof Integer 
	controlled available: Product -> QuantityDomain
	controlled coins: CoinDomain
	monitored insertedCoin: CoinType
	monitored chosenProduct: Product

definitions:
	domain QuantityDomain = {0 : 10}
	domain CoinDomain = {0 : 25}

	rule r_serveProduct($p in Product) =
		par
			available($p) := available($p) - 1
			coins := coins + 1
		endpar

	//nella macchinetta di sono sempre almeno k unita' di prodotto. Quanto vale questo k?
	invariant over coins: coins=15 implies available(MILK)+available(COFFEE)+available(TEA)=28

	main rule r_Main =
		if(coins < 25) then
			if(insertedCoin = HALF) then
				if(available(MILK) > 0) then
					r_serveProduct[MILK]
				endif
			else
			// if chosenProduct != MILK and available(chosenProduct)>0 then
			//	r_serveProduct[chosenProduct]
			//endif
				if chosenProduct != MILK then
					if (chosenProduct = TEA and available(TEA)>0) then
						r_serveProduct[TEA]
					else
						if (chosenProduct = COFFEE and available(COFFEE)>0) then
							r_serveProduct[COFFEE]
						endif
					endif
				endif
			endif
		endif

default init s0:
	function coins = 0
	function available($p in Product) = 10
