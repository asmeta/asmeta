asm asmDecPipe

import StandardLibrary

signature:
    monitored funcInc: Integer
    out funcDec: Integer

definitions:
    main rule r_Main =
        funcDec := funcInc - 1