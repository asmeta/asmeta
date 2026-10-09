asm asmIncPipe

import StandardLibrary

signature:
    monitored funcMulti: Integer
    out funcInc: Integer

definitions:
    main rule r_Main =
        funcInc := funcMulti + 2