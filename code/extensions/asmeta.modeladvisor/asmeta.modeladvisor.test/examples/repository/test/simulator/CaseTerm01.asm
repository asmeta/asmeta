asm CaseTerm01
import ../../STDL/StandardLibrary
	
signature:
controlled f: Powerset(Integer)
		
definitions:
main rule r_main =
	f :=
		let ($set = {2,4,6}) in
			switch $set
				case {1,2,3}: {11}
				case {$x in {1,2,3} : 2 * $x }: {22}
				otherwise {33}
			endswitch
		endlet

