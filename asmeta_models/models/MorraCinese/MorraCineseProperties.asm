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
	enum domain Result = {WINUSER | WINCOMPUTER | PATTA}
	dynamic monitored userChoice: Sign //scelta dell'utente
	dynamic monitored computerChoice: Sign //scelta del computer
	dynamic controlled winner: Result

definitions:

	
// reachability: esistono partite vinte da uno dei due giocatori o patta
CTLSPEC ef(winner = WINUSER)
CTLSPEC ef(winner = WINCOMPUTER)
CTLSPEC ef(winner = PATTA)

// liveness: e' sempre possibile che uno dei due giocatori vinca o che ci sia una patta
CTLSPEC ag(ef(winner = WINUSER))
CTLSPEC ag(ef(winner = WINCOMPUTER))
CTLSPEC ag(ef(winner = PATTA))

// e' patta se e solo se le due giocate sono uguali
CTLSPEC    ag((userChoice = computerChoice) implies ax(winner=PATTA))
// vince WINCOMPUTER se (computerChoice=FORBICE and userChoice=CARTA) or (computerChoice=CARTA and userChoice=SASSO) or (computerChoice=SASSO and userChoice=FORBICE)
CTLSPEC  ag(((computerChoice = CARTA and userChoice = SASSO) or
               (computerChoice = FORBICE and userChoice = CARTA) or
               (computerChoice = SASSO and userChoice = FORBICE) ) implies ax(winner=WINCOMPUTER))
// vince WINUSER se (userChoice=FORBICE and computerChoice=CARTA) or (userChoice=CARTA and computerChoice=SASSO) or (userChoice=SASSO and computerChoice=FORBICE)
CTLSPEC  ag(((userChoice = CARTA and computerChoice = SASSO) or
               (userChoice = FORBICE and computerChoice = CARTA) or
               (userChoice = SASSO and computerChoice = FORBICE) ) implies ax(winner=WINUSER))

//La partita è sempre patta
CTLSPEC eg(winner = PATTA)

/*
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
				winner := WINCOMPUTER
			else
				winner := WINUSER
			 endif 
		endif
		
default init s0:
	function winner = PATTA