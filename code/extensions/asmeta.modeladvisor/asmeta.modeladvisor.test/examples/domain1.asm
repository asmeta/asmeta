asm domain1

import ../../../../asm_examples/STDL/StandardLibrary

signature:

//bip
domain Second subsetof Integer

controlled seconds:Second    
//eip

definitions:    
//bip
domain Second = {0:60}    

main rule r_Main = 
	//par
	  seconds := (seconds + 1) mod 60
	//endpar
//eip

default init s0:

  function seconds = 0
