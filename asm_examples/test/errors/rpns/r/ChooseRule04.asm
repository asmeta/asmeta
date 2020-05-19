// questo il simulatore non riesce a parsarlo perchè $a non viene legato prima del with
// e' corretto o sbagliato dal punto di vista semantico?

asm ChooseRule04
import ../../../../STDL/StandardLibrary

signature:

    controlled f: Integer
					
definitions:

main rule r_main = 
	choose $a in {{1, 2, 3},{5,6}}, $x in $a with $x = 2 do
			f := $x 
