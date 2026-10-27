asm ConcreteDomainOperators

import ../STDL/StandardLibrary

signature:
	domain Amount subsetof Integer
	controlled first: Amount
	controlled second: Amount
	controlled total: Amount
	controlled ordered: Boolean

definitions:
	domain Amount = {0 : 10}

	main rule r_Main =
		par
			total := first + second
			ordered := first <= second
		endpar

default init s0:
	function first = 1
	function second = 2
	function total = 0
	function ordered = true
