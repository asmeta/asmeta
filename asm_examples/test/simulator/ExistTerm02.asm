asm ExistTerm02
import ../../STDL/StandardLibrary
	
signature:
controlled f: Boolean
		
definitions:

macro rule r_foo($z in Integer, $p in Powerset(Integer)) =
	f := (exist $x in $p, $y in {5, 10, 20} with $x + $y = $z)

main rule r_main =
		let ($x = 12, $y = 1) in
			r_foo[$x + $y, {1, 2, 3}]
		endlet
