asm diceNoProbability

import ../../STDL/StandardLibrary
import ../../STDL/CTLLibrary

signature:
	domain Number subsetof Integer
	domain BetDomain subsetof Integer
	domain Money subsetof Integer
	dynamic monitored bet: BetDomain
	dynamic controlled playerBudget: Money
	dynamic controlled bankBudget: Money
	derived gain: BetDomain -> Money

definitions:
	domain Number = {1 : 6}
	domain BetDomain = {2 : 12}
	domain Money = {0 : 10}

	function gain($b in BetDomain) =
		if($b < 5 or $b > 9) then
			2
		else
			1
		endif

	rule r_game =
		choose $x in Number, $y in Number with true do
			if ($x + $y = bet) then
				par
					playerBudget := playerBudget + gain($x + $y)
					bankBudget := bankBudget - gain($x + $y) 
				endpar
			else
				par
					playerBudget := playerBudget - gain($x + $y)
					bankBudget := bankBudget + gain($x + $y) 
				endpar
			endif

	//nel sistema ci sono sempre globalmente 10 euro
	CTLSPEC ag(playerBudget + bankBudget = 10)
	//il saldo del giocatore puo' assumere un qualsiasi valore nell'intervallo [0, 10]
	CTLSPEC (forall $x in Money with ef(playerBudget = $x))
	//il saldo del banco puo' assumere un qualsiasi valore nell'intervallo [0, 10]
	CTLSPEC (forall $x in Money with ef(bankBudget = $x))
	//esiste un cammino in cui il saldo del giocatore e' sempre maggiore od uguale a 2 euro
	CTLSPEC eg(playerBudget >= 2)

	main rule r_Main =
		if(playerBudget >= 2 and bankBudget >= 2) then
			r_game[]
		endif

default init s0:
	function playerBudget = 5
	function bankBudget = 5
