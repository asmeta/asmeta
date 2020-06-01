//applied flatteners: MCR FR ChR AR LR CaR NR 
asm ferrymanSimulator_raff1_flat
import ./STDL/StandardLibrary

signature:
    enum domain Actors = {FERRYMAN | GOAT | CABBAGE | WOLF}
    enum domain Side = {LEFT | RIGHT}

    controlled position: Actors -> Side
    monitored carry: Actors
    controlled outMess: String
    derived oppositeSide: Side -> Side
    derived allOnRightSide: Boolean

definitions:

    function allOnRightSide = (forall $a in Actors with eq(position($a),RIGHT))
    function oppositeSide($s in Side) = if eq($s,LEFT) then RIGHT else LEFT endif


    invariant over position: implies(eq(position(GOAT),position(CABBAGE)),eq(position(GOAT),position(FERRYMAN)))
    invariant over position: implies(eq(position(WOLF),position(GOAT)),eq(position(WOLF),position(FERRYMAN)))
    main rule r_Main =
        par
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,CABBAGE),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,FERRYMAN),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,GOAT),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,CABBAGE),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,WOLF),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,WOLF),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT)) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,FERRYMAN),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,GOAT),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,CABBAGE),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,WOLF),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,CABBAGE),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,FERRYMAN),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,FERRYMAN),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,WOLF),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,CABBAGE),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,GOAT),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,GOAT),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),eq(position(FERRYMAN),RIGHT)) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,FERRYMAN),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,GOAT),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,GOAT),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,WOLF),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,FERRYMAN),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,GOAT),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,CABBAGE),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,GOAT),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,GOAT),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,FERRYMAN),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,GOAT),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,CABBAGE),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,CABBAGE),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,CABBAGE),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,CABBAGE),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,FERRYMAN),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,CABBAGE),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,WOLF),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,CABBAGE),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,WOLF),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if allOnRightSide then
                outMess := "All on right side"
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,WOLF),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,GOAT),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,GOAT),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,CABBAGE),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,FERRYMAN),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,WOLF),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,CABBAGE),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),eq(position(FERRYMAN),RIGHT)) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,CABBAGE),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,CABBAGE),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),not(eq(outMess,"From left to right"))) then
                outMess := "From left to right"
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,GOAT),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,FERRYMAN),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,FERRYMAN),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),eq(position(FERRYMAN),LEFT)) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,FERRYMAN),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,GOAT),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,WOLF),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,WOLF),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,FERRYMAN),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,GOAT),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,CABBAGE),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,WOLF),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,FERRYMAN),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,WOLF),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,FERRYMAN),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),eq(position(FERRYMAN),LEFT)) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,FERRYMAN),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,GOAT),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,FERRYMAN),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,GOAT),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,FERRYMAN),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,WOLF),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,GOAT),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,CABBAGE),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,FERRYMAN),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,WOLF),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),eq(outMess,"From left to right")) then
                outMess := "From right to left"
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,CABBAGE),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,WOLF),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,CABBAGE),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,GOAT),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,CABBAGE),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),not(eq(outMess,"From left to right"))) then
                outMess := "From left to right"
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),not(eq(outMess,"From left to right"))) then
                outMess := "From left to right"
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,FERRYMAN),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,CABBAGE),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,WOLF),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,GOAT),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,WOLF),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),eq(outMess,"From left to right")) then
                outMess := "From right to left"
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,FERRYMAN),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,GOAT),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,WOLF),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,CABBAGE),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,WOLF),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,FERRYMAN),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),eq(position(FERRYMAN),RIGHT)) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,FERRYMAN),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,WOLF),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,CABBAGE),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,CABBAGE),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,GOAT),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT)) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,WOLF),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,GOAT),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,CABBAGE),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),not(eq(outMess,"From left to right"))) then
                outMess := "From left to right"
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,CABBAGE),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,WOLF),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,FERRYMAN),eq(carry,WOLF)),eq(position(WOLF),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,WOLF),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,GOAT),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,WOLF),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,GOAT),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,CABBAGE),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,FERRYMAN),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,CABBAGE),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,GOAT),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,FERRYMAN),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,FERRYMAN),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,GOAT),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,WOLF),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,WOLF),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,CABBAGE),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(CABBAGE) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,GOAT),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,CABBAGE),eq(carry,CABBAGE)),eq(position(CABBAGE),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,GOAT),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),eq(position(FERRYMAN),LEFT)) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,CABBAGE),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(CABBAGE) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,FERRYMAN),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,WOLF),eq(carry,CABBAGE)),eq(position(CABBAGE),RIGHT))) then
                position(WOLF) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,FERRYMAN),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,WOLF),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,GOAT),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,GOAT),eq(carry,GOAT)),eq(position(GOAT),RIGHT))) then
                position(GOAT) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,WOLF),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),eq(outMess,"From left to right")) then
                outMess := "From right to left"
            endif
            if and(and(and(not(allOnRightSide),eq(carry,GOAT)),eq(position(FERRYMAN),position(GOAT))),and(and(eq(carry,FERRYMAN),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,FERRYMAN),eq(carry,FERRYMAN)),eq(position(FERRYMAN),LEFT))) then
                position(FERRYMAN) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,FERRYMAN),eq(carry,FERRYMAN)),eq(position(FERRYMAN),RIGHT))) then
                position(FERRYMAN) := oppositeSide(RIGHT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),and(and(eq(carry,WOLF),eq(carry,WOLF)),eq(position(WOLF),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,WOLF)),eq(position(FERRYMAN),position(WOLF))),and(and(eq(carry,GOAT),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(GOAT) := oppositeSide(LEFT)
            endif
            if and(and(not(allOnRightSide),eq(carry,FERRYMAN)),and(and(eq(carry,WOLF),eq(carry,GOAT)),eq(position(GOAT),LEFT))) then
                position(WOLF) := oppositeSide(LEFT)
            endif
            if and(and(and(not(allOnRightSide),eq(carry,CABBAGE)),eq(position(FERRYMAN),position(CABBAGE))),eq(outMess,"From left to right")) then
                outMess := "From right to left"
            endif
        endpar

default init s0:
    function position($a in Actors) = LEFT
    function outMess = "From left to right"
