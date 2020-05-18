// asm obtained by semantic mapping (Fsm2AsmM.atl) 
// of the FSM in figure 15
// by using strings

asm FSM_map0

import ../../STDL/StandardLibrary

signature:

      controlled currentState: String 

	  monitored input: String
	  
      out output: String       //(Mealy Fsm)
	
definitions:
	
	rule r_s1_1 = if currentState = "s1" and input = "1"
        		  then par currentState:= "s1" output:= "0" endpar endif
	rule r_s1_0 = if currentState = "s1" and input = "0"
        		  then par currentState:= "s2" output:= "1" endpar endif
	rule r_s2_1 = if currentState = "s2" and input = "0"
        		  then par currentState:= "s2" output:= "0" endpar endif
	rule r_s2_0 = if currentState = "s2" and input = "1"
        		  then par currentState:= "s1" output:= "1" endpar endif
					
// run all the transition rules  

	main rule r_Main = par r_s1_1[] r_s1_0[] r_s2_1[] r_s2_0[] endpar
		
	
		
//** For a specific Fsm terminal model 
default init s0:

	 function currentState = "s1"       





