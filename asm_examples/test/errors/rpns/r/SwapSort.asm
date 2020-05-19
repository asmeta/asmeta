// with a term as initialization of a function

asm SwapSort

import ../../../../STDL/StandardLibrary
	
signature:
	static n: Integer
	controlled vect: Integer->Integer
		
definitions:

function n = 10

// note that no temprary variable is needed to swap two variables, if the
// swap is performed in a par rule
// the semantics is the copy semantics

macro rule r_swap($x in Integer, $y in Integer) =
	par
		$x := $y
		$y := $x
	endpar

macro rule r_swapSort =
	choose $i in {0 : 9}, $j in {0 : 9} with $i < $j and vect($i) > vect($j) do
		r_swap[vect($i), vect($j)]
	
main rule r_main =
	r_swapSort[]

default init s1:
	function vect($x in Integer) = at({0->5, 1->7 , 2->3 , 3->8, 4->0, 5->4, 6->9, 7->2, 8->1, 9->6}, $x)
