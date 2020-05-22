asm nestedIf2_flattened
import ../../../../asm_examples/STDL/StandardLibrary

signature:
    domain MyDomain subsetof Integer

    controlled k: MyDomain
    monitored a: Boolean
    monitored b: Boolean

definitions:

    domain MyDomain = {1,2,3,4}


    main rule r_Main =
        par
            if and(a,not(b)) then
                k := 3
            endif
            if and(a,b) then
                k := 2
            endif
            if not(a) then
                k := 4
            endif
        endpar

default init s0:
    function k = 1
