asm GiocoDelCento_2

import ../../../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain Players = { PLAYER1 | PLAYER2 }
	enum domain WinDomain = { WIN | LOSE | NONE }
	//domain Giocata subsetof Integer
	dynamic controlled win: Players -> WinDomain
	dynamic controlled distanza: Integer
	dynamic controlled obiettivo: Integer
	dynamic controlled diffGiocata: Integer
	dynamic controlled giocataCorrenteP1_i: Integer
	dynamic controlled giocataCorrenteP2_i: Integer
	dynamic controlled giocataCorrenteP1_ii: Integer
	dynamic controlled giocataCorrenteP2_ii: Integer
	dynamic monitored gameP1: Integer
	dynamic monitored gameP2: Integer
	dynamic controlled outMess: String
	
	
	
definitions:
	
	rule r_giocata_i =
		if(gameP1 > 0 and gameP2 > 0) then
			par
				diffGiocata := giocataCorrenteP1_ii - giocataCorrenteP2_i
				if(diffGiocata <= distanza) then
					giocataCorrenteP1_i := gameP1
				else
					outMess := "Giocata non valida"
				endif
				diffGiocata := giocataCorrenteP2_i - giocataCorrenteP1_i
				if(diffGiocata <= distanza) then
					giocataCorrenteP2_i := gameP2
				else
					outMess := "Giocata non valida"
				endif
			endpar
		else
			outMess := "Inserisci un numero valido:"
		endif
		
		
	rule r_giocata_ii =
		if(gameP1 > 0 and gameP2 > 0) then
		par
			diffGiocata := giocataCorrenteP1_ii - giocataCorrenteP2_i
			if(diffGiocata <= distanza) then
				giocataCorrenteP1_ii := gameP1
			else
				outMess := "Giocata non valida"
			endif
			diffGiocata := giocataCorrenteP2_ii - giocataCorrenteP1_ii
			if(diffGiocata <= distanza) then
				giocataCorrenteP2_ii := gameP2
			else
				outMess := "Giocata non valida"
			endif
		endpar
	else
		outMess := "Inserisci un numero valido:"
	endif
			
	
	main rule r_Main =
		seq
			r_giocata_i[]
			r_giocata_ii[]
		endseq
	
	
default init s0:
	function distanza = 10
	function obiettivo = 100
	function giocataCorrenteP1_i = 0
	function giocataCorrenteP2_i = 0
	function giocataCorrenteP1_ii = 0
	function giocataCorrenteP2_ii = 0
	function outMess = " "
