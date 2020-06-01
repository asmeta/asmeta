//applied flatteners: LR 
asm letRule3_flat
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
    domain MyDomain subsetof Integer

    monitored foo: Boolean
    monitored fooA: MyDomain
    controlled fooB: Boolean
    controlled fooC: MyDomain

definitions:

    domain MyDomain = {1,2,3,4}


    main rule r_main =
        par
            if and(foo,eq(fooA,2)) then
                par
                    fooB := true
                    fooC := 2
                endpar
            endif
            if and(foo,eq(fooA,1)) then
                par
                    fooB := true
                    fooC := 1
                endpar
            endif
            if and(foo,eq(fooA,4)) then
                par
                    fooB := true
                    fooC := 4
                endpar
            endif
            if and(foo,eq(fooA,3)) then
                par
                    fooB := true
                    fooC := 3
                endpar
            endif
            if and(eq(foo,false),eq(fooA,2)) then
                par
                    fooB := false
                    fooC := 2
                endpar
            endif
            if and(eq(foo,false),eq(fooA,1)) then
                par
                    fooB := false
                    fooC := 1
                endpar
            endif
            if and(eq(foo,false),eq(fooA,4)) then
                par
                    fooB := false
                    fooC := 4
                endpar
            endif
            if and(eq(foo,false),eq(fooA,3)) then
                par
                    fooB := false
                    fooC := 3
                endpar
            endif
        endpar

default init s0:
