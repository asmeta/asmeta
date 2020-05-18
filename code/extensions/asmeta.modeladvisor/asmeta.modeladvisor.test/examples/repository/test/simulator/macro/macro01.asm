asm macro01

	import ../../../STDL/StandardLibrary

signature:

	abstract domain Chan
	domain ChanBool subsetof Chan
	domain ChanInt subsetof Chan
	
	static chan1: ChanBool

definitions:

	macro rule r_write1($x in Chan, $y in Integer, $z in Boolean) =
		skip
		
	macro rule r_write2($x in ChanBool, $y in Integer, $z in Boolean) =
		skip
		
	macro rule r_write3($x in ChanInt, $y in Integer, $z in Boolean) =
		skip
		
	main rule r_main =
		r_write2[chan1, 0, false]
		
