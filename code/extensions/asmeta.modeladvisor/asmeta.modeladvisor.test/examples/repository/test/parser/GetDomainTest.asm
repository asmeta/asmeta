asm GetDomainTest

import ../../STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
    
definitions:
	domain MyDomain = {1..4}

	main rule r_Bop =
		skip

default init s0: