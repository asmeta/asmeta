asm consumer

import ./StandardLibrary

signature:
    enum domain StatusDomain = {IDLE | HELLO_WORLD}
    enum domain StateDomain = {WAITING | RESPONDED}
    dynamic monitored incomingStatus: StatusDomain
    dynamic controlled state: StateDomain
    dynamic monitored sharedValue: Integer

    out resultShared: Integer

definitions:

    main rule r_Main =
        if(incomingStatus = HELLO_WORLD) then
            par
                state := RESPONDED
                if (isUndef(sharedValue)) then
                    resultShared := 0
                else
                    resultShared := 1
                endif
            endpar
        else
            state := WAITING
        endif

default init s0:
    function state = WAITING