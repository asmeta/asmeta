asm MySmartHomeAQ_HC

import StandardLibrary

signature:
	//Hierarchical_AQ_HC_MAPE
	domain SysHMdA subsetof Agent
	domain SysAMdA subsetof Agent
	domain IntHCMgA subsetof Agent
	domain MainAQMgA subsetof Agent
	domain MainHCMgA subsetof Agent
	domain HighAQ_HCMgA subsetof Agent
	controlled syshManagedByIntHC: IntHCMgA -> SysHMdA
	controlled sysaManagedByIntHC: IntHCMgA -> Powerset(SysAMdA)
	derived startIntHCM: IntHCMgA -> Boolean
	derived startIntHCE: IntHCMgA -> Boolean
	controlled sysaManagedByMainAQ: MainAQMgA -> Powerset(SysAMdA)
	derived startMainAQM: MainAQMgA -> Boolean
	derived startMainAQA: MainAQMgA -> Boolean
	derived startMainAQP: MainAQMgA -> Boolean
	derived startMainAQE: MainAQMgA -> Boolean
	derived startMainHCM: MainHCMgA -> Boolean
	derived startMainHCA: MainHCMgA -> Boolean
	derived startMainHCP: MainHCMgA -> Boolean
	derived startMainHCE: MainHCMgA -> Boolean
	derived startHighAQ_HCA: HighAQ_HCMgA -> Boolean
	derived startHighAQ_HCE: HighAQ_HCMgA -> Boolean
	//I: IntHC.M -> MainHC.M [*-SOME,1]
	controlled sgnIntHCMMainHCM: Prod(IntHCMgA, MainHCMgA) -> Boolean
	controlled fromIntHCMtoMainHCM: IntHCMgA -> MainHCMgA
	controlled fromMainHCMtoIntHCM: MainHCMgA -> Powerset(IntHCMgA)
	//I: MainHC.E -> IntHC.E [1,*-SOME]
	controlled sgnMainHCEIntHCE: Prod(MainHCMgA, IntHCMgA) -> Boolean
	controlled fromMainHCEtoIntHCE: MainHCMgA -> Powerset(IntHCMgA)
	derived orSelectorfromMainHCEtoIntHCE: MainHCMgA -> Powerset(IntHCMgA)
	controlled fromIntHCEtoMainHCE: IntHCMgA -> MainHCMgA
	//MySmartHomeAQ_HC
	static hs_ff: SysHMdA
	static hs_gf: SysHMdA
	static aqs_gf: SysAMdA
	static aqs_ff: SysAMdA
	static main_aq: MainAQMgA
	static main_hc: MainHCMgA
	static int_hc_gf: IntHCMgA
	static int_hc_ff: IntHCMgA
	static high_aq_hs: HighAQ_HCMgA

definitions:
	function startIntHCM($b in IntHCMgA) =
		true

	function startIntHCE($b in IntHCMgA) =
		sgnMainHCEIntHCE(fromIntHCEtoMainHCE($b), $b)

	function startMainAQM($b in MainAQMgA) =
		true

	function startMainAQA($b in MainAQMgA) =
		true

	function startMainAQP($b in MainAQMgA) =
		true

	function startMainAQE($b in MainAQMgA) =
		true

	function startMainHCM($b in MainHCMgA) =
		(exist $a in fromMainHCMtoIntHCM($b) with sgnIntHCMMainHCM($a, $b))

	function startMainHCA($b in MainHCMgA) =
		true

	function startMainHCP($b in MainHCMgA) =
		true

	function startMainHCE($b in MainHCMgA) =
		true

	function startHighAQ_HCA($b in HighAQ_HCMgA) =
		true

	function startHighAQ_HCE($b in HighAQ_HCMgA) =
		true

	function orSelectorfromMainHCEtoIntHCE($b in MainHCMgA) =
		chooseone({$a in Powerset(IntHCMgA) | not(isEmpty($a)): $a})

	rule r_SysH =
		skip //<<TODO>>

	rule r_SysA =
		skip //<<TODO>>

	rule r_CleanUp_IntHCM =
		skip //<<TODO>>

	rule r_IntHCM =
		if startIntHCM(self) then
			par
				skip //<<TODO>>
				if not sgnIntHCMMainHCM(self,fromIntHCMtoMainHCM(self)) then sgnIntHCMMainHCM(self,fromIntHCMtoMainHCM(self)) := true endif
				r_CleanUp_IntHCM[]
			endpar
		endif

	rule r_CleanUp_IntHCE =
		sgnMainHCEIntHCE(fromIntHCEtoMainHCE(self), self) := false

	rule r_IntHCE =
		if startIntHCE(self) then
			par
				skip //<<TODO>>
				r_CleanUp_IntHCE[]
			endpar
		endif

	rule r_IntHC =
		par
			r_IntHCM[]
			r_IntHCE[]
		endpar

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

	rule r_CleanUp_MainHCE =
		skip //<<TODO>>

	rule r_MainHCE =
		if startMainHCE(self) then
			par
				skip //<<TODO>>
				forall $a in orSelectorfromMainHCEtoIntHCE(self) do sgnMainHCEIntHCE(self, $a) := true
				r_CleanUp_MainHCE[]
			endpar
		endif

	rule r_CleanUp_MainHCP =
		skip //<<TODO>>

	rule r_MainHCP =
		if startMainHCP(self) then
			par
				skip //<<TODO>>
				r_MainHCE[]
				r_CleanUp_MainHCP[]
			endpar
		endif

	rule r_CleanUp_MainHCA =
		skip //<<TODO>>

	rule r_MainHCA =
		if startMainHCA(self) then
			par
				skip //<<TODO>>
				r_MainHCP[]
				r_CleanUp_MainHCA[]
			endpar
		endif

	rule r_CleanUp_MainHCM =
		forall $a in fromMainHCMtoIntHCM(self) do sgnIntHCMMainHCM($a, self) := false

	rule r_MainHCM =
		if startMainHCM(self) then
			par
				skip //<<TODO>>
				r_MainHCA[]
				r_CleanUp_MainHCM[]
			endpar
		endif

	rule r_MainHC =
		r_MainHCM[]

	rule r_CleanUp_HighAQ_HCE =
		skip //<<TODO>>

	rule r_HighAQ_HCE =
		if startHighAQ_HCE(self) then
			par
				skip //<<TODO>>
				r_CleanUp_HighAQ_HCE[]
			endpar
		endif

	rule r_CleanUp_HighAQ_HCA =
		skip //<<TODO>>

	rule r_HighAQ_HCA =
		if startHighAQ_HCA(self) then
			par
				skip //<<TODO>>
				r_HighAQ_HCE[]
				r_CleanUp_HighAQ_HCA[]
			endpar
		endif

	rule r_HighAQ_HC =
		r_HighAQ_HCA[]

	main rule r_mainRule =
		forall $a in Agent with true do
			program($a)

default init s0:
	function sgnIntHCMMainHCM($a in IntHCMgA, $b in MainHCMgA) = false
	function sgnMainHCEIntHCE($a in MainHCMgA, $b in IntHCMgA) = false
	function fromIntHCMtoMainHCM($a in IntHCMgA) =
		switch($a)
			case int_hc_gf: main_hc
			case int_hc_ff: main_hc
		endswitch

	function fromMainHCMtoIntHCM($a in MainHCMgA) =
		switch($a)
			case main_hc: {int_hc_gf, int_hc_ff}
		endswitch

	function fromMainHCEtoIntHCE($a in MainHCMgA) =
		switch($a)
			case main_hc: {int_hc_gf, int_hc_ff}
		endswitch

	function fromIntHCEtoMainHCE($a in IntHCMgA) =
		switch($a)
			case int_hc_gf: main_hc
			case int_hc_ff: main_hc
		endswitch

	function sysaManagedByMainAQ($x in MainAQMgA) =
		switch($x)
			case main_aq: {aqs_gf, aqs_ff}
		endswitch

	function syshManagedByIntHC($x in IntHCMgA) =
		switch($x)
			case int_hc_gf: hs_gf
			case int_hc_ff: hs_ff
		endswitch

	function sysaManagedByIntHC($x in IntHCMgA) =
		switch($x)
			case int_hc_gf: {aqs_gf}
			case int_hc_ff: {aqs_ff}
		endswitch


	agent IntHCMgA: r_IntHC[]
	agent SysAMdA: r_SysA[]
	agent MainAQMgA: r_MainAQ[]
	agent HighAQ_HCMgA: r_HighAQ_HC[]
	agent MainHCMgA: r_MainHC[]
	agent SysHMdA: r_SysH[]
