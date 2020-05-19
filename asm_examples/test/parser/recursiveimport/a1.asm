asm a1

import ../../../STDL/StandardLibrary
import a2

signature:
	domain ConcrDom subsetof Integer
	controlled foo3: ConcrDom

definitions:

	main rule r_Main = skip

default init s0:
	function foo3 = 5
