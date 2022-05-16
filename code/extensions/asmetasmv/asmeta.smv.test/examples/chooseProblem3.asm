//i valori impostati sono sempre foo(6)=6, foo(7)=7, foo(8)=8, foo(9)=9 e foo(10)=10

asm chooseProblem3
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain -> MyDomain

definitions:
	domain MyDomain = {1:10}

	main rule r_main = 
		forall $x in MyDomain with $x > 5 do
			choose $y in MyDomain with $y >= $x do
				foo($x) := $y

default init s0:
