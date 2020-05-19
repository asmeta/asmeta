// An invariant is false in its final state
// run 1 does not find it !!!


asm AxiomInitialState

import ../../../../STDL/StandardLibrary
		
signature:

// dynamic functions

    controlled firstStep: Boolean // centers and ssrs are iniializated

// definitions 

definitions:


  invariant inv_NeverStep over firstStep: firstStep

  //invariant inv_FirstStep over firstStep: not firstStep // must be false at the beginning
	      
//RULES

//Main Rule
 
    main rule r_main = if firstStep then firstStep := false endif
  
//define the initial states 

default init initial_state:

	
	function firstStep = true
	
