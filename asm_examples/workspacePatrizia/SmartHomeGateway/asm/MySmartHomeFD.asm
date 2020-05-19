asm MySmartHomeFD

import StandardLibrary

signature:
	//FD_MAPE
	domain FireDetectionMdA subsetof Agent
	domain MasterFDMgA subsetof Agent
	domain SlaveFDMgA subsetof Agent
	derived startMasterFDA: MasterFDMgA -> Boolean
	derived startMasterFDP: MasterFDMgA -> Boolean
	controlled firedetectionManagedBySlaveFD: SlaveFDMgA -> FireDetectionMdA
	derived startSlaveFDM: SlaveFDMgA -> Boolean
	derived startSlaveFDE: SlaveFDMgA -> Boolean
	//I: SlaveFD.M -> MasterFD.A [*-SOME,1]
	controlled sgnSlaveFDMMasterFDA: Prod(SlaveFDMgA, MasterFDMgA) -> Boolean
	controlled fromSlaveFDMtoMasterFDA: SlaveFDMgA -> MasterFDMgA
	controlled fromMasterFDAtoSlaveFDM: MasterFDMgA -> Powerset(SlaveFDMgA)
	//I: MasterFD.P -> SlaveFD.E [1,*-SOME]
	controlled sgnMasterFDPSlaveFDE: Prod(MasterFDMgA, SlaveFDMgA) -> Boolean
	controlled fromMasterFDPtoSlaveFDE: MasterFDMgA -> Powerset(SlaveFDMgA)
	derived orSelectorfromMasterFDPtoSlaveFDE: MasterFDMgA -> Powerset(SlaveFDMgA)
	controlled fromSlaveFDEtoMasterFDP: SlaveFDMgA -> MasterFDMgA
	//MySmartHomeFD
	static fd_ff: FireDetectionMdA
	static fd_gf: FireDetectionMdA
	static fd_master: MasterFDMgA
	static gf_slave: SlaveFDMgA
	static ff_slave: SlaveFDMgA

definitions:
	function startMasterFDA($b in MasterFDMgA) =
		(exist $a in fromMasterFDAtoSlaveFDM($b) with sgnSlaveFDMMasterFDA($a, $b))

	function startMasterFDP($b in MasterFDMgA) =
		true

	function startSlaveFDM($b in SlaveFDMgA) =
		true

	function startSlaveFDE($b in SlaveFDMgA) =
		sgnMasterFDPSlaveFDE(fromSlaveFDEtoMasterFDP($b), $b)

	function orSelectorfromMasterFDPtoSlaveFDE($b in MasterFDMgA) =
		chooseone({$a in Powerset(SlaveFDMgA) | not(isEmpty($a)): $a})

	rule r_FireDetection =
		skip //<<TODO>>

	rule r_CleanUp_MasterFDP =
		skip //<<TODO>>

	rule r_MasterFDP =
		if startMasterFDP(self) then
			par
				skip //<<TODO>>
				forall $a in orSelectorfromMasterFDPtoSlaveFDE(self) do sgnMasterFDPSlaveFDE(self, $a) := true
				r_CleanUp_MasterFDP[]
			endpar
		endif

	rule r_CleanUp_MasterFDA =
		forall $a in fromMasterFDAtoSlaveFDM(self) do sgnSlaveFDMMasterFDA($a, self) := false

	rule r_MasterFDA =
		if startMasterFDA(self) then
			par
				skip //<<TODO>>
				r_MasterFDP[]
				r_CleanUp_MasterFDA[]
			endpar
		endif

	rule r_MasterFD =
		r_MasterFDA[]

	rule r_CleanUp_SlaveFDM =
		skip //<<TODO>>

	rule r_SlaveFDM =
		if startSlaveFDM(self) then
			par
				skip //<<TODO>>
				if not sgnSlaveFDMMasterFDA(self,fromSlaveFDMtoMasterFDA(self)) then sgnSlaveFDMMasterFDA(self,fromSlaveFDMtoMasterFDA(self)) := true endif
				r_CleanUp_SlaveFDM[]
			endpar
		endif

	rule r_CleanUp_SlaveFDE =
		sgnMasterFDPSlaveFDE(fromSlaveFDEtoMasterFDP(self), self) := false

	rule r_SlaveFDE =
		if startSlaveFDE(self) then
			par
				skip //<<TODO>>
				r_CleanUp_SlaveFDE[]
			endpar
		endif

	rule r_SlaveFD =
		par
			r_SlaveFDM[]
			r_SlaveFDE[]
		endpar

	main rule r_mainRule =
		forall $a in Agent with true do
			program($a)

default init s0:
	function sgnSlaveFDMMasterFDA($a in SlaveFDMgA, $b in MasterFDMgA) = false
	function sgnMasterFDPSlaveFDE($a in MasterFDMgA, $b in SlaveFDMgA) = false
	function fromSlaveFDMtoMasterFDA($a in SlaveFDMgA) =
		switch($a)
			case gf_slave: fd_master
			case ff_slave: fd_master
		endswitch

	function fromMasterFDAtoSlaveFDM($a in MasterFDMgA) =
		switch($a)
			case fd_master: {gf_slave, ff_slave}
		endswitch

	function fromMasterFDPtoSlaveFDE($a in MasterFDMgA) =
		switch($a)
			case fd_master: {gf_slave, ff_slave}
		endswitch

	function fromSlaveFDEtoMasterFDP($a in SlaveFDMgA) =
		switch($a)
			case gf_slave: fd_master
			case ff_slave: fd_master
		endswitch

	function firedetectionManagedBySlaveFD($x in SlaveFDMgA) =
		switch($x)
			case gf_slave: fd_gf
			case ff_slave: fd_ff
		endswitch


	agent FireDetectionMdA: r_FireDetection[]
	agent MasterFDMgA: r_MasterFD[]
	agent SlaveFDMgA: r_SlaveFD[]
