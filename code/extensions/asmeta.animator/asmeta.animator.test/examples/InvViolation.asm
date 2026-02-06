/** 
*/
asm InvViolation

import STDL/StandardLibrary

signature:
	domain Hour subsetof Integer
	controlled hours: Hour

definitions:
	domain Hour = {0:23}

	invariant inv_1 over hours: hours < 3

	main rule r_Main = 
			hours := hours + 1

default init s0:
	function hours = 0
