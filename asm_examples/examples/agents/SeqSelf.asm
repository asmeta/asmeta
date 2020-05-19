// problemi con il seq i multi agenti

asm SeqSelf

import ../../STDL/StandardLibrary

signature:

	domain Robot subsetof Agent
	domain Pompa subsetof Agent
	
	dynamic controlled f: Robot -> Integer
	static r: Robot
	
definitions:
		
	macro rule r_robot = f(self) := 10
	macro rule r_robot2 = f(self) := 5
	
	macro rule r_programRobot = seq r_robot[]	r_robot2[] endseq
		
	main rule r_Main = program(r)
	
default init initial_state:

   // agent Robot : seq r_robot[]	r_robot2[] endseq
   agent Robot : r_programRobot[]
   
