asm parking

import ../STDL/StandardLibrary
import ../STDL/CTLLibrary

signature:
	domain Row subsetof Integer
	domain Column subsetof Integer
	domain ProbDom subsetof Integer
	controlled free: Prod(Row, Column) -> Boolean
	monitored newCar: Boolean

definitions:
	domain Row = {1 : 3}
	domain Column = {1 : 5}
	domain ProbDom = {1 : 100}

	rule r_freePark($r in Row, $c in Column, $p in ProbDom, $maxProb in ProbDom) =
		if $p <= $maxProb then
			free($r, $c) := true
		endif

	//esiste uno stato in cui tutti i posti sono occupati
	CTLSPEC ef((forall $r in Row, $c in Column with not(free($r, $c))))
	//esiste uno stato a partire dal quale inizia un cammino in cui tutti i posti sono occupati
	CTLSPEC ef(eg((forall $r in Row, $c in Column with free($r, $c))))
	//trovare tramite model checking un cammino che porti in uno stato in cui i posti (1,3)
	//e (1,4) sono occupati
	CTLSPEC not(ef(not(free(1,3)) and not(free(1,4))))

	main rule r_Main =
		par
			if newCar then
				choose $r in Row, $c in Column with free($r, $c) do
					free($r, $c) := false
			endif
			forall $r1 in Row, $c1 in Column with not(free($r1, $c1)) do
				choose $p in ProbDom with true do
					r_freePark[$r1, $c1, $p, $r1*10]
		endpar

default init s0:
	function free($r in Row, $c in Column) = true
