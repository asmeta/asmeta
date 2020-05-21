asm sultanGreedy

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain Rows subsetof Integer
	domain Columns subsetof Integer
	domain Values subsetof Integer
	domain SumValues subsetof Integer
	domain InsertedQueens subsetof Integer

	enum domain ResultDomain = {WIN | LOST | NOTFINISHED}

	dynamic monitored colCoord: Columns
	dynamic monitored rowCoord: Rows
	dynamic controlled queensMatrix: Prod(Rows, Columns) -> Boolean
	dynamic controlled insertedQueens: InsertedQueens
	dynamic controlled sumQueenValues: SumValues
	dynamic controlled gameResult: ResultDomain

	static queensNumber: Rows
	static kValue: Values
	static matrixValues: Prod(Rows, Columns) -> Values
	
	derived isQueenPositionOK: Prod(Rows,Columns) -> Boolean
	derived queenInRow: Rows -> Boolean
	derived queenInColumn: Columns -> Boolean
	derived queenInDiagonals: Prod(Rows, Columns) -> Boolean
	
definitions:
	domain Rows = {1:5}
	domain Columns = {1:5}
	domain Values = {1:25}
	domain SumValues = {0:484}
	domain InsertedQueens = {0:5}

	function queensNumber = 5
	function kValue = 45

	//Board 1: it does not permit to find a solution
	function matrixValues($r in Rows,$c in Columns)=
		at({(1, 1) -> 14, (1, 2) -> 13, (1, 3) -> 25, (1, 4) ->  4, (1, 5) -> 11,
		    (2, 1) ->  6, (2, 2) -> 21, (2, 3) -> 17, (2, 4) -> 24, (2, 5) ->  7,
		    (3, 1) -> 19, (3, 2) ->  2, (3, 3) -> 23, (3, 4) -> 22, (3, 5) -> 20,
		    (4, 1) -> 15, (4, 2) ->  8, (4, 3) ->  5, (4, 4) ->  9, (4, 5) ->  3,
		    (5, 1) -> 12, (5, 2) ->  1, (5, 3) -> 18, (5, 4) -> 16, (5, 5) -> 10}, ($r, $c))

	//Board 2: it permits to find a solution with value greater than K
	/*function matrixValues($r in Rows,$c in Columns)=
	at({(1, 1) -> 18, (1, 2) -> 5,(1, 3) -> 4,(1, 4) -> 24,(1, 5) -> 8,
	    (2, 1) -> 23, (2, 2) -> 20,(2, 3) -> 15,(2, 4) -> 12,(2, 5) -> 7,
	    (3, 1) -> 14, (3, 2) -> 11,(3, 3) -> 25,(3, 4) -> 9,(3, 5) -> 17,
	    (4, 1) -> 6, (4, 2) -> 2,(4, 3) -> 19,(4, 4) -> 10,(4, 5) -> 22,
	    (5, 1) -> 16, (5, 2) -> 21,(5, 3) -> 13,(5, 4) -> 3,(5, 5) -> 1}, ($r, $c))*/

	//Board 3: it permits to find a solution, but only with value less than K
	/*function matrixValues($r in Rows,$c in Columns) =
		at({(1, 1) -> 18, (1, 2) -> 5,(1, 3) -> 24,(1, 4) -> 4,(1, 5) -> 23,
		    (2, 1) -> 8, (2, 2) -> 20,(2, 3) -> 15,(2, 4) -> 12,(2, 5) -> 7,
		    (3, 1) -> 14, (3, 2) -> 11,(3, 3) -> 25,(3, 4) -> 9,(3, 5) -> 17,
		    (4, 1) -> 6, (4, 2) -> 22,(4, 3) -> 19,(4, 4) -> 10,(4, 5) -> 2,
		    (5, 1) -> 16, (5, 2) -> 1,(5, 3) -> 13,(5, 4) -> 3,(5, 5) -> 21}, ($r, $c))*/

	function isQueenPositionOK($r in Rows, $c in Columns) =
		(not(queenInRow($r)) and not(queenInColumn($c)) and not(queenInDiagonals($r, $c)))

	function queenInRow($r in Rows) =
		(exist $c in Columns with queensMatrix($r, $c))

	function queenInColumn($c in Columns) =
		(exist $r in Rows with queensMatrix($r, $c))

	function queenInDiagonals($r in Rows, $c in Columns) =
		(exist $x in Rows, $y in Columns with $x - $y = $r - $c and queensMatrix($x, $y)) or 
		(exist $i in Rows, $j in Columns with $i + $j = $r + $c and queensMatrix($i, $j))

	macro rule r_insert_queen($r in Rows, $c in Columns) =
		par
			queensMatrix($r,$c) := true
			sumQueenValues := sumQueenValues + matrixValues($r,$c)
			insertedQueens := insertedQueens + 1
		endpar

	macro rule r_greedySearch =
		choose $r in Rows, $c in Columns with 
								isQueenPositionOK($r, $c) and
								(forall $r2 in Rows, $c2 in Columns with isQueenPositionOK($r2, $c2) implies
				                                         matrixValues($r2, $c2) <= matrixValues($r, $c)) do
			r_insert_queen[$r, $c]

	//Board 1
	//Not possible to place 5 queens
	CTLSPEC not(ef(insertedQueens = queensNumber))
	//equivalent to the previous one
	CTLSPEC ag(insertedQueens < queensNumber)
	//at most, 4 queens can be inserted
	CTLSPEC ef(insertedQueens = 4 and ag(insertedQueens = 4))

	//Board 2
	//It's possible to find a solution with 5 queens and with a sum greater than K
	//CTLSPEC ef(insertedQueens = queensNumber and sumQueenValues >= kValue)
	//CTLSPEC ef(insertedQueens = queensNumber and sumQueenValues >= kValue and ag(insertedQueens = queensNumber and sumQueenValues >= kValue))

	//Board 3
	//It's possible to find a solution with 5 queens
	//CTLSPEC ef(insertedQueens = queensNumber and ag(insertedQueens = queensNumber))
	//It's not possible to find a solution with value greater than K
	//CTLSPEC not(ef(insertedQueens = queensNumber and sumQueenValues >= kValue))
	//CTLSPEC ag(sumQueenValues < kValue)

	main rule r_Main =
		if insertedQueens < queensNumber then
			r_greedySearch[]
		endif

default init s0:
	function insertedQueens = 0
	function sumQueenValues = 0
	function queensMatrix($r in Rows, $c in Columns) = false