asm CaseRule02
import ../../STDL/StandardLibrary
	
signature:
controlled f: Integer

definitions:

macro rule r_foo($z in Integer) =
	switch [1,4,9,16]
		case []: skip
		case [$x in [1 : 4] : $x * $x ]:
			switch 77
				case 0: skip
				otherwise f := 88
			endswitch
		otherwise f:= 99
	endswitch

main rule r_main =
	let ($x = 0) in
		r_foo[$x]
	endlet
