asm gameOfLifeAgents

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

//ASM model with agents: the agent itself identifies the cell it is placed.

import ../../STDL/StandardLibrary

signature:
	domain Cell subsetof Agent
	dynamic controlled alive: Cell -> Boolean
	derived aliveNeighb: Cell -> Integer
	static neighbours: Cell -> Powerset(Cell)
	static cell11: Cell
	static cell12: Cell
	static cell13: Cell
	static cell14: Cell
	static cell21: Cell
	static cell22: Cell
	static cell23: Cell
	static cell24: Cell
	static cell31: Cell
	static cell32: Cell
	static cell33: Cell
	static cell34: Cell
	static cell41: Cell
	static cell42: Cell
	static cell43: Cell
	static cell44: Cell

definitions:

	function neighbours($c in Cell) =
		switch $c
			case cell11: {cell12, cell21, cell22}
			case cell12: {cell11, cell13, cell21, cell22, cell23}
			case cell13: {cell12, cell14, cell22, cell23, cell24}
			case cell14: {cell13, cell23, cell24}
			case cell21: {cell11, cell12, cell22, cell31, cell32}
			case cell22: {cell11, cell12, cell13, cell21, cell23, cell31, cell32, cell33}
			case cell23: {cell12, cell13, cell14, cell22, cell24, cell32, cell33, cell34}
			case cell24: {cell13, cell14, cell23, cell33, cell34}
			case cell31: {cell21, cell22, cell32, cell41, cell42}
			case cell32: {cell21, cell22, cell23, cell31, cell33, cell41, cell42, cell43}
			case cell33: {cell22, cell23, cell24, cell32, cell34, cell42, cell43, cell44}
			case cell34: {cell23, cell24, cell33, cell43, cell44}
			case cell41: {cell42, cell31, cell32}
			case cell42: {cell41, cell43, cell31, cell32, cell33}
			case cell43: {cell42, cell44, cell32, cell33, cell34}
			case cell44: {cell43, cell33, cell34}
		endswitch

	function aliveNeighb($c in Cell) =
		size({$i in Cell | contains(neighbours($c), $i) and alive($i): $i})

	rule r_conway =
		if(alive(self)) then
			if (aliveNeighb(self) < 2 or aliveNeighb(self) > 3) then
				alive(self) := false
			endif
		else
			if (aliveNeighb(self) = 3) then
				alive(self) := true
			endif
		endif

	main rule r_Main =
		forall $c in Cell with true do
			program($c)

default init s0:
	/*function alive($c in Cell) =
		switch $c
			case cell33: true
			case cell23: true
			case cell34: true
			case cell43: true
			otherwise false
		endswitch*/
		
	//Blinker (period 2)
	/*function alive($c in Cell) =
		switch $c
			case cell12: true
			case cell22: true
			case cell32: true
			otherwise false
		endswitch*/

	//Toad (period 2)
	function alive($c in Cell) =
		switch $c
			case cell11: true
			case cell12: true
			case cell21: true
			case cell34: true
			case cell43: true
			case cell44: true
			otherwise false
		endswitch

	agent Cell:
		r_conway[]
