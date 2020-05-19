asm whileRuleSubstitution
import ../../../STDL/StandardLibrary
//for testing the rule substitution of the IterativeWhileRule
signature:
	controlled x: Integer -> Integer
	controlled counter: Integer
		
definitions:

	macro rule r_increment($i in Integer) =
		while x($i) < 10 do
			x($i) := x($i) + 1

	main rule r_main =
		par
			r_increment[counter]
			counter := counter + 1
		endpar

default init s0:
	function x($i in Integer) = 0
	function counter = 0