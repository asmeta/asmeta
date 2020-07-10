/** at every step increments the seconds 
*/
asm MapDomain

import STDL/StandardLibrary

signature:
	controlled voti: Map(String, Integer)


definitions:


	main rule r_Main = 
		voti := {"A"-> 5, "B"-> 7}



default init s0:


