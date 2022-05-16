module agentsB

import ../../../../../../asm_examples/STDL/StandardLibrary
import moduloC

export *

signature:
	domain SubAgentB subsetof Agent
	domain MyDomain subsetof Integer
	dynamic controlled fooB: MyDomain
	dynamic controlled fooBimp: ImportedDomain -> ImportedDomain
	static agentB: SubAgentB
	
definitions:
	domain MyDomain = {1: 5} 
	
	rule r_b = 
		par
			fooB := 2
			forall $i in ImportedDomain with true do
				fooBimp($i) := $i
		endpar
