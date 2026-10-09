asm m2
import StandardLibrary

signature:

   enum domain Status = {NORMAL | ALERT}

    // input da M1 (su :5556)
    dynamic monitored outTemp: Integer
    dynamic monitored outHR: Integer

    // output verso M1 (su :5557)
    dynamic out receivedOK: Status
   
   
definitions:
    
     main rule r_Main =
        if (isUndef(outTemp) or isUndef(outHR)) then
            receivedOK := NORMAL
        else
            if (outTemp > 38 or outHR < 50 or outHR > 120) then
                receivedOK := ALERT
            else
                receivedOK := NORMAL
            endif
        endif

default init s0:
 function receivedOK = NORMAL