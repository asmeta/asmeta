asm SemaforiAttraversamentoPedonale

import ../STDL/StandardLibrary
import ../STDL/CTLLibrary

signature:
	enum domain StatoSemaforo = {ROSSO | VERDE | LAMPEGGIANTE}
	controlled semaforoMacchine: StatoSemaforo
	controlled semaforoPedoni: StatoSemaforo
	monitored prenotazione: Boolean

definitions:

	rule r_impostaSemafori($semM in StatoSemaforo, $semC in StatoSemaforo) =
		par
			semaforoMacchine := $semM
			semaforoPedoni := $semC
		endpar

	//esiste uno stato in cui i due semafori sono lampeggianti
	CTLSPEC ef(semaforoMacchine = LAMPEGGIANTE and semaforoPedoni = LAMPEGGIANTE)
	//esiste uno stato a partire dal quale inizia un cammino in cui di due semafori
	//non sono mai lampeggianti
	CTLSPEC ef(eg(semaforoMacchine != LAMPEGGIANTE and semaforoPedoni != LAMPEGGIANTE))
	//se il semaforo della macchine e' rosso quello dei pedoni deve
	//essere verde
	CTLSPEC ag(semaforoMacchine = ROSSO implies semaforoPedoni = VERDE)
	CTLSPEC ag(semaforoMacchine = VERDE implies semaforoPedoni = ROSSO)
	CTLSPEC ag(semaforoPedoni = ROSSO implies semaforoMacchine = VERDE)
	CTLSPEC ag(semaforoPedoni = VERDE implies semaforoMacchine = ROSSO)

	main rule r_Main =
		choose $b in Boolean with true do
			if $b then
				r_impostaSemafori[LAMPEGGIANTE, LAMPEGGIANTE]
			else
				if prenotazione then
					r_impostaSemafori[ROSSO, VERDE]
				else
					r_impostaSemafori[VERDE, ROSSO]
				endif
			endif

default init s0:
	function semaforoMacchine = VERDE
	function semaforoPedoni = ROSSO