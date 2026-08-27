asm StaticConstsAbstractDomain

import ../../STDL/StandardLibrary

signature:

    abstract domain Position

    static top: Position
    static bottom: Position
    dynamic monitored event: Position -> Boolean
    

definitions:


    main rule r_Main =
        if event(top) then
        	skip
        endif

default init s0:
