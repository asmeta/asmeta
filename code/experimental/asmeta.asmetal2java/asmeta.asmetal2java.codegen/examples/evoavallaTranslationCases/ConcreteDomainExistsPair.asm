asm ConcreteDomainExistsPair

import ../STDL/StandardLibrary

// Generic inference: quantified domain values become Integer in Pair.with(...),
// but the controlled function requires Pair<Row, Column>.
signature:
    domain Row subsetof Integer
    domain Column subsetof Integer
    controlled occupied: Prod(Row, Column) -> Boolean
    derived anyOccupied: Boolean
    controlled observed: Boolean

definitions:
    domain Row = {1 : 2}
    domain Column = {1 : 2}

    function anyOccupied =
        (exists $row in Row, $column in Column with occupied($row, $column))

    main rule r_Main =
        observed := anyOccupied

default init s0:
    function observed = false
