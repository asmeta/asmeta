//applied flatteners: NR 
asm forallRuleChoose_flat
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
    domain ConcrDom subsetof Integer

    controlled foo: ConcrDom -> ConcrDom

definitions:

    domain ConcrDom = {1,2,3,4,5,6,7,8,9,10}

    macro rule r_rule($a in ConcrDom, $x in ConcrDom) =
        foo($a) := $x


    main rule r_Main =
        forall $a in ConcrDom with lt($a,6) do
            choose $x in ConcrDom with lt($x,$a) do
                r_rule[$a,$x]

default init s0:
    function foo($x in ConcrDom) = 1
