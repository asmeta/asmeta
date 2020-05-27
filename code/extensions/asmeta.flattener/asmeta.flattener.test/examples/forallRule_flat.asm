//applied flatteners: FR 
asm forallRule_flat
import ../../../../asm_examples/STDL/StandardLibrary

signature:
    domain ConcrDom subsetof Integer

    controlled foo: ConcrDom -> ConcrDom
    controlled foo2: ConcrDom

definitions:

    domain ConcrDom = {1}


    main rule r_Main =
        par
            if true then
                skip
            endif
            foo2 := 1
        endpar

default init s0:
    function foo($x in ConcrDom) = 1
