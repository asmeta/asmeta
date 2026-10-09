asm consumer

import ./StandardLibrary

signature:
    enum domain StatusDomain = {IDLE | HELLO_WORLD}
    enum domain StateDomain = {WAITING | RESPONDED}
    dynamic monitored incomingStatus: StatusDomain
    dynamic controlled state: StateDomain

definitions:

    main rule r_Main =
        if(incomingStatus = HELLO_WORLD) then
            state := RESPONDED
        else
            state := WAITING
        endif

default init s0:
    function state = WAITING