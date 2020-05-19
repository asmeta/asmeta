asm MacroCallRule02
import ../../STDL/StandardLibrary
	
signature:
controlled g: Integer->Integer
controlled f: Integer

definitions:

macro rule r_m1($x in Integer, $y in Integer) =
	par
		$x := 1 + $y
		seq
			f := g($y)
			g($x) := 0
		endseq // update set = {f=49, g(3)=0}
	endpar // update set = {g(3)=50, f=49, g(3)=0}
	// l'update set è inconsistente!

main rule r_main = 
    r_m1[g(3), f * f]

default init s1:   
	function f = 7
	function g($x in Integer) = $x
