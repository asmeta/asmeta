asm diceWithProbability

import ../../STDL/StandardLibrary
import ../../STDL/CTLLibrary

signature:
	domain Number subsetof Integer
	domain BetDomain subsetof Integer
	domain Money subsetof Integer
	dynamic monitored bet: BetDomain
	dynamic controlled playerBudget: Money
	dynamic controlled bankBudget: Money
	static gain: BetDomain -> Money

definitions:
	domain Number = {1 : 6}
	domain BetDomain = {2 : 12}
	domain Money = {0 : 60}

	function gain($b in BetDomain) =
		switch($b)
			case  2: 6
			case  3: 5
			case  4: 4
			case  5: 3
			case  6: 2
			case  7: 1
			case  8: 2
			case  9: 3
			case 10: 4
			case 11: 5
			case 12: 6
		endswitch

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

	CTLSPEC ag(playerBudget + bankBudget = 40)
	CTLSPEC (forall $x in Money with ef(playerBudget = $x))
	/*CTLSPEC ef(playerBudget = 0)
	CTLSPEC ef(playerBudget = 1)
	CTLSPEC ef(playerBudget = 2)
	CTLSPEC ef(playerBudget = 3)
	CTLSPEC ef(playerBudget = 4)
	CTLSPEC ef(playerBudget = 5)
	CTLSPEC ef(playerBudget = 6)
	CTLSPEC ef(playerBudget = 7)
	CTLSPEC ef(playerBudget = 8)
	CTLSPEC ef(playerBudget = 9)
	CTLSPEC ef(playerBudget = 10)
	CTLSPEC ef(playerBudget = 11)
	CTLSPEC ef(playerBudget = 12)
	CTLSPEC ef(playerBudget = 13)
	CTLSPEC ef(playerBudget = 14)
	CTLSPEC ef(playerBudget = 15)
	CTLSPEC ef(playerBudget = 16)
	CTLSPEC ef(playerBudget = 17)
	CTLSPEC ef(playerBudget = 18)
	CTLSPEC ef(playerBudget = 19)
	CTLSPEC ef(playerBudget = 20)
	CTLSPEC ef(playerBudget = 21)
	CTLSPEC ef(playerBudget = 22)
	CTLSPEC ef(playerBudget = 23)
	CTLSPEC ef(playerBudget = 24)
	CTLSPEC ef(playerBudget = 25)
	CTLSPEC ef(playerBudget = 26)
	CTLSPEC ef(playerBudget = 27)
	CTLSPEC ef(playerBudget = 28)
	CTLSPEC ef(playerBudget = 29)
	CTLSPEC ef(playerBudget = 30)
	CTLSPEC ef(playerBudget = 30)
	CTLSPEC ef(playerBudget = 31)
	CTLSPEC ef(playerBudget = 32)
	CTLSPEC ef(playerBudget = 33)
	CTLSPEC ef(playerBudget = 34)
	CTLSPEC ef(playerBudget = 35)
	CTLSPEC ef(playerBudget = 36)
	CTLSPEC ef(playerBudget = 37)
	CTLSPEC ef(playerBudget = 38)
	CTLSPEC ef(playerBudget = 39)
	CTLSPEC ef(playerBudget = 40)
	CTLSPEC ef(playerBudget = 41)
	CTLSPEC ef(playerBudget = 42)
	CTLSPEC ef(playerBudget = 43)
	CTLSPEC ef(playerBudget = 44)
	CTLSPEC ef(playerBudget = 45)
	CTLSPEC ef(playerBudget = 46)
	CTLSPEC ef(playerBudget = 47)
	CTLSPEC ef(playerBudget = 48)
	CTLSPEC ef(playerBudget = 49)
	CTLSPEC ef(playerBudget = 50)
	CTLSPEC ef(playerBudget = 51)
	CTLSPEC ef(playerBudget = 52)
	CTLSPEC ef(playerBudget = 53)
	CTLSPEC ef(playerBudget = 54)
	CTLSPEC ef(playerBudget = 55)
	CTLSPEC ef(playerBudget = 56)
	CTLSPEC ef(playerBudget = 57)
	CTLSPEC ef(playerBudget = 58)
	CTLSPEC ef(playerBudget = 59)
	CTLSPEC ef(playerBudget = 60)*/
	CTLSPEC (forall $x in Money with ef(bankBudget = $x))
	CTLSPEC eg(playerBudget > 6)

	main rule r_Main =
		if(playerBudget >= 6 and bankBudget >= 6) then
			r_game[]
		endif

default init s0:
	function playerBudget = 30
	function bankBudget = 30
