module c

	import ../../../STDL/StandardLibrary
	export *

signature:

	controlled f: Integer
	controlled g: Integer

definitions:

	macro rule r_m1($x in Integer) =
		f := f + $x
		
	macro rule r_m2($x in Integer) =
		g := g - $x
		
