//fibonacci non ricorsivo

asm fibonacci

import ../../STDL/StandardLibrary

signature:
	dynamic monitored numeri_fibo: Integer //numero di numeri di Fibonacci
	dynamic controlled num_fibo: Integer
	dynamic controlled indice: Integer
	dynamic controlled succ_fibo: Seq(Integer)
	
	dynamic controlled temp: Integer
	
definitions:
	
	macro rule r_insert_number =
		if(num_fibo=2) then
			num_fibo := numeri_fibo
		endif
	
	macro rule r_fibonacci =
		if(indice<num_fibo) then
			seq
				succ_fibo := append(succ_fibo, at(succ_fibo,iton(indice-1)) + at(succ_fibo,iton(indice-2)))
				indice := indice+1
			endseq
		endif
			
	main rule r_Main =
		seq
			r_insert_number[]
			r_fibonacci[]
		endseq
	

default init s0:
	function succ_fibo = [1, 1]
	function indice = 2
	function num_fibo = 2