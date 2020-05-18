asm CaseTerm02
import ../../STDL/StandardLibrary
	
signature:
controlled f: Integer->Integer
		
definitions:

macro rule r_m1($z in Integer, $x in Integer, $y in Integer) =
	f($z) :=
			switch $z
				case $x: 11
				case $y: 22
				otherwise 33
			endswitch

main rule r_main =
	let ($x = 12, $y = 2 * $x, $z = if $x > $y then $x else $y endif) in
		r_m1[$z mod 7, 5, $x]
	endlet

