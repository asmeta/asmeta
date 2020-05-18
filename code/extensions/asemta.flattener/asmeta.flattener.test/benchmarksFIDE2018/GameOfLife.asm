asm GameOfLife

//The version described in the ASM book is slightly different.

//This is the version described in Wikipedia

//The universe of the Game of Life is an infinite two-dimensional orthogonal
//grid of square cells, each of which is in one of two possible states, live or dead.
//Every cell interacts with its eight neighbours, which are the cells that are horizontally,
//vertically, or diagonally adjacent. At each step in time, the following transitions occur:
//1. Any live cell with fewer than two live neighbours dies, as if caused by under-population.
//2. Any live cell with two or three live neighbours lives on to the next generation.
//3. Any live cell with more than three live neighbours dies, as if by overcrowding.
//4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

//The initial pattern constitutes the seed of the system. The first generation is
//created by applying the above rules simultaneously to every cell in the seed—births
//and deaths occur simultaneously, and the discrete moment at which this happens
//is sometimes called a tick (in other words, each generation is a pure function of the
//preceding one). The rules continue to be applied repeatedly to create further generations.

import ./STDL/StandardLibrary

signature:
	domain RowsWorld subsetof Natural
	domain ColumnsWorld subsetof Natural 
	dynamic controlled alive: Prod(RowsWorld, ColumnsWorld) -> Boolean
	derived aliveNeighb: Prod(RowsWorld, ColumnsWorld) -> Integer
	derived neighbours: Prod(RowsWorld, ColumnsWorld) -> Powerset(Prod(RowsWorld, ColumnsWorld))

definitions:
	domain RowsWorld = {1n .. 4n}
	domain ColumnsWorld = {1n .. 4n}

	function neighbours($r in RowsWorld, $c in ColumnsWorld) =
		{$i in RowsWorld, $j in ColumnsWorld | (abs($r - $i) + abs($c - $j) = 1) or (abs($r - $i) = 1 and abs($c - $j) = 1): ($i , $j)}

	function aliveNeighb($r in RowsWorld, $c in ColumnsWorld) =
		size({$h in RowsWorld, $k in ColumnsWorld | contains(neighbours($r, $c), ($h, $k)) and alive($h, $k): ($h , $k)})

	rule r_conway($r in RowsWorld, $c in ColumnsWorld) =
		if(alive($r, $c)) then
			if (aliveNeighb($r, $c) < 2 or aliveNeighb($r, $c) > 3) then
				alive($r, $c) := false
			endif
		else
			if (aliveNeighb($r, $c) = 3) then
				alive($r, $c) := true
			endif
		endif

	main rule r_Main =
		forall $r in RowsWorld, $c in ColumnsWorld with true do
			r_conway[$r, $c]

default init s0:
	//Toad (period 2)
	function alive($r in RowsWorld, $c in ColumnsWorld) =
		switch($r, $c)
			case (1n, 1n): true
			case (1n, 2n): true
			case (2n, 1n): true
			case (3n, 4n): true
			case (4n, 3n): true
			case (4n, 4n): true
			otherwise false
		endswitch