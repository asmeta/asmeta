//la funzione "a" assume sempre il valore 6

asm chooseProblem

import ../../STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled a: MyDomain

definitions:
	domain MyDomain = {1:10}

	main rule r_main =
		choose $x in MyDomain with $x>5 do a := $x
