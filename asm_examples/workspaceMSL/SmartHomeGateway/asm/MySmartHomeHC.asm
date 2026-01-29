asm MySmartHomeHC

import StandardLibrary

signature:
	//HC_MAPE
	domain HeatingMdA subsetof Agent
	domain MainHCMgA subsetof Agent
	domain IntHCMgA subsetof Agent
	derived startMainHCM: MainHCMgA -> Boolean
	derived startMainHCA: MainHCMgA -> Boolean
	derived startMainHCP: MainHCMgA -> Boolean
	derived startMainHCE: MainHCMgA -> Boolean
	controlled heatingManagedByIntHC: IntHCMgA -> HeatingMdA
	derived startIntHCM: IntHCMgA -> Boolean
	derived startIntHCE: IntHCMgA -> Boolean
	//I: IntHC.M -> MainHC.M [*-SOME,1]
	controlled sgnIntHCMMainHCM: Prod(IntHCMgA, MainHCMgA) -> Boolean
	controlled fromIntHCMtoMainHCM: IntHCMgA -> MainHCMgA
	controlled fromMainHCMtoIntHCM: MainHCMgA -> Powerset(IntHCMgA)
	//I: MainHC.E -> IntHC.E [1,*-SOME]
	controlled sgnMainHCEIntHCE: Prod(MainHCMgA, IntHCMgA) -> Boolean
	controlled fromMainHCEtoIntHCE: MainHCMgA -> Powerset(IntHCMgA)
	derived orSelectorfromMainHCEtoIntHCE: MainHCMgA -> Powerset(IntHCMgA)
	controlled fromIntHCEtoMainHCE: IntHCMgA -> MainHCMgA
	//MySmartHomeHC
	static hs_ff: HeatingMdA
	static hs_gf: HeatingMdA
	static main_hc: MainHCMgA
	static int_hc_gf: IntHCMgA
	static int_hc_ff: IntHCMgA

definitions:
	function startMainHCM($b in MainHCMgA) =
		(exist $a in fromMainHCMtoIntHCM($b) with sgnIntHCMMainHCM($a, $b))

	function startMainHCA($b in MainHCMgA) =
		true

	function startMainHCP($b in MainHCMgA) =
		true

	function startMainHCE($b in MainHCMgA) =
		true

	function startIntHCM($b in IntHCMgA) =
		true

	function startIntHCE($b in IntHCMgA) =
		sgnMainHCEIntHCE(fromIntHCEtoMainHCE($b), $b)

	function orSelectorfromMainHCEtoIntHCE($b in MainHCMgA) =
		chooseone({$a in Powerset(IntHCMgA) | not(isEmpty($a)): $a})

	rule r_Heating =
		skip //<<TODO>>

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

	function heatingManagedByIntHC($x in IntHCMgA) =
		switch($x)
			case int_hc_gf: hs_gf
			case int_hc_ff: hs_ff
		endswitch


	agent IntHCMgA: r_IntHC[]
	agent HeatingMdA: r_Heating[]
	agent MainHCMgA: r_MainHC[]
