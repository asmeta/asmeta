asm tombola

import ../STDL/StandardLibrary
import ../STDL/CTLlibrary

signature:
	domain Numero subsetof Integer
	dynamic controlled uscito: Numero -> Boolean
	static cartellaGiocatoreA: Numero -> Boolean
	static cartellaGiocatoreB: Numero -> Boolean
	dynamic controlled uscitoSuCartellaGiocatoreA: Numero -> Boolean
	dynamic controlled uscitoSuCartellaGiocatoreB: Numero -> Boolean
	derived cartellaGiocatoreACompleta: Boolean
	derived cartellaGiocatoreBCompleta: Boolean

definitions:
	domain Numero = {1 : 30}
	function cartellaGiocatoreA($n in Numero) = $n = 2 or $n = 7 or $n = 12 or $n = 18 or $n = 25
	function cartellaGiocatoreB($n in Numero) = $n = 1 or $n = 5 or $n = 14 or $n = 18 or $n = 26
	function cartellaGiocatoreACompleta = uscitoSuCartellaGiocatoreA(2) and uscitoSuCartellaGiocatoreA(7) and uscitoSuCartellaGiocatoreA(12) and uscitoSuCartellaGiocatoreA(18) and uscitoSuCartellaGiocatoreA(25)
	function cartellaGiocatoreBCompleta = uscitoSuCartellaGiocatoreB(1) and uscitoSuCartellaGiocatoreB(5) and uscitoSuCartellaGiocatoreB(14) and uscitoSuCartellaGiocatoreB(18) and uscitoSuCartellaGiocatoreB(26)

	//esiste uno stato in cui il giocatore A completa la cartella
	CTLSPEC ef(cartellaGiocatoreACompleta)
	//esiste uno cammino in cui il giocatore A non completa mai la cartella
	CTLSPEC eg(not(cartellaGiocatoreACompleta))
	//Trovare tramite model checking un cammino che porti in uno stato in cui
	//la cartella del giocatore A e quella del giocatore B sono entrambe complete
	CTLSPEC ag(not(cartellaGiocatoreACompleta and cartellaGiocatoreBCompleta))

	main rule r_Main =
		if not(cartellaGiocatoreACompleta) and not(cartellaGiocatoreBCompleta) then
			choose $n in Numero with not(uscito($n)) do
				par
					uscito($n) := true
					if cartellaGiocatoreA($n) then
						uscitoSuCartellaGiocatoreA($n) := true
					endif
					if cartellaGiocatoreB($n) then
						uscitoSuCartellaGiocatoreB($n) := true
					endif
				endpar
			endif

default init s0:
	function uscito($n in Numero) = false
	function uscitoSuCartellaGiocatoreA($n in Numero) = false
	function uscitoSuCartellaGiocatoreB($n in Numero) = false
