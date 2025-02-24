asm roulette

import StandardLibrary

signature:
	domain Number subsetof Integer
	domain Money  subsetof Integer
	enum domain Color = {GREEN | RED | BLACK}
	controlled playerMoney: Money
	controlled bancoMoney: Money

	monitored chosenNumber: Number
	static color: Number -> Color

definitions:
	domain Money = {0 : 10}
	domain Number = {0 : 36}

	function color($n in Number) =
		if($n = 0) then
			GREEN
		else
			if(($n mod 2) = 0) then
				RED
			else
				BLACK
			endif
		endif

	rule r_pagaVincita($n in Money) =
		par
			playerMoney := playerMoney + $n
			bancoMoney := bancoMoney - $n
		endpar

	rule r_incassaBanco($n in Money) =
		par
			playerMoney := playerMoney - $n
			bancoMoney := bancoMoney + $n
		endpar

	rule r_giocata =
		choose $i in Number with true do
			if(chosenNumber = $i) then
				r_pagaVincita[2]
			else
				if(color(chosenNumber) = color($i)) then
					r_pagaVincita[1]
				else
					r_incassaBanco[1]
				endif
			endif

	//Nel sistema ci sono sempre globalmente 10 euro
	//CTLSPEC ag(playerMoney + bancoMoney = 10)

	//Se il giocatore perde tutti i soldi non lo recupera piu'
	//CTLSPEC ag((playerMoney = 0 and bancoMoney = 10) implies ag(playerMoney = 0 and bancoMoney = 10))
	
	//CTLSPEC ag((playerMoney = 9 and bancoMoney = 1) implies ag(playerMoney = 9 and bancoMoney = 1))

	main rule r_Main =
		if(playerMoney > 0 and bancoMoney > 1) then
			r_giocata[]
		endif

default init s0:
	function playerMoney = 5
	function bancoMoney = 5