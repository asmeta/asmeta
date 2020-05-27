//applied flatteners: MCR FR ChR AR LR CaR NR 
asm chooseRule_flat
import ../../../../asm_examples/STDL/StandardLibrary

signature:
    domain ConcrDom subsetof Integer

    controlled foo: ConcrDom -> ConcrDom
    derived chooseVar0: ConcrDom

definitions:

    domain ConcrDom = {1,2,3,4,5}

    function chooseVar0 = chooseone({$a in ConcrDom| lt($a,foo($a)) : $a})


    main rule r_Main =
        par
            if and(isDef(chooseVar0),eq(chooseVar0,1)) then
                foo(1) := chooseVar0
            endif
            if and(isDef(chooseVar0),eq(chooseVar0,5)) then
                foo(5) := chooseVar0
            endif
            if and(isDef(chooseVar0),eq(chooseVar0,4)) then
                foo(4) := chooseVar0
            endif
            if and(isDef(chooseVar0),eq(chooseVar0,2)) then
                foo(2) := chooseVar0
            endif
            if and(isDef(chooseVar0),eq(chooseVar0,3)) then
                foo(3) := chooseVar0
            endif
        endpar

default init s0:
    function foo($x in ConcrDom) = 5
