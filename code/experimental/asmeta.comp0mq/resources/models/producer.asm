asm producer

import ./StandardLibrary

signature:
    enum domain StatusDomain = {IDLE | HELLO_WORLD}
    dynamic out incomingStatus: StatusDomain
    dynamic monitored trigger: Integer

definitions:

    main rule r_Main =
        if(trigger = 1) then
            incomingStatus := HELLO_WORLD
        else
            incomingStatus := IDLE
        endif

default init s0:
    function incomingStatus = IDLE