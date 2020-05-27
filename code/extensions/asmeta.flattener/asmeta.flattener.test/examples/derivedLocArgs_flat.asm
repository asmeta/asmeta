//applied flatteners: AR 
asm derivedLocArgs_flat
import ../../../../asm_examples/STDL/StandardLibrary
import ../../../../asm_examples/STDL/CTLlibrary

signature:
    domain Y subsetof Integer
    domain X subsetof Integer
    enum domain Moves = {NORTH | SOUTH | EAST | WEST | NONE}

    controlled playerPosY: Y
    controlled playerPosX: X
    controlled north: Prod(X, Y) -> Boolean
    controlled east: Prod(X, Y) -> Boolean
    controlled south: Prod(X, Y) -> Boolean
    controlled west: Prod(X, Y) -> Boolean
    monitored move: Moves
    derived moveExists: Moves -> Boolean

definitions:

    domain X = {1,2}
    domain Y = {1,2}

    function moveExists($a in Moves) = let($var_266=playerPosX,$var_267=playerPosY,$var_268=playerPosX,$var_269=playerPosY,$var_270=playerPosX,$var_271=playerPosY,$var_272=playerPosX,$var_273=playerPosY) in switch $a case NORTH:eq(north($var_266,$var_267),true) case EAST:eq(east($var_268,$var_269),true) case SOUTH:eq(south($var_270,$var_271),true) case WEST:eq(west($var_272,$var_273),true) case NONE:false endswitch endlet


    CTLSPEC (forall $x in X,$y in Y with ag(implies(and(eq(playerPosX,$x),eq(playerPosY,$y)),iff(moveExists(NORTH),north($x,$y)))))
    CTLSPEC (forall $x in X,$y in Y with ag(implies(and(eq(playerPosX,$x),eq(playerPosY,$y)),iff(moveExists(SOUTH),south($x,$y)))))
    CTLSPEC (forall $x in X,$y in Y with ag(implies(and(eq(playerPosX,$x),eq(playerPosY,$y)),iff(moveExists(WEST),west($x,$y)))))
    CTLSPEC (forall $x in X,$y in Y with ag(implies(and(eq(playerPosX,$x),eq(playerPosY,$y)),iff(moveExists(EAST),east($x,$y)))))
    main rule r_Main =
        let ($var_274 = move) in
            if moveExists($var_274) then
                choose $x in X, $y in Y with true do
                    par
                        playerPosX := $x
                        playerPosY := $y
                    endpar
            endif
        endlet

default init s0:
    function playerPosX = 1
    function playerPosY = 1
    function north($e in X, $f in Y) = at({(1,1)->true,(2,1)->false,(1,2)->false},($e,$f))
    function east($e in X, $f in Y) = at({(1,1)->true,(2,1)->false,(1,2)->false},($e,$f))
    function south($e in X, $f in Y) = at({(1,1)->true,(2,1)->true,(1,2)->true},($e,$f))
    function west($e in X, $f in Y) = at({(1,1)->false,(2,1)->false,(1,2)->true},($e,$f))
