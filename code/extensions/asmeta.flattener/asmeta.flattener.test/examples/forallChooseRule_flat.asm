//applied flatteners: ChR FR 
asm forallChooseRule_flat
import ../../../../asm_examples/STDL/StandardLibrary

signature:
    domain ConcrDom subsetof Integer

    controlled foo: ConcrDom -> ConcrDom
    derived chooseVar0: ConcrDom
    derived chooseVar1: ConcrDom
    derived chooseVar2: ConcrDom
    derived chooseVar3: ConcrDom
    derived chooseVar4: ConcrDom

definitions:

    domain ConcrDom = {1,2,3,4,5}

    function chooseVar0 = chooseone({$b509 in ConcrDom| lt($b509,5) : $b509})
    function chooseVar1 = chooseone({$b510 in ConcrDom| lt($b510,2) : $b510})
    function chooseVar2 = chooseone({$b511 in ConcrDom| lt($b511,3) : $b511})
    function chooseVar3 = chooseone({$b512 in ConcrDom| lt($b512,1) : $b512})
    function chooseVar4 = chooseone({$b513 in ConcrDom| lt($b513,4) : $b513})


    main rule r_Main =
        par
            if lt(5,foo(5)) then
                if isDef(chooseVar0) then
                    foo(chooseVar0) := chooseVar0
                endif
            endif
            if lt(2,foo(2)) then
                if isDef(chooseVar1) then
                    foo(chooseVar1) := chooseVar1
                endif
            endif
            if lt(3,foo(3)) then
                if isDef(chooseVar2) then
                    foo(chooseVar2) := chooseVar2
                endif
            endif
            if lt(1,foo(1)) then
                if isDef(chooseVar3) then
                    foo(chooseVar3) := chooseVar3
                endif
            endif
            if lt(4,foo(4)) then
                if isDef(chooseVar4) then
                    foo(chooseVar4) := chooseVar4
                endif
            endif
        endpar

default init s0:
    function foo($x in ConcrDom) = 5
