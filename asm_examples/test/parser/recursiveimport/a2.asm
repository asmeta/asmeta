asm a2

import ../../../STDL/StandardLibrary
import a1

signature:
	domain ConcrDom3 subsetof Integer
	controlled foo4: ConcrDom3

definitions:

	main rule r_Main = skip

default init s0:
	function foo4 = 5
