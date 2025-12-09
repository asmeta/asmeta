asm concreteDomain2

	import ../StandardLibrary

signature:

	dynamic abstract domain A
	dynamic domain B subsetof A
	
	controlled f : A -> Integer
	
definitions:

	main rule r_main =
		seq
			extend B with $b1 do skip
			extend B with $b2, $b3 do skip
			extend A with $a1 do skip
			
			forall $x in A with true do f($x) := 9 
						
		endseq
