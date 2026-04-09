asm decrementa
import StandardLibrary

signature:
    monitored myinput: Integer
    monitored funcInc: Integer
    out funcDec: Integer

definitions:
    main rule r_Main =
        funcDec := myinput + funcInc - 1