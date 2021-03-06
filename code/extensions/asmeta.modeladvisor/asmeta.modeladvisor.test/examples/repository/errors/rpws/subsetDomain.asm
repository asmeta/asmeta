// questa specifica � corretta per il compilatore
// ma il simulatore dovrebbe acorgersi run time che a va al di fuori del suo dominio

asm subsetDomain

import ../asm_examples/STDL/StandardLibrary

signature:
	domain SubDomain subsetof Integer
	dynamic controlled a : SubDomain
	
definitions:
	domain SubDomain = {1 : 6}

	main rule r_Main =
		a := a + 1
	
default init s0:
	function a = 5
