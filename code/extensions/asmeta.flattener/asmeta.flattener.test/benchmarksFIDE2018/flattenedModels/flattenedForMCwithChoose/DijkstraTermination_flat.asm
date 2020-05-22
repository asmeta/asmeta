//applied flatteners: MCR FR AR LR CaR NR 
asm DijkstraTermination_flat
import ../STDL/StandardLibrary
import ../STDL/CTLlibrary
import ../STDL/LTLlibrary

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

definitions:

    domain Machine = {0,1,2,3,4,5}

    function master = 0
    function pred($m in Machine) = mod(plus($m,1),6)
    function isMaster($m in Machine) = eq($m,master)
    function hasToken($m in Machine) = eq($m,token)
    function terminated = and(not(firstRound),and(and(eq(token,0),eq(machineColor(0),WHITE)),eq(tokenColor,WHITE)))
    function stableState = (forall $m in Machine with not(active($m)))


    LTLSPEC g(implies(terminated,stableState))
    LTLSPEC g(implies(stableState,f(terminated)))
    CTLSPEC ag(implies(terminated,stableState))
    CTLSPEC ag(implies(stableState,ef(terminated)))
    main rule r_main =
        par
            if active(3) then
                choose $sendMsg113 in Boolean with true do
                    choose $higherMachine214 in Machine with true do
                        par
                            if and($sendMsg113,not(active($higherMachine214))) then
                                active($higherMachine214) := true
                            endif
                            if $sendMsg113 then
                                machineColor(3) := BLACK
                            endif
                        endpar
            endif
            if and(and(not(active(4)),hasToken(4)),neq(4,master)) then
                par
                    token := pred(4)
                    machineColor(4) := WHITE
                endpar
            endif
            if and(and(and(not(active(4)),hasToken(4)),isMaster(4)),or(or(eq(machineColor(4),BLACK),eq(tokenColor,BLACK)),firstRound)) then
                par
                    token := pred(4)
                    machineColor(4) := WHITE
                    machineColor(0) := WHITE
                    tokenColor := WHITE
                endpar
            endif
            if and(and(and(not(active(2)),hasToken(2)),isMaster(2)),or(or(eq(machineColor(2),BLACK),eq(tokenColor,BLACK)),firstRound)) then
                par
                    token := pred(2)
                    machineColor(2) := WHITE
                    machineColor(0) := WHITE
                    tokenColor := WHITE
                endpar
            endif
            if and(and(not(active(2)),hasToken(2)),neq(2,master)) then
                par
                    token := pred(2)
                    machineColor(2) := WHITE
                endpar
            endif
            if and(and(and(not(active(2)),hasToken(2)),neq(2,master)),eq(machineColor(2),BLACK)) then
                tokenColor := BLACK
            endif
            if active(0) then
                choose $sendMsg110 in Boolean with true do
                    choose $higherMachine211 in Machine with true do
                        par
                            if and($sendMsg110,not(active($higherMachine211))) then
                                active($higherMachine211) := true
                            endif
                            if $sendMsg110 then
                                machineColor(0) := BLACK
                            endif
                        endpar
            endif
            if and(and(not(active(5)),hasToken(5)),neq(5,master)) then
                par
                    token := pred(5)
                    machineColor(5) := WHITE
                endpar
            endif
            if active(4) then
                choose $sendMsg116 in Boolean with true do
                    choose $higherMachine217 in Machine with true do
                        par
                            if and($sendMsg116,not(active($higherMachine217))) then
                                active($higherMachine217) := true
                            endif
                            if $sendMsg116 then
                                machineColor(4) := BLACK
                            endif
                        endpar
            endif
            if active(0) then
                choose $nextStatus312 in Boolean with true do
                    active(0) := $nextStatus312
            endif
            if and(and(and(not(active(3)),hasToken(3)),neq(3,master)),eq(machineColor(3),BLACK)) then
                tokenColor := BLACK
            endif
            if active(1) then
                choose $sendMsg119 in Boolean with true do
                    choose $higherMachine220 in Machine with true do
                        par
                            if $sendMsg119 then
                                machineColor(1) := BLACK
                            endif
                            if and($sendMsg119,not(active($higherMachine220))) then
                                active($higherMachine220) := true
                            endif
                        endpar
            endif
            if active(1) then
                choose $nextStatus321 in Boolean with true do
                    active(1) := $nextStatus321
            endif
            if active(4) then
                choose $nextStatus318 in Boolean with true do
                    active(4) := $nextStatus318
            endif
            if and(and(and(not(active(1)),hasToken(1)),neq(1,master)),eq(machineColor(1),BLACK)) then
                tokenColor := BLACK
            endif
            if and(and(and(and(not(active(5)),hasToken(5)),isMaster(5)),or(or(eq(machineColor(5),BLACK),eq(tokenColor,BLACK)),firstRound)),firstRound) then
                firstRound := false
            endif
            if and(and(not(active(3)),hasToken(3)),neq(3,master)) then
                par
                    token := pred(3)
                    machineColor(3) := WHITE
                endpar
            endif
            if and(and(and(and(not(active(0)),hasToken(0)),isMaster(0)),or(or(eq(machineColor(0),BLACK),eq(tokenColor,BLACK)),firstRound)),firstRound) then
                firstRound := false
            endif
            if active(3) then
                choose $nextStatus315 in Boolean with true do
                    active(3) := $nextStatus315
            endif
            if and(and(and(not(active(3)),hasToken(3)),isMaster(3)),or(or(eq(machineColor(3),BLACK),eq(tokenColor,BLACK)),firstRound)) then
                par
                    token := pred(3)
                    machineColor(3) := WHITE
                    machineColor(0) := WHITE
                    tokenColor := WHITE
                endpar
            endif
            if and(and(not(active(0)),hasToken(0)),neq(0,master)) then
                par
                    token := pred(0)
                    machineColor(0) := WHITE
                endpar
            endif
            if and(and(and(not(active(4)),hasToken(4)),neq(4,master)),eq(machineColor(4),BLACK)) then
                tokenColor := BLACK
            endif
            if and(and(and(not(active(1)),hasToken(1)),isMaster(1)),or(or(eq(machineColor(1),BLACK),eq(tokenColor,BLACK)),firstRound)) then
                par
                    token := pred(1)
                    machineColor(1) := WHITE
                    machineColor(0) := WHITE
                    tokenColor := WHITE
                endpar
            endif
            if active(2) then
                choose $nextStatus36 in Boolean with true do
                    active(2) := $nextStatus36
            endif
            if active(5) then
                choose $sendMsg17 in Boolean with true do
                    choose $higherMachine28 in Machine with true do
                        par
                            if and($sendMsg17,not(active($higherMachine28))) then
                                active($higherMachine28) := true
                            endif
                            if $sendMsg17 then
                                machineColor(5) := BLACK
                            endif
                        endpar
            endif
            if and(and(and(not(active(0)),hasToken(0)),neq(0,master)),eq(machineColor(0),BLACK)) then
                tokenColor := BLACK
            endif
            if active(2) then
                choose $sendMsg14 in Boolean with true do
                    choose $higherMachine25 in Machine with true do
                        par
                            if and($sendMsg14,not(active($higherMachine25))) then
                                active($higherMachine25) := true
                            endif
                            if $sendMsg14 then
                                machineColor(2) := BLACK
                            endif
                        endpar
            endif
            if and(and(and(and(not(active(3)),hasToken(3)),isMaster(3)),or(or(eq(machineColor(3),BLACK),eq(tokenColor,BLACK)),firstRound)),firstRound) then
                firstRound := false
            endif
            if active(5) then
                choose $nextStatus39 in Boolean with true do
                    active(5) := $nextStatus39
            endif
            if and(and(and(not(active(5)),hasToken(5)),isMaster(5)),or(or(eq(machineColor(5),BLACK),eq(tokenColor,BLACK)),firstRound)) then
                par
                    token := pred(5)
                    machineColor(5) := WHITE
                    machineColor(0) := WHITE
                    tokenColor := WHITE
                endpar
            endif
            if and(and(not(active(1)),hasToken(1)),neq(1,master)) then
                par
                    token := pred(1)
                    machineColor(1) := WHITE
                endpar
            endif
            if and(and(and(and(not(active(2)),hasToken(2)),isMaster(2)),or(or(eq(machineColor(2),BLACK),eq(tokenColor,BLACK)),firstRound)),firstRound) then
                firstRound := false
            endif
            if and(and(and(not(active(5)),hasToken(5)),neq(5,master)),eq(machineColor(5),BLACK)) then
                tokenColor := BLACK
            endif
            if and(and(and(not(active(0)),hasToken(0)),isMaster(0)),or(or(eq(machineColor(0),BLACK),eq(tokenColor,BLACK)),firstRound)) then
                par
                    token := pred(0)
                    machineColor(0) := WHITE
                    machineColor(0) := WHITE
                    tokenColor := WHITE
                endpar
            endif
            if and(and(and(and(not(active(1)),hasToken(1)),isMaster(1)),or(or(eq(machineColor(1),BLACK),eq(tokenColor,BLACK)),firstRound)),firstRound) then
                firstRound := false
            endif
            if and(and(and(and(not(active(4)),hasToken(4)),isMaster(4)),or(or(eq(machineColor(4),BLACK),eq(tokenColor,BLACK)),firstRound)),firstRound) then
                firstRound := false
            endif
        endpar

default init s_0:
    function token = 0
    function active($m in Machine) = initActive($m)
    function machineColor($m in Machine) = at({0->WHITE,1->BLACK,2->BLACK,3->BLACK,4->BLACK,5->BLACK},$m)
    function tokenColor = WHITE
    function firstRound = true
