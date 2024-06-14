asm giocoDelleTreScatole

import ../STDL/StandardLibrary
import ../STDL/CTLLibrary

signature:
	domain Scatola subsetof Integer
	domain Money subsetof Integer
	dynamic monitored scatolaScelta1: Scatola
	dynamic monitored scatolaScelta2: Scatola
	dynamic controlled playerBudget: Money
	dynamic controlled bancoBudget: Money

definitions:
	domain Scatola = {1 : 3}
	domain Money = {0 : 20}

	rule r_game =
		choose $s1 in Scatola, $s2 in Scatola with true do
			if ($s1 = scatolaScelta1 and $s2 = scatolaScelta2) or
			   ($s1 = scatolaScelta2 and $s2 = scatolaScelta1) then
				par
					playerBudget := playerBudget + 2
					bancoBudget := bancoBudget - 2
				endpar
			else if ($s1 = scatolaScelta1 or $s2 = scatolaScelta2 or
			         $s1 = scatolaScelta2 or $s2 = scatolaScelta1) then
				par
					playerBudget := playerBudget + 1
					bancoBudget := bancoBudget - 1
				endpar
			else
				par
					playerBudget := playerBudget - 1
					bancoBudget := bancoBudget + 1
				endpar
			endif endif

	//tra il giocatore e il banco ci sono sempre complessivamente 20 euro
	CTLSPEC ag(playerBudget + bancoBudget = 20)

	//esiste un cammino in cui il giocatore ha sempre tra i 5 e i 15 euro
	CTLSPEC eg(playerBudget >= 5 and playerBudget <= 15)

	//se il giocatore perde tutti i soldi, non puo' piu riguadagnarli
	CTLSPEC ag(playerBudget = 0 implies ag(playerBudget = 0))
	//se il manco  perde tutti i soldi, non puo' piu riguadagnarli
	CTLSPEC ag(bancoBudget = 0 implies ag(bancoBudget = 0))

	main rule r_Main =
		if(playerBudget >= 1 and bancoBudget >= 2) then
			r_game[]
		endif

default init s0:
	function playerBudget = 10
	function bancoBudget = 10
