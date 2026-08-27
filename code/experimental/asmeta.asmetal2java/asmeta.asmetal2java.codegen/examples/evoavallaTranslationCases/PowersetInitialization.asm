asm PowersetInitialization

import ../STDL/StandardLibrary

signature:
	enum domain Color = {RED | GREEN | BLUE}
	controlled values: Powerset(Integer)
	controlled flags: Powerset(Boolean)
	controlled colors: Powerset(Color)
	controlled combinedValues: Powerset(Integer)
	controlled containsTwo: Boolean

definitions:
	main rule r_Main =
		par
			values := including(excluding(values, 1), 4)
			flags := including(flags, true)
			colors := excluding(colors, RED)
			combinedValues := union(values, {7, 8})
			containsTwo := contains(values, 2)
		endpar

default init s0:
	function values = {1, 2, 3}
	function flags = {false}
	function colors = {RED, GREEN}
	function combinedValues = {}
	function containsTwo = false
