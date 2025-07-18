asm testsum2

import ../libraries/StandardLibrary

signature:

	abstract domain Counter

    enum domain Dir = {SUM | DIF}

    dynamic out opresult: Integer

    dynamic monitored input1: Integer

    dynamic monitored input2: Integer

    dynamic monitored reqOp: Dir -> Boolean


definitions:

    main rule r_Main =

        if reqOp(SUM) then opresult := input1 + input2

        else if reqOp(DIF) then opresult := input1 - input2

        endif endif

default init s0:
 