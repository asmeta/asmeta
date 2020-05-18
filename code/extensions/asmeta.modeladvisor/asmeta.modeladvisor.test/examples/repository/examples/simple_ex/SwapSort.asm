asm SwapSort

import  ../../STDL/StandardLibrary
	
signature:
	static n: Integer
	controlled vect: Integer->Integer
		
definitions:

function n = 10

// note that no temprary variable is needed to swap two variables, if the
// swap is performed in a par rule
macro rule r_swap($x in Integer, $y in Integer) =
	par
		$x := $y
		$y := $x
	endpar

macro rule r_swapSort =
	choose $i in {0..9}, $j in {0..9} with $i < $j and vect($i) > vect($j) do
		r_swap[vect($i), vect($j)]
	
main rule r_main =
	r_swapSort[]

default init s1:
	function vect($x in Integer) = 
		switch $x
			case 0: 5
			case 1: 7
			case 2: 1
			case 3: 8
			case 4: 3
			case 5: 2
			case 6: 6
			case 7: 4
			case 8: 8
			case 9: 9
		endswitch
