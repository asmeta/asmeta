asm ag
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
    dynamic controlled fooA: Boolean
    dynamic controlled fooB: Boolean

definitions:
    
    //invariant for simulation with AsmetaS
    invariant over fooA, fooB: fooA != fooB
    
    //property for NuSMV
    CTLSPEC  ctlSpec_p : ag(fooA != fooB)

    main rule r_Main = 
        par
            fooA := not(fooA)
            fooB := not(fooB)
        endpar

default init s0:
    function fooA = true
    function fooB = false
