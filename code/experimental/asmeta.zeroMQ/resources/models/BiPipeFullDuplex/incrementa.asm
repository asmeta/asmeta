asm incrementa
import StandardLibrary

signature:
domain SmallInt subsetof Integer

    monitored myinput: SmallInt
    monitored funcDec: Integer
    out funcInc: Integer

definitions:
domain SmallInt = {2:3}

    main rule r_Main =
        funcInc := myinput + funcDec + 2
        
     