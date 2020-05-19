asm MySmartHomeAQ

import StandardLibrary

signature:
	//AQ_MAPE
	domain AirMdA subsetof Agent
	domain MainAQMgA subsetof Agent
	controlled airManagedByMainAQ: MainAQMgA -> Powerset(AirMdA)
	derived startMainAQM: MainAQMgA -> Boolean
	derived startMainAQA: MainAQMgA -> Boolean
	derived startMainAQP: MainAQMgA -> Boolean
	derived startMainAQE: MainAQMgA -> Boolean
	//MySmartHomeAQ
	static aqs_gf: AirMdA
	static aqs_ff: AirMdA
	static aq_main: MainAQMgA

definitions:
	function startMainAQM($b in MainAQMgA) =
		true

	function startMainAQA($b in MainAQMgA) =
		true

	function startMainAQP($b in MainAQMgA) =
		true

	function startMainAQE($b in MainAQMgA) =
		true

	rule r_Air =
		skip //<<TODO>>

	rule r_CleanUp_MainAQE =
		skip //<<TODO>>

	rule r_MainAQE =
		if startMainAQE(self) then
			par
				skip //<<TODO>>
				r_CleanUp_MainAQE[]
			endpar
		endif

	rule r_CleanUp_MainAQP =
		skip //<<TODO>>

	rule r_MainAQP =
		if startMainAQP(self) then
			par
				skip //<<TODO>>
				r_MainAQE[]
				r_CleanUp_MainAQP[]
			endpar
		endif

	rule r_CleanUp_MainAQA =
		skip //<<TODO>>

	rule r_MainAQA =
		if startMainAQA(self) then
			par
				skip //<<TODO>>
				r_MainAQP[]
				r_CleanUp_MainAQA[]
			endpar
		endif

	rule r_CleanUp_MainAQM =
		skip //<<TODO>>

	rule r_MainAQM =
		if startMainAQM(self) then
			par
				skip //<<TODO>>
				r_MainAQA[]
				r_CleanUp_MainAQM[]
			endpar
		endif

	rule r_MainAQ =
		r_MainAQM[]

	main rule r_mainRule =
		forall $a in Agent with true do
			program($a)

default init s0:
	function airManagedByMainAQ($x in MainAQMgA) =
		switch($x)
			case aq_main: {aqs_gf, aqs_ff}
		endswitch


	agent MainAQMgA: r_MainAQ[]
	agent AirMdA: r_Air[]
