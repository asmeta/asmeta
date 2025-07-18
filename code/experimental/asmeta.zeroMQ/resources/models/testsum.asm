asm testsum

import ../libraries/StandardLibrary

signature:
    dynamic out resultSum: Integer
    dynamic monitored input1: Integer
    dynamic monitored input2: Integer


definitions:

    main rule r_Main =
        resultSum := input1 + input2

default init s0:
    