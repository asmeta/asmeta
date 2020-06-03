//applied flatteners: NR 
asm macroNesting_flat
import ../../../../asm_examples/STDL/StandardLibrary

signature:
    domain MyDomain subsetof Integer

    controlled x: MyDomain
    controlled y: MyDomain
    controlled k: MyDomain
    controlled z: MyDomain
    controlled f: MyDomain
    monitored a: Boolean
    monitored b: Boolean
    monitored c: Boolean
    monitored d: Boolean

definitions:

    domain MyDomain = {1,2,3,4}

    macro rule r_a =
        par
            if and(c,d) then
                z := 3
            endif
            if and(c,not(d)) then
                z := 2
            endif
        endpar


    main rule r_Main =
        par
            if and(a,b) then
                y := 4
            endif
            if not(a) then
                r_a[]
            endif
            if a then
                par
                    k := 2
                    x := 3
                endpar
            endif
        endpar

default init s0:
    function x = 1
    function y = 1
    function z = 1
    function k = 1
    function f = 1
