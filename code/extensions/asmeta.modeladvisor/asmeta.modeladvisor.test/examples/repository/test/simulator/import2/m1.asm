module m1

	export f1, f2, r_m1

signature:

	controlled f1: Integer
	controlled f2: Integer
	
definitions:

	macro rule r_m1 =
		f1 := 123
		