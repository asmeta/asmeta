//applied flatteners: MCR FR ChR AR LR CaR NR 
asm SluiceGateMotorCtl_flat
import ./STDL/StandardLibrary
import ./STDL/CTLlibrary

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


    CTLSPEC ef(eq(phase,FULLYOPEN))
    main rule r_Main =
        par
            if and(and(eq(phase,OPENING),eq(top,bottom)),event(bottom)) then
                par
                    motor := OFF
                    phase := FULLYOPEN
                endpar
            endif
            if and(and(eq(phase,FULLYCLOSED),eq(closedPeriod,170)),passed(170)) then
                par
                    dir := CLOCKWISE
                    motor := ON
                    phase := OPENING
                endpar
            endif
            if and(and(eq(phase,FULLYCLOSED),eq(closedPeriod,10)),passed(10)) then
                par
                    dir := CLOCKWISE
                    motor := ON
                    phase := OPENING
                endpar
            endif
            if and(eq(phase,OPENING),event(top)) then
                par
                    motor := OFF
                    phase := FULLYOPEN
                endpar
            endif
            if and(eq(phase,CLOSING),event(bottom)) then
                par
                    motor := OFF
                    phase := FULLYCLOSED
                endpar
            endif
            if and(and(eq(phase,CLOSING),eq(bottom,top)),event(top)) then
                par
                    motor := OFF
                    phase := FULLYCLOSED
                endpar
            endif
            if and(and(eq(phase,FULLYOPEN),eq(openPeriod,170)),passed(170)) then
                par
                    dir := ANTICLOCKWISE
                    motor := ON
                    phase := CLOSING
                endpar
            endif
            if and(and(eq(phase,FULLYOPEN),eq(openPeriod,10)),passed(10)) then
                par
                    dir := ANTICLOCKWISE
                    motor := ON
                    phase := CLOSING
                endpar
            endif
        endpar

default init s0:
    function phase = FULLYCLOSED
    function motor = OFF
    function dir = ANTICLOCKWISE
    function passed($m in Minutes) = true
    function event($e in Position) = false
