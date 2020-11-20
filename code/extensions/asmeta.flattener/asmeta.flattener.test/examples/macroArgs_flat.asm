//applied flatteners: AR 
asm macroArgs_flat
import ../../../../asm_examples/STDL/StandardLibrary

signature:
    enum domain Actors = {WOLF | GOAT | CABBAGE | FERRYMAN}
    enum domain Side = {LEFT | RIGHT}

    controlled position: Actors -> Side
    monitored choice: Actors
    derived opposite: Side -> Side

definitions:

    function opposite($l in Side) = if eq($l,LEFT) then RIGHT else LEFT endif

    macro rule r_move($a in Actors) =
        if eq(position(FERRYMAN),position($a)) then
            par
                let ($var_0 = position($a)) in
                    position($a) := opposite($var_0)
                endlet
                let ($var_1 = position(FERRYMAN)) in
                    position(FERRYMAN) := opposite($var_1)
                endlet
            endpar
        endif


    main rule r_Main =
        r_move[choice]

default init s0:
    function position($a in Actors) = LEFT
