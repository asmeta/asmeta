//applied flatteners: FR 
asm gameOfLife_flat
import ../../STDL/StandardLibrary

signature:
    domain RowsWorld subsetof Natural
    domain ColumnsWorld subsetof Natural

    controlled alive: Prod(RowsWorld, ColumnsWorld) -> Boolean
    derived aliveNeighb: Prod(RowsWorld, ColumnsWorld) -> Integer
    derived neighbours: Prod(RowsWorld, ColumnsWorld) -> Powerset(Prod(RowsWorld, ColumnsWorld))

definitions:

    domain RowsWorld = {1n,2n,3n,4n}
    domain ColumnsWorld = {1n,2n,3n,4n}

    function neighbours($r in RowsWorld, $c in ColumnsWorld) = {$i in RowsWorld,$j in ColumnsWorld| or(eq(plus(abs(minus($r,$i)),abs(minus($c,$j))),1),and(eq(abs(minus($r,$i)),1),eq(abs(minus($c,$j)),1))) : ($i,$j)}
    function aliveNeighb($r in RowsWorld, $c in ColumnsWorld) = size({$h in RowsWorld,$k in ColumnsWorld| and(contains(neighbours($r,$c),($h,$k)),alive($h,$k)) : ($h,$k)})

    macro rule r_conway($r in RowsWorld, $c in ColumnsWorld) =
        if alive($r,$c) then
            if or(lt(aliveNeighb($r,$c),2),gt(aliveNeighb($r,$c),3)) then
                alive($r,$c) := false
            endif
        else 
            if eq(aliveNeighb($r,$c),3) then
                alive($r,$c) := true
            endif
        endif


    main rule r_Main =
        par
            r_conway[3n,3n]
            r_conway[3n,1n]
            r_conway[3n,2n]
            r_conway[3n,4n]
            r_conway[1n,3n]
            r_conway[1n,1n]
            r_conway[1n,2n]
            r_conway[1n,4n]
            r_conway[2n,3n]
            r_conway[2n,1n]
            r_conway[2n,2n]
            r_conway[2n,4n]
            r_conway[4n,3n]
            r_conway[4n,1n]
            r_conway[4n,2n]
            r_conway[4n,4n]
        endpar

default init s0:
    function alive($r in RowsWorld, $c in ColumnsWorld) = switch ($r,$c) case (1n,1n):true case (1n,2n):true case (2n,1n):true case (3n,4n):true case (4n,3n):true case (4n,4n):true otherwise false endswitch
