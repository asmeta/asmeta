asm macroRuleChoose

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain Side = {LEFT | RIGHT}
	dynamic controlled position: Side
	dynamic monitored choice: Side
	
definitions:

	rule r_choose($b in Side) =
		choose $s in Side with $s = $b do
			position := $s

	main rule r_Main = 
		r_choose[choice]

default init s0:
	function position = LEFT
