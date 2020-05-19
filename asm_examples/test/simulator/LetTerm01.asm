asm LetTerm01
import ../../STDL/StandardLibrary
	
signature:
controlled f: Integer
		
definitions:
main rule r_main =
		f := let ($x = 1, $y = $x + 1, $z = $y + 1) in $x * $y * $z endlet
