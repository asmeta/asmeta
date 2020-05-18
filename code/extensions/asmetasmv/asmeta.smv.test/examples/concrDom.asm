asm concrDom
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
    domain SubDomInt1 subsetof Integer
    domain SubDomInt2 subsetof Integer
    domain SubDomNat subsetof Natural
    dynamic controlled fooI1: SubDomInt1
    dynamic controlled fooI2: SubDomInt2
    dynamic controlled fooN: SubDomNat

definitions:
    domain SubDomInt1 = {1 : 5}
    domain SubDomInt2 = {2, 1, 5}
    domain SubDomNat = {1n: 4n}

    main rule r_Main =
        skip

default init s0:
    function fooI1 = 1
    function fooI2 = 2
    function fooN = 3n