asm correctDomainAsActualParameter

import ../../STDL/StandardLibrary

signature:
	abstract domain Chan
	domain ChanInt subsetof Chan

	controlled f: Integer
	dynamic controlled c: Chan
	
	static ci: ChanInt

definitions:

	macro rule r_m1($x in ChanInt) =
		f := 1

	main rule r_main=
		seq
			c := ci
			r_m1[c]
		endseq

default init s0:
	function f = 3
