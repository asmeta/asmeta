// a simple example with Cipher

asm Cipher


import StandardLibrary

signature:
	// DOMAINS
	dynamic abstract domain Cipher
	
	// FUNCTIONS
	controlled cipher: Cipher
	controlled key: Cipher-> Integer
	controlled plain: Cipher-> Seq(Integer)
	
definitions:
	// DOMAIN DEFINITIONS
	

	// FUNCTION DEFINITIONS
	rule r_Encrypt($m in Seq(Integer), $pk in Integer)=
	      extend Cipher with $e2 do
	        par
	          cipher:=$e2   
	          plain($e2):=$m
	          key($e2):=$pk
	        endpar
	      
	// INVARIANTS
	
	// MAIN RULE
	main rule r_Main =
	  let ($m=[1,2]) in
	     r_Encrypt[$m, 3]
	  endlet
      
// INITIAL STATE
default init s0:
   function cipher=undef
