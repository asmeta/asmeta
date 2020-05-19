asm MacroCallRule01
import ../../STDL/StandardLibrary
	
signature:
controlled f: Integer
		
definitions:

macro rule r_m1($x in Integer, $y in Integer) =
    $x := 1 + $y

main rule r_main = 
    r_m1[f, 16]
