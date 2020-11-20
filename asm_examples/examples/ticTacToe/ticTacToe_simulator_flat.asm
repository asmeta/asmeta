//applied flatteners: MCR FR NR LR AR CaR 
asm ticTacToe_simulator_flat
import ../../STDL/StandardLibrary
import ../../STDL/CTLlibrary

signature:
    domain Coord subsetof Integer
    enum domain Sign = {CROSS | NOUGHT}
    enum domain Status = {TURN_USER | TURN_PC}

    controlled board: Prod(Coord, Coord) -> Sign
    controlled status: Status
    monitored userChoiceR: Coord
    monitored userChoiceC: Coord
    derived winner: Sign -> Boolean
    derived endOfGame: Boolean

definitions:

    domain Coord = {1,2,3}

    function winner($s in Sign) = or(or(or((exist $r in Coord with (forall $c in Coord with eq(board($r,$c),$s))),(exist $c2 in Coord with (forall $r2 in Coord with eq(board($r2,$c2),$s)))),(forall $d in Coord with eq(board($d,$d),$s))),(forall $d1 in Coord with eq(board($d1,minus(4,$d1)),$s)))
    function endOfGame = or((exist $s in Sign with winner($s)),(forall $r in Coord,$c in Coord with isDef(board($r,$c))))


    CTLSPEC ef(winner(CROSS))
    CTLSPEC ef(winner(NOUGHT))
    CTLSPEC not(ef(winner(NOUGHT)))
    CTLSPEC ag(not(and(winner(CROSS),winner(NOUGHT))))
    main rule r_Main =
        par
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,2))),isUndef(board(1,2))),and(eq(userChoiceR,2),eq(userChoiceC,1))) then
                board(2,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,1))),isUndef(board(2,1))),and(eq(userChoiceR,2),eq(userChoiceC,3))) then
                board(2,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,2))),isUndef(board(3,2))),and(eq(userChoiceR,2),eq(userChoiceC,1))) then
                board(2,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,1))),isUndef(board(1,1))),and(eq(userChoiceR,1),eq(userChoiceC,1))) then
                board(1,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,2))),isUndef(board(3,2))),and(eq(userChoiceR,2),eq(userChoiceC,3))) then
                board(2,3) := CROSS
            endif
            if and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,3))),isUndef(board(3,3))) then
                status := TURN_PC
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,2))),isUndef(board(2,2))),and(eq(userChoiceR,3),eq(userChoiceC,3))) then
                board(3,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,3))),isUndef(board(2,3))),and(eq(userChoiceR,3),eq(userChoiceC,1))) then
                board(3,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,2))),isUndef(board(2,2))),and(eq(userChoiceR,1),eq(userChoiceC,1))) then
                board(1,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,2))),isUndef(board(1,2))),and(eq(userChoiceR,1),eq(userChoiceC,3))) then
                board(1,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,3))),isUndef(board(2,3))),and(eq(userChoiceR,1),eq(userChoiceC,2))) then
                board(1,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,3))),isUndef(board(1,3))),and(eq(userChoiceR,3),eq(userChoiceC,2))) then
                board(3,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,3))),isUndef(board(1,3))),and(eq(userChoiceR,3),eq(userChoiceC,3))) then
                board(3,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,2))),isUndef(board(1,2))),and(eq(userChoiceR,3),eq(userChoiceC,2))) then
                board(3,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,3))),isUndef(board(1,3))),and(eq(userChoiceR,2),eq(userChoiceC,1))) then
                board(2,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,3))),isUndef(board(3,3))),and(eq(userChoiceR,2),eq(userChoiceC,3))) then
                board(2,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,1))),isUndef(board(3,1))),and(eq(userChoiceR,3),eq(userChoiceC,2))) then
                board(3,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,1))),isUndef(board(1,1))),and(eq(userChoiceR,2),eq(userChoiceC,3))) then
                board(2,3) := CROSS
            endif
            if and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,2))),isUndef(board(1,2))) then
                status := TURN_PC
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,1))),isUndef(board(2,1))),and(eq(userChoiceR,2),eq(userChoiceC,2))) then
                board(2,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,3))),isUndef(board(2,3))),and(eq(userChoiceR,1),eq(userChoiceC,3))) then
                board(1,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,2))),isUndef(board(1,2))),and(eq(userChoiceR,3),eq(userChoiceC,3))) then
                board(3,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,1))),isUndef(board(1,1))),and(eq(userChoiceR,3),eq(userChoiceC,1))) then
                board(3,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,2))),isUndef(board(2,2))),and(eq(userChoiceR,1),eq(userChoiceC,3))) then
                board(1,3) := CROSS
            endif
            if and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,1))),isUndef(board(3,1))) then
                status := TURN_PC
            endif
            if and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,2))),isUndef(board(3,2))) then
                status := TURN_PC
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,3))),isUndef(board(3,3))),and(eq(userChoiceR,3),eq(userChoiceC,1))) then
                board(3,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,1))),isUndef(board(1,1))),and(eq(userChoiceR,3),eq(userChoiceC,2))) then
                board(3,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,2))),isUndef(board(2,2))),and(eq(userChoiceR,2),eq(userChoiceC,3))) then
                board(2,3) := CROSS
            endif
            if and(not(endOfGame),not(eq(status,TURN_USER))) then
                choose $r21 in Coord, $c22 in Coord with isUndef(board($r21,$c22)) do
                    par
                        board($r21,$c22) := NOUGHT
                        status := TURN_USER
                    endpar
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,2))),isUndef(board(1,2))),and(eq(userChoiceR,1),eq(userChoiceC,2))) then
                board(1,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,1))),isUndef(board(3,1))),and(eq(userChoiceR,1),eq(userChoiceC,1))) then
                board(1,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,1))),isUndef(board(1,1))),and(eq(userChoiceR,2),eq(userChoiceC,2))) then
                board(2,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,1))),isUndef(board(3,1))),and(eq(userChoiceR,1),eq(userChoiceC,2))) then
                board(1,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,3))),isUndef(board(1,3))),and(eq(userChoiceR,3),eq(userChoiceC,1))) then
                board(3,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,1))),isUndef(board(2,1))),and(eq(userChoiceR,3),eq(userChoiceC,3))) then
                board(3,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,1))),isUndef(board(2,1))),and(eq(userChoiceR,1),eq(userChoiceC,1))) then
                board(1,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,1))),isUndef(board(2,1))),and(eq(userChoiceR,3),eq(userChoiceC,2))) then
                board(3,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,3))),isUndef(board(1,3))),and(eq(userChoiceR,1),eq(userChoiceC,2))) then
                board(1,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,3))),isUndef(board(2,3))),and(eq(userChoiceR,1),eq(userChoiceC,1))) then
                board(1,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,2))),isUndef(board(2,2))),and(eq(userChoiceR,3),eq(userChoiceC,1))) then
                board(3,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,3))),isUndef(board(2,3))),and(eq(userChoiceR,2),eq(userChoiceC,3))) then
                board(2,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,1))),isUndef(board(3,1))),and(eq(userChoiceR,1),eq(userChoiceC,3))) then
                board(1,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,1))),isUndef(board(1,1))),and(eq(userChoiceR,2),eq(userChoiceC,1))) then
                board(2,1) := CROSS
            endif
            if and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,1))),isUndef(board(2,1))) then
                status := TURN_PC
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,3))),isUndef(board(3,3))),and(eq(userChoiceR,2),eq(userChoiceC,1))) then
                board(2,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,3))),isUndef(board(2,3))),and(eq(userChoiceR,2),eq(userChoiceC,1))) then
                board(2,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,3))),isUndef(board(1,3))),and(eq(userChoiceR,1),eq(userChoiceC,3))) then
                board(1,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,2))),isUndef(board(2,2))),and(eq(userChoiceR,3),eq(userChoiceC,2))) then
                board(3,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,3))),isUndef(board(3,3))),and(eq(userChoiceR,3),eq(userChoiceC,2))) then
                board(3,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,2))),isUndef(board(2,2))),and(eq(userChoiceR,1),eq(userChoiceC,2))) then
                board(1,2) := CROSS
            endif
            if and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,1))),isUndef(board(1,1))) then
                status := TURN_PC
            endif
            if and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,2))),isUndef(board(2,2))) then
                status := TURN_PC
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,2))),isUndef(board(1,2))),and(eq(userChoiceR,1),eq(userChoiceC,1))) then
                board(1,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,1))),isUndef(board(2,1))),and(eq(userChoiceR,3),eq(userChoiceC,1))) then
                board(3,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,2))),isUndef(board(3,2))),and(eq(userChoiceR,1),eq(userChoiceC,2))) then
                board(1,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,3))),isUndef(board(3,3))),and(eq(userChoiceR,1),eq(userChoiceC,1))) then
                board(1,1) := CROSS
            endif
            if and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,3))),isUndef(board(1,3))) then
                status := TURN_PC
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,1))),isUndef(board(3,1))),and(eq(userChoiceR,2),eq(userChoiceC,3))) then
                board(2,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,2))),isUndef(board(3,2))),and(eq(userChoiceR,3),eq(userChoiceC,1))) then
                board(3,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,2))),isUndef(board(2,2))),and(eq(userChoiceR,2),eq(userChoiceC,1))) then
                board(2,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,1))),isUndef(board(3,1))),and(eq(userChoiceR,2),eq(userChoiceC,1))) then
                board(2,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,1))),isUndef(board(2,1))),and(eq(userChoiceR,1),eq(userChoiceC,2))) then
                board(1,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,3))),isUndef(board(1,3))),and(eq(userChoiceR,1),eq(userChoiceC,1))) then
                board(1,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,2))),isUndef(board(3,2))),and(eq(userChoiceR,1),eq(userChoiceC,1))) then
                board(1,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,2))),isUndef(board(3,2))),and(eq(userChoiceR,1),eq(userChoiceC,3))) then
                board(1,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,3))),isUndef(board(2,3))),and(eq(userChoiceR,3),eq(userChoiceC,3))) then
                board(3,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,3))),isUndef(board(1,3))),and(eq(userChoiceR,2),eq(userChoiceC,3))) then
                board(2,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,3))),isUndef(board(1,3))),and(eq(userChoiceR,2),eq(userChoiceC,2))) then
                board(2,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,2))),isUndef(board(1,2))),and(eq(userChoiceR,2),eq(userChoiceC,2))) then
                board(2,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,3))),isUndef(board(2,3))),and(eq(userChoiceR,3),eq(userChoiceC,2))) then
                board(3,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,1))),isUndef(board(3,1))),and(eq(userChoiceR,2),eq(userChoiceC,2))) then
                board(2,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,2))),isUndef(board(1,2))),and(eq(userChoiceR,3),eq(userChoiceC,1))) then
                board(3,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,1))),isUndef(board(2,1))),and(eq(userChoiceR,1),eq(userChoiceC,3))) then
                board(1,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,3))),isUndef(board(3,3))),and(eq(userChoiceR,1),eq(userChoiceC,2))) then
                board(1,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,3))),isUndef(board(3,3))),and(eq(userChoiceR,2),eq(userChoiceC,2))) then
                board(2,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,2))),isUndef(board(1,2))),and(eq(userChoiceR,2),eq(userChoiceC,3))) then
                board(2,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,1))),isUndef(board(3,1))),and(eq(userChoiceR,3),eq(userChoiceC,1))) then
                board(3,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,1))),isUndef(board(1,1))),and(eq(userChoiceR,3),eq(userChoiceC,3))) then
                board(3,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,3))),isUndef(board(3,3))),and(eq(userChoiceR,3),eq(userChoiceC,3))) then
                board(3,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,2))),isUndef(board(3,2))),and(eq(userChoiceR,2),eq(userChoiceC,2))) then
                board(2,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,3))),isUndef(board(2,3))),and(eq(userChoiceR,2),eq(userChoiceC,2))) then
                board(2,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,1))),isUndef(board(2,1))),and(eq(userChoiceR,2),eq(userChoiceC,1))) then
                board(2,1) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,2))),isUndef(board(3,2))),and(eq(userChoiceR,3),eq(userChoiceC,2))) then
                board(3,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,2))),isUndef(board(3,2))),and(eq(userChoiceR,3),eq(userChoiceC,3))) then
                board(3,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,1))),isUndef(board(1,1))),and(eq(userChoiceR,1),eq(userChoiceC,3))) then
                board(1,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,1),eq(userChoiceC,1))),isUndef(board(1,1))),and(eq(userChoiceR,1),eq(userChoiceC,2))) then
                board(1,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,2))),isUndef(board(2,2))),and(eq(userChoiceR,2),eq(userChoiceC,2))) then
                board(2,2) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,1))),isUndef(board(3,1))),and(eq(userChoiceR,3),eq(userChoiceC,3))) then
                board(3,3) := CROSS
            endif
            if and(and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,3),eq(userChoiceC,3))),isUndef(board(3,3))),and(eq(userChoiceR,1),eq(userChoiceC,3))) then
                board(1,3) := CROSS
            endif
            if and(and(and(not(endOfGame),eq(status,TURN_USER)),and(eq(userChoiceR,2),eq(userChoiceC,3))),isUndef(board(2,3))) then
                status := TURN_PC
            endif
        endpar

default init s0:
    function status = TURN_USER
