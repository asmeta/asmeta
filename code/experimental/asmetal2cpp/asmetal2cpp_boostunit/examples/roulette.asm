asm roulette

import STDL/StandardLibrary

signature:
	domain Number subsetof Integer
	domain Money  subsetof Integer
	enum domain Color = {GREEN | RED | BLACK}
	controlled playerMoney: Money
	controlled houseMoney: Money

	monitored chosenNumber: Number
	derived color: Number -> Color

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

	rule r_payWin($n in Money) =
		par
			playerMoney := playerMoney + $n
			houseMoney := houseMoney - $n
		endpar

	rule r_houseWin($n in Money) =
		par
			playerMoney := playerMoney - $n
			houseMoney := houseMoney + $n
		endpar

	rule r_play =
		choose $i in Number with true do
			if(chosenNumber = $i) then
				r_payWin[2]
			else
				if(color(chosenNumber) = color($i)) then
					r_payWin[1]
				else
					r_houseWin[1]
				endif
			endif

	
	main rule r_Main =
		if(playerMoney > 0 and houseMoney > 1) then
			r_play[]
		endif

default init s0:
	function playerMoney = 5
	function houseMoney = 5
