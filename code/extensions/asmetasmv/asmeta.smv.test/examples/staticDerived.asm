asm staticDerived

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer	
	dynamic monitored mon1: Boolean
	dynamic monitored mon2: Boolean
	static stat: MyDomain
	derived der: Boolean

definitions:
	domain MyDomain = {1:4}

	function stat = 2
	function der = mon1 and mon2
	
	main rule r_Main =
		skip
