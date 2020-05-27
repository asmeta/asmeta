//applied flatteners: FR 
asm forallRule3_flat
import ../../../../asm_examples/STDL/StandardLibrary

signature:
    enum domain EnumDom = {AA | BB | CC | DD}
    domain ConcrDom subsetof Integer

    controlled foo: ConcrDom -> ConcrDom
    controlled foo2: ConcrDom -> EnumDom
    controlled foo3: EnumDom

definitions:

    domain ConcrDom = {1,2,3,4}


    main rule r_Main =
        par
            if true then
                par
                    par
                        foo(1) := 3
                        foo2(3) := BB
                    endpar
                    par
                        foo(2) := 3
                        foo2(3) := BB
                    endpar
                    par
                        foo(3) := 4
                        foo2(4) := BB
                    endpar
                    par
                        foo(1) := 4
                        foo2(4) := BB
                    endpar
                    par
                        foo(2) := 4
                        foo2(4) := BB
                    endpar
                    par
                        foo(1) := 2
                        foo2(2) := BB
                    endpar
                endpar
            endif
            foo3 := AA
        endpar

default init s0:
    function foo($x in ConcrDom) = 4
