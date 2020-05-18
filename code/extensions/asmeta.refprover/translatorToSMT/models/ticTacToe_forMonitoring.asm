asm ticTacToe_forMonitoring

import StandardLibrary

signature:
    domain Coord subsetof Integer
    enum domain Sign = {CROSS | NOUGHT | EMPTY}
    enum domain Status = {TURN_USER | TURN_PC}

    controlled board: Prod(Coord, Coord) -> Sign
    controlled status: Status
    monitored userChoiceR: Coord
    monitored userChoiceC: Coord
    derived winner: Sign -> Boolean
    derived endOfGame: Boolean

definitions:
    domain Coord = {1..3}

    function winner($s in Sign) =
        (exist $r in Coord with
                         (forall $c in Coord with board($r, $c) = $s)) or
        (exist $k in Coord with
                         (forall $t in Coord with board($t, $k) = $s)) or
        (forall $d in Coord with board($d, $d) = $s) or
        (forall $d1 in Coord with board($d1, 4 - $d1) = $s)

    function endOfGame =
        winner(CROSS) or winner(NOUGHT) or
        (forall $r in Coord, $c in Coord with board($r, $c) != EMPTY)

    rule r_moveUser =
        if board(userChoiceR, userChoiceC) = EMPTY then
            par
                board(userChoiceR, userChoiceC) := CROSS
                status := TURN_PC
            endpar
        endif

    rule r_movePC =
        par
            choose $r in Coord, $c in Coord with board($r, $c) = EMPTY do
                board($r, $c) := NOUGHT
            status := TURN_USER
        endpar

    main rule r_Main =
        if(not(endOfGame)) then
            if(status = TURN_USER) then
                r_moveUser[]
            else
                r_movePC[]
            endif
        endif

default init s0:
	function board($r in Coord, $c in Coord) = EMPTY
	function status = TURN_USER