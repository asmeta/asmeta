asm choosePhdThesis

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
    domain ConcrDom subsetof Integer
    dynamic controlled foo: ConcrDom

definitions:
    domain ConcrDom = {1:4}

	CTLSPEC ag(foo != 2)

    main rule r_Main = 
        choose $x in ConcrDom with $x < 3 do
            foo := $x + 2
          ifnone
              foo := 1

default init s0:
    function foo = 1