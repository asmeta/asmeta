asm nestedIf3_flattened
import ../../../../asm_examples/STDL/StandardLibrary

signature:
    domain MyDomain subsetof Integer

    controlled x: MyDomain
    controlled y: MyDomain
    monitored a: Boolean
    monitored b: Boolean

definitions:

    domain MyDomain = {1,2,3,4}


    main rule r_Main =
        par
            if and(a,b) then
                x := 2
            endif
            if and(b,a) then
                y := 3
            endif
        endpar

default init s0:
    function x = 1
    function y = 1
