asm consumerasm


import ./StandardLibrary

signature:
    enum domain Status = {NORMAL | ALERT}

    // input dai producer
    dynamic monitored outTemp: Integer
    dynamic monitored outHR: Integer


    // output: risultato della valutazione
    dynamic out statusOut: Status

definitions:

    main rule r_Main =
        
            // se mancano dati, rimani in NORMAL ???
            if (isUndef(outTemp) or isUndef(outHR)) then
                statusOut := NORMAL
            else
                // soglie: temperatura >38°C o HR fuori [50–120]
                if (outTemp > 38 or outHR < 50 or outHR > 120) then
                    statusOut := ALERT
                else
                    statusOut := NORMAL
                endif
            endif
           
       

default init s0:
   function statusOut = NORMAL