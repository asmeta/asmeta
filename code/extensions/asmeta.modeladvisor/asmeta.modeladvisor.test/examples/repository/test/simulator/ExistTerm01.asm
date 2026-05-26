asm ExistTerm01
import ../../STDL/StandardLibrary
	
signature:
controlled f: Boolean
		
definitions:
main rule r_main =
	f :=
		let ($d = {1 : 5}) in
			(exists $z in $d, $x in $d, $y in $d with $z*$z = $x*$x + $y*$y)
		endlet
		
