asm HemodialysisGround

/*
The Hemodialysis Machine Case Study
http://www.cdcc.faw.jku.at/ABZ2016/HD-CaseStudy.pdf
*/

import ../../../STDL/StandardLibrary

signature:
	enum domain Phases = {PREPARATION | INITIATION | ENDING}
	dynamic controlled phase: Phases //Phase therapy in which is currently the system

definitions:

	macro rule r_run_preparation =
		phase := INITIATION

	macro rule r_run_initiation =
		phase := ENDING

	macro rule r_run_ending =
		skip

	macro rule r_run_dialysis =
		par
			if phase = PREPARATION then
				r_run_preparation[]
			endif
			if phase = INITIATION then
				r_run_initiation[]
			endif
			if phase = ENDING then
				r_run_ending[]
			endif
		endpar

	main rule r_Main =
		r_run_dialysis[]

default init s0:
	function phase = PREPARATION