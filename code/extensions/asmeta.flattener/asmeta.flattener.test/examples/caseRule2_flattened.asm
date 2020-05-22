asm caseRule2_flattened
import ../../../../asm_examples/STDL/StandardLibrary

signature:
    enum domain EnumDom = {AA | BB | CC | DD}

    controlled sw: EnumDom
    controlled foo: EnumDom

definitions:


    main rule r_Main =
        par
            if eq(AA,sw) then
                foo := AA
            endif
            if eq(BB,sw) then
                foo := BB
            endif
            if eq(CC,sw) then
                foo := DD
            endif
            if and(and(and(not(eq(sw,AA)),not(eq(AA,sw))),not(eq(BB,sw))),not(eq(CC,sw))) then
                foo := AA
            endif
        endpar

default init s0:
    function sw = DD
