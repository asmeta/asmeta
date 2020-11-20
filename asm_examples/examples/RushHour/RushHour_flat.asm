//applied flatteners: FR 
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
        par
            if eq(board(2,2),$car) then
                par
                    if eq(board(mod(plus(2,rMove($dir)),6),mod(plus(2,cMove($dir)),6)),undef) then
                        board(mod(plus(2,rMove($dir)),6),mod(plus(2,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(2,rMoveP($dir)),6),mod(plus(2,cMoveP($dir)),6)),$car) then
                        board(2,2) := undef
                    endif
                endpar
            endif
            if eq(board(2,4),$car) then
                par
                    if eq(board(mod(plus(2,rMove($dir)),6),mod(plus(4,cMove($dir)),6)),undef) then
                        board(mod(plus(2,rMove($dir)),6),mod(plus(4,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(2,rMoveP($dir)),6),mod(plus(4,cMoveP($dir)),6)),$car) then
                        board(2,4) := undef
                    endif
                endpar
            endif
            if eq(board(2,3),$car) then
                par
                    if eq(board(mod(plus(2,rMove($dir)),6),mod(plus(3,cMove($dir)),6)),undef) then
                        board(mod(plus(2,rMove($dir)),6),mod(plus(3,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(2,rMoveP($dir)),6),mod(plus(3,cMoveP($dir)),6)),$car) then
                        board(2,3) := undef
                    endif
                endpar
            endif
            if eq(board(2,0),$car) then
                par
                    if eq(board(mod(plus(2,rMove($dir)),6),mod(plus(0,cMove($dir)),6)),undef) then
                        board(mod(plus(2,rMove($dir)),6),mod(plus(0,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(2,rMoveP($dir)),6),mod(plus(0,cMoveP($dir)),6)),$car) then
                        board(2,0) := undef
                    endif
                endpar
            endif
            if eq(board(2,5),$car) then
                par
                    if eq(board(mod(plus(2,rMove($dir)),6),mod(plus(5,cMove($dir)),6)),undef) then
                        board(mod(plus(2,rMove($dir)),6),mod(plus(5,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(2,rMoveP($dir)),6),mod(plus(5,cMoveP($dir)),6)),$car) then
                        board(2,5) := undef
                    endif
                endpar
            endif
            if eq(board(2,1),$car) then
                par
                    if eq(board(mod(plus(2,rMove($dir)),6),mod(plus(1,cMove($dir)),6)),undef) then
                        board(mod(plus(2,rMove($dir)),6),mod(plus(1,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(2,rMoveP($dir)),6),mod(plus(1,cMoveP($dir)),6)),$car) then
                        board(2,1) := undef
                    endif
                endpar
            endif
            if eq(board(4,2),$car) then
                par
                    if eq(board(mod(plus(4,rMove($dir)),6),mod(plus(2,cMove($dir)),6)),undef) then
                        board(mod(plus(4,rMove($dir)),6),mod(plus(2,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(4,rMoveP($dir)),6),mod(plus(2,cMoveP($dir)),6)),$car) then
                        board(4,2) := undef
                    endif
                endpar
            endif
            if eq(board(4,4),$car) then
                par
                    if eq(board(mod(plus(4,rMove($dir)),6),mod(plus(4,cMove($dir)),6)),undef) then
                        board(mod(plus(4,rMove($dir)),6),mod(plus(4,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(4,rMoveP($dir)),6),mod(plus(4,cMoveP($dir)),6)),$car) then
                        board(4,4) := undef
                    endif
                endpar
            endif
            if eq(board(4,3),$car) then
                par
                    if eq(board(mod(plus(4,rMove($dir)),6),mod(plus(3,cMove($dir)),6)),undef) then
                        board(mod(plus(4,rMove($dir)),6),mod(plus(3,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(4,rMoveP($dir)),6),mod(plus(3,cMoveP($dir)),6)),$car) then
                        board(4,3) := undef
                    endif
                endpar
            endif
            if eq(board(4,0),$car) then
                par
                    if eq(board(mod(plus(4,rMove($dir)),6),mod(plus(0,cMove($dir)),6)),undef) then
                        board(mod(plus(4,rMove($dir)),6),mod(plus(0,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(4,rMoveP($dir)),6),mod(plus(0,cMoveP($dir)),6)),$car) then
                        board(4,0) := undef
                    endif
                endpar
            endif
            if eq(board(4,5),$car) then
                par
                    if eq(board(mod(plus(4,rMove($dir)),6),mod(plus(5,cMove($dir)),6)),undef) then
                        board(mod(plus(4,rMove($dir)),6),mod(plus(5,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(4,rMoveP($dir)),6),mod(plus(5,cMoveP($dir)),6)),$car) then
                        board(4,5) := undef
                    endif
                endpar
            endif
            if eq(board(4,1),$car) then
                par
                    if eq(board(mod(plus(4,rMove($dir)),6),mod(plus(1,cMove($dir)),6)),undef) then
                        board(mod(plus(4,rMove($dir)),6),mod(plus(1,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(4,rMoveP($dir)),6),mod(plus(1,cMoveP($dir)),6)),$car) then
                        board(4,1) := undef
                    endif
                endpar
            endif
            if eq(board(3,2),$car) then
                par
                    if eq(board(mod(plus(3,rMove($dir)),6),mod(plus(2,cMove($dir)),6)),undef) then
                        board(mod(plus(3,rMove($dir)),6),mod(plus(2,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(3,rMoveP($dir)),6),mod(plus(2,cMoveP($dir)),6)),$car) then
                        board(3,2) := undef
                    endif
                endpar
            endif
            if eq(board(3,4),$car) then
                par
                    if eq(board(mod(plus(3,rMove($dir)),6),mod(plus(4,cMove($dir)),6)),undef) then
                        board(mod(plus(3,rMove($dir)),6),mod(plus(4,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(3,rMoveP($dir)),6),mod(plus(4,cMoveP($dir)),6)),$car) then
                        board(3,4) := undef
                    endif
                endpar
            endif
            if eq(board(3,3),$car) then
                par
                    if eq(board(mod(plus(3,rMove($dir)),6),mod(plus(3,cMove($dir)),6)),undef) then
                        board(mod(plus(3,rMove($dir)),6),mod(plus(3,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(3,rMoveP($dir)),6),mod(plus(3,cMoveP($dir)),6)),$car) then
                        board(3,3) := undef
                    endif
                endpar
            endif
            if eq(board(3,0),$car) then
                par
                    if eq(board(mod(plus(3,rMove($dir)),6),mod(plus(0,cMove($dir)),6)),undef) then
                        board(mod(plus(3,rMove($dir)),6),mod(plus(0,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(3,rMoveP($dir)),6),mod(plus(0,cMoveP($dir)),6)),$car) then
                        board(3,0) := undef
                    endif
                endpar
            endif
            if eq(board(3,5),$car) then
                par
                    if eq(board(mod(plus(3,rMove($dir)),6),mod(plus(5,cMove($dir)),6)),undef) then
                        board(mod(plus(3,rMove($dir)),6),mod(plus(5,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(3,rMoveP($dir)),6),mod(plus(5,cMoveP($dir)),6)),$car) then
                        board(3,5) := undef
                    endif
                endpar
            endif
            if eq(board(3,1),$car) then
                par
                    if eq(board(mod(plus(3,rMove($dir)),6),mod(plus(1,cMove($dir)),6)),undef) then
                        board(mod(plus(3,rMove($dir)),6),mod(plus(1,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(3,rMoveP($dir)),6),mod(plus(1,cMoveP($dir)),6)),$car) then
                        board(3,1) := undef
                    endif
                endpar
            endif
            if eq(board(0,2),$car) then
                par
                    if eq(board(mod(plus(0,rMove($dir)),6),mod(plus(2,cMove($dir)),6)),undef) then
                        board(mod(plus(0,rMove($dir)),6),mod(plus(2,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(0,rMoveP($dir)),6),mod(plus(2,cMoveP($dir)),6)),$car) then
                        board(0,2) := undef
                    endif
                endpar
            endif
            if eq(board(0,4),$car) then
                par
                    if eq(board(mod(plus(0,rMove($dir)),6),mod(plus(4,cMove($dir)),6)),undef) then
                        board(mod(plus(0,rMove($dir)),6),mod(plus(4,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(0,rMoveP($dir)),6),mod(plus(4,cMoveP($dir)),6)),$car) then
                        board(0,4) := undef
                    endif
                endpar
            endif
            if eq(board(0,3),$car) then
                par
                    if eq(board(mod(plus(0,rMove($dir)),6),mod(plus(3,cMove($dir)),6)),undef) then
                        board(mod(plus(0,rMove($dir)),6),mod(plus(3,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(0,rMoveP($dir)),6),mod(plus(3,cMoveP($dir)),6)),$car) then
                        board(0,3) := undef
                    endif
                endpar
            endif
            if eq(board(0,0),$car) then
                par
                    if eq(board(mod(plus(0,rMove($dir)),6),mod(plus(0,cMove($dir)),6)),undef) then
                        board(mod(plus(0,rMove($dir)),6),mod(plus(0,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(0,rMoveP($dir)),6),mod(plus(0,cMoveP($dir)),6)),$car) then
                        board(0,0) := undef
                    endif
                endpar
            endif
            if eq(board(0,5),$car) then
                par
                    if eq(board(mod(plus(0,rMove($dir)),6),mod(plus(5,cMove($dir)),6)),undef) then
                        board(mod(plus(0,rMove($dir)),6),mod(plus(5,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(0,rMoveP($dir)),6),mod(plus(5,cMoveP($dir)),6)),$car) then
                        board(0,5) := undef
                    endif
                endpar
            endif
            if eq(board(0,1),$car) then
                par
                    if eq(board(mod(plus(0,rMove($dir)),6),mod(plus(1,cMove($dir)),6)),undef) then
                        board(mod(plus(0,rMove($dir)),6),mod(plus(1,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(0,rMoveP($dir)),6),mod(plus(1,cMoveP($dir)),6)),$car) then
                        board(0,1) := undef
                    endif
                endpar
            endif
            if eq(board(5,2),$car) then
                par
                    if eq(board(mod(plus(5,rMove($dir)),6),mod(plus(2,cMove($dir)),6)),undef) then
                        board(mod(plus(5,rMove($dir)),6),mod(plus(2,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(5,rMoveP($dir)),6),mod(plus(2,cMoveP($dir)),6)),$car) then
                        board(5,2) := undef
                    endif
                endpar
            endif
            if eq(board(5,4),$car) then
                par
                    if eq(board(mod(plus(5,rMove($dir)),6),mod(plus(4,cMove($dir)),6)),undef) then
                        board(mod(plus(5,rMove($dir)),6),mod(plus(4,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(5,rMoveP($dir)),6),mod(plus(4,cMoveP($dir)),6)),$car) then
                        board(5,4) := undef
                    endif
                endpar
            endif
            if eq(board(5,3),$car) then
                par
                    if eq(board(mod(plus(5,rMove($dir)),6),mod(plus(3,cMove($dir)),6)),undef) then
                        board(mod(plus(5,rMove($dir)),6),mod(plus(3,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(5,rMoveP($dir)),6),mod(plus(3,cMoveP($dir)),6)),$car) then
                        board(5,3) := undef
                    endif
                endpar
            endif
            if eq(board(5,0),$car) then
                par
                    if eq(board(mod(plus(5,rMove($dir)),6),mod(plus(0,cMove($dir)),6)),undef) then
                        board(mod(plus(5,rMove($dir)),6),mod(plus(0,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(5,rMoveP($dir)),6),mod(plus(0,cMoveP($dir)),6)),$car) then
                        board(5,0) := undef
                    endif
                endpar
            endif
            if eq(board(5,5),$car) then
                par
                    if eq(board(mod(plus(5,rMove($dir)),6),mod(plus(5,cMove($dir)),6)),undef) then
                        board(mod(plus(5,rMove($dir)),6),mod(plus(5,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(5,rMoveP($dir)),6),mod(plus(5,cMoveP($dir)),6)),$car) then
                        board(5,5) := undef
                    endif
                endpar
            endif
            if eq(board(5,1),$car) then
                par
                    if eq(board(mod(plus(5,rMove($dir)),6),mod(plus(1,cMove($dir)),6)),undef) then
                        board(mod(plus(5,rMove($dir)),6),mod(plus(1,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(5,rMoveP($dir)),6),mod(plus(1,cMoveP($dir)),6)),$car) then
                        board(5,1) := undef
                    endif
                endpar
            endif
            if eq(board(1,2),$car) then
                par
                    if eq(board(mod(plus(1,rMove($dir)),6),mod(plus(2,cMove($dir)),6)),undef) then
                        board(mod(plus(1,rMove($dir)),6),mod(plus(2,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(1,rMoveP($dir)),6),mod(plus(2,cMoveP($dir)),6)),$car) then
                        board(1,2) := undef
                    endif
                endpar
            endif
            if eq(board(1,4),$car) then
                par
                    if eq(board(mod(plus(1,rMove($dir)),6),mod(plus(4,cMove($dir)),6)),undef) then
                        board(mod(plus(1,rMove($dir)),6),mod(plus(4,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(1,rMoveP($dir)),6),mod(plus(4,cMoveP($dir)),6)),$car) then
                        board(1,4) := undef
                    endif
                endpar
            endif
            if eq(board(1,3),$car) then
                par
                    if eq(board(mod(plus(1,rMove($dir)),6),mod(plus(3,cMove($dir)),6)),undef) then
                        board(mod(plus(1,rMove($dir)),6),mod(plus(3,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(1,rMoveP($dir)),6),mod(plus(3,cMoveP($dir)),6)),$car) then
                        board(1,3) := undef
                    endif
                endpar
            endif
            if eq(board(1,0),$car) then
                par
                    if eq(board(mod(plus(1,rMove($dir)),6),mod(plus(0,cMove($dir)),6)),undef) then
                        board(mod(plus(1,rMove($dir)),6),mod(plus(0,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(1,rMoveP($dir)),6),mod(plus(0,cMoveP($dir)),6)),$car) then
                        board(1,0) := undef
                    endif
                endpar
            endif
            if eq(board(1,5),$car) then
                par
                    if eq(board(mod(plus(1,rMove($dir)),6),mod(plus(5,cMove($dir)),6)),undef) then
                        board(mod(plus(1,rMove($dir)),6),mod(plus(5,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(1,rMoveP($dir)),6),mod(plus(5,cMoveP($dir)),6)),$car) then
                        board(1,5) := undef
                    endif
                endpar
            endif
            if eq(board(1,1),$car) then
                par
                    if eq(board(mod(plus(1,rMove($dir)),6),mod(plus(1,cMove($dir)),6)),undef) then
                        board(mod(plus(1,rMove($dir)),6),mod(plus(1,cMove($dir)),6)) := $car
                    endif
                    if neq(board(mod(plus(1,rMoveP($dir)),6),mod(plus(1,cMoveP($dir)),6)),$car) then
                        board(1,1) := undef
                    endif
                endpar
            endif
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
