asm macro03

	import ../../../STDL/StandardLibrary

signature:

	abstract domain Chan
	domain ChanBool subsetof Chan
	domain ChanInt subsetof Chan
	
	controlled f: Chan -> Integer
	
	static chanInt: ChanInt
	static chanInt2: ChanInt
	static chanBool: ChanBool
	static chanBool2: ChanBool

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
		par
			r_write[3, chanInt]
			r_write[7.14, chanInt2]
			r_write[5, chanBool] // the square
			r_write[10, chanBool2, "abc"] // the cube
		endpar
		
