asm turbo01
import ../../../STDL/StandardLibrary
	
signature:
controlled f: Integer->Integer
		
definitions:

turbo rule r_turbo = 
	result := 125
	
main rule r_main = 
	let ($x = 0) in seq
		$x <- r_turbo()
		$x := $x * 2
		f(0) := $x + 50
	endseq endlet

