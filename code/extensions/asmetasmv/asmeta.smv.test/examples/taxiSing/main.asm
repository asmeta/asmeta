asm main

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLLibrary
import client
import taxi

signature:

definitions:

	main rule r_Main =
		par
			program(client)
			program(taxi)
		endpar

default init s0:
	function clientNumViaggi($c in ClientAgent) = 2
	function clientState($c in ClientAgent) = IDLECL
	function taxiState($t in TaxiAgent) = IDLETX

	agent ClientAgent:
		r_ClientAgent[]

	agent TaxiAgent:
		r_TaxiAgent[]
