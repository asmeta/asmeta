asm MorraCineseProperties

//Morra cinese 
//Lo scopo del gioco e' sconfiggere l'avversario scegliendo un segno
//in grado di battere quello dell'altro, secondo le seguenti regole:
//1. Il sasso spezza le forbici (vince il sasso)
//2. Le forbici tagliano la carta (vincono le forbici)
//3. La carta avvolge il sasso (vince la carta)
//Se i due giocatori scelgono la stessa arma, il gioco e' pari
//e si gioca di nuovo.

//Questo modello ASM permette di giocare a morra cinese contro il computer.

import ../../STDL/StandardLibrary
import ../../STDL/CTLLibrary
signature:
	enum domain Sign = {CARTA | FORBICE | SASSO}
	enum domain Result = {WINFIRST | WINSECOND | PATTA}
	dynamic monitored userChoice: Sign //scelta dell'utente
	dynamic monitored computerChoice: Sign //scelta del computer
	dynamic controlled winner: Result

definitions:

	
// reachability
// esistono partite vinte da WINFIRST
CTLSPEC ef(winner = WINFIRST)
// esistono partite vinte da WINSECOND
CTLSPEC ef(winner = WINSECOND)
// esistono partite PATTA
CTLSPEC ef(winner = PATTA)

// liveness: e' sempre possibile che uno dei due giocatori vinca o che ci sia una patta
CTLSPEC ag(ef(winner = WINFIRST))
CTLSPEC ag(ef(winner = WINSECOND))
CTLSPEC ag(ef(winner = PATTA))
/*


-- verificano la correttezza del modello
-- e' patta se e solo se le due giocate sono uguali; i giocatori A e B vincono
-- solo sotto determinate condizioni
CTLSPEC    AG((choicePlayerA = choicePlayerB) <-> (winner=PATTA));
CTLSPEC    AG( (  (choicePlayerA = CARTA & choicePlayerB = SASSO) |
               (choicePlayerA = FORBICE & choicePlayerB = CARTA) |
               (choicePlayerA = SASSO & choicePlayerB = FORBICE) ) <-> (winner=PLAYER_A));
CTLSPEC    AG( (  (choicePlayerB = CARTA & choicePlayerA = SASSO) |
               (choicePlayerB = FORBICE & choicePlayerA = CARTA) |
               (choicePlayerB = SASSO & choicePlayerA = FORBICE) ) <-> (winner=PLAYER_B));

-- verifica dell'esistenza di path particolari
CTLSPEC    EG(winner = PATTA); -- sempre patta
CTLSPEC    EX(EG(winner = PLAYER_A)); -- vince sempre PLAYER_A (EX perche' nello stato iniziale e' PATTA)
CTLSPEC    EX(EG(winner = PLAYER_B)); -- vince sempre PLAYER_B (EX perche' nello stato iniziale e' PATTA)
*/
	main rule r_Main = 
		if(computerChoice=userChoice) then //tre casi
					winner := PATTA
		else
			if ((computerChoice=FORBICE and userChoice=CARTA) or (computerChoice=CARTA and userChoice=SASSO) or (computerChoice=SASSO and userChoice=FORBICE)) then
				winner := WINFIRST
			else
				winner := WINSECOND
			 endif 
		endif
default init s0: