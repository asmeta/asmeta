asm sultanGreedy

import ../../STDL/StandardLibrary

signature:
	domain Righe subsetof Integer
	domain Colonne subsetof Integer
	dynamic controlled matriceRegine: Prod(Righe, Colonne) -> Boolean
	derived matriceValori: Prod(Righe, Colonne) -> Integer
	dynamic controlled massimo: Integer
	dynamic controlled regineInserite: Integer
	dynamic controlled sommaValoriRegine: Integer
	
	static nRegine : Integer
	static valoreK: Integer
	derived okPosRegina: Prod(Righe, Colonne) -> Boolean
	derived riempiMatriceValori: Prod(Righe, Colonne) -> Integer
	derived regina_in_riga: Righe -> Boolean
	derived regina_in_colonna: Colonne -> Boolean
	derived regina_in_diagonali: Prod(Righe, Colonne) -> Boolean
	
definitions:
	domain Righe = {1 : 8}
	domain Colonne = {1 : 8}

	function nRegine = 8
	function valoreK = 200

	//Non permette di ottenere una soluzione
	//La soluzione parziale trovata e': (5,8), (7,2), (6,4), (1,7), (2,3)
	/*function matriceValori($r in Righe, $c in Colonne) =
		switch($r)
			case 1: at([21, 39, 40,  8, 27, 38, 51,  9], iton($c-1))
			case 2: at([ 7, 42, 41, 26, 36, 11, 19, 46], iton($c-1))
			case 3: at([15, 43, 18, 35,  1, 37,  4, 13], iton($c-1))
			case 4: at([ 5, 59, 34, 12, 54, 60, 17, 23], iton($c-1))
			case 5: at([44, 58, 33, 50, 24,  2, 53, 64], iton($c-1))
			case 6: at([48, 61, 20, 62, 52, 47, 56, 25], iton($c-1))
			case 7: at([22, 63, 31, 32,  3, 28, 10, 30], iton($c-1))
			case 8: at([55, 14, 45, 16, 57, 29, 49,  6], iton($c-1))
		endswitch*/

	//Permette di ottenere una soluzione. La soluzione ha valore 484, che e' maggiore di K = 200. 
	//La soluzione trovata e': (1,4), (2,7), (3,3), (4,8), (5,2), (6,5), (7,1), (8,6)
	/*function matriceValori($r in Righe, $c in Colonne) =
		switch($r)
			case 1: at([21, 39, 40, 64, 27, 38, 51,  9], iton($c-1))
			case 2: at([ 7, 42, 41, 26, 36, 11, 63, 46], iton($c-1))
			case 3: at([15, 43, 62, 35,  1, 37,  4, 13], iton($c-1))
			case 4: at([ 5, 52, 34, 12, 54, 22, 17, 61], iton($c-1))
			case 5: at([44, 60, 33, 50, 24,  2, 53,  8], iton($c-1))
			case 6: at([48, 23, 20, 18, 59, 47, 56, 25], iton($c-1))
			case 7: at([58, 19, 31, 32,  3, 28, 10, 30], iton($c-1))
			case 8: at([55, 14, 45, 16, 29, 57, 49,  6], iton($c-1))
		endswitch*/

	//Permette di ottenere una soluzione. La soluzione ha valore 165, che non e' maggiore di K = 200. 
	//La soluzione trovata e': (1,4), (2,7), (3,3), (4,8), (5,2), (6,5), (7,1), (8,6)
	function matriceValori($r in Righe, $c in Colonne) =
		switch($r)
			case 1: at([63, 62, 61, 64, 60, 59, 58, 57], iton($c-1))
			case 2: at([41, 40, 49, 56, 46, 39, 42, 38], iton($c-1))
			case 3: at([26, 48, 27, 55, 25, 45, 37, 28], iton($c-1))
			case 4: at([47, 20, 24, 54, 32, 14, 44, 15], iton($c-1))
			case 5: at([19, 10, 23, 53, 18,  9, 36, 43], iton($c-1))
			case 6: at([ 6,  8, 31, 52,  4, 17, 35, 13], iton($c-1))
			case 7: at([ 2, 30, 22, 51, 11,  3, 34, 12], iton($c-1))
			case 8: at([29,  7, 21, 50,  5,  1, 33, 16], iton($c-1))
		endswitch

	function okPosRegina($r in Righe, $c in Colonne) =
		(not(regina_in_riga($r)) and not(regina_in_colonna($c)) and not(regina_in_diagonali($r, $c)))

	function regina_in_riga($r in Righe) =
		(exist $c in Colonne with matriceRegine($r, $c))

	function regina_in_colonna($c in Colonne) =
		(exist $r in Righe with matriceRegine($r, $c))

	function regina_in_diagonali($r in Righe, $c in Colonne) =
		(exist $x in Righe, $y in Colonne with $x - $y = $r - $c and matriceRegine($x, $y)) or 
		(exist $i in Righe, $j in Colonne with $i + $j = $r + $c and matriceRegine($i, $j))
	

	macro rule r_inserisci_regina($r in Righe, $c in Colonne) =
		par
			matriceRegine($r,$c) := true
			sommaValoriRegine := sommaValoriRegine + matriceValori($r,$c)
			regineInserite := regineInserite + 1
		endpar

	macro rule r_cerca_greedy =
		choose $r in Righe, $c in Colonne with okPosRegina($r, $c) and
												(forall $r2 in Righe, $c2 in Colonne with okPosRegina($r2, $c2) implies
												                                         matriceValori($r2, $c2) <= matriceValori($r, $c)) do
			r_inserisci_regina[$r, $c]

	main rule r_Main =
		if regineInserite < nRegine then
			r_cerca_greedy[]
		endif

default init s0:
	function regineInserite = 0
	function sommaValoriRegine = 0
	function matriceRegine($r in Righe, $c in Colonne) = false
