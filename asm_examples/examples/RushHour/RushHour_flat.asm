//applied flatteners: AR 
asm RushHour_flat
import ../../STDL/StandardLibrary

signature:
    enum domain Dir = {NORTH | SOUTH | EAST | WEST}
    domain Coord subsetof Integer
    domain Num subsetof Integer
    domain Car subsetof Integer

    controlled board: Prod(Coord, Coord) -> Car
    static isDirMachine: Prod(Car, Dir) -> Boolean
    derived isNextCellFree: Prod(Car, Dir) -> Boolean
    derived isMovePermitted: Prod(Car, Dir) -> Boolean
    derived rMove: Dir -> Num
    derived cMove: Dir -> Num
    derived rMoveP: Dir -> Num
    derived cMoveP: Dir -> Num
    static redCar: Num
    static redCarAtExit: Boolean

definitions:

    domain Coord = {0,1,2,3,4,5}
    domain Num = {1,5,0}
    domain Car = {1,2,3,4,5,6}

    function isDirMachine($car in Car, $dir in Dir) = if or(eq($dir,NORTH),eq($dir,SOUTH)) then (exist $r in Coord,$c in Coord with and(eq(board($r,$c),$car),eq(board(mod(plus($r,1),6),$c),$car))) else (exist $r2 in Coord,$c2 in Coord with and(eq(board($r2,$c2),$car),eq(board($r2,mod(plus($c2,1),6)),$car))) endif
    function rMove($d in Dir) = if eq($d,NORTH) then 5 else if eq($d,SOUTH) then 1 else 0 endif endif
    function cMove($d in Dir) = if eq($d,WEST) then 5 else if eq($d,EAST) then 1 else 0 endif endif
    function rMoveP($d in Dir) = if eq($d,NORTH) then rMove(SOUTH) else if eq($d,SOUTH) then rMove(NORTH) else 0 endif endif
    function cMoveP($d in Dir) = if eq($d,WEST) then cMove(EAST) else if eq($d,EAST) then cMove(WEST) else 0 endif endif
    function isNextCellFree($car in Car, $dir in Dir) = switch $dir case NORTH:(exist $r in Coord,$c in Coord with and(and(gt($r,0),eq(board($r,$c),$car)),eq(board(mod(plus($r,5),6),$c),undef))) case SOUTH:(exist $r1 in Coord,$c1 in Coord with and(and(lt($r1,5),eq(board($r1,$c1),$car)),eq(board(mod(plus($r1,1),6),$c1),undef))) case WEST:(exist $r3 in Coord,$c3 in Coord with and(and(gt($c3,0),eq(board($r3,$c3),$car)),eq(board($r3,mod(plus($c3,5),6)),undef))) case EAST:(exist $r2 in Coord,$c2 in Coord with and(and(lt($c2,5),eq(board($r2,$c2),$car)),eq(board($r2,mod(plus($c2,1),6)),undef))) endswitch
    function isMovePermitted($car in Car, $dir in Dir) = and(isDirMachine($car,$dir),isNextCellFree($car,$dir))
    function redCar = 1
    function redCarAtExit = eq(board(2,5),redCar)

    macro rule r_move($car in Car, $dir in Dir) =
        forall $r in Coord, $c in Coord with eq(board($r,$c),$car) do
            par
                let ($var_280 = rMove($dir), $var_281 = cMove($dir)) in
                    if eq(board(mod(plus($r,$var_280),6),mod(plus($c,$var_281),6)),undef) then
                        let ($var_282 = rMove($dir), $var_283 = cMove($dir)) in
                            board(mod(plus($r,$var_282),6),mod(plus($c,$var_283),6)) := $car
                        endlet
                    endif
                endlet
                let ($var_284 = rMoveP($dir), $var_285 = cMoveP($dir)) in
                    if neq(board(mod(plus($r,$var_284),6),mod(plus($c,$var_285),6)),$car) then
                        board($r,$c) := undef
                    endif
                endlet
            endpar


    invariant inv_0 over board: eq(size({$r in Coord,$c in Coord| eq(board($r,$c),undef) : plus(mult($r,6),$c)}),22)
    invariant inv_1 over board: eq(size({$r in Coord,$c in Coord| eq(board($r,$c),redCar) : plus(mult($r,6),$c)}),2)
    invariant inv_2 over board: eq(size({$r in Coord,$c in Coord| eq(board($r,$c),2) : plus(mult($r,6),$c)}),3)
    invariant inv_3 over board: eq(size({$r in Coord,$c in Coord| eq(board($r,$c),3) : plus(mult($r,6),$c)}),2)
    invariant inv_4 over board: eq(size({$r in Coord,$c in Coord| eq(board($r,$c),4) : plus(mult($r,6),$c)}),3)
    invariant inv_5 over board: eq(size({$r in Coord,$c in Coord| eq(board($r,$c),5) : plus(mult($r,6),$c)}),2)
    invariant inv_6 over board: eq(size({$r in Coord,$c in Coord| eq(board($r,$c),6) : plus(mult($r,6),$c)}),2)
    main rule r_Main =
        if not(redCarAtExit) then
            choose $car in Car, $dir in Dir with isMovePermitted($car,$dir) do
                r_move[$car,$dir]
        endif

default init s0:
    function board($r in Coord, $c in Coord) = at({(2,1)->redCar,(2,2)->redCar,(2,3)->2,(3,1)->3,(3,2)->3,(3,3)->2,(3,5)->4,(4,1)->5,(4,3)->2,(4,5)->4,(5,1)->5,(5,2)->6,(5,3)->6,(5,5)->4},($r,$c))
