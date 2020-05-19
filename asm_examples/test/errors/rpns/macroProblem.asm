//errore "Unresolved reference" se il dominio della variabile di una macro call rule e' un Concrete Domain 
asm macroProblem

import ../../../STDL/StandardLibrary

signature:
	domain SubDom subsetof Integer
	
definitions:
	domain SubDom = {1 : 4}

	macro rule r_SubDom($x in SubDom) = 
		skip

	main rule r_main =
		//errore: Unresolved reference to r_SubDom
		r_SubDom[2]
