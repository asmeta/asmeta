//fattoriale non ricorsivo

asm fattoriale

import ../../STDL/StandardLibrary

signature:
	dynamic monitored valore: Integer
	dynamic controlled indice: Integer
	dynamic controlled fattoriale: Integer
	dynamic controlled outMess: String
	
definitions:
	
	macro rule r_fattoriale =
		if(indice > 1) then
			par
				fattoriale := fattoriale*indice
				indice := indice - 1
			endpar
		endif
			
	main rule r_Main =
		seq
			if(indice=1) then
				if(valore>0) then
					par
						indice := valore
						fattoriale := 1
						outMess := "Calcolo il fattoriale"
					endpar
				else
					outMess := "Inserire un valore maggiore di zero"
				endif
			endif
			r_fattoriale[]
		endseq
	

default init s0:
	function indice = 1
