asm concreteDomain

	import ../../../STDL/StandardLibrary

signature:

	dynamic abstract domain A
	dynamic domain B subsetof A
	
	dynamic abstract domain C
	
definitions:

	main rule r_main =
		seq
			extend B with $b1 do skip
			extend B with $b2, $b3 do skip
			extend A with $a1 do skip
			
			extend C with $c1, $c2 do skip
		endseq
