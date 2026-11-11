asm incBiPipe
import StandardLibrary

signature:
//send by the environment
    monitored myinput: Integer
//send by M2
    monitored funcDec: Integer
    out funcInc: Integer

definitions:
    main rule r_Main =
        funcInc := myinput + funcDec + 2