asm MacroCallRule03
import ../../STDL/StandardLibrary
	
signature:
	controlled f: Integer
	static g:Integer->Integer
		
definitions:

	function g($x in Integer) = $x * $x

	macro rule r_m1($x in Integer, $y in Integer) =
		$x := 1 + $y

	main rule r_main =
		let ($x = 3) in
    		r_m1[f, g($x + $x)]
		endlet
