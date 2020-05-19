asm m4

import ../../../STDL/StandardLibrary

signature:
	abstract domain Chan
	domain ChanInt subsetof Chan
	domain ChanBool subsetof Chan

	controlled turn: Boolean

	controlled f: Integer
	dynamic controlled c: Chan

	static cI: ChanInt
	static cB: ChanBool

definitions:

	macro rule r_m1($x in ChanInt) =
		f := 1

	macro rule r_m1($x in ChanBool) =
		f := 2

	main rule r_main=
		par
			if(turn) then
				c := cB
			else
				c := cI
			endif
			r_m1[c]
			turn := not(turn)
		endpar

default init s0:
	function turn = true
	function c = cI
