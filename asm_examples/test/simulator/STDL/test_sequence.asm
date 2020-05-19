asm test_sequence

import ../../../STDL/StandardLibrary

signature:
	dynamic controlled riga: Seq(Integer)
	dynamic controlled lung: Integer
	dynamic controlled pos: Integer
	dynamic controlled contiene: Boolean
	dynamic controlled conta: Natural
	dynamic controlled primo: Integer
	dynamic controlled ultimo: Integer
	dynamic controlled atElement0: Integer
	dynamic controlled atElement1: Integer
	dynamic controlled atElement2: Integer
	dynamic controlled atElement3: Integer
	dynamic controlled atElement4: Integer

definitions:

	main rule r_Main =
		par
			conta := count(riga,1)
			contiene := contains(riga,1)
			lung := length(riga)
			pos := indexOf(riga,1)
			primo := first(riga)
			atElement0 := at(riga, 0n)
			atElement1 := at(riga, 1n)
			atElement2 := at(riga, 2n)
			atElement3 := at(riga, 3n)
			atElement4 := at(riga, 4n)
			ultimo := last(riga)
			//riga := insertAt(riga, 5n, 0)
			riga := replaceAt(riga, 0n, 10)
		endpar

default init s0:
function riga = [1, 2, 3, 4, 5]
