//applied flatteners: FR 
asm gameOfLifeAgents_flat
import ../../STDL/StandardLibrary

signature:
    domain Cell subsetof Agent

    controlled alive: Cell -> Boolean
    derived aliveNeighb: Cell -> Integer
    derived neighbours: Cell -> Powerset(Cell)
    static cell11: Cell
    static cell12: Cell
    static cell13: Cell
    static cell14: Cell
    static cell21: Cell
    static cell22: Cell
    static cell23: Cell
    static cell24: Cell
    static cell31: Cell
    static cell32: Cell
    static cell33: Cell
    static cell34: Cell
    static cell41: Cell
    static cell42: Cell
    static cell43: Cell
    static cell44: Cell

definitions:

    function neighbours($c in Cell) = switch $c case cell11:{cell12,cell21,cell22} case cell12:{cell11,cell13,cell21,cell22,cell23} case cell13:{cell12,cell14,cell22,cell23,cell24} case cell14:{cell13,cell23,cell24} case cell21:{cell11,cell12,cell22,cell31,cell32} case cell22:{cell11,cell12,cell13,cell21,cell23,cell31,cell32,cell33} case cell23:{cell12,cell13,cell14,cell22,cell24,cell32,cell33,cell34} case cell24:{cell13,cell14,cell23,cell33,cell34} case cell31:{cell21,cell22,cell32,cell41,cell42} case cell32:{cell21,cell22,cell23,cell31,cell33,cell41,cell42,cell43} case cell33:{cell22,cell23,cell24,cell32,cell34,cell42,cell43,cell44} case cell34:{cell23,cell24,cell33,cell43,cell44} case cell41:{cell42,cell31,cell32} case cell42:{cell41,cell43,cell31,cell32,cell33} case cell43:{cell42,cell44,cell32,cell33,cell34} case cell44:{cell43,cell33,cell34} endswitch
    function aliveNeighb($c in Cell) = size({$i in Cell| and(contains(neighbours($c),$i),alive($i)) : $i})

    macro rule r_conway =
        if alive(self) then
            if or(lt(aliveNeighb(self),2),gt(aliveNeighb(self),3)) then
                alive(self) := false
            endif
        else 
            if eq(aliveNeighb(self),3) then
                alive(self) := true
            endif
        endif


    main rule r_Main =
        par
            program(cell33)
            program(cell24)
            program(cell31)
            program(cell34)
            program(cell11)
            program(cell43)
            program(cell44)
            program(cell13)
            program(cell22)
            program(cell12)
            program(cell23)
            program(cell21)
            program(cell32)
            program(cell41)
            program(cell14)
            program(cell42)
        endpar

default init s0:
    function alive($c in Cell) = switch $c case cell11:true case cell12:true case cell21:true case cell34:true case cell43:true case cell44:true otherwise false endswitch
    agent Cell:
r_conway[]
