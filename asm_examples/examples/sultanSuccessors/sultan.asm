asm sultan

import ../../STDL/StandardLibrary
import ../../STDL/CTLlibrary

signature:
	domain Righe subsetof Integer
	domain Colonne subsetof Integer
	
	dynamic monitored coordinataColonna: Colonne
	dynamic monitored coordinataRiga: Righe
	dynamic controlled outMess: String
	dynamic controlled matriceRegine: Prod(Righe, Colonne) -> Boolean
	dynamic controlled regineInserite: Integer
	dynamic controlled sommaValoriRegine: Integer
	
	static nRegine: Integer
	static valoreK: Integer
	static matriceValori: Prod(Righe, Colonne) -> Integer
	
	derived okPosRegina: Prod(Righe,Colonne) -> Boolean
	derived regina_in_riga: Righe -> Boolean
	derived regina_in_colonna: Colonne -> Boolean
	derived regina_in_diagonali: Prod(Righe, Colonne) -> Boolean
	
definitions:
	domain Righe = {1 : 8}
	domain Colonne = {1 : 8}

	function nRegine = 8
	function valoreK = 200

/*	function matriceValori($r in Righe, $c in Colonne) =
		switch($r)
			case 1: at([21, 39, 40, 8, 27, 38, 51, 9], iton($c-1))
			case 2: at([7, 42, 41, 26, 36, 11, 19, 46], iton($c-1))
			case 3: at([15, 43, 18, 35, 1, 37, 4, 13], iton($c-1))
			case 4: at([5, 59, 34, 12, 54, 60, 17, 23], iton($c-1))
			case 5: at([44, 58, 33, 50, 24, 2, 53, 64], iton($c-1))
			case 6: at([48, 61, 20, 62, 52, 47, 56, 25], iton($c-1))
			case 7: at([22, 63, 31, 32, 3, 28, 10, 30], iton($c-1))
			case 8: at([55, 14, 45, 16, 57, 29, 49, 6], iton($c-1))
		endswitch*/
		
	function matriceValori($r in Righe,$c in Colonne)=
		at({(1, 1) -> 14, (1, 2) -> 13,(1, 3) -> 25,(1, 4) -> 4,(1, 5) -> 11,
	    (2, 1) -> 6, (2, 2) -> 21,(2, 3) -> 17,(2, 4) -> 24,(2, 5) -> 7,
	    (3, 1) -> 19, (3, 2) -> 2,(3, 3) -> 23,(3, 4) -> 22,(3, 5) -> 20,
	    (4, 1) -> 15, (4, 2) -> 8,(4, 3) -> 5,(4, 4) -> 9,(4, 5) -> 3,
	    (5, 1) -> 12, (5, 2) -> 1,(5, 3) -> 18,(5, 4) -> 16,(5, 5) -> 10}, ($r, $c))

	function okPosRegina($r in Righe, $c in Colonne) =
		(not(regina_in_riga($r)) and not(regina_in_colonna($c)) and not(regina_in_diagonali($r, $c)))

	function regina_in_riga($r in Righe) =
		(exist $c in Colonne with matriceRegine($r, $c))

	function regina_in_colonna($c in Colonne) =
		(exist $r in Righe with matriceRegine($r, $c))

	function regina_in_diagonali($r in Righe, $c in Colonne) =
		(exist $x in Righe, $y in Colonne with $x - $y = $r - $c and matriceRegine($x, $y)) or 
		(exist $i in Righe, $j in Colonne with $i + $j = $r + $c and matriceRegine($i, $j))

	macro rule r_inserisci_regina =
		if regineInserite < nRegine then	
			if (okPosRegina(coordinataRiga, coordinataColonna)) then
				par
					matriceRegine(coordinataRiga, coordinataColonna) := true
					sommaValoriRegine := sommaValoriRegine + matriceValori(coordinataRiga, coordinataColonna)
					regineInserite := regineInserite + 1
					outMess := "Regina inserita"
				endpar
			else
				outMess := "Posizione errata. La regina può essere mangiata."
			endif
		endif
	
	macro rule r_controlla_vittoria =
		if regineInserite = nRegine then
			if sommaValoriRegine >= valoreK then
				outMess := "Complimenti. Hai vinto"
			else
				outMess := "Peccato. Hai perso"
			endif
		endif

	main rule r_Main =
		par
			r_inserisci_regina[]
			r_controlla_vittoria[]
		endpar

default init s0:
	function matriceRegine($r in Righe, $c in Colonne) = false
	function outMess = ""
	function regineInserite = 0
	function sommaValoriRegine = 0
