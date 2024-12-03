asm sultanGreedyBoardSolutionLessK

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain Righe subsetof Integer
	domain Colonne subsetof Integer
	domain Valore subsetof Integer
	domain SommaValore subsetof Integer
	domain RegineInserite subsetof Integer

	enum domain ResultDomain = {WIN | LOST | NOTFINISHED}

	dynamic monitored coordinataColonna: Colonne
	dynamic monitored coordinataRiga: Righe
	dynamic controlled matriceRegine: Prod(Righe, Colonne) -> Boolean
	dynamic controlled regineInserite: RegineInserite
	dynamic controlled sommaValoriRegine: SommaValore
	dynamic controlled gameResult: ResultDomain

	static nRegine: Righe
	static valoreK: Valore
	static matriceValori: Prod(Righe, Colonne) -> Valore
	
	derived okPosRegina: Prod(Righe,Colonne) -> Boolean
	derived regina_in_riga: Righe -> Boolean
	derived regina_in_colonna: Colonne -> Boolean
	derived regina_in_diagonali: Prod(Righe, Colonne) -> Boolean
	
definitions:
	domain Righe = {1 : 5}
	domain Colonne = {1 : 5}
	domain Valore = {1:25}
	domain SommaValore = {0:484}
	domain RegineInserite = {0:5}

	function nRegine = 5
	function valoreK = 45

	//Board 3: permette di ottenere una soluzione, ma con valore minore di K
	function matriceValori($r in Righe,$c in Colonne) =
		at({(1, 1) -> 18, (1, 2) -> 5,(1, 3) -> 24,(1, 4) -> 4,(1, 5) -> 23,
		    (2, 1) -> 8, (2, 2) -> 20,(2, 3) -> 15,(2, 4) -> 12,(2, 5) -> 7,
		    (3, 1) -> 14, (3, 2) -> 11,(3, 3) -> 25,(3, 4) -> 9,(3, 5) -> 17,
		    (4, 1) -> 6, (4, 2) -> 22,(4, 3) -> 19,(4, 4) -> 10,(4, 5) -> 2,
		    (5, 1) -> 16, (5, 2) -> 1,(5, 3) -> 13,(5, 4) -> 3,(5, 5) -> 21}, ($r, $c))

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

	//Board 3
	//E' possibile ottenere una soluzione con 5 regine
	CTLSPEC ef(regineInserite = nRegine and ag(regineInserite = nRegine))
	//Non è possibile ottenere una soluzione con valore maggiore di K
	CTLSPEC not(ef(regineInserite = nRegine and sommaValoriRegine >= valoreK))
	CTLSPEC ag(sommaValoriRegine < valoreK)

	main rule r_Main =
		if regineInserite < nRegine then
			r_cerca_greedy[]
		endif

default init s0:
	function regineInserite = 0
	function sommaValoriRegine = 0
	function matriceRegine($r in Righe, $c in Colonne) = false