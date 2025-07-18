asm processPressureModel

import ../libraries/StandardLibrary

signature:
    dynamic monitored respiratoryRate: Integer
    dynamic monitored ieRatio: Real
    dynamic monitored pinsp: Real
    dynamic monitored peep: Real
    dynamic monitored simTime: Real

    dynamic out pressure: Real

definitions:

    main rule r_Main =
        if(isUndef(simTime)) then
            pressure := peep
        else
            let
                totalCycleDuration = 60.0 / respiratoryRate,
                inspiratoryTime = totalCycleDuration * (ieRatio / (1.0 + ieRatio)),
                cycleTime = simTime mod totalCycleDuration
            in
                if (cycleTime < inspiratoryTime) then
                    pressure := pinsp
                else
                    pressure := peep
                endif
            endlet
        endif

default init s0:
    function pressure = 0.0