//applied flatteners: NR 
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
            if a then
                par
                    k := 2
                    x := 3
                endpar
            endif
            if and(a,b) then
                par
                    x := 2
                    switch sw
                        case AA:
                            switch sw
                                case AA:
                                    foo := CC
                                case BB:
                                    par
                                        if and(d,d) then
                                            k := 3
                                        endif
                                        if not(d) then
                                            z := 2
                                        endif
                                        if and(d,not(d)) then
                                            k := 2
                                        endif
                                    endpar
                                case CC:
                                    foo := DD
                            otherwise
                                foo := AA
                            endswitch
                        case BB:
                            foo := BB
                        case CC:
                            foo := DD
                    otherwise
                        foo := AA
                    endswitch
                endpar
            endif
            if and(and(not(a),c),not(d)) then
                z := 2
            endif
            if and(and(not(a),c),d) then
                z := 3
            endif
        endpar

default init s0:
    function x = 1
    function y = 1
    function z = 1
    function k = 1
    function f = 1
