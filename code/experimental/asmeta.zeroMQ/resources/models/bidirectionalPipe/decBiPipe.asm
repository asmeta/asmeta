asm decBiPipe
import StandardLibrary

signature:
//received from M1
    monitored funcInc: Integer
    out funcDec: Integer

definitions:
    main rule r_Main =
        funcDec := funcInc - 1