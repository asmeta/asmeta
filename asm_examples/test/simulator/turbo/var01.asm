asm var01
import ../../../STDL/StandardLibrary
	
signature:
controlled f: Integer->Integer
		
definitions:	                 	
main rule r_main = 
	let ($x = 0) in seq
		$x := 125
		$x := $x * 2
		f(0) := $x + 50
	endseq endlet

