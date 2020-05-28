asm m5

import ../../../STDL/StandardLibrary

signature:
	abstract domain Chan
	domain ChanInt subsetof Chan
	domain ChanBool subsetof Chan

	controlled f: ChanInt -> Integer
	static c: Chan

definitions:

	main rule r_main=
		f(c) := 1

default init s0:
