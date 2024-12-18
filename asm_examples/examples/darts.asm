asm darts

import ../STDL/StandardLibrary
import ../STDL/CTLLibrary

signature:
	enum domain Area = {RED | GREEN | OUT}
	enum domain Dir = {UP | DOWN}
	enum domain Speed = {FAST | SLOW}
	domain Budget subsetof Integer
	domain Gain subsetof Integer
	monitored dir: Dir
	monitored speed: Speed
	controlled barBudget: Budget
	controlled playerBudget: Budget
	static gain: Area -> Gain

definitions:
	domain Gain = {-1, 1, 2}
	domain Budget = {0 : 20}

	function gain($a in Area) =
		switch($a)
			case RED: 2
			case GREEN: 1
			otherwise -1
		endswitch

	rule r_pay($a in Area) =
		par
			playerBudget := playerBudget + gain($a)
			barBudget := barBudget - gain($a)
		endpar

	//esiste uno stato in cui il giocatore ha 5 euro ed il bar ha 15 euro
	CTLSPEC ef(playerBudget = 5 and barBudget = 15)
	//esiste uno stato in cui il giocatore ha 9 euro e, dopo aver fatto
	//il lancio, vince 2 euro
	CTLSPEC ef(playerBudget = 9 and ex(playerBudget = 11))
	//se il giocatore non ha piu' soldi, non li riguadagna piu'
	CTLSPEC ag(playerBudget = 0 implies ag(playerBudget = 0))

	main rule r_Main =
		if(playerBudget >= 1 and barBudget >= 2) then
			if(speed = SLOW) then
				r_pay[OUT]
			else
				if(dir = DOWN) then
					r_pay[GREEN]
				else
					choose $a in Area with $a != OUT do
						r_pay[$a]
				endif
			endif
		endif

default init s0:
	function barBudget = 10
	function playerBudget = 10
