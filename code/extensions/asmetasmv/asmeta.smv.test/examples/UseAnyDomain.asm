// spec give by a student who gets an error about AnyDomain
// i cannot replicate the error
asm UseAnyDomain

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	enum domain Result = {WINUSER | WINPC | PATTA}
	domain Valore1_5 subsetof Integer
	domain Valore0_10 subsetof Integer
	dynamic controlled numeroEstrattoUtente: Valore1_5
	dynamic controlled numeroEstrattoComputer: Valore1_5
	dynamic controlled creditoUtente: Valore0_10
	dynamic controlled creditoComputer: Valore0_10
	dynamic controlled outMess: Result
	derived winner: Result
	derived finalWinner: Result
	
	
definitions:
	domain Valore1_5 = {1, 2, 3, 4, 5}
	domain Valore0_10 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
	
	function winner =
    	if(numeroEstrattoUtente > numeroEstrattoComputer) then
        	WINUSER
    	else if(numeroEstrattoUtente < numeroEstrattoComputer) then
        	WINPC
    	else
        	PATTA
    	endif
    	endif
		
	function finalWinner =
		if(creditoUtente=0) then
			WINPC
		else if(creditoComputer=0) then
			WINUSER
		else
			PATTA
		endif
		endif

	rule r_calculate_winner =
			if(creditoUtente != 0 and creditoComputer != 0) then
				if(winner = WINUSER) then
					par
						creditoUtente := creditoUtente + 1
						creditoComputer := creditoComputer - 1
					endpar
				else if(winner = WINPC) then
					par
						creditoComputer := creditoComputer + 1
						creditoUtente := creditoUtente - 1
					endpar
				endif
				endif
			else
				outMess := finalWinner
			endif
	
	//Invariante su somma dei crediti utenti sempre uguale a 10
	invariant over creditoUtente, creditoComputer: creditoUtente + creditoComputer = 10
		
	CTLSPEC ag(creditoUtente >=0 and creditoUtente <=10)
	CTLSPEC ag(creditoUtente + creditoComputer = 10)
	CTLSPEC ef(ag(creditoComputer >= 1))
	
	main rule r_main =
		par
			choose $x in Valore1_5 with true do
				numeroEstrattoUtente := $x
			choose $y in Valore1_5 with true do
				numeroEstrattoComputer := $y
			r_calculate_winner[]
		endpar
		

default init s0:
	function numeroEstrattoUtente = 1
	function numeroEstrattoComputer = 1
	function creditoUtente = 5
	function creditoComputer = 5
