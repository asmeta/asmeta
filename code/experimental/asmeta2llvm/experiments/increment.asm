//
//  time java -jar ../../../core/asmeta.simulator/asmeta.simulator.cli/dist/AsmetaS.jar increment.asm
//
asm increment

import  ../../../../asm_examples/STDL/StandardLibrary

signature:

	controlled x : Integer

definitions:


   main rule r_main = 
//   		if x < 100 then x:= x + 1
// 			endif
   		x:= x + 1

   		
default init s0:
   function x = 0
   
   
