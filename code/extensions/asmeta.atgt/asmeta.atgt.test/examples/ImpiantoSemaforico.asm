asm ImpiantoSemaforico

import StandardLibrary

signature:

	enum domain Colore = {ROSSO, GIALLO, VERDE}
	enum domain Turno = {TURNO_A, TURNO_B}	
	enum domain Semaforo = {SEM1, SEM2, SEM3, SEM4}	
	monitored signal: Boolean
	
	controlled semaforo: Semaforo -> Colore
	controlled turno: Turno

definitions:

	rule r_set_colore($a in Turno, $c in Colore) =
		if $a = TURNO_A then
			par
				semaforo(SEM1) := $c
				semaforo(SEM3) := $c
			endpar
		else // TURNO_B
			par
				semaforo(SEM2) := $c
				semaforo(SEM4) := $c
			endpar
		endif
		
			
	main rule r_main = 
		if turno = TURNO_A then  
			// controllo solo SEM1 , l'altro SEM3 sarà uguale
			if semaforo(SEM1) = ROSSO then // diventa verde
					r_set_colore[TURNO_A, VERDE]
			else if semaforo(SEM1) = VERDE and signal then // diventa verde
					r_set_colore[TURNO_A, GIALLO]			
			else if semaforo(SEM1) = GIALLO then // diventa rosso e cambia turno
				par
					r_set_colore[TURNO_A, ROSSO]
					turno := TURNO_B
				endpar
			endif endif endif 
		else // turno = TURNO_B
			if semaforo(SEM2) = ROSSO then // diventa verde
					r_set_colore[TURNO_B, VERDE]
			else if semaforo(SEM2) = VERDE and signal then // diventa verde
					r_set_colore[TURNO_B, GIALLO]			
			else if semaforo(SEM2) = GIALLO then // diventa rosso e cambia turno
				par
					r_set_colore[TURNO_B, ROSSO]
					turno := TURNO_B
				endpar
			endif endif endif 
		endif

default init s0:

	function semaforo($s in Semaforo) = ROSSO
	function turno = TURNO_A
	