asm forallRuleChoose_flattened
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
    domain ConcrDom subsetof Integer

    controlled foo: ConcrDom -> ConcrDom

definitions:

    domain ConcrDom = {1,2,3,4,5,6,7,8,9,10}


    main rule r_Main =
        par
            if lt(2,6) then
                choose $x in ConcrDom with lt($x,2) do
                    foo(2) := $x
            endif
            if lt(1,6) then
                choose $x in ConcrDom with lt($x,1) do
                    foo(1) := $x
            endif
            if lt(4,6) then
                choose $x in ConcrDom with lt($x,4) do
                    foo(4) := $x
            endif
            if lt(6,6) then
                choose $x in ConcrDom with lt($x,6) do
                    foo(6) := $x
            endif
            if lt(7,6) then
                choose $x in ConcrDom with lt($x,7) do
                    foo(7) := $x
            endif
            if lt(10,6) then
                choose $x in ConcrDom with lt($x,10) do
                    foo(10) := $x
            endif
            if lt(5,6) then
                choose $x in ConcrDom with lt($x,5) do
                    foo(5) := $x
            endif
            if lt(9,6) then
                choose $x in ConcrDom with lt($x,9) do
                    foo(9) := $x
            endif
            if lt(8,6) then
                choose $x in ConcrDom with lt($x,8) do
                    foo(8) := $x
            endif
            if lt(3,6) then
                choose $x in ConcrDom with lt($x,3) do
                    foo(3) := $x
            endif
        endpar

default init s0:
    function foo($x in ConcrDom) = 1
