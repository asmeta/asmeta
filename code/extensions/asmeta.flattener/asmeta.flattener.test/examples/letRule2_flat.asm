//applied flatteners: LR 
asm letRule2_flat
import ../../../../asm_examples/STDL/StandardLibrary

signature:

    controlled foo: Boolean
    controlled fooA: Boolean
    controlled fooB: Boolean
    controlled fooC: Boolean -> Boolean
    controlled fooD: Boolean
    controlled fooE: Boolean -> Boolean
    monitored mon: Boolean

definitions:


    main rule r_main =
        par
            foo := true
            par
                if eq(mon,false) then
                    fooA := true
                endif
                if mon then
                    fooA := false
                endif
            endpar
            par
                if eq(fooB,false) then
                    fooB := true
                endif
                if fooB then
                    fooB := false
                endif
            endpar
            forall $b in Boolean with true do
                par
                    if eq($b,false) then
                        fooC(false) := not($b)
                    endif
                    if $b then
                        fooC(true) := not($b)
                    endif
                endpar
            choose $t in Boolean with eq($t,mon) do
                par
                    if eq($t,false) then
                        fooD := true
                    endif
                    if $t then
                        fooD := false
                    endif
                endpar
            par
                if eq(fooD,false) then
                    fooE(false) := not(fooE(false))
                endif
                if fooD then
                    fooE(true) := not(fooE(true))
                endif
            endpar
        endpar

default init s0:
    function foo = false
    function fooA = false
    function fooB = true
    function fooC($b in Boolean) = $b
    function fooD = mon
    function fooE($b in Boolean) = true
