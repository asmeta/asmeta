asm ConcreteDomainRuleArgument

import ../STDL/StandardLibrary

// Type mismatch: an Integer literal is passed to a Java rule expecting Amount.
signature:
    domain Amount subsetof Integer
    controlled balance: Amount

definitions:
    domain Amount = {0 : 10}

    rule r_setBalance($amount in Amount) =
        balance := $amount

    main rule r_Main =
        r_setBalance[2]

default init s0:
    function balance = 0
