//applied flatteners: MCR FR ChR AR LR CaR NR 
asm OneWayTrafficLight_flat
import ./STDL/StandardLibrary

signature:
    abstract domain LightUnit
    domain TimerDomain subsetof Integer
    enum domain PhaseDomain = {STOP1STOP2 | GO2STOP1 | STOP2STOP1 | GO1STOP2}

    controlled phase: PhaseDomain
    controlled stopLight: LightUnit -> Boolean
    controlled goLight: LightUnit -> Boolean
    static timer: PhaseDomain -> TimerDomain
    static lightUnit1: LightUnit
    static lightUnit2: LightUnit
    monitored passed: Integer -> Boolean

definitions:

    domain TimerDomain = {50,120}

    function timer($p in PhaseDomain) = switch $p case STOP1STOP2:50 case GO2STOP1:120 case STOP2STOP1:50 case GO1STOP2:120 endswitch


    main rule r_Main =
        par
            if and(and(and(eq(phase,STOP1STOP2),eq(timer(STOP1STOP2),50)),passed(50)),eq(lightUnit2,lightUnit1)) then
                stopLight(lightUnit1) := not(stopLight(lightUnit2))
            endif
            if and(and(and(eq(phase,GO1STOP2),eq(timer(GO1STOP2),120)),passed(120)),eq(lightUnit1,lightUnit2)) then
                stopLight(lightUnit2) := not(stopLight(lightUnit1))
            endif
            if and(and(and(eq(phase,STOP1STOP2),eq(timer(STOP1STOP2),120)),passed(120)),eq(lightUnit2,lightUnit1)) then
                stopLight(lightUnit2) := not(stopLight(lightUnit1))
            endif
            if and(and(and(eq(phase,STOP2STOP1),eq(timer(STOP2STOP1),120)),passed(120)),eq(lightUnit1,lightUnit2)) then
                goLight(lightUnit1) := not(goLight(lightUnit2))
            endif
            if and(and(and(eq(phase,STOP2STOP1),eq(timer(STOP2STOP1),120)),passed(120)),eq(lightUnit1,lightUnit2)) then
                goLight(lightUnit2) := not(goLight(lightUnit1))
            endif
            if and(and(and(eq(phase,GO1STOP2),eq(timer(GO1STOP2),50)),passed(50)),and(eq(lightUnit1,lightUnit2),eq(lightUnit1,lightUnit2))) then
                goLight(lightUnit2) := not(goLight(lightUnit2))
            endif
            if and(and(and(eq(phase,GO1STOP2),eq(timer(GO1STOP2),50)),passed(50)),eq(lightUnit1,lightUnit2)) then
                stopLight(lightUnit1) := not(stopLight(lightUnit2))
            endif
            if and(and(and(eq(phase,STOP1STOP2),eq(timer(STOP1STOP2),120)),passed(120)),eq(lightUnit2,lightUnit1)) then
                goLight(lightUnit2) := not(goLight(lightUnit1))
            endif
            if and(and(and(eq(phase,GO2STOP1),eq(timer(GO2STOP1),50)),passed(50)),and(eq(lightUnit2,lightUnit1),eq(lightUnit2,lightUnit1))) then
                goLight(lightUnit1) := not(goLight(lightUnit1))
            endif
            if and(and(eq(phase,STOP2STOP1),eq(timer(STOP2STOP1),50)),passed(50)) then
                par
                    goLight(lightUnit1) := not(goLight(lightUnit1))
                    stopLight(lightUnit1) := not(stopLight(lightUnit1))
                    phase := GO1STOP2
                endpar
            endif
            if and(and(eq(phase,GO2STOP1),eq(timer(GO2STOP1),50)),passed(50)) then
                par
                    goLight(lightUnit2) := not(goLight(lightUnit2))
                    stopLight(lightUnit2) := not(stopLight(lightUnit2))
                    phase := STOP2STOP1
                endpar
            endif
            if and(and(and(eq(phase,GO2STOP1),eq(timer(GO2STOP1),120)),passed(120)),eq(lightUnit2,lightUnit1)) then
                stopLight(lightUnit1) := not(stopLight(lightUnit2))
            endif
            if and(and(and(eq(phase,GO1STOP2),eq(timer(GO1STOP2),120)),passed(120)),eq(lightUnit1,lightUnit2)) then
                stopLight(lightUnit1) := not(stopLight(lightUnit2))
            endif
            if and(and(and(eq(phase,GO1STOP2),eq(timer(GO1STOP2),50)),passed(50)),eq(lightUnit1,lightUnit2)) then
                stopLight(lightUnit2) := not(stopLight(lightUnit1))
            endif
            if and(and(eq(phase,STOP1STOP2),eq(timer(STOP1STOP2),50)),passed(50)) then
                par
                    goLight(lightUnit2) := not(goLight(lightUnit2))
                    stopLight(lightUnit2) := not(stopLight(lightUnit2))
                    phase := GO2STOP1
                endpar
            endif
            if and(and(and(eq(phase,STOP2STOP1),eq(timer(STOP2STOP1),120)),passed(120)),eq(lightUnit1,lightUnit2)) then
                stopLight(lightUnit2) := not(stopLight(lightUnit1))
            endif
            if and(and(and(eq(phase,GO1STOP2),eq(timer(GO1STOP2),120)),passed(120)),eq(lightUnit1,lightUnit2)) then
                goLight(lightUnit1) := not(goLight(lightUnit2))
            endif
            if and(and(and(eq(phase,GO2STOP1),eq(timer(GO2STOP1),120)),passed(120)),eq(lightUnit2,lightUnit1)) then
                goLight(lightUnit1) := not(goLight(lightUnit2))
            endif
            if and(and(and(eq(phase,GO2STOP1),eq(timer(GO2STOP1),50)),passed(50)),eq(lightUnit2,lightUnit1)) then
                goLight(lightUnit1) := not(goLight(lightUnit2))
            endif
            if and(and(and(eq(phase,GO2STOP1),eq(timer(GO2STOP1),50)),passed(50)),and(eq(lightUnit2,lightUnit1),eq(lightUnit2,lightUnit1))) then
                stopLight(lightUnit1) := not(stopLight(lightUnit1))
            endif
            if and(and(and(eq(phase,STOP2STOP1),eq(timer(STOP2STOP1),120)),passed(120)),and(eq(lightUnit1,lightUnit2),eq(lightUnit1,lightUnit2))) then
                stopLight(lightUnit2) := not(stopLight(lightUnit2))
            endif
            if and(and(and(eq(phase,STOP2STOP1),eq(timer(STOP2STOP1),120)),passed(120)),eq(lightUnit1,lightUnit2)) then
                stopLight(lightUnit1) := not(stopLight(lightUnit2))
            endif
            if and(and(and(eq(phase,STOP1STOP2),eq(timer(STOP1STOP2),120)),passed(120)),and(eq(lightUnit2,lightUnit1),eq(lightUnit2,lightUnit1))) then
                goLight(lightUnit1) := not(goLight(lightUnit1))
            endif
            if and(and(and(eq(phase,STOP2STOP1),eq(timer(STOP2STOP1),50)),passed(50)),eq(lightUnit1,lightUnit2)) then
                goLight(lightUnit2) := not(goLight(lightUnit1))
            endif
            if and(and(and(eq(phase,STOP2STOP1),eq(timer(STOP2STOP1),120)),passed(120)),and(eq(lightUnit1,lightUnit2),eq(lightUnit1,lightUnit2))) then
                goLight(lightUnit2) := not(goLight(lightUnit2))
            endif
            if and(and(eq(phase,GO1STOP2),eq(timer(GO1STOP2),50)),passed(50)) then
                par
                    goLight(lightUnit1) := not(goLight(lightUnit1))
                    stopLight(lightUnit1) := not(stopLight(lightUnit1))
                    phase := STOP1STOP2
                endpar
            endif
            if and(and(and(eq(phase,STOP1STOP2),eq(timer(STOP1STOP2),120)),passed(120)),eq(lightUnit2,lightUnit1)) then
                goLight(lightUnit1) := not(goLight(lightUnit2))
            endif
            if and(and(and(eq(phase,GO1STOP2),eq(timer(GO1STOP2),120)),passed(120)),and(eq(lightUnit1,lightUnit2),eq(lightUnit1,lightUnit2))) then
                stopLight(lightUnit2) := not(stopLight(lightUnit2))
            endif
            if and(and(and(eq(phase,STOP1STOP2),eq(timer(STOP1STOP2),50)),passed(50)),and(eq(lightUnit2,lightUnit1),eq(lightUnit2,lightUnit1))) then
                goLight(lightUnit1) := not(goLight(lightUnit1))
            endif
            if and(and(and(eq(phase,STOP1STOP2),eq(timer(STOP1STOP2),50)),passed(50)),eq(lightUnit2,lightUnit1)) then
                stopLight(lightUnit2) := not(stopLight(lightUnit1))
            endif
            if and(and(and(eq(phase,GO2STOP1),eq(timer(GO2STOP1),50)),passed(50)),eq(lightUnit2,lightUnit1)) then
                stopLight(lightUnit1) := not(stopLight(lightUnit2))
            endif
            if and(and(and(eq(phase,STOP2STOP1),eq(timer(STOP2STOP1),50)),passed(50)),eq(lightUnit1,lightUnit2)) then
                stopLight(lightUnit2) := not(stopLight(lightUnit1))
            endif
            if and(and(eq(phase,STOP2STOP1),eq(timer(STOP2STOP1),120)),passed(120)) then
                par
                    goLight(lightUnit1) := not(goLight(lightUnit1))
                    stopLight(lightUnit1) := not(stopLight(lightUnit1))
                    phase := GO1STOP2
                endpar
            endif
            if and(and(and(eq(phase,STOP1STOP2),eq(timer(STOP1STOP2),50)),passed(50)),eq(lightUnit2,lightUnit1)) then
                goLight(lightUnit2) := not(goLight(lightUnit1))
            endif
            if and(and(and(eq(phase,STOP1STOP2),eq(timer(STOP1STOP2),50)),passed(50)),eq(lightUnit2,lightUnit1)) then
                goLight(lightUnit1) := not(goLight(lightUnit2))
            endif
            if and(and(and(eq(phase,STOP2STOP1),eq(timer(STOP2STOP1),50)),passed(50)),and(eq(lightUnit1,lightUnit2),eq(lightUnit1,lightUnit2))) then
                stopLight(lightUnit2) := not(stopLight(lightUnit2))
            endif
            if and(and(and(eq(phase,GO1STOP2),eq(timer(GO1STOP2),120)),passed(120)),eq(lightUnit1,lightUnit2)) then
                goLight(lightUnit2) := not(goLight(lightUnit1))
            endif
            if and(and(and(eq(phase,GO1STOP2),eq(timer(GO1STOP2),50)),passed(50)),eq(lightUnit1,lightUnit2)) then
                goLight(lightUnit1) := not(goLight(lightUnit2))
            endif
            if and(and(eq(phase,GO1STOP2),eq(timer(GO1STOP2),120)),passed(120)) then
                par
                    goLight(lightUnit1) := not(goLight(lightUnit1))
                    stopLight(lightUnit1) := not(stopLight(lightUnit1))
                    phase := STOP1STOP2
                endpar
            endif
            if and(and(eq(phase,GO2STOP1),eq(timer(GO2STOP1),120)),passed(120)) then
                par
                    goLight(lightUnit2) := not(goLight(lightUnit2))
                    stopLight(lightUnit2) := not(stopLight(lightUnit2))
                    phase := STOP2STOP1
                endpar
            endif
            if and(and(and(eq(phase,STOP1STOP2),eq(timer(STOP1STOP2),120)),passed(120)),and(eq(lightUnit2,lightUnit1),eq(lightUnit2,lightUnit1))) then
                stopLight(lightUnit1) := not(stopLight(lightUnit1))
            endif
            if and(and(and(eq(phase,GO2STOP1),eq(timer(GO2STOP1),120)),passed(120)),eq(lightUnit2,lightUnit1)) then
                stopLight(lightUnit2) := not(stopLight(lightUnit1))
            endif
            if and(and(and(eq(phase,STOP2STOP1),eq(timer(STOP2STOP1),50)),passed(50)),eq(lightUnit1,lightUnit2)) then
                stopLight(lightUnit1) := not(stopLight(lightUnit2))
            endif
            if and(and(eq(phase,STOP1STOP2),eq(timer(STOP1STOP2),120)),passed(120)) then
                par
                    goLight(lightUnit2) := not(goLight(lightUnit2))
                    stopLight(lightUnit2) := not(stopLight(lightUnit2))
                    phase := GO2STOP1
                endpar
            endif
            if and(and(and(eq(phase,STOP1STOP2),eq(timer(STOP1STOP2),120)),passed(120)),eq(lightUnit2,lightUnit1)) then
                stopLight(lightUnit1) := not(stopLight(lightUnit2))
            endif
            if and(and(and(eq(phase,GO1STOP2),eq(timer(GO1STOP2),50)),passed(50)),eq(lightUnit1,lightUnit2)) then
                goLight(lightUnit2) := not(goLight(lightUnit1))
            endif
            if and(and(and(eq(phase,STOP1STOP2),eq(timer(STOP1STOP2),50)),passed(50)),and(eq(lightUnit2,lightUnit1),eq(lightUnit2,lightUnit1))) then
                stopLight(lightUnit1) := not(stopLight(lightUnit1))
            endif
            if and(and(and(eq(phase,GO2STOP1),eq(timer(GO2STOP1),50)),passed(50)),eq(lightUnit2,lightUnit1)) then
                stopLight(lightUnit2) := not(stopLight(lightUnit1))
            endif
            if and(and(and(eq(phase,GO2STOP1),eq(timer(GO2STOP1),120)),passed(120)),and(eq(lightUnit2,lightUnit1),eq(lightUnit2,lightUnit1))) then
                goLight(lightUnit1) := not(goLight(lightUnit1))
            endif
            if and(and(and(eq(phase,GO2STOP1),eq(timer(GO2STOP1),120)),passed(120)),and(eq(lightUnit2,lightUnit1),eq(lightUnit2,lightUnit1))) then
                stopLight(lightUnit1) := not(stopLight(lightUnit1))
            endif
            if and(and(and(eq(phase,GO2STOP1),eq(timer(GO2STOP1),50)),passed(50)),eq(lightUnit2,lightUnit1)) then
                goLight(lightUnit2) := not(goLight(lightUnit1))
            endif
            if and(and(and(eq(phase,GO1STOP2),eq(timer(GO1STOP2),120)),passed(120)),and(eq(lightUnit1,lightUnit2),eq(lightUnit1,lightUnit2))) then
                goLight(lightUnit2) := not(goLight(lightUnit2))
            endif
            if and(and(and(eq(phase,STOP2STOP1),eq(timer(STOP2STOP1),50)),passed(50)),eq(lightUnit1,lightUnit2)) then
                goLight(lightUnit1) := not(goLight(lightUnit2))
            endif
            if and(and(and(eq(phase,GO2STOP1),eq(timer(GO2STOP1),120)),passed(120)),eq(lightUnit2,lightUnit1)) then
                goLight(lightUnit2) := not(goLight(lightUnit1))
            endif
            if and(and(and(eq(phase,STOP2STOP1),eq(timer(STOP2STOP1),50)),passed(50)),and(eq(lightUnit1,lightUnit2),eq(lightUnit1,lightUnit2))) then
                goLight(lightUnit2) := not(goLight(lightUnit2))
            endif
            if and(and(and(eq(phase,GO1STOP2),eq(timer(GO1STOP2),50)),passed(50)),and(eq(lightUnit1,lightUnit2),eq(lightUnit1,lightUnit2))) then
                stopLight(lightUnit2) := not(stopLight(lightUnit2))
            endif
        endpar

default init s0:
    function stopLight($l in LightUnit) = true
    function goLight($l in LightUnit) = false
    function phase = STOP1STOP2
