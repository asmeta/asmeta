asm euclideMCD

/*Euclid's Algorithm for calculating the Greatest Common Divisor (GCD)
 
  function GCD(a, b)
   while a != b
         if a > b
             a := a - b
         else
             b := b - a
     return a

This ASM (Abstract State Machine) model implements Euclid's algorithm in the following way:

Each step of the machine corresponds to one iteration of the while loop;
When the update set is empty, it means the result has been obtained and can be read from either numA or numB.

The model must be executed with the -ne option (run until empty).
The values of the two numbers numA and numB must be initialized in the initial state.
*/

import ../../STDL/StandardLibrary

signature:
	dynamic controlled numA: Integer
	dynamic controlled numB: Integer

definitions:

	main rule r_Main =
		if(numA != numB) then //if numbers are different
			if(numA > numB) then // if numA is higher than numB
				numA := numA - numB //Decrease numA by numB
			else
				numB := numB - numA //Decrease numB by numA
			endif
		endif

default init s0:
	function numA = 6409 //121
	function numB = 3289 //22
