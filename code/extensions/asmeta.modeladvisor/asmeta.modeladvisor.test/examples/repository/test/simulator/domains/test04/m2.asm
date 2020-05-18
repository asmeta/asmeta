asm m2

	import ../../../../STDL/StandardLibrary
	import m1
	
signature:

	controlled f: Integer
	
definitions:

	main rule r_main =
		par
			f := 0
			r_m1[]
		endpar
		