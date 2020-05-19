// a simple example with a tic tac toe game

asm InvariantsMon

import StandardLibrary

signature:
   dynamic controlled fooA: Integer
   dynamic controlled fooB: Integer
   dynamic monitored  monA : Boolean 
   dynamic monitored  monB : Boolean 
definitions:
     macro rule r_a =
         if(monA) then 
           fooA := fooA + 1 
         endif
     macro rule r_b = 
          if(monB) then
          fooB := fooB + 1 
          endif
     invariant over fooA, fooB: fooA != fooB
     main rule r_main =
          par
            r_a[]
            r_b[]
          endpar
default init s0:
     function fooA = 1
     function fooB = 0