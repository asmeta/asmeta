asm RobotsWithAgents

import ../STDL/StandardLibrary

signature:
    domain Robot subsetof Agent

    static robot1: Robot
    static robot2: Robot

	monitored muovi: Robot -> Boolean
    controlled passi: Robot -> Integer

definitions:
    rule r_Robot =
    		if muovi(self) then
        		passi(self) := passi(self) + 1
        	endif

    main rule r_Main =
        forall $r in Robot with true do
            program($r)

default init s0:
    function passi($r in Robot) = 0

agent Robot: r_Robot[]
