//applied flatteners: FR 
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
        par
            choose $x82 in ConcrDom with lt($x82,4) do
                r_rule[4,$x82]
            choose $x83 in ConcrDom with lt($x83,1) do
                r_rule[1,$x83]
            choose $x85 in ConcrDom with lt($x85,3) do
                r_rule[3,$x85]
            choose $x87 in ConcrDom with lt($x87,5) do
                r_rule[5,$x87]
            choose $x89 in ConcrDom with lt($x89,2) do
                r_rule[2,$x89]
        endpar

default init s0:
    function foo($x in ConcrDom) = 1
