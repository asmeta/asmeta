/*Non-Recursive Fibonacci Algorithm – Description
The Fibonacci sequence is a series of numbers where each number is the sum of the two preceding ones, typically starting with 0 and 1. The non-recursive (iterative) version of the algorithm calculates the Fibonacci number at a given position using a loop instead of recursion.
Algorithm Logic:

Start with two initial values:

f0 = 0 (Fibonacci(0))
f1 = 1 (Fibonacci(1))


Use a loop to compute the next Fibonacci numbers up to the desired position n.
At each iteration, update the values:

next = f0 + f1
f0 = f1
f1 = next


Repeat until reaching the nth Fibonacci number.
 */


asm fibonacci

import ../../STDL/StandardLibrary

signature:
	dynamic monitored numeri_fibo: Integer //number of Fibonacci numbers
	dynamic controlled num_fibo: Integer
	dynamic controlled indice: Integer
	dynamic controlled succ_fibo: Seq(Integer)
	
	dynamic controlled temp: Integer
	
definitions:
	
	macro rule r_insert_number =
		if(num_fibo=0) then
			num_fibo := numeri_fibo
		endif
	
	macro rule r_fibonacci =
		if(indice<num_fibo) then
			par
				succ_fibo := append(succ_fibo, at(succ_fibo,iton(indice-1)) + at(succ_fibo,iton(indice-2)))
				indice := indice + 1
			endpar
		endif
			
	main rule r_Main =
		seq
			r_insert_number[]
			r_fibonacci[]
		endseq
	

default init s0:
	function succ_fibo = [0, 1]
	function indice = 2
	function num_fibo = 0