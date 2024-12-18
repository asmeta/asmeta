asm ticTacToe_forMonitoringSimple

import StandardLibrary

signature:
    domain Coord subsetof Integer
    enum domain Sign = {CROSS | NOUGHT | EMPTY}
    enum domain Status = {TURN_USER | TURN_PC}

    controlled board: Coord-> Sign
    controlled status: Status
    monitored userChoice: Coord

definitions:
    domain Coord = {1:2}

    rule r_moveUser =
        if board(userChoice) = EMPTY then
            par
                board(userChoice) := CROSS
                status := TURN_PC
            endpar
        endif

    rule r_movePC =
        par
            choose $r in Coord with board($r) = EMPTY do
                board($r) := NOUGHT
            status := TURN_USER
        endpar

    main rule r_Main =
        if(status = TURN_USER) then
			r_moveUser[]
		else
			r_movePC[]
        endif

default init s0:
	function board($r in Coord) = EMPTY
	function status = TURN_USER