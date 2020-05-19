FILE DISTRIBUTION

Semantic mapping applied to the SystemC UML profile
----------------------------------------------------

main.asm: to start the simulation  -- by Alessandro
top.asm: to initialize the top module and sub-modules -- by Alessandro
chan.asm: contains common.asm, chanint.asm, chanbool.asm for layer 1 of SystemC originally defined by Alessandro-- by Sara
clock.asm: clock for layer 1 of SystemC 
display.asm: block of the Counter case study -- by Alessandro   

Semantic mapping/Meta-hooking applied to the SystemC UML profile -- by Sara:
----------------------------------------------------------------------------

StandardLibrary.java: added some function implementations for undef values -- by Sara
ruleSM.asm: iota + tao  (rules + "eval") 
SystemCUMLProfile_INIT.asm:  gamma + tao (added functions except "eval")


LIMITATIONS

- Currently, the schduler executes only the count_stim thread, while the coun_up thread never executes 
- Completion events are not cancelled from the event queue
- no timed events for wait state, no entry/exit actions, no effect actions on transitions
- functions and initializing rules depending on the terminal model are "eval" e "r_StringToRule". They should be generated automatically by model transformation. 
- Currently, functions upstate and downState are declared and initialized as "controlled funtions" 
- Currently, the project uses a specific Java implementation of the standard library (see file StandardLibrary.java) and it should be put in the package org.asmeta.interpreter.util