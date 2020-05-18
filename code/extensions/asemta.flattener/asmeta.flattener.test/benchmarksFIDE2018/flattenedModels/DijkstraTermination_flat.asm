//applied flatteners: MCR FR ChR AR LR CaR NR 
asm DijkstraTermination_flat
import ./STDL/StandardLibrary
import ./STDL/CTLlibrary
import ./STDL/LTLlibrary

signature:
    domain Machine subsetof Integer
    enum domain Color = {WHITE | BLACK}

    controlled machineColor: Machine -> Color
    controlled tokenColor: Color
    static master: Machine
    controlled active: Machine -> Boolean
    derived hasToken: Machine -> Boolean
    static isMaster: Machine -> Boolean
    controlled token: Machine
    static pred: Machine -> Machine
    monitored initActive: Machine -> Boolean
    derived terminated: Boolean
    derived stableState: Boolean
    controlled firstRound: Boolean
    derived chooseVar0: Boolean
    derived chooseVar1: Machine
    derived chooseVar2: Boolean
    derived chooseVar3: Boolean
    derived chooseVar4: Machine
    derived chooseVar5: Boolean
    derived chooseVar6: Boolean
    derived chooseVar7: Machine
    derived chooseVar8: Boolean
    derived chooseVar9: Boolean
    derived chooseVar10: Machine
    derived chooseVar11: Boolean
    derived chooseVar12: Boolean
    derived chooseVar13: Machine
    derived chooseVar14: Boolean
    derived chooseVar15: Boolean
    derived chooseVar16: Machine
    derived chooseVar17: Boolean

definitions:

    domain Machine = {0,1,2,3,4,5}

    function master = 0
    function pred($m in Machine) = mod(plus($m,1),6)
    function isMaster($m in Machine) = eq($m,master)
    function hasToken($m in Machine) = eq($m,token)
    function terminated = and(not(firstRound),and(and(eq(token,0),eq(machineColor(0),WHITE)),eq(tokenColor,WHITE)))
    function stableState = (forall $m in Machine with not(active($m)))
    function chooseVar0 = chooseone({$sendMsg14 in Boolean| true : $sendMsg14})
    function chooseVar1 = chooseone({$higherMachine25 in Machine| true : $higherMachine25})
    function chooseVar2 = chooseone({$nextStatus36 in Boolean| true : $nextStatus36})
    function chooseVar3 = chooseone({$sendMsg17 in Boolean| true : $sendMsg17})
    function chooseVar4 = chooseone({$higherMachine28 in Machine| true : $higherMachine28})
    function chooseVar5 = chooseone({$nextStatus39 in Boolean| true : $nextStatus39})
    function chooseVar6 = chooseone({$sendMsg110 in Boolean| true : $sendMsg110})
    function chooseVar7 = chooseone({$higherMachine211 in Machine| true : $higherMachine211})
    function chooseVar8 = chooseone({$nextStatus312 in Boolean| true : $nextStatus312})
    function chooseVar9 = chooseone({$sendMsg113 in Boolean| true : $sendMsg113})
    function chooseVar10 = chooseone({$higherMachine214 in Machine| true : $higherMachine214})
    function chooseVar11 = chooseone({$nextStatus315 in Boolean| true : $nextStatus315})
    function chooseVar12 = chooseone({$sendMsg116 in Boolean| true : $sendMsg116})
    function chooseVar13 = chooseone({$higherMachine217 in Machine| true : $higherMachine217})
    function chooseVar14 = chooseone({$nextStatus318 in Boolean| true : $nextStatus318})
    function chooseVar15 = chooseone({$sendMsg119 in Boolean| true : $sendMsg119})
    function chooseVar16 = chooseone({$higherMachine220 in Machine| true : $higherMachine220})
    function chooseVar17 = chooseone({$nextStatus321 in Boolean| true : $nextStatus321})


    LTLSPEC g(implies(terminated,stableState))
    LTLSPEC g(implies(stableState,f(terminated)))
    CTLSPEC ag(implies(terminated,stableState))
    CTLSPEC ag(implies(stableState,ef(terminated)))
    main rule r_main =
        par
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,1)),not(active(1))),eq(chooseVar4,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,2)),not(active(2))),eq(chooseVar1,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,5)),not(active(5))),eq(chooseVar4,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,0)),not(active(0))),eq(chooseVar1,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,3)),not(active(3))),eq(chooseVar13,0)) then
                active(0) := true
            endif
            if and(and(and(not(active(5)),hasToken(5)),neq(5,master)),eq(machineColor(5),BLACK)) then
                tokenColor := BLACK
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,0)),not(active(0))),eq(chooseVar7,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,0)),not(active(0))),eq(chooseVar10,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,0)),not(active(0))),eq(chooseVar10,0)) then
                active(0) := true
            endif
            if and(and(and(not(active(3)),hasToken(3)),isMaster(3)),or(or(eq(machineColor(3),BLACK),eq(tokenColor,BLACK)),firstRound)) then
                par
                    token := pred(3)
                    machineColor(3) := WHITE
                    machineColor(0) := WHITE
                    tokenColor := WHITE
                endpar
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,5)),not(active(5))),eq(chooseVar13,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,4)),not(active(4))),eq(chooseVar16,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,3)),not(active(3))),eq(chooseVar1,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,0)),not(active(0))),eq(chooseVar13,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,3)),not(active(3))),eq(chooseVar13,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,0)),not(active(0))),eq(chooseVar13,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,1)),not(active(1))),eq(chooseVar7,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,1)),not(active(1))),eq(chooseVar10,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,5)),not(active(5))),eq(chooseVar10,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,3)),not(active(3))),eq(chooseVar10,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,5)),not(active(5))),eq(chooseVar13,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,1)),not(active(1))),eq(chooseVar16,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,5)),not(active(5))),eq(chooseVar13,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,2)),not(active(2))),eq(chooseVar7,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,4)),not(active(4))),eq(chooseVar1,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,0)),not(active(0))),eq(chooseVar16,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,4)),not(active(4))),eq(chooseVar16,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,1)),not(active(1))),eq(chooseVar13,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,4)),not(active(4))),eq(chooseVar16,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,5)),not(active(5))),eq(chooseVar4,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,2)),not(active(2))),eq(chooseVar1,2)) then
                active(2) := true
            endif
            if and(and(not(active(0)),hasToken(0)),neq(0,master)) then
                par
                    token := pred(0)
                    machineColor(0) := WHITE
                endpar
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,1)),not(active(1))),eq(chooseVar13,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,5)),not(active(5))),eq(chooseVar1,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,5)),not(active(5))),eq(chooseVar7,5)) then
                active(5) := true
            endif
            if and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9) then
                machineColor(2) := BLACK
            endif
            if and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15) then
                machineColor(4) := BLACK
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,2)),not(active(2))),eq(chooseVar16,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,1)),not(active(1))),eq(chooseVar4,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,4)),not(active(4))),eq(chooseVar7,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,1)),not(active(1))),eq(chooseVar13,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,0)),not(active(0))),eq(chooseVar1,2)) then
                active(2) := true
            endif
            if and(and(and(and(not(active(4)),hasToken(4)),isMaster(4)),or(or(eq(machineColor(4),BLACK),eq(tokenColor,BLACK)),firstRound)),firstRound) then
                firstRound := false
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,2)),not(active(2))),eq(chooseVar13,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,5)),not(active(5))),eq(chooseVar16,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,1)),not(active(1))),eq(chooseVar4,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,2)),not(active(2))),eq(chooseVar10,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,2)),not(active(2))),eq(chooseVar10,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,2)),not(active(2))),eq(chooseVar13,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,4)),not(active(4))),eq(chooseVar4,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,4)),not(active(4))),eq(chooseVar10,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,0)),not(active(0))),eq(chooseVar16,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,1)),not(active(1))),eq(chooseVar1,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,4)),not(active(4))),eq(chooseVar1,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,0)),not(active(0))),eq(chooseVar10,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,3)),not(active(3))),eq(chooseVar4,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,5)),not(active(5))),eq(chooseVar4,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,3)),not(active(3))),eq(chooseVar4,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,2)),not(active(2))),eq(chooseVar13,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,3)),not(active(3))),eq(chooseVar7,5)) then
                active(5) := true
            endif
            if and(and(and(not(active(4)),hasToken(4)),neq(4,master)),eq(machineColor(4),BLACK)) then
                tokenColor := BLACK
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,4)),not(active(4))),eq(chooseVar1,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,4)),not(active(4))),eq(chooseVar13,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,0)),not(active(0))),eq(chooseVar4,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,3)),not(active(3))),eq(chooseVar4,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,5)),not(active(5))),eq(chooseVar7,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,4)),not(active(4))),eq(chooseVar4,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,2)),not(active(2))),eq(chooseVar4,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,3)),not(active(3))),eq(chooseVar4,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,1)),not(active(1))),eq(chooseVar16,4)) then
                active(4) := true
            endif
            if and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6) then
                machineColor(5) := BLACK
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,0)),not(active(0))),eq(chooseVar13,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,5)),not(active(5))),eq(chooseVar10,0)) then
                active(0) := true
            endif
            if and(and(not(active(3)),hasToken(3)),neq(3,master)) then
                par
                    token := pred(3)
                    machineColor(3) := WHITE
                endpar
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,4)),not(active(4))),eq(chooseVar13,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,3)),not(active(3))),eq(chooseVar1,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,5)),not(active(5))),eq(chooseVar16,1)) then
                active(1) := true
            endif
            if and(and(and(and(not(active(2)),hasToken(2)),isMaster(2)),or(or(eq(machineColor(2),BLACK),eq(tokenColor,BLACK)),firstRound)),firstRound) then
                firstRound := false
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,2)),not(active(2))),eq(chooseVar13,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,3)),not(active(3))),eq(chooseVar16,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,0)),not(active(0))),eq(chooseVar7,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,1)),not(active(1))),eq(chooseVar4,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,2)),not(active(2))),eq(chooseVar4,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,1)),not(active(1))),eq(chooseVar7,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,5)),not(active(5))),eq(chooseVar13,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,5)),not(active(5))),eq(chooseVar16,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,0)),not(active(0))),eq(chooseVar10,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,0)),not(active(0))),eq(chooseVar7,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,0)),not(active(0))),eq(chooseVar1,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,4)),not(active(4))),eq(chooseVar7,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,5)),not(active(5))),eq(chooseVar13,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,2)),not(active(2))),eq(chooseVar7,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,5)),not(active(5))),eq(chooseVar4,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,2)),not(active(2))),eq(chooseVar10,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,1)),not(active(1))),eq(chooseVar7,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,0)),not(active(0))),eq(chooseVar16,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,4)),not(active(4))),eq(chooseVar13,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,3)),not(active(3))),eq(chooseVar1,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,2)),not(active(2))),eq(chooseVar1,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,2)),not(active(2))),eq(chooseVar4,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,3)),not(active(3))),eq(chooseVar10,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,0)),not(active(0))),eq(chooseVar13,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,2)),not(active(2))),eq(chooseVar13,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,5)),not(active(5))),eq(chooseVar4,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,0)),not(active(0))),eq(chooseVar10,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,5)),not(active(5))),eq(chooseVar1,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,3)),not(active(3))),eq(chooseVar1,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,0)),not(active(0))),eq(chooseVar16,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,5)),not(active(5))),eq(chooseVar1,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,3)),not(active(3))),eq(chooseVar13,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,5)),not(active(5))),eq(chooseVar7,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,3)),not(active(3))),eq(chooseVar7,0)) then
                active(0) := true
            endif
            if and(active(3),isDef(chooseVar14)) then
                active(3) := chooseVar14
            endif
            if and(and(not(active(4)),hasToken(4)),neq(4,master)) then
                par
                    token := pred(4)
                    machineColor(4) := WHITE
                endpar
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,1)),not(active(1))),eq(chooseVar1,2)) then
                active(2) := true
            endif
            if and(active(1),isDef(chooseVar5)) then
                active(1) := chooseVar5
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,2)),not(active(2))),eq(chooseVar7,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,4)),not(active(4))),eq(chooseVar7,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,5)),not(active(5))),eq(chooseVar1,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,5)),not(active(5))),eq(chooseVar7,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,2)),not(active(2))),eq(chooseVar10,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,2)),not(active(2))),eq(chooseVar1,4)) then
                active(4) := true
            endif
            if and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0) then
                machineColor(0) := BLACK
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,5)),not(active(5))),eq(chooseVar1,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,0)),not(active(0))),eq(chooseVar16,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,1)),not(active(1))),eq(chooseVar16,2)) then
                active(2) := true
            endif
            if and(and(and(and(not(active(5)),hasToken(5)),isMaster(5)),or(or(eq(machineColor(5),BLACK),eq(tokenColor,BLACK)),firstRound)),firstRound) then
                firstRound := false
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,1)),not(active(1))),eq(chooseVar16,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,5)),not(active(5))),eq(chooseVar10,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,5)),not(active(5))),eq(chooseVar10,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,5)),not(active(5))),eq(chooseVar7,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,3)),not(active(3))),eq(chooseVar7,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,3)),not(active(3))),eq(chooseVar4,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,1)),not(active(1))),eq(chooseVar13,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,1)),not(active(1))),eq(chooseVar16,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,4)),not(active(4))),eq(chooseVar10,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,4)),not(active(4))),eq(chooseVar4,0)) then
                active(0) := true
            endif
            if and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12) then
                machineColor(3) := BLACK
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,1)),not(active(1))),eq(chooseVar1,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,0)),not(active(0))),eq(chooseVar13,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,2)),not(active(2))),eq(chooseVar10,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,1)),not(active(1))),eq(chooseVar10,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,1)),not(active(1))),eq(chooseVar13,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,5)),not(active(5))),eq(chooseVar13,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,3)),not(active(3))),eq(chooseVar16,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,4)),not(active(4))),eq(chooseVar4,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,1)),not(active(1))),eq(chooseVar1,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,0)),not(active(0))),eq(chooseVar1,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,5)),not(active(5))),eq(chooseVar1,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,4)),not(active(4))),eq(chooseVar16,4)) then
                active(4) := true
            endif
            if and(and(and(not(active(5)),hasToken(5)),isMaster(5)),or(or(eq(machineColor(5),BLACK),eq(tokenColor,BLACK)),firstRound)) then
                par
                    token := pred(5)
                    machineColor(5) := WHITE
                    machineColor(0) := WHITE
                    tokenColor := WHITE
                endpar
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,2)),not(active(2))),eq(chooseVar7,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,1)),not(active(1))),eq(chooseVar10,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,2)),not(active(2))),eq(chooseVar13,3)) then
                active(3) := true
            endif
            if and(and(not(active(2)),hasToken(2)),neq(2,master)) then
                par
                    token := pred(2)
                    machineColor(2) := WHITE
                endpar
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,0)),not(active(0))),eq(chooseVar4,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,4)),not(active(4))),eq(chooseVar13,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,2)),not(active(2))),eq(chooseVar16,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,3)),not(active(3))),eq(chooseVar7,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,0)),not(active(0))),eq(chooseVar4,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,5)),not(active(5))),eq(chooseVar10,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,3)),not(active(3))),eq(chooseVar10,3)) then
                active(3) := true
            endif
            if and(and(and(and(not(active(0)),hasToken(0)),isMaster(0)),or(or(eq(machineColor(0),BLACK),eq(tokenColor,BLACK)),firstRound)),firstRound) then
                firstRound := false
            endif
            if and(and(and(not(active(4)),hasToken(4)),isMaster(4)),or(or(eq(machineColor(4),BLACK),eq(tokenColor,BLACK)),firstRound)) then
                par
                    token := pred(4)
                    machineColor(4) := WHITE
                    machineColor(0) := WHITE
                    tokenColor := WHITE
                endpar
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,3)),not(active(3))),eq(chooseVar7,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,4)),not(active(4))),eq(chooseVar7,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,2)),not(active(2))),eq(chooseVar1,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,1)),not(active(1))),eq(chooseVar7,0)) then
                active(0) := true
            endif
            if and(and(not(active(5)),hasToken(5)),neq(5,master)) then
                par
                    token := pred(5)
                    machineColor(5) := WHITE
                endpar
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,3)),not(active(3))),eq(chooseVar13,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,1)),not(active(1))),eq(chooseVar4,3)) then
                active(3) := true
            endif
            if and(active(2),isDef(chooseVar11)) then
                active(2) := chooseVar11
            endif
            if and(and(and(not(active(2)),hasToken(2)),isMaster(2)),or(or(eq(machineColor(2),BLACK),eq(tokenColor,BLACK)),firstRound)) then
                par
                    token := pred(2)
                    machineColor(2) := WHITE
                    machineColor(0) := WHITE
                    tokenColor := WHITE
                endpar
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,1)),not(active(1))),eq(chooseVar4,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,4)),not(active(4))),eq(chooseVar13,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,0)),not(active(0))),eq(chooseVar4,0)) then
                active(0) := true
            endif
            if and(and(and(not(active(0)),hasToken(0)),isMaster(0)),or(or(eq(machineColor(0),BLACK),eq(tokenColor,BLACK)),firstRound)) then
                par
                    token := pred(0)
                    machineColor(0) := WHITE
                    machineColor(0) := WHITE
                    tokenColor := WHITE
                endpar
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,0)),not(active(0))),eq(chooseVar13,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,1)),not(active(1))),eq(chooseVar1,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,2)),not(active(2))),eq(chooseVar16,4)) then
                active(4) := true
            endif
            if and(and(and(not(active(1)),hasToken(1)),neq(1,master)),eq(machineColor(1),BLACK)) then
                tokenColor := BLACK
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,5)),not(active(5))),eq(chooseVar7,3)) then
                active(3) := true
            endif
            if and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3) then
                machineColor(1) := BLACK
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,4)),not(active(4))),eq(chooseVar4,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,3)),not(active(3))),eq(chooseVar16,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,2)),not(active(2))),eq(chooseVar10,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,4)),not(active(4))),eq(chooseVar10,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,2)),not(active(2))),eq(chooseVar7,4)) then
                active(4) := true
            endif
            if and(and(and(not(active(1)),hasToken(1)),isMaster(1)),or(or(eq(machineColor(1),BLACK),eq(tokenColor,BLACK)),firstRound)) then
                par
                    token := pred(1)
                    machineColor(1) := WHITE
                    machineColor(0) := WHITE
                    tokenColor := WHITE
                endpar
            endif
            if and(active(0),isDef(chooseVar2)) then
                active(0) := chooseVar2
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,0)),not(active(0))),eq(chooseVar4,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,1)),not(active(1))),eq(chooseVar7,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,0)),not(active(0))),eq(chooseVar10,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,4)),not(active(4))),eq(chooseVar4,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,5)),not(active(5))),eq(chooseVar16,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,5)),not(active(5))),eq(chooseVar16,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,3)),not(active(3))),eq(chooseVar10,5)) then
                active(5) := true
            endif
            if and(and(and(and(not(active(3)),hasToken(3)),isMaster(3)),or(or(eq(machineColor(3),BLACK),eq(tokenColor,BLACK)),firstRound)),firstRound) then
                firstRound := false
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,0)),not(active(0))),eq(chooseVar16,5)) then
                active(5) := true
            endif
            if and(active(4),isDef(chooseVar17)) then
                active(4) := chooseVar17
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,1)),not(active(1))),eq(chooseVar13,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,3)),not(active(3))),eq(chooseVar10,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,4)),not(active(4))),eq(chooseVar1,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,1)),not(active(1))),eq(chooseVar10,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,3)),not(active(3))),eq(chooseVar7,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,3)),not(active(3))),eq(chooseVar4,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,2)),not(active(2))),eq(chooseVar4,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,4)),not(active(4))),eq(chooseVar7,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,1)),not(active(1))),eq(chooseVar16,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,4)),not(active(4))),eq(chooseVar7,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,0)),not(active(0))),eq(chooseVar7,3)) then
                active(3) := true
            endif
            if and(and(and(not(active(0)),hasToken(0)),neq(0,master)),eq(machineColor(0),BLACK)) then
                tokenColor := BLACK
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,0)),not(active(0))),eq(chooseVar7,0)) then
                active(0) := true
            endif
            if and(and(and(not(active(2)),hasToken(2)),neq(2,master)),eq(machineColor(2),BLACK)) then
                tokenColor := BLACK
            endif
            if and(and(and(not(active(3)),hasToken(3)),neq(3,master)),eq(machineColor(3),BLACK)) then
                tokenColor := BLACK
            endif
            if and(and(and(and(not(active(1)),hasToken(1)),isMaster(1)),or(or(eq(machineColor(1),BLACK),eq(tokenColor,BLACK)),firstRound)),firstRound) then
                firstRound := false
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,0)),not(active(0))),eq(chooseVar1,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,2)),not(active(2))),eq(chooseVar7,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,2)),not(active(2))),eq(chooseVar16,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,5)),not(active(5))),eq(chooseVar16,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,2)),not(active(2))),eq(chooseVar1,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,4)),not(active(4))),eq(chooseVar1,1)) then
                active(1) := true
            endif
            if and(and(not(active(1)),hasToken(1)),neq(1,master)) then
                par
                    token := pred(1)
                    machineColor(1) := WHITE
                endpar
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,5)),not(active(5))),eq(chooseVar10,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,1)),not(active(1))),eq(chooseVar10,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,3)),not(active(3))),eq(chooseVar13,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,4)),not(active(4))),eq(chooseVar13,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,3)),not(active(3))),eq(chooseVar16,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,2)),not(active(2))),eq(chooseVar4,4)) then
                active(4) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,4)),not(active(4))),eq(chooseVar16,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,0)),not(active(0))),eq(chooseVar4,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,5)),not(active(5))),eq(chooseVar4,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,3)),not(active(3))),eq(chooseVar1,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(3),isDef(chooseVar12)),isDef(chooseVar13)),chooseVar12),eq(chooseVar13,3)),not(active(3))),eq(chooseVar13,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,2)),not(active(2))),eq(chooseVar16,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,4)),not(active(4))),eq(chooseVar1,0)) then
                active(0) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,3)),not(active(3))),eq(chooseVar1,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,4)),not(active(4))),eq(chooseVar16,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,1)),not(active(1))),eq(chooseVar1,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,3)),not(active(3))),eq(chooseVar10,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,4)),not(active(4))),eq(chooseVar10,2)) then
                active(2) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,4)),not(active(4))),eq(chooseVar10,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,2)),not(active(2))),eq(chooseVar16,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,1)),not(active(1))),eq(chooseVar10,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(0),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar0),eq(chooseVar1,0)),not(active(0))),eq(chooseVar1,3)) then
                active(3) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,0)),not(active(0))),eq(chooseVar7,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(2),isDef(chooseVar9)),isDef(chooseVar10)),chooseVar9),eq(chooseVar10,4)),not(active(4))),eq(chooseVar10,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,3)),not(active(3))),eq(chooseVar16,5)) then
                active(5) := true
            endif
            if and(and(and(and(and(and(active(4),isDef(chooseVar15)),isDef(chooseVar16)),chooseVar15),eq(chooseVar16,3)),not(active(3))),eq(chooseVar16,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(1),isDef(chooseVar3)),isDef(chooseVar4)),chooseVar3),eq(chooseVar4,2)),not(active(2))),eq(chooseVar4,1)) then
                active(1) := true
            endif
            if and(and(and(and(and(and(active(5),isDef(chooseVar6)),isDef(chooseVar7)),chooseVar6),eq(chooseVar7,1)),not(active(1))),eq(chooseVar7,3)) then
                active(3) := true
            endif
            if and(active(5),isDef(chooseVar8)) then
                active(5) := chooseVar8
            endif
        endpar

default init s_0:
    function token = 0
    function active($m in Machine) = initActive($m)
    function machineColor($m in Machine) = at({0->WHITE,1->BLACK,2->BLACK,3->BLACK,4->BLACK,5->BLACK},$m)
    function tokenColor = WHITE
    function firstRound = true
