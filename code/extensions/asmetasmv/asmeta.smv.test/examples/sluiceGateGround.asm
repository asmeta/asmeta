asm sluiceGateGround

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain Minutes subsetof Integer
	enum domain PhaseDomain = { FULLYCLOSED | FULLYOPENED }
	dynamic controlled phase: PhaseDomain
	dynamic monitored passed: Minutes -> Boolean

definitions:
	domain Minutes = {10, 170}
	
	rule r_open =
		skip
	
	rule r_shut =
		skip

	CTLSPEC ag(phase=FULLYCLOSED and passed(170) implies ax(phase = FULLYOPENED))
	CTLSPEC ag(phase=FULLYOPENED and passed(10) implies ax(phase = FULLYCLOSED))

	//proprieta' CTL con la vecchia interpretazione delle monitorate: le monitorate
	//appartengono al nuovo updateSet
	//Tutto quello che segue e' un discorso che ora non e' piu' valido.
	//Lo manteniamo ora solo per documentazione.
	//INIZIO DEL TESTO NON PIU' VALIDO
	/*
	//le seguenti proprieta' non sono verificate.
	//in realta' non sono corrette (non esprimono correttamente il nostro pensiero).
	//La proprieta' in linguaggio naturale e': se phase e' FULLYCLOSED e sono
	//passati 170 secondi, allora nello stato successivo phase e' FULLYOPENED.
	//Nel modello Asm, pero', il valore che ci interessa della monitorata "passed(170)" e'
	//quello dello stato in cui phase vale gia' FULLYOPENED e non quello dello stato
	//precedente in cui valeva ancora FULLYCLOSED. Questo comportamento e' dovuto
	//al fatto che il valore delle monitorate e' settato all'inizio della transizione 
	//di stato ed influenza l'update set delle funzioni controllate.
	//CTLSPEC ag(phase=FULLYCLOSED and passed(170) implies ax(phase = FULLYOPENED))
	//CTLSPEC ag(phase=FULLYOPENED and passed(10) implies ax(phase = FULLYCLOSED))
	//questa e' la forma corretta di esprimere le proprieta'
	CTLSPEC ag(phase=FULLYCLOSED implies
						ax(
							(passed(170) iff phase = FULLYOPENED) and
							(not(passed(170)) iff phase = FULLYCLOSED)
						)
					)
	CTLSPEC ag(phase=FULLYOPENED implies
						ax(
							(passed(10) iff phase = FULLYCLOSED) and
							(not(passed(10)) iff phase = FULLYOPENED)
						)
					)*/
	//FINE DEL TESTO NON PIU' VALIDO

	main rule r_Main =
		par
			if(phase=FULLYCLOSED) then
				if(passed(170)) then
					par
						r_open[]
						phase := FULLYOPENED
					endpar
				endif
			endif
			if(phase=FULLYOPENED) then
				if(passed(10)) then
					par
						r_shut[]
						phase := FULLYCLOSED
					endpar
				endif
			endif
		endpar

default init s0:
	function phase = FULLYCLOSED
