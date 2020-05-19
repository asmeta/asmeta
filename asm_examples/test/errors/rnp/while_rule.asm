//regola per simulare il while
//la regola r_corpowhile viene eseguita fino a quando indice e' maggiore di zero
//in questo esempio la r_corpowhile esegue la somma da 1 a indice

asm while_rule

import ../../STDL/StandardLibrary

signature:
	dynamic controlled indice: Integer
	derived cond: Boolean
	dynamic controlled sum: Integer
	
definitions:
	
	function cond =
		indice>0
	
	macro rule r_corpowhile =
		seq
			sum := sum + indice
			indice := indice-1
		endseq
	
	macro rule r_while =
		if(cond) then
			seq
				r_corpowhile[]
				r_while[]
			endseq
		endif
	
	
	main rule r_Main =
		r_while[]	

default init s0:
	function indice = 9
	function sum = 0