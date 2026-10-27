asm ConcreteDomainProductInitialization

import ../STDL/StandardLibrary

// Type mismatch: initialization passes Row instead of the required Pair<Row, Column>.
signature:
    domain Row subsetof Integer
    domain Column subsetof Integer
    controlled occupied: Prod(Row, Column) -> Boolean

definitions:
    domain Row = {1 : 2}
    domain Column = {1 : 2}

    main rule r_Main = skip

default init s0:
    function occupied($row in Row, $column in Column) = false
