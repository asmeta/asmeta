//applied flatteners: MCR 
asm macroRule_flat
import ../../../../asm_examples/STDL/StandardLibrary

signature:
    enum domain Actors = {WOLF | GOAT | CABBAGE | FERRYMAN}
    enum domain Side = {LEFT | RIGHT}

    controlled position: Actors -> Side
    monitored choice: Actors
    derived opposite: Side -> Side

definitions:

    function opposite($l in Side) = if eq($l,LEFT) then RIGHT else LEFT endif


    main rule r_Main =
        if eq(position(FERRYMAN),position(choice)) then
            par
                position(choice) := opposite(position(choice))
                position(FERRYMAN) := opposite(position(FERRYMAN))
            endpar
        endif

default init s0:
    function position($a in Actors) = LEFT
