//fattoriale non ricorsivo

asm fattoriale

import ../../STDL/StandardLibrary

signature:
	domain ValoreDomain subsetof Integer
	domain ResultDomain subsetof Integer
	
	dynamic monitored valore: ValoreDomain
	dynamic controlled indice: ValoreDomain
	dynamic controlled fattoriale: ResultDomain
	//dynamic controlled outMess: Any
	
definitions:
	domain ValoreDomain = {1..4}
	domain ResultDomain = {1..24}
	
	macro rule r_fattoriale =
		if(indice>1) then
			par
				fattoriale := fattoriale*indice
				indice := indice-1
			endpar
		endif
			
	main rule r_Main =
		seq
			if(indice=1) then
				if(valore>0) then
					par
						indice := valore
						fattoriale := 1
						//outMess := "Calcolo il fattoriale"
					endpar
				//else
				//	outMess := "Inserire un valore maggiore di zero"
				endif
			endif
			r_fattoriale[]
		endseq
	

default init s0:
	function indice = 1