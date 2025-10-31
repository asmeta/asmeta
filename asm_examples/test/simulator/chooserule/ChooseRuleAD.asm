asm ChooseRuleAD

import  ../../../STDL/StandardLibrary
	
signature:
 
	abstract domain Orders
	controlled a: Orders
	static o: Orders

		
definitions:	                 	
	main rule r_1 = choose $x in Orders with true do a:=$x
	
