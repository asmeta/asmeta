asm MorraCineseWithAgents

//Morra cinese (Wikipedia)
//Lo scopo del gioco e' sconfiggere l'avversario scegliendo un segno
//in grado di battere quello dell'altro, secondo le seguenti regole:
//1. Il sasso spezza le forbici (vince il sasso)
//2. Le forbici tagliano la carta (vincono le forbici)
//3. La carta avvolge il sasso (vince la carta)
//Se i due giocatori scelgono la stessa arma, il gioco e' pari
//e si gioca di nuovo.

//Questo modello ASM mostra una partita tra due giocatori (agenti) simulati dal
//computer. Nessuna interazione con l'utente e' richiesta.

//Versione con agenti

import ../../STDL/StandardLibrary

signature:
	domain Player subsetof Agent
	enum domain Sign = {CARTA | FORBICE | SASSO}
	enum domain Result = {WINFIRST | WINSECOND | PATTA}
	dynamic controlled choice: Player -> Sign //scelta del player
	dynamic controlled outMess: String //stampa a video del risultato della partita
	static playResult: Prod(Sign, Sign) -> Result //data una coppia di giocate, ritorna il risultato

	static playerA: Player
	static playerB: Player

definitions:

	//La funzione e' completamente specificata.
	//Infatti, tutte le 9 combinazioni sono considerate.
	function playResult($s1 in Sign, $s2 in Sign) =
		if($s1=$s2) then //tre casi
			PATTA
		else
			switch($s1, $s2)
				case (FORBICE, CARTA):
					WINFIRST
				case (CARTA, SASSO):
					WINFIRST
				case (SASSO, FORBICE):
					WINFIRST
				otherwise //i tre casi rimanenti
					WINSECOND
			endswitch
		endif

	rule r_play =
		choose $s in Sign with true do
			choice(self) := $s

	main rule r_Main =
		seq //Prima i giocatori devono fare la giocata. Poi, la giocata deve essere valutata.
			par
				program(playerA)
				program(playerB)
			endpar
			switch(playResult(choice(playerA), choice(playerB)))
				case WINFIRST:
					outMess := "Ha vinto il player A."
				case WINSECOND:
					outMess := "Ha vinto il player B."
				case PATTA:
					outMess := "Patta."
			endswitch
		endseq

default init s0:
	function outMess = ""

	agent Player:
		r_play[]
