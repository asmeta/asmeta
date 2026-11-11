asm m1
import StandardLibrary

signature:

    enum domain Status = {NORMAL | ALERT}

	// input from environment
    dynamic monitored inputTemp: Integer
	dynamic monitored inputHR: Integer
	
	 // input from M2 (su :5557)
	dynamic monitored receivedOK: Status  // from M2, NORMAL or ALERT
	
	 // output verso M2 (su :5556)
    dynamic out outTemp: Integer
    dynamic out outHR: Integer
    
    // output finale elaborato da M1 (su :5558)
    dynamic out elaboratedStatus: Status
	
definitions:
    main rule r_Main =
        if (not(isUndef(inputTemp)) and not(isUndef(inputHR))) then
            if (receivedOK = NORMAL) then
                par
                    outTemp:= inputTemp
                    outHR:= inputHR
                    elaboratedStatus:= NORMAL
                endpar           
            endif
        endif

default init s0:
    function elaboratedStatus = NORMAL