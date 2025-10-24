asm MorraCinese_raff2

//Morra cinese
//Lo scopo del gioco e' sconfiggere l'avversario scegliendo un segno
//in grado di battere quello dell'altro, secondo le seguenti regole:
//1. Il sasso spezza le forbici (vince il sasso)
//2. Le forbici tagliano la carta (vincono le forbici)
//3. La carta avvolge il sasso (vince la carta)
//Se i due giocatori scelgono la stessa arma, il gioco e' pari
//e si gioca di nuovo.

//Questo modello ASM permette di giocare a morra cinese contro il computer.

//E' il secondo raffinamento del modello MorraCinese.asm (raffinamento di
//MorraCinese_raff1.asm): permette impostare il numero massimo di partite che
//devono essere giocate.

import ../../STDL/StandardLibrary

signature:
	enum domain Sign = {CARTA | FORBICE | SASSO}
	enum domain Result = {WINFIRST | WINSECOND | PATTA}
	dynamic monitored userChoice: Sign //scelta dell'utente
	dynamic controlled computerChoice: Sign //scelta del computer

	dynamic controlled userWins: Natural //numero di vittorie dell'utente
	dynamic controlled computerWins: Natural //numero di vittorie del computer

	dynamic controlled numberOfPlays: Natural
	dynamic controlled maxNumberOfPlays: Natural
	dynamic monitored maxNumberOfPlaysInit: Natural

	dynamic controlled outMess: String //stampa a video del risultato della partita
	static playResult: Prod(Sign, Sign) -> Result //data una coppia di giocate, ritorna il risultato

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

	main rule r_Main =
		if(numberOfPlays < maxNumberOfPlays) then
			par
				//il computer sceglie in modo nondeterministico un segno
				choose $s in Sign with true do
					par
						computerChoice := $s //viene memorizzato il segno solo per vedere la giocata del computer nell'update set 
						switch(playResult(userChoice, $s))
							case WINFIRST:
								par
									outMess := "Hai vinto!"
									userWins := userWins + 1n
								endpar
							case WINSECOND:
								par
									outMess := "Ha vinto il computer."
									computerWins := computerWins + 1n
								endpar
							case PATTA:
								outMess := "Patta."
						endswitch
					endpar
				numberOfPlays := numberOfPlays + 1n
			endpar
		else
			outMess := "Fine partite."
		endif

default init s0:
	function outMess = ""
	function userWins = 0n
	function computerWins = 0n
	function numberOfPlays = 0n
	function maxNumberOfPlays = maxNumberOfPlaysInit