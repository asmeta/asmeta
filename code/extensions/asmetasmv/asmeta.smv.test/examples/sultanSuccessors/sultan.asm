asm sultan

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLLibrary

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
	static kValue: SumValues
	static matrixValues: Prod(Rows, Columns) -> Values
	
	derived isQueenPositionOK: Prod(Rows,Columns) -> Boolean
	derived queenInRow: Rows -> Boolean
	derived queenInColumn: Columns -> Boolean
	derived queenInDiagonals: Prod(Rows, Columns) -> Boolean
	
definitions:
	domain Rows = {1 : 5}
	domain Columns = {1 : 5}
	domain Values = {1:25}
	domain SumValues = {0:484}
	domain InsertedQueens = {0:5}

	function queensNumber = 5
	function kValue = 45
	
	function matrixValues($r in Rows,$c in Columns)=
		at({(1, 1) -> 14, (1, 2) -> 13, (1, 3) -> 25, (1, 4) ->  4, (1, 5) -> 11,
		    (2, 1) ->  6, (2, 2) -> 21, (2, 3) -> 17, (2, 4) -> 24, (2, 5) ->  7,
		    (3, 1) -> 19, (3, 2) ->  2, (3, 3) -> 23, (3, 4) -> 22, (3, 5) -> 20,
		    (4, 1) -> 15, (4, 2) ->  8, (4, 3) ->  5, (4, 4) ->  9, (4, 5) ->  3,
		    (5, 1) -> 12, (5, 2) ->  1, (5, 3) -> 18, (5, 4) -> 16, (5, 5) -> 10}, ($r, $c))

	function isQueenPositionOK($r in Rows, $c in Columns) =
		(not(queenInRow($r)) and not(queenInColumn($c)) and not(queenInDiagonals($r, $c)))

	function queenInRow($r in Rows) =
		(exist $c in Columns with queensMatrix($r, $c))

	function queenInColumn($c in Columns) =
		(exist $r in Rows with queensMatrix($r, $c))


	function queenInDiagonals($r in Rows, $c in Columns) =
		(exist $x in Rows, $y in Columns with $x - $y = $r - $c and queensMatrix($x, $y)) or 
		(exist $i in Rows, $j in Columns with $i + $j = $r + $c and queensMatrix($i, $j))


	macro rule r_insert_queen =
		if insertedQueens < queensNumber then
			let ($r = rowCoord, $c = colCoord) in
				if (isQueenPositionOK($r, $c)) then
					par
						queensMatrix($r, $c) := true
						sumQueenValues := sumQueenValues + matrixValues($r, $c)
						insertedQueens := insertedQueens + 1
					endpar
				endif
			endlet
		endif
	
	macro rule r_check_victory =
		if insertedQueens = queensNumber then
			if sumQueenValues >= kValue then
				gameResult := WIN
			else
				gameResult := LOST
			endif
		endif

	//true starting from any initial state
	CTLSPEC ef(insertedQueens = queensNumber and sumQueenValues >= kValue)
	//we look for a counterexample
	CTLSPEC not(ef(insertedQueens = queensNumber and sumQueenValues >= kValue))

	//false. We do not know if it is false starting from any initial state
	CTLSPEC ef(insertedQueens = queensNumber and sumQueenValues < kValue)
	//we look for a counterexample. If a counterexample is found, it means that
	//there exists a state from which the previous property is true. 
	CTLSPEC not(ef(insertedQueens = queensNumber and sumQueenValues < kValue))

	main rule r_Main =
		par
			r_insert_queen[]
			r_check_victory[]
		endpar

default init s0:
	function queensMatrix($r in Rows, $c in Columns) = false
	function gameResult = NOTFINISHED
	function insertedQueens = 0
	function sumQueenValues = 0