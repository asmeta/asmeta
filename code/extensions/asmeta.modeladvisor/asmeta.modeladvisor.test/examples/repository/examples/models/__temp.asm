asm __temp
import ../../STDL/StandardLibrary

signature:
    abstract domain Lift
    domain Floor subsetof Integer
    enum domain Dir = {UP | DOWN}
    enum domain State = {HALTING | MOVING}

    // added by validator
    controlled step__: Integer
    controlled dir: Lift -> Dir
    controlled ctlState: Lift -> State
    controlled floor: Lift -> Floor
    // converted to controlled by validator
    controlled hasToDeliverAt: Prod(Lift, Floor) -> Boolean
    // converted to controlled by validator
    controlled existsCallFromTo: Prod(Floor, Dir) -> Boolean
    derived hasToVisit: Prod(Lift, Floor) -> Boolean
    derived attracted: Prod(Dir, Lift) -> Boolean
    derived canContinue: Lift -> Boolean
    static opposite: Dir -> Dir
    static ground: Integer
    static top: Integer
    static lift1: Lift

definitions:

    domain Floor = {0,1,2,3,4}

    function ground = 0
    function top = 4
    function opposite($d in Dir) = if eq($d,UP) then DOWN else UP endif
    function hasToVisit($l in Lift, $floor in Floor) = or(or(hasToDeliverAt($l,$floor),existsCallFromTo($floor,UP)),existsCallFromTo($floor,DOWN))
    function attracted($dir in Dir, $l in Lift) = or(and(eq($dir,UP),(exist $floor in Floor with and(gt($floor,floor($l)),hasToVisit($l,$floor)))),and(eq($dir,DOWN),(exist $floor2 in Floor with and(lt($floor2,floor($l)),hasToVisit($l,$floor2)))))
    function canContinue($l in Lift) = and(and(attracted(dir($l),$l),not(hasToDeliverAt($l,floor($l)))),not(existsCallFromTo(floor($l),dir($l))))

    macro rule r_cancelRequest($dir in Dir, $l in Lift) =
        par
            hasToDeliverAt($l,floor($l)) := false
            existsCallFromTo(floor($l),$dir) := false
        endpar

    macro rule r_moveLift($l in Lift) =
        par
            if eq(dir($l),UP) then
                floor($l) := plus(floor($l),1)
            endif
            if eq(dir($l),DOWN) then
                floor($l) := minus(floor($l),1)
            endif
        endpar

    macro rule r_depart($l in Lift) =
        if and(eq(ctlState($l),HALTING),attracted(dir($l),$l)) then
            par
                r_moveLift[$l]
                ctlState($l) := MOVING
            endpar
        endif

    macro rule r_continue($l in Lift) =
        if and(eq(ctlState($l),MOVING),canContinue($l)) then
            r_moveLift[$l]
        endif

    macro rule r_stop($l in Lift) =
        if and(eq(ctlState($l),MOVING),not(canContinue($l))) then
            par
                r_cancelRequest[dir($l),$l]
                ctlState($l) := HALTING
            endpar
        endif

    macro rule r_change($l in Lift) =
        let ($d = dir($l), $d2 = opposite($d)) in
            if and(and(eq(ctlState($l),HALTING),not(attracted($d,$l))),attracted($d2,$l)) then
                par
                    dir($l) := $d2
                    r_cancelRequest[$d2,$l]
                endpar
            endif
        endlet

    macro rule r_lift($l in Lift) =
        par
            r_depart[$l]
            r_continue[$l]
            r_stop[$l]
            r_change[$l]
        endpar

macro rule r_main =
    r_lift[lift1]

// new main added by validator
main rule r_main__ =
switch step__
case 0:
seq
hasToDeliverAt(lift1, 0) := false
hasToDeliverAt(lift1, 1) := false
hasToDeliverAt(lift1, 2) := false
hasToDeliverAt(lift1, 3) := false
hasToDeliverAt(lift1, 4) := false
existsCallFromTo(0, UP) := false
existsCallFromTo(0, DOWN) := false
existsCallFromTo(1, UP) := false
existsCallFromTo(1, DOWN) := false
existsCallFromTo(2, UP) := false
existsCallFromTo(2, DOWN) := false
existsCallFromTo(3, UP) := false
existsCallFromTo(3, DOWN) := false
existsCallFromTo(4, UP) := false
existsCallFromTo(4, DOWN) := false
step__ := 1
endseq

case 1:
if floor(lift1) = 0 then seq
result := print("check succeeded: floor(lift1) = 0")
step__ := 2
endseq else seq
result := print("CHECK FAILED: floor(lift1) = 0")
step__ := -1
endseq endif

case 2:
seq
r_main[]
step__ := 3
endseq

case 3:
if floor(lift1) = 0 then seq
result := print("check succeeded: floor(lift1) = 0")
step__ := 4
endseq else seq
result := print("CHECK FAILED: floor(lift1) = 0")
step__ := -1
endseq endif

case 4:
if ctlState(lift1) = HALTING then seq
result := print("check succeeded: ctlState(lift1) = HALTING")
step__ := 5
endseq else seq
result := print("CHECK FAILED: ctlState(lift1) = HALTING")
step__ := -1
endseq endif

case 5:
if dir(lift1) = UP then seq
result := print("check succeeded: dir(lift1) = UP")
step__ := 6
endseq else seq
result := print("CHECK FAILED: dir(lift1) = UP")
step__ := -1
endseq endif

case 6:
seq
r_main[]
r_main[]
step__ := 7
endseq

endswitch

default init s0:
    // added by validator
    function step__ = 0
    function floor($l in Lift) = ground
    function dir($l in Lift) = UP
    function ctlState($l in Lift) = HALTING
