// asm obtained by semantic mapping (Fsm2AsmM.atl) 
// of the FSM in figure 15
// by using enumerations

asm FSM_map0_2

import ../../STDL/StandardLibrary

signature:

	  enum domain State = {ST_S1 | ST_S2}	

	  enum domain Event = {EV_0 | EV_1}	

      controlled currentState: State 

	  monitored input: Event	  
	  
      out output: Event       //(Mealy Fsm)
	
definitions:
	
	rule r_s1_1 = if currentState = ST_S1  and input = EV_1
        		  then par currentState:= ST_S1  output:= EV_0 endpar endif

	rule r_s1_0 = if currentState = ST_S1  and input = EV_0
        		  then par currentState:= ST_S2  output:= EV_1 endpar endif
						
	rule r_s2_1 = if currentState = ST_S2  and input = EV_1
        		  then par currentState:= ST_S1  output:= EV_1 endpar endif
	
	rule r_s2_0 = if currentState = ST_S2  and input = EV_0
        		  then par currentState:= ST_S2  output:= EV_0 endpar endif
					
// run all the transition rules  

	main rule r_Main = par r_s1_1[] r_s1_0[] r_s2_1[] r_s2_0[] endpar
		
	
		
//** For a specific Fsm terminal model 
default init s0:

	 function currentState = ST_S1




