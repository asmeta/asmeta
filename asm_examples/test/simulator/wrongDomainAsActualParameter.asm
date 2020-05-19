asm wrongDomainAsActualParameter

import ../../STDL/StandardLibrary

signature:
	abstract domain Chan
	domain ChanInt subsetof Chan

	controlled f: Integer
	controlled c: Chan

definitions:

	macro rule r_m1($x in ChanInt) =
		f := 1

	main rule r_main=
		r_m1[c]

default init s0:
	function f = 3