asm letRule4Sluicegate

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
    domain Minutes subsetof Integer
    abstract domain Position
    enum domain PhaseDomain = {FULLYCLOSED | OPENING | FULLYOPEN | CLOSING}
    enum domain DirectionDomain = {CLOCKWISE | ANTICLOCKWISE}
    enum domain MotorDomain = {ON | OFF}

    controlled phase: PhaseDomain
    controlled dir: DirectionDomain
    controlled motor: MotorDomain
    static openPeriod: Minutes
    static closedPeriod: Minutes
    static top: Position
    static bottom: Position
    monitored passed: Minutes -> Boolean
    monitored event: Position -> Boolean

definitions:

    domain Minutes = {10,170}

    function openPeriod = 10
    function closedPeriod = 170


    main rule r_Main =
        if eq(phase,OPENING) then
            let ($var_0 = top) in
                if event($var_0) then
                    par
                        dir := CLOCKWISE
                        motor := OFF
                        phase := FULLYOPEN
                    endpar
                endif
            endlet
        endif

default init s0:
    // this function does not belong to this asm, but it can be initialized 
    function phase = FULLYCLOSED
    // this function does not belong to this asm, but it can be initialized 
    function motor = OFF
    // this function does not belong to this asm, but it can be initialized 
    function dir = ANTICLOCKWISE