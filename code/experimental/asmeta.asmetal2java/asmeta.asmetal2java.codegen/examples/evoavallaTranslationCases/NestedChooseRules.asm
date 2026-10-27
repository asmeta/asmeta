asm NestedChooseRules

import ../STDL/StandardLibrary

// Duplicate locals: the inner choose redeclares point0 and rndm in the outer scope.
signature:
    enum domain Item = {FIRST | SECOND}
    controlled selected: Item

definitions:
    main rule r_Main =
        choose $outer in Item with true do
            choose $inner in Item with true do
                selected := $inner

default init s0:
    function selected = FIRST
