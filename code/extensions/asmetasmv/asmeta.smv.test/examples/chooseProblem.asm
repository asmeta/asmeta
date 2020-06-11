//la funzione "foo" assume sempre il valore 6
asm chooseProblem

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain

definitions:
	domain MyDomain = {1..10}

	main rule r_main = 
		choose $x in MyDomain with $x>5 do
			foo := $x
