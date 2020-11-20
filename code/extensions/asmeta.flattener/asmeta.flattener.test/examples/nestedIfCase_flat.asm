//applied flatteners: CaR NR 
asm nestedIfCase_flat
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
    domain MyDomain subsetof Integer
    enum domain EnumDom = {AA | BB | CC | DD}

    controlled x: MyDomain
    controlled y: MyDomain
    controlled k: MyDomain
    controlled z: MyDomain
    controlled f: MyDomain
    monitored a: Boolean
    monitored b: Boolean
    monitored c: Boolean
    monitored d: Boolean
    controlled foo: EnumDom
    controlled sw: EnumDom

definitions:

    domain MyDomain = {1,2,3,4}


    main rule r_Main =
        par
            if and(and(a,b),eq(CC,sw)) then
                foo := DD
            endif
            if and(and(and(a,b),eq(AA,sw)),eq(AA,sw)) then
                foo := CC
            endif
            if and(and(not(a),c),d) then
                z := 3
            endif
            if and(and(and(a,b),eq(AA,sw)),and(and(not(eq(AA,sw)),not(eq(BB,sw))),not(eq(CC,sw)))) then
                foo := AA
            endif
            if and(and(and(a,b),eq(AA,sw)),eq(CC,sw)) then
                foo := DD
            endif
            if and(and(not(a),c),not(d)) then
                z := 2
            endif
            if and(a,b) then
                x := 2
            endif
            if and(and(and(and(and(a,b),eq(AA,sw)),eq(BB,sw)),d),d) then
                k := 3
            endif
            if and(and(and(and(a,b),eq(AA,sw)),eq(BB,sw)),not(d)) then
                z := 2
            endif
            if and(and(a,b),and(and(not(eq(AA,sw)),not(eq(BB,sw))),not(eq(CC,sw)))) then
                foo := AA
            endif
            if a then
                par
                    k := 2
                    x := 3
                endpar
            endif
            if and(and(a,b),eq(BB,sw)) then
                foo := BB
            endif
            if and(and(and(and(and(a,b),eq(AA,sw)),eq(BB,sw)),d),not(d)) then
                k := 2
            endif
        endpar

default init s0:
    function x = 1
    function y = 1
    function z = 1
    function k = 1
    function f = 1
