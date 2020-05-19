asm m3

import ../../../STDL/StandardLibrary

signature:
	abstract domain Chan
	domain ChanInt subsetof Chan
	domain ChanBool subsetof Chan

	controlled f: Integer
	static c: Chan

definitions:

	macro rule r_m1($x in ChanInt) =
		f := 1

	macro rule r_m1($x in ChanBool) =
		f := 2

	main rule r_main=
		r_m1[c]

default init s0:
	function f = 3