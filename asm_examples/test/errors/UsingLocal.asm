// wrong use of local in signature
// it can be used only in turbo rules
//
asm UsingLocal

import ../../STDL/StandardLibrary

signature:
	local foo: Integer

definitions:
	
	main rule r_Main = skip

default init s0:
