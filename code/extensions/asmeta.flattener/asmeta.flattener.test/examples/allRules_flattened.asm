asm allRules_flattened
import ../../../../asm_examples/STDL/StandardLibrary

signature:
    domain MyDomain subsetof Integer
    enum domain EnumDom = {AA | BB | CC | DD | EE | FF | GG | HH}

    monitored f1: EnumDom
    monitored f2: EnumDom
    controlled dc1: MyDomain
    controlled dc2: MyDomain
    controlled dc3: MyDomain
    monitored dm1: MyDomain
    monitored dm2: MyDomain
    monitored dm3: MyDomain
    monitored bm1: Boolean
    monitored bm2: Boolean
    monitored bm3: Boolean

definitions:

    domain MyDomain = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50}


    main rule r_Main =
        par
            if and(bm2,neq(f1,BB)) then
                dc2 := 21
            endif
            dc3 := 4
            par
                if eq(AA,f1) then
                    if bm1 then
                        par
                            if eq(AA,f2) then
                                par
                                    if not(bm2) then
                                        if bm3 then
                                            par
                                                dc2 := 31
                                                dc1 := 50
                                            endpar
                                        endif
                                    else 
                                        dc2 := 21
                                    endif
                                    dc3 := 4
                                endpar
                            endif
                        endpar
                    endif
                endif
                if eq(BB,f1) then
                    par
                        dc1 := 12
                        dc2 := 12
                        dc3 := 4
                    endpar
                endif
                if eq(DD,f1) then
                    par
                        dc1 := 14
                        dc3 := 4
                    endpar
                endif
                if eq(EE,f1) then
                    if bm1 then
                        par
                            if eq(AA,f2) then
                                par
                                    if not(bm2) then
                                        if bm3 then
                                            par
                                                dc2 := 31
                                                dc1 := 49
                                            endpar
                                        endif
                                    else 
                                        dc2 := 21
                                    endif
                                    dc3 := 4
                                endpar
                            endif
                        endpar
                    endif
                endif
                if and(and(and(and(not(eq(f1,AA)),not(eq(AA,f1))),not(eq(BB,f1))),not(eq(DD,f1))),not(eq(EE,f1))) then
                    par
                        if bm3 then
                            dc1 := 48
                        endif
                        dc3 := 4
                    endpar
                endif
            endpar
        endpar

default init s0:
    function dc1 = 10
    function dc2 = 30
    function dc3 = 20
