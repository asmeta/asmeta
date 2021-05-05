asm outofbound

import ../../STDL/StandardLibrary

signature:
    domain Subset subsetof Integer
	dynamic controlled foo: Subset

definitions:

	domain Subset = {0:3}

	main rule r_Main =
			foo := foo + 1

default init s0:
	function foo = 0