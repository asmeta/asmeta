asm extendDomains

	import ../../../STDL/StandardLibrary
	
signature:

	dynamic domain Dom1 subsetof Agent
	dynamic domain Dom2 subsetof Agent
	
	controlled f1: Integer
	controlled f2: Integer
	
definitions:

	macro rule r_prog1 =
		f1 := 1
		
	macro rule r_prog2 =
		f2 := 2

	main rule r_main =
		par
			extend Dom1 with $d1 do
				program($d1)
			extend Dom2 with $d2 do
				program($d2)
		endpar

default init s0:
		
	agent Dom1:
		r_prog1[]
		
	agent Dom2:
		r_prog2[]
		