asm extendSeqProblem

import ../../STDL/StandardLibrary

signature:
	dynamic abstract domain Traffic
	dynamic controlled status: Traffic -> Boolean

	static agentA: Agent
	static agentB: Agent

definitions:

	rule r_agentRule =
		seq
			extend Traffic with $t do
				status($t) := false
			choose $q in Traffic with not(status($q)) do
				skip//status($q) := true
		endseq

	main rule r_Main =
		par
			program(agentA)
			program(agentB)
		endpar

default init s0:

	agent Agent:
		r_agentRule[]
