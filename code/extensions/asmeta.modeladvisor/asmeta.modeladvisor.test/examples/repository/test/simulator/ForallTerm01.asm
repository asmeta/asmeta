asm ForallTerm01
import ../../STDL/StandardLibrary
	
signature:
controlled f: Boolean
static sum: Integer->Integer
		
definitions:

function sum($x in Integer) =
	if $x = 0 then 0
	else $x + sum($x - 1)
	endif

main rule r_main =
	f :=
		let ($d = {1 : 5}) in
			(forall $x in $d with ($x*($x+1))/2 = sum($x))
		endlet
		
