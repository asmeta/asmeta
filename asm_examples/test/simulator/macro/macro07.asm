// must fail to parse!!
asm macro07

	import ../../../STDL/StandardLibrary

signature:

	abstract domain Chan
	domain ChanBool subsetof Chan
	domain ChanInt subsetof Chan
	
	controlled f: Chan -> Integer
	
	static chan: Chan
	
	controlled f1: Rule(Integer, ChanInt)

definitions:

	macro rule r_write($x in Integer, $y in ChanInt) =
		f($y) := $x

	main rule r_main =
		r_write[3, chan]
		
default init s0:

	function f1 = <<r_write(Integer, ChanInt)>>

