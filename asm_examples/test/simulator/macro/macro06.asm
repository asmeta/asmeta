// must fail to parse!!
asm macro06

	import ../../../STDL/StandardLibrary

signature:

	abstract domain Chan
	domain ChanBool subsetof Chan
	domain ChanInt subsetof Chan
	
	controlled f: Chan -> Integer
	
	static chan: Chan
	static chanInt: ChanInt
	static chanInt2: ChanInt
	static chanBool: ChanBool
	static chanBool2: ChanBool
	
	controlled f1: Rule(Integer, ChanInt)
	controlled f2: Rule(Real, ChanInt)
	controlled f3: Rule(Integer, ChanBool)
	controlled f4: Rule(Integer, ChanBool, String)

definitions:

	macro rule r_write($x in Integer, $y in ChanInt) =
		f($y) := $x

	macro rule r_write($x in Real, $y in ChanInt) =
		f($y) := rtoi($x)
		
	macro rule r_write($x in Integer, $y in ChanBool) =
		f($y) := $x * $x
		
	macro rule r_write($x in Integer, $y in ChanBool, $z in String) =
		seq
			r_write[$x, $y]
			f($y) := f($y) * $x // f($y) = $x*$x*$x
		endseq

	main rule r_main =
		skip
		
default init s0:

	function f1 = <<r_write(Integer, Chan)>>
	function f2 = <<r_write(Real, ChanInt)>>
	function f3 = <<r_write(Integer, ChanBool)>>
	function f4 = <<r_write(Integer, ChanBool, String)>>

