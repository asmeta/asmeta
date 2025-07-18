asm consumerasm


import ./StandardLibrary

signature:
    enum domain Status = {NORMAL | ALERT}

    // input dai producer
    dynamic monitored outTemp: Integer
    dynamic monitored outHR: Integer

    // stato interno del consumer
    dynamic controlled status: Status

    // output: risultato della valutazione
    dynamic out statusOut: Status

definitions:

    main rule r_Main =
        par
            // se mancano dati, rimani in NORMAL
            if (isUndef(outTemp) or isUndef(outHR)) then
                status := NORMAL
            else
                // soglie: temperatura >38°C o HR fuori [50–120]
                if (outTemp > 38 or outHR < 50 or outHR > 120) then
                    status := ALERT
                else
                    status := NORMAL
                endif
            endif
            // esponi il risultato
            statusOut := status
        endpar

default init s0:
    function status = NORMAL