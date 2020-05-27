//applied flatteners: MCR 
asm macroChoose_flat
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
    domain MyDomain subsetof Integer

    controlled foo: MyDomain -> MyDomain

definitions:

    domain MyDomain = {1,2,3,4}


    main rule r_Main =
        choose $a in MyDomain with true do
            foo($a) := $a

default init s0:
    function foo($a in MyDomain) = 1
