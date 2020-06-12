//non puo' essere tradotta
asm locationWithFunction
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
    dynamic controlled fooA: Boolean -> Boolean
    dynamic controlled fooB: Boolean

definitions:

	invariant over fooA, fooB: fooA(fooB) = fooB
	invariant over fooA, fooB: fooA(not(fooB)) = not(fooB)
    
    main rule r_Main = 
        par
            fooA(fooB) := fooB
            fooA(not(fooB)) := not(fooB)
            fooB := not(fooB)
        endpar

default init s0:
    function fooA($b in Boolean) = $b
    function fooB = false