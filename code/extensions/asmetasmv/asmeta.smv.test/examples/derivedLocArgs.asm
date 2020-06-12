asm derivedLocArgs

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain Y subsetof Integer
	domain X subsetof Integer
	enum domain Moves = { NORTH|SOUTH|EAST|WEST|NONE}
	dynamic controlled playerPosY: Y
	dynamic controlled playerPosX: X	
	dynamic controlled north: Prod (X,Y) -> Boolean
	dynamic controlled east: Prod (X,Y) -> Boolean
	dynamic controlled south: Prod (X,Y) -> Boolean
	dynamic controlled west: Prod (X,Y) -> Boolean
	dynamic monitored move : Moves
	derived moveExists: Moves -> Boolean
	
definitions:	
	domain X = {1..2}
	domain Y = {1..2}

	function moveExists($a in Moves) =
		switch ($a)
			case NORTH: north(playerPosX, playerPosY)=true
			case EAST: east(playerPosX, playerPosY)=true
			case SOUTH: south(playerPosX, playerPosY)=true
			case WEST: west(playerPosX, playerPosY)=true
			case NONE: false
		endswitch

	CTLSPEC (forall $x in X, $y in Y with ag((playerPosX = $x and playerPosY = $y) implies (moveExists(NORTH) iff north($x, $y))))
	CTLSPEC (forall $x in X, $y in Y with ag((playerPosX = $x and playerPosY = $y) implies (moveExists(SOUTH) iff south($x, $y))))
	CTLSPEC (forall $x in X, $y in Y with ag((playerPosX = $x and playerPosY = $y) implies (moveExists(WEST) iff west($x, $y))))
	CTLSPEC (forall $x in X, $y in Y with ag((playerPosX = $x and playerPosY = $y) implies (moveExists(EAST) iff east($x, $y))))

	main rule r_Main =
		if(moveExists(move)) then
			choose $x in X, $y in Y with true do
				par
					playerPosX := $x
					playerPosY := $y
				endpar
		endif

default init s0:
	function playerPosX = 1
	function playerPosY = 1
	function north ( $e in X, $f in Y) =
		at({(1,1) -> true,  (2,1) -> false,
			(1,2) -> false}, ($e,$f))
	function east ( $e in X, $f in Y) =
		at({(1,1) -> true,  (2,1) -> false,
			(1,2) -> false}, ($e,$f))
	function south ( $e in X, $f in Y) =
		at({(1,1) -> true,  (2,1) -> true,
			(1,2) -> true}, ($e,$f))
	function west ( $e in X, $f in Y) =
		at({(1,1) -> false,  (2,1) -> false,
			(1,2) -> true}, ($e,$f))