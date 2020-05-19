//inzializzazione di una funzione con MAP term
// preso da ATM
asm MapTerm

import ../../../STDL/StandardLibrary

signature:

	domain NumCard subsetof Integer //numero della carta (e del conto di riferimento)
	static pin: NumCard -> Integer  

definitions:

	function pin($c in NumCard) = at({ 123456 -> 1234,
									234567 -> 2345, 
									345678 -> 3456}, $c)
