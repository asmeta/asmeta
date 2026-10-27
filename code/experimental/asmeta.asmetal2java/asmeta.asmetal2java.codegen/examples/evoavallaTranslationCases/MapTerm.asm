asm MapTerm

import ../STDL/StandardLibrary

signature:
	controlled selected: Boolean

definitions:
	main rule r_Main =
		selected := not(selected)

default init s0:
	function selected = at({1 -> true, 2 -> false}, 1)
