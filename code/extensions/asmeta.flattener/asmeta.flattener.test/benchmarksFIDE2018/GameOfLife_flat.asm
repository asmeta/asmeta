//applied flatteners: MCR FR ChR AR LR CaR NR 
asm GameOfLife_flat
import ./STDL/StandardLibrary

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


    main rule r_Main =
        par
            if and(alive(2n,3n),or(lt(aliveNeighb(2n,3n),2),gt(aliveNeighb(2n,3n),3))) then
                alive(2n,3n) := false
            endif
            if and(not(alive(4n,1n)),eq(aliveNeighb(4n,1n),3)) then
                alive(4n,1n) := true
            endif
            if and(not(alive(4n,4n)),eq(aliveNeighb(4n,4n),3)) then
                alive(4n,4n) := true
            endif
            if and(alive(1n,2n),or(lt(aliveNeighb(1n,2n),2),gt(aliveNeighb(1n,2n),3))) then
                alive(1n,2n) := false
            endif
            if and(not(alive(3n,1n)),eq(aliveNeighb(3n,1n),3)) then
                alive(3n,1n) := true
            endif
            if and(alive(1n,1n),or(lt(aliveNeighb(1n,1n),2),gt(aliveNeighb(1n,1n),3))) then
                alive(1n,1n) := false
            endif
            if and(not(alive(1n,3n)),eq(aliveNeighb(1n,3n),3)) then
                alive(1n,3n) := true
            endif
            if and(alive(4n,3n),or(lt(aliveNeighb(4n,3n),2),gt(aliveNeighb(4n,3n),3))) then
                alive(4n,3n) := false
            endif
            if and(alive(3n,2n),or(lt(aliveNeighb(3n,2n),2),gt(aliveNeighb(3n,2n),3))) then
                alive(3n,2n) := false
            endif
            if and(not(alive(2n,1n)),eq(aliveNeighb(2n,1n),3)) then
                alive(2n,1n) := true
            endif
            if and(not(alive(1n,2n)),eq(aliveNeighb(1n,2n),3)) then
                alive(1n,2n) := true
            endif
            if and(alive(4n,2n),or(lt(aliveNeighb(4n,2n),2),gt(aliveNeighb(4n,2n),3))) then
                alive(4n,2n) := false
            endif
            if and(alive(2n,4n),or(lt(aliveNeighb(2n,4n),2),gt(aliveNeighb(2n,4n),3))) then
                alive(2n,4n) := false
            endif
            if and(alive(1n,4n),or(lt(aliveNeighb(1n,4n),2),gt(aliveNeighb(1n,4n),3))) then
                alive(1n,4n) := false
            endif
            if and(not(alive(3n,4n)),eq(aliveNeighb(3n,4n),3)) then
                alive(3n,4n) := true
            endif
            if and(not(alive(3n,3n)),eq(aliveNeighb(3n,3n),3)) then
                alive(3n,3n) := true
            endif
            if and(not(alive(2n,3n)),eq(aliveNeighb(2n,3n),3)) then
                alive(2n,3n) := true
            endif
            if and(not(alive(1n,1n)),eq(aliveNeighb(1n,1n),3)) then
                alive(1n,1n) := true
            endif
            if and(alive(1n,3n),or(lt(aliveNeighb(1n,3n),2),gt(aliveNeighb(1n,3n),3))) then
                alive(1n,3n) := false
            endif
            if and(alive(3n,3n),or(lt(aliveNeighb(3n,3n),2),gt(aliveNeighb(3n,3n),3))) then
                alive(3n,3n) := false
            endif
            if and(alive(4n,4n),or(lt(aliveNeighb(4n,4n),2),gt(aliveNeighb(4n,4n),3))) then
                alive(4n,4n) := false
            endif
            if and(not(alive(2n,2n)),eq(aliveNeighb(2n,2n),3)) then
                alive(2n,2n) := true
            endif
            if and(not(alive(3n,2n)),eq(aliveNeighb(3n,2n),3)) then
                alive(3n,2n) := true
            endif
            if and(not(alive(4n,3n)),eq(aliveNeighb(4n,3n),3)) then
                alive(4n,3n) := true
            endif
            if and(alive(3n,1n),or(lt(aliveNeighb(3n,1n),2),gt(aliveNeighb(3n,1n),3))) then
                alive(3n,1n) := false
            endif
            if and(alive(3n,4n),or(lt(aliveNeighb(3n,4n),2),gt(aliveNeighb(3n,4n),3))) then
                alive(3n,4n) := false
            endif
            if and(not(alive(2n,4n)),eq(aliveNeighb(2n,4n),3)) then
                alive(2n,4n) := true
            endif
            if and(not(alive(1n,4n)),eq(aliveNeighb(1n,4n),3)) then
                alive(1n,4n) := true
            endif
            if and(alive(2n,1n),or(lt(aliveNeighb(2n,1n),2),gt(aliveNeighb(2n,1n),3))) then
                alive(2n,1n) := false
            endif
            if and(alive(2n,2n),or(lt(aliveNeighb(2n,2n),2),gt(aliveNeighb(2n,2n),3))) then
                alive(2n,2n) := false
            endif
            if and(not(alive(4n,2n)),eq(aliveNeighb(4n,2n),3)) then
                alive(4n,2n) := true
            endif
            if and(alive(4n,1n),or(lt(aliveNeighb(4n,1n),2),gt(aliveNeighb(4n,1n),3))) then
                alive(4n,1n) := false
            endif
        endpar

default init s0:
    function alive($r in RowsWorld, $c in ColumnsWorld) = switch ($r,$c) case (1n,1n):true case (1n,2n):true case (2n,1n):true case (3n,4n):true case (4n,3n):true case (4n,4n):true otherwise false endswitch
