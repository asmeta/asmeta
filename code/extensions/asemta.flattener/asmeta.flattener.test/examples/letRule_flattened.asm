asm letRule_flattened
import ../../../../asm_examples/STDL/StandardLibrary

signature:

    controlled foo: Boolean

definitions:


    main rule r_main =
        let ($x = true) in
            foo := $x
        endlet

default init s0:
    function foo = false
