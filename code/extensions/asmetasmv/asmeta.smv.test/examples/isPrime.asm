asm isPrime

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	dynamic monitored number: Integer
	dynamic controlled selectedNumber: Integer
	dynamic controlled index: Integer
	dynamic controlled askNumber: Boolean
	dynamic controlled outMess: String

definitions:

	rule r_askNumber =
		if(askNumber) then
			if(number>1) then
				par
					askNumber := false
					selectedNumber := number
					index := 2
				endpar
			else
				outMess := "Inserisci un numero maggiore di 1"
			endif
		endif

	rule r_getResult =
		if(not(askNumber)) then
			if(selectedNumber = 2) then
				par
					askNumber := true
					outMess := "E' primo"
				endpar
			else if(index > floor(selectedNumber/2)+1) then
				par
					askNumber := true
					outMess := "E' primo"
				endpar
			else if((selectedNumber mod index) = 0) then
				par
					askNumber := true
					outMess := "Non e' primo"
				endpar
			else
				index := index + 1
			endif endif	endif
		endif

	main rule r_Main =
		par
			r_askNumber[]
			r_getResult[]
		endpar

default init s0:
	function askNumber = true
	function index = 2