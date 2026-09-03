asm SetComprehension

import ../STDL/StandardLibrary

signature:
	controlled selected: Powerset(Integer)

definitions:
	main rule r_Main =
		selected := {$value in {1 : 5} | $value > 2 : $value}

default init s0:
	function selected = {}
