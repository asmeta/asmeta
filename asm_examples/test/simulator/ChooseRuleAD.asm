asm ChooseRuleAD
	
signature:
 
    anydomain Any 
	abstract domain Orders
	basic domain Integer
		
definitions:	                 	
	rule r_1 = choose $x in Orders with true do skip
	