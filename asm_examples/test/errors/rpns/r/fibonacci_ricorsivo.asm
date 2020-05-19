//fibonacci ricorsivo with a turbo rule

asm fibonacci_ricorsivo

import ../../../../STDL/StandardLibrary

signature:
	dynamic monitored n: Integer //numero di numeri di Fibonacci
	dynamic controlled succ_fibo: Seq(Integer)
	
definitions:
	
	turbo rule r_fibonacci($i in Integer) =
		if($i<n) then
			seq
				succ_fibo := append(succ_fibo, at(succ_fibo,iton($i-1)) + at(succ_fibo,iton($i-2)))
				r_fibonacci($i+1)
			endseq
		endif
			
	main rule r_Main =
		seq
			succ_fibo := [1, 1]
			r_fibonacci(3)
		endseq
	

default init s0:
