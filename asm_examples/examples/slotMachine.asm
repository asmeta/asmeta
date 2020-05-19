asm slotMachine

import ../STDL/StandardLibrary
import ../STDL/CTLlibrary

signature:
	enum domain Sign = {BAR | CHERRY | DOLLAR}
	domain BetDomain subsetof Integer
	domain Money subsetof Integer
	domain MultDomain subsetof Integer
	dynamic monitored insertedMoney: BetDomain
	dynamic controlled playerBudget: Money
	dynamic controlled slotMachineBudget: Money
	derived multFactor: Sign -> MultDomain

definitions:
	domain MultDomain = {1 : 3}
	domain BetDomain = {1 : 3}
	domain Money = {0 : 100}

	function multFactor($s in Sign) =
		switch($s)
			case BAR: 1
			case CHERRY: 2
			case DOLLAR: 3
		endswitch

	rule r_game =
		choose $x in Sign, $y in Sign, $z in Sign with true do
			if ($x = $y and $y = $z) then
				par
					playerBudget := playerBudget + multFactor($x)*insertedMoney
					slotMachineBudget := slotMachineBudget - multFactor($x)*insertedMoney 
				endpar
			else
				par
					playerBudget := playerBudget - insertedMoney
					slotMachineBudget := slotMachineBudget + insertedMoney 
				endpar
			endif

	//complessivamente, tra i soldi del giocatore e quelli della slot machine, ci sono sempre 100 euro
	CTLSPEC ag(playerBudget + slotMachineBudget = 100)
	//esiste uno stato in cui il giocatore ha 23 euro e riesce a vincere 9 euro, raggiungendo un saldo di 32 euro
	CTLSPEC ef(playerBudget = 23 and ex(playerBudget = 32))
	//esiste un cammino in cui il saldo del giocatore e' sempre compreso tra 40 e 60 euro
	CTLSPEC eg(playerBudget >= 40 and playerBudget <= 60)

	main rule r_Main =
		if(playerBudget >= 3 and slotMachineBudget >= 9) then
			r_game[]
		endif

default init s0:
	function playerBudget = 50
	function slotMachineBudget = 50
