asm producerasm


import ./StandardLibrary

signature:
    enum domain ProducerState = {RUN}
    dynamic controlled state: ProducerState

    // input: due valori da console
    dynamic monitored inputTemp: Integer
    dynamic monitored inputHR: Integer

    // output: li inoltra al consumer
    dynamic out outTemp: Integer
    dynamic out outHR: Integer

definitions:

    main rule r_Main =
        par
            // instrada la temperatura
            if (isUndef(inputTemp)) then
                outTemp := 0
            else
                outTemp := inputTemp
            endif

            // instrada il battito cardiaco
            if (isUndef(inputHR)) then
                outHR := 0
            else
                outHR := inputHR
            endif
        endpar

default init s0:
    function state = RUN