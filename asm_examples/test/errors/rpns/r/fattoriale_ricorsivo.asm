//fattoriale ricorsivo with a turbo rule

asm fattoriale_ricorsivo

import ../../../../STDL/StandardLibrary

signature:
	dynamic controlled valore: Integer
	
definitions:
	
	turbo rule r_fattoriale($n in Integer) in Integer =
		local x : Integer [ x:=1 ]
		if($n=1) then
			result := 1
		else
			seq
				x <- r_fattoriale($n-1)
				result := $n*x
			endseq
		endif
			
	main rule r_Main =
		r_fattoriale(valore)
	
		

default init s0:
	function valore = 5
