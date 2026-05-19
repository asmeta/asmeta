asm asmDecPipe

import StandardLibrary

signature:
    monitored funcMulti: Integer
    out funcDec: Integer

definitions:
    main rule r_Main =
        funcDec := funcMulti - 1