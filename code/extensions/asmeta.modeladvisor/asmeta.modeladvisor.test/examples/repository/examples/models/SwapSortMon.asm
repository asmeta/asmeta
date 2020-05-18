// legge un array da input (come sequenza) e lo ordina


asm SwapSortMon

import  ../../STDL/StandardLibrary

signature:

	controlled vect: Natural -> Integer
	controlled v_max_i: Natural // massimo indice per vect
	monitored  vect_in: Seq(Integer)

definitions:

// note that no temprary variable is needed to swap two variables, if the
// swap is performed in a par rule
macro rule r_swap($x in Integer, $y in Integer) =
	par
		$x := $y
		$y := $x
	endpar

macro rule r_swapSort =
	choose $i in range(0n,v_max_i), $j in range(0n,v_max_i) with $i < $j and vect($i) > vect($j)  do
		r_swap[vect($i), vect($j)]

main rule r_main =
	r_swapSort[]

default init s1:
	function vect($x in Natural) = at(vect_in,$x)
	function v_max_i = iton(length(vect_in) - 1)
