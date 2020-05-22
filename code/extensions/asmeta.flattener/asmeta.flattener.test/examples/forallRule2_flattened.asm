asm forallRule2_flattened
import ../../../../asm_examples/STDL/StandardLibrary

signature:
    domain ConcrDom subsetof Integer

    controlled foo: ConcrDom -> ConcrDom

definitions:

    domain ConcrDom = {1,2,3,4}


    main rule r_Main =
        par
            if true then
                par
                    if lt(1,1) then
                        foo(1) := 1
                    endif
                    if lt(2,1) then
                        foo(2) := 1
                    endif
                    if lt(4,1) then
                        foo(4) := 1
                    endif
                    if lt(3,1) then
                        foo(3) := 1
                    endif
                    if lt(1,2) then
                        foo(1) := 2
                    endif
                    if lt(2,2) then
                        foo(2) := 2
                    endif
                    if lt(4,2) then
                        foo(4) := 2
                    endif
                    if lt(3,2) then
                        foo(3) := 2
                    endif
                    if lt(1,4) then
                        foo(1) := 4
                    endif
                    if lt(2,4) then
                        foo(2) := 4
                    endif
                    if lt(4,4) then
                        foo(4) := 4
                    endif
                    if lt(3,4) then
                        foo(3) := 4
                    endif
                    if lt(1,3) then
                        foo(1) := 3
                    endif
                    if lt(2,3) then
                        foo(2) := 3
                    endif
                    if lt(4,3) then
                        foo(4) := 3
                    endif
                    if lt(3,3) then
                        foo(3) := 3
                    endif
                endpar
            endif
            par
                if lt(1,3) then
                    foo(1) := minus(4,1)
                endif
                if lt(2,3) then
                    foo(2) := minus(4,2)
                endif
                if lt(4,3) then
                    foo(4) := minus(4,4)
                endif
                if lt(3,3) then
                    foo(3) := minus(4,3)
                endif
            endpar
            par
                if eq(minus(foo(4),1),1) then
                    foo(1) := minus(1,1)
                endif
                if eq(minus(foo(4),1),2) then
                    foo(2) := minus(2,1)
                endif
                if eq(minus(foo(4),1),4) then
                    foo(4) := minus(4,1)
                endif
                if eq(minus(foo(4),1),3) then
                    foo(3) := minus(3,1)
                endif
            endpar
            par
                if lt(1,3) then
                    par
                        if lt(1,2) then
                            par
                                if eq(1,1) then
                                    foo(1) := 1
                                endif
                                if eq(2,1) then
                                    foo(1) := 1
                                endif
                                if eq(4,1) then
                                    foo(1) := 1
                                endif
                                if eq(3,1) then
                                    foo(1) := 1
                                endif
                            endpar
                        endif
                        if lt(2,2) then
                            par
                                if eq(1,1) then
                                    foo(1) := 2
                                endif
                                if eq(2,1) then
                                    foo(1) := 2
                                endif
                                if eq(4,1) then
                                    foo(1) := 2
                                endif
                                if eq(3,1) then
                                    foo(1) := 2
                                endif
                            endpar
                        endif
                        if lt(4,2) then
                            par
                                if eq(1,1) then
                                    foo(1) := 4
                                endif
                                if eq(2,1) then
                                    foo(1) := 4
                                endif
                                if eq(4,1) then
                                    foo(1) := 4
                                endif
                                if eq(3,1) then
                                    foo(1) := 4
                                endif
                            endpar
                        endif
                        if lt(3,2) then
                            par
                                if eq(1,1) then
                                    foo(1) := 3
                                endif
                                if eq(2,1) then
                                    foo(1) := 3
                                endif
                                if eq(4,1) then
                                    foo(1) := 3
                                endif
                                if eq(3,1) then
                                    foo(1) := 3
                                endif
                            endpar
                        endif
                    endpar
                endif
                if lt(2,3) then
                    par
                        if lt(1,2) then
                            par
                                if eq(1,1) then
                                    foo(2) := 1
                                endif
                                if eq(2,1) then
                                    foo(2) := 1
                                endif
                                if eq(4,1) then
                                    foo(2) := 1
                                endif
                                if eq(3,1) then
                                    foo(2) := 1
                                endif
                            endpar
                        endif
                        if lt(2,2) then
                            par
                                if eq(1,1) then
                                    foo(2) := 2
                                endif
                                if eq(2,1) then
                                    foo(2) := 2
                                endif
                                if eq(4,1) then
                                    foo(2) := 2
                                endif
                                if eq(3,1) then
                                    foo(2) := 2
                                endif
                            endpar
                        endif
                        if lt(4,2) then
                            par
                                if eq(1,1) then
                                    foo(2) := 4
                                endif
                                if eq(2,1) then
                                    foo(2) := 4
                                endif
                                if eq(4,1) then
                                    foo(2) := 4
                                endif
                                if eq(3,1) then
                                    foo(2) := 4
                                endif
                            endpar
                        endif
                        if lt(3,2) then
                            par
                                if eq(1,1) then
                                    foo(2) := 3
                                endif
                                if eq(2,1) then
                                    foo(2) := 3
                                endif
                                if eq(4,1) then
                                    foo(2) := 3
                                endif
                                if eq(3,1) then
                                    foo(2) := 3
                                endif
                            endpar
                        endif
                    endpar
                endif
                if lt(4,3) then
                    par
                        if lt(1,2) then
                            par
                                if eq(1,1) then
                                    foo(4) := 1
                                endif
                                if eq(2,1) then
                                    foo(4) := 1
                                endif
                                if eq(4,1) then
                                    foo(4) := 1
                                endif
                                if eq(3,1) then
                                    foo(4) := 1
                                endif
                            endpar
                        endif
                        if lt(2,2) then
                            par
                                if eq(1,1) then
                                    foo(4) := 2
                                endif
                                if eq(2,1) then
                                    foo(4) := 2
                                endif
                                if eq(4,1) then
                                    foo(4) := 2
                                endif
                                if eq(3,1) then
                                    foo(4) := 2
                                endif
                            endpar
                        endif
                        if lt(4,2) then
                            par
                                if eq(1,1) then
                                    foo(4) := 4
                                endif
                                if eq(2,1) then
                                    foo(4) := 4
                                endif
                                if eq(4,1) then
                                    foo(4) := 4
                                endif
                                if eq(3,1) then
                                    foo(4) := 4
                                endif
                            endpar
                        endif
                        if lt(3,2) then
                            par
                                if eq(1,1) then
                                    foo(4) := 3
                                endif
                                if eq(2,1) then
                                    foo(4) := 3
                                endif
                                if eq(4,1) then
                                    foo(4) := 3
                                endif
                                if eq(3,1) then
                                    foo(4) := 3
                                endif
                            endpar
                        endif
                    endpar
                endif
                if lt(3,3) then
                    par
                        if lt(1,2) then
                            par
                                if eq(1,1) then
                                    foo(3) := 1
                                endif
                                if eq(2,1) then
                                    foo(3) := 1
                                endif
                                if eq(4,1) then
                                    foo(3) := 1
                                endif
                                if eq(3,1) then
                                    foo(3) := 1
                                endif
                            endpar
                        endif
                        if lt(2,2) then
                            par
                                if eq(1,1) then
                                    foo(3) := 2
                                endif
                                if eq(2,1) then
                                    foo(3) := 2
                                endif
                                if eq(4,1) then
                                    foo(3) := 2
                                endif
                                if eq(3,1) then
                                    foo(3) := 2
                                endif
                            endpar
                        endif
                        if lt(4,2) then
                            par
                                if eq(1,1) then
                                    foo(3) := 4
                                endif
                                if eq(2,1) then
                                    foo(3) := 4
                                endif
                                if eq(4,1) then
                                    foo(3) := 4
                                endif
                                if eq(3,1) then
                                    foo(3) := 4
                                endif
                            endpar
                        endif
                        if lt(3,2) then
                            par
                                if eq(1,1) then
                                    foo(3) := 3
                                endif
                                if eq(2,1) then
                                    foo(3) := 3
                                endif
                                if eq(4,1) then
                                    foo(3) := 3
                                endif
                                if eq(3,1) then
                                    foo(3) := 3
                                endif
                            endpar
                        endif
                    endpar
                endif
            endpar
        endpar

default init s0:
    function foo($x in ConcrDom) = 4
