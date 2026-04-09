asm incrementa
import StandardLibrary

signature:
    monitored myinput: Integer
    monitored funcDec: Integer
    out funcInc: Integer

definitions:
    main rule r_Main =
        funcInc := myinput + funcDec + 2