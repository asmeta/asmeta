//applied flatteners: FR 
asm abstractDomain_flat
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
    domain MyDomain subsetof Integer
    abstract domain Place

    controlled x: MyDomain
    controlled y: MyDomain
    controlled tokens: Place -> MyDomain
    static p1: Place
    static p2: Place
    static p3: Place
    static p4: Place

definitions:

    domain MyDomain = {0,1,2,3,4,5,6,7,8,9,10}


    main rule r_Main =
        par
            switch p1
                case p1:
                    tokens(p1) := 1
                case p2:
                    tokens(p2) := 2
                case p3:
                    tokens(p3) := 4
                case p4:
                    tokens(p4) := 6
            endswitch
            switch p3
                case p1:
                    tokens(p1) := 1
                case p2:
                    tokens(p2) := 2
                case p3:
                    tokens(p3) := 4
                case p4:
                    tokens(p4) := 6
            endswitch
            switch p2
                case p1:
                    tokens(p1) := 1
                case p2:
                    tokens(p2) := 2
                case p3:
                    tokens(p3) := 4
                case p4:
                    tokens(p4) := 6
            endswitch
            switch p4
                case p1:
                    tokens(p1) := 1
                case p2:
                    tokens(p2) := 2
                case p3:
                    tokens(p3) := 4
                case p4:
                    tokens(p4) := 6
            endswitch
        endpar

default init s0:
    function x = 1
    function y = 1
    function tokens($p in Place) = at({p1->1,p2->1,p3->2,p4->1},$p)
