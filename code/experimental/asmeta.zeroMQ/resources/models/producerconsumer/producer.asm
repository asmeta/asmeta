asm producer

import ./StandardLibrary

signature:
    enum domain StatusDomain = {IDLE | HELLO_WORLD}
    dynamic monitored trigger: Integer
    dynamic monitored sharedValue: Integer
    dynamic monitored consoleInput: Integer

    dynamic out incomingStatus: StatusDomain
    dynamic out resultShared: Integer
    dynamic out consoleOutput: Integer

definitions:

    main rule r_Main =
            par
                if (isUndef(sharedValue)) then
                    resultShared := 0
                else
                    resultShared := 1
                endif

                if (isUndef(consoleInput)) then
                    consoleOutput := 0
                else
                    consoleOutput := consoleInput 
                endif

                if(trigger = 1) then
                        incomingStatus := HELLO_WORLD
                else
                    incomingStatus := IDLE
                endif
            endpar

default init s0:
    function incomingStatus = IDLE