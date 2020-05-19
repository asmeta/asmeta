asm macro05

	import ../../../STDL/StandardLibrary

signature:

	abstract domain Chan
	domain ChanBool subsetof Chan
	domain ChanInt subsetof Chan
	
	controlled f: Chan -> Integer
	
	static chanInt: ChanInt
	
	controlled f1: Rule(Integer, Chan)

definitions:

	macro rule r_write($x in Integer, $y in Chan) =
		f($y) := $x

	main rule r_main =
		f1[3, chanInt]
		
default init s0:

	function f1 = <<r_write(Integer, Chan)>>

