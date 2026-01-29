asm ComfortableHeating

import ../../../STDL/StandardLibrary

signature:
	//ComfortableHeatingMAPE
	domain HeaterMdA subsetof Agent
	domain MainCHMgA subsetof Agent
	domain IntTempMgA subsetof Agent
	derived startMainCHM: MainCHMgA -> Boolean
	derived startMainCHA: MainCHMgA -> Boolean
	derived startMainCHP: MainCHMgA -> Boolean
	derived startMainCHE: MainCHMgA -> Boolean
	controlled heaterManagedByIntTemp: IntTempMgA -> HeaterMdA
	derived startIntTempM: IntTempMgA -> Boolean
	derived startIntTempE: IntTempMgA -> Boolean
	//I: IntTemp.M -> MainCH.M [*,1]
	controlled sgnIntTempMMainCHM: Prod(IntTempMgA, MainCHMgA) -> Boolean
	controlled fromIntTempMtoMainCHM: IntTempMgA -> MainCHMgA
	controlled fromMainCHMtoIntTempM: MainCHMgA -> Powerset(IntTempMgA)
	//I: MainCH.E -> IntTemp.E [1,*]
	controlled sgnMainCHEIntTempE: Prod(MainCHMgA, IntTempMgA) -> Boolean
	controlled fromMainCHEtoIntTempE: MainCHMgA -> Powerset(IntTempMgA)
	controlled fromIntTempEtoMainCHE: IntTempMgA -> MainCHMgA
	//ComfortableHeating
	static hs0: HeaterMdA
	static hs1: HeaterMdA
	static hs2: HeaterMdA
	static ch: MainCHMgA
	static h0: IntTempMgA
	static h1: IntTempMgA
	static h2: IntTempMgA

definitions:
	function startMainCHM($b in MainCHMgA) =
		(forall $a in fromMainCHMtoIntTempM($b) with sgnIntTempMMainCHM($a, $b))

	function startMainCHA($b in MainCHMgA) =
		true

	function startMainCHP($b in MainCHMgA) =
		true

	function startMainCHE($b in MainCHMgA) =
		true

	function startIntTempM($b in IntTempMgA) =
		true

	function startIntTempE($b in IntTempMgA) =
		sgnMainCHEIntTempE(fromIntTempEtoMainCHE($b), $b)

	rule r_Heater =
		skip //<<TODO>>

	rule r_CleanUp_MainCHE =
		skip //<<TODO>>

	rule r_MainCHE =
		if startMainCHE(self) then
			par
				skip //<<TODO>>
				forall $a in fromMainCHEtoIntTempE(self) do sgnMainCHEIntTempE(self, $a) := true
				r_CleanUp_MainCHE[]
			endpar
		endif

	rule r_CleanUp_MainCHP =
		skip //<<TODO>>

	rule r_MainCHP =
		if startMainCHP(self) then
			par
				skip //<<TODO>>
				r_MainCHE[]
				r_CleanUp_MainCHP[]
			endpar
		endif

	rule r_CleanUp_MainCHA =
		skip //<<TODO>>

	rule r_MainCHA =
		if startMainCHA(self) then
			par
				skip //<<TODO>>
				r_MainCHP[]
				r_CleanUp_MainCHA[]
			endpar
		endif

	rule r_CleanUp_MainCHM =
		forall $a in fromMainCHMtoIntTempM(self) do sgnIntTempMMainCHM($a, self) := false

	rule r_MainCHM =
		if startMainCHM(self) then
			par
				skip //<<TODO>>
				r_MainCHA[]
				r_CleanUp_MainCHM[]
			endpar
		endif

	rule r_MainCH =
		r_MainCHM[]

	rule r_CleanUp_IntTempM =
		skip //<<TODO>>

	rule r_IntTempM =
		if startIntTempM(self) then
			par
				skip //<<TODO>>
				if not sgnIntTempMMainCHM(self,fromIntTempMtoMainCHM(self)) then sgnIntTempMMainCHM(self,fromIntTempMtoMainCHM(self)) := true endif
				r_CleanUp_IntTempM[]
			endpar
		endif

	rule r_CleanUp_IntTempE =
		sgnMainCHEIntTempE(fromIntTempEtoMainCHE($b), $b) := false

	rule r_IntTempE =
		if startIntTempE(self) then
			par
				skip //<<TODO>>
				r_CleanUp_IntTempE[]
			endpar
		endif

	rule r_IntTemp =
		par
			r_IntTempM[]
			r_IntTempE[]
		endpar

	main rule r_mainRule =
		forall $a in Agent with true do
			program($a)

default init s0:
	function sgnIntTempMMainCHM($a in IntTempMgA, $b in MainCHMgA) = false
	function sgnMainCHEIntTempE($a in MainCHMgA, $b in IntTempMgA) = false
	function fromIntTempMtoMainCHM($a in IntTempMgA) =
		switch($a)
			case h0: ch
			case h1: ch
			case h2: ch
		endswitch

	function fromMainCHMtoIntTempM($a in MainCHMgA) =
		switch($a)
			case ch: {h0, h1, h2}
		endswitch

	function fromMainCHEtoIntTempE($a in MainCHMgA) =
		switch($a)
			case ch: {h0, h1, h2}
		endswitch

	function fromIntTempEtoMainCHE($a in IntTempMgA) =
		switch($a)
			case h0: ch
			case h1: ch
			case h2: ch
		endswitch

	function heaterManagedByIntTemp($x in IntTempMgA) =
		switch($x)
			case h0: hs0
			case h1: hs1
			case h2: hs2
		endswitch

	agent MainCHMgA: r_MainCH[]
	agent IntTempMgA: r_IntTemp[]
	agent HeaterMdA: r_Heater[]
