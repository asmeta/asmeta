asm m6

import ../../../STDL/StandardLibrary

signature:
	domain ChanInt subsetof Integer

	controlled f: ChanInt -> Integer
	static c: Integer

definitions:

	main rule r_main=
		f(c) := 1

default init s0: