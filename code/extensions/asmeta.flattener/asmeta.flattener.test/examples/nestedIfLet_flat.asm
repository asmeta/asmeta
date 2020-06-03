//applied flatteners: NR 
asm nestedIfLet_flat
import ../../../../../asm_examples/STDL/StandardLibrary

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
    controlled foo: MyDomain -> MyDomain

definitions:

    domain MyDomain = {1,2,3,4}


    main rule r_Main =
        par
            if and(a,b) then
                par
                    x := 2
                    let ($x = 2) in
                        par
                            foo($x) := $x
                            if and(c,d) then
                                z := 3
                            endif
                            if and(c,not(d)) then
                                z := 2
                            endif
                        endpar
                    endlet
                endpar
            endif
            if and(and(not(a),c),d) then
                z := 3
            endif
            if a then
                par
                    k := 2
                    x := 3
                endpar
            endif
            if and(and(not(a),c),not(d)) then
                z := 2
            endif
        endpar

default init s0:
    function x = 1
    function y = 1
    function z = 1
    function k = 1
    function f = 1
    function foo($x in MyDomain) = 1
