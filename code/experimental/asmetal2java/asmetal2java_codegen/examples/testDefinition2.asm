// a simple example with a tic tac toe game

asm testDefinition2

import STDL/StandardLibrary

signature:

    abstract domain Lift

    enum domain Actors = {FERRYMAN | GOAT | CABBAGE | WOLF}
    enum domain Side = {LEFT | RIGHT}
	enum domain Product = {COFFEE | TEA | MILK}
	domain QuantityDomain subsetof Integer
	domain CoinDomain subsetof Integer
	domain State subsetof Natural
	domain RowsWorld subsetof Natural
	domain ColumnsWorld subsetof Natural

	enum domain DoorStatus = {CLOSED | OPENING | OPEN | CLOSING}
	enum domain GearStatus = {RETRACTED | EXTENDING | EXTENDED | RETRACTING}
	enum domain Dir = {UP | DOWN}
    enum domain StateS = {HALTING | MOVING}
    domain Floor subsetof Integer



	dynamic controlled numA: Integer
	dynamic controlled numB: Integer
	//dynamic controlled indice: Integer

	controlled available: Product -> QuantityDomain
	controlled coins: CoinDomain

	dynamic controlled position: Actors -> Side

	dynamic controlled num_fibo: Integer
	dynamic controlled indice: Integer
	dynamic controlled succ_fibo: Seq(Integer)

	dynamic monitored high : Boolean
    dynamic monitored low : Boolean
    dynamic controlled ctl_state : State

    dynamic controlled alive: Prod(RowsWorld, ColumnsWorld) -> Boolean

    dynamic controlled doors: DoorStatus
	dynamic controlled gears: GearStatus


    dynamic controlled generalElectroValve: Boolean
	dynamic controlled openDoorsElectroValve: Boolean
	dynamic controlled closeDoorsElectroValve: Boolean
	dynamic controlled retractGearsElectroValve: Boolean
	dynamic controlled extendGearsElectroValve: Boolean


	dynamic controlled direction:  Lift -> Dir
    dynamic controlled ctlState: Lift -> StateS
    dynamic controlled floor:  Lift -> Floor

     static lift1: Lift
     static lift2: Lift

     dynamic controlled controlledfunction: Integer
     dynamic monitored monitoredfunction: Integer

definitions:
domain State = {0n,1n}

	domain RowsWorld = {1n .. 4n}
	domain ColumnsWorld = {1n .. 4n}


	// MAIN RULE
	main rule r_Main =
      skip

// INITIAL STATE
default init s0:
	function coins = 0
	function available($p in Product) = 10

	function numA = 6409 //121
	function numB = 3289 //22

	//function indice = 1

	function position($a in Actors) = LEFT

	//function succ_fibo = [0, 1]
	function indice = 2
	function num_fibo = 0

    function ctl_state = 0n
	function high = false
	function low = false

	/*function alive($r in RowsWorld, $c in ColumnsWorld) =
		switch($r, $c)
			case (3n, 3n): true
			case (2n, 3n): true
			case (3n, 4n): true
			case (4n, 3n): true
			otherwise false
		endswitch*/

	//Blinker (period 2)
	/*function alive($r in RowsWorld, $c in ColumnsWorld) =
		switch($r, $c)
			case (1n, 2n): true
			case (2n, 2n): true
			case (3n, 2n): true
			otherwise false
		endswitch*/

	//Toad (period 2)
	/*
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
	*/
	function doors = CLOSED
	function gears = EXTENDED
	function generalElectroValve = false
	function extendGearsElectroValve = false
	function retractGearsElectroValve = false
	function openDoorsElectroValve = false
	function closeDoorsElectroValve = false


	function floor($l in Lift) = 0
	function direction($l in Lift) = UP
	function ctlState($l in Lift)= HALTING


	function controlledfunction = monitoredfunction+1




