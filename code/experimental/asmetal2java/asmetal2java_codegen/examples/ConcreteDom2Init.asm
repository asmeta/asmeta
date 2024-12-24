/**
two function in the same domain but different initialization

 */
asm ConcreteDom2Init

import STDL/StandardLibrary

signature:
	domain NumCard subsetof Integer
	controlled a: NumCard
	controlled b: NumCard

definitions:

    domain NumCard = {0:3}

	main rule r_Main = 
		skip

default init s0:
function a = 0
function b = 3

