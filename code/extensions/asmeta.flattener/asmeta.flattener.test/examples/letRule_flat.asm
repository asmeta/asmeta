//applied flatteners: LR 
asm letRule_flat
import ../../../../asm_examples/STDL/StandardLibrary

signature:

    controlled foo: Boolean

definitions:


    main rule r_main =
        foo := true

default init s0:
    function foo = false
