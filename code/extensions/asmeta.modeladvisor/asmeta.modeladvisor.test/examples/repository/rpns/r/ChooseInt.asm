// AsmetaS cannot simulate it because it contains a choose in Integer

asm ChooseInt
	
signature:
 
    anydomain Any 
	basic domain Integer
		
definitions:	                 	
	rule r_2 = choose $x in Integer with true do skip
	