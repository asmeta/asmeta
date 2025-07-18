asm processPressureModel

import StandardLibrary
import TimeLibrary

signature:

    dynamic monitored respiratoryRate: Integer
    dynamic monitored ieRatio: Real
    dynamic monitored pinsp: Real
    dynamic monitored peep: Real
    
    dynamic monitored ventilatorType: String // set by user MUST BE "Volume" or "Pressure"
    
    dynamic monitored optimal_lung_volume: Real // read by external queue
        
    static simTime: Timer

    dynamic out value: Real // is the output/volume pressure based on the ventilation type

definitions:

    main rule r_Main =
    par
    if (ventilatorType="Pressure") then
        if(isUndef(simTime)) then
            value := peep
        else
            let ($inspiratoryTime = 60 / respiratoryRate * (ieRatio / (1.0 + ieRatio)),
                $cycleTime = currentTime(simTime) mod rtoi(60.0 / itor(respiratoryRate)))
            in
                if ($cycleTime < rtoi($inspiratoryTime)) then
                    value := pinsp
                else
                    value := peep
                endif
            endlet
        endif
       endif
      	if (ventilatorType="Volume") then 
      		value := optimal_lung_volume
      	endif
     endpar

default init s0:
    function value = 0.0
    