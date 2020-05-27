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
                if mon then
                    fooA := false
                endif
                if eq(mon,false) then
                    fooA := true
                endif
            endpar
            par
                if fooB then
                    fooB := false
                endif
                if eq(fooB,false) then
                    fooB := true
                endif
            endpar
            forall $b in Boolean with true do
                par
                    if $b then
                        fooC(true) := not($b)
                    endif
                    if eq($b,false) then
                        fooC(false) := not($b)
                    endif
                endpar
            choose $t in Boolean with eq($t,mon) do
                par
                    if $t then
                        fooD := false
                    endif
                    if eq($t,false) then
                        fooD := true
                    endif
                endpar
            par
                if fooD then
                    fooE(true) := not(fooE(true))
                endif
                if eq(fooD,false) then
                    fooE(false) := not(fooE(false))
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
