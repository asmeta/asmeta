asm Counter

import ../StandardLibrary
import RandomModule

signature:
	domain CounterDomain subsetof Integer 
	controlled counter: CounterDomain

definitions:
	domain CounterDomain = {-100 : 100}

	main rule r_Main =
		seq
			r_random[]
			choose $b in Boolean with true do
				if random and $b then
					counter := counter + 1
				else
					counter := counter - 1
				endif
		endseq
	
default init s0:
	function counter = 0
