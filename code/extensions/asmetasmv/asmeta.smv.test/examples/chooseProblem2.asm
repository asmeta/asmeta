//la funzione "foo" assume sempre il valore 1

asm chooseProblem2

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain

definitions:
	domain MyDomain = {1..10}

	main rule r_main = 
		choose $x in MyDomain with true do
			foo := $x
