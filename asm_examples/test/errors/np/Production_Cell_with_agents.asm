//DANIELA CUGLIETTA, PATRIZIA SCANDURRA, ANGELO
//EXERCISE pag. 188			SU UNICO FILE
//descritta nell'articolo "Integrating ASMs into the Software Development Life Cycle" di Borger-Mearelli
//senza il crane
//con agenti

asm Production_Cell_with_agents

import ../../../../../asm_examples/STDL/StandardLibrary

//declare universes and functions
	
signature:
    enum domain Phasefeed = {NORMALRUN | CRITICALRUN | STOPPED | STOPPEDINLOADPOS}  
	enum domain Phasert = {STOPPEDINLOADPOS| STOPPEDINUNLOADPOS | MOVINGTOLOADPOS |MOVINGTOUNLOADPOS}
	enum domain Phaserobot = {WAITINGINLOADDEPBELTPOS | WAITINGINUNLOADPRESSPOS |
								WAITINGINUNLOADTABLEPOS | WAITINGINLOADPRESSPOS |UNLOADINGTABLE|
								UNLOADINGPRESS|LOADINGDEPBELT| LOADINGPRESS| MOVINGTOUNLOADPRESSPOS|
								MOVINGTOLOADDEPBELTPOS| MOVINGTOLOADPRESSPOS| MOVINGTOUNLOADTABLEPOS}
	enum domain Phasepress= {OPENFORLOAD | OPENFORUNLOAD | MOVINGTOMIDDLEPOSITION |MOVINGTOTOPPOSITION |
								MOVINGTOBOTTOMPOSITION| CLOSEDFORFORGIN}
	enum domain Phasedeposit= {NORMAL| CRITICAL | STOP}
	enum domain Phasecrane = {WAITINGTOUNLOADDEPOSITBELT|UNLOADINGDEPOSITBELT| MOVINGTOLOADFEEDBELTPOS| 
								WAITINGLOADFEEDBELT| LOADINGFEEDBELT |MOVINGTOUNLOADDEPOSITBELTPOS}
	
	
	
	domain FEEDBELTAgent subsetof Agent
	domain ERTAgent subsetof Agent
	domain ROBOTAgent subsetof Agent
	domain PRESSAgent subsetof Agent 
	domain DEPOSITBELTAgent subsetof Agent
	
	//functions for "FEED BELT"
	dynamic controlled currphasef: FEEDBELTAgent -> Phasefeed 
	dynamic monitored piecefeedbeltlightbarrier : Boolean
	
	//functions for "ERT"
	dynamic controlled currphasee: ERTAgent -> Phasert
	derived tablereadyforloading: Boolean
	derived tableinloadposition: Boolean
	dynamic monitored loadpositionreachedERT_FB: Boolean
	dynamic monitored unloadpositionreachedERT_ROBOT: Boolean

	//fuctions for Agent "ROBOT"	
	dynamic controlled currphaser: ROBOTAgent -> Phaserobot
	dynamic monitored unloadtablecompERT_ROBOT: Boolean
	dynamic monitored unloadpresscompPRESS_ROBOT: Boolean
	dynamic monitored loaddepbeltcompROBOT_DB: Boolean
	dynamic monitored loadpresscompROBOT_PRESS: Boolean
	dynamic monitored unloadpressposreachedROBOT_PRESS: Boolean
	dynamic monitored loaddepbeltposreachedROBOT_DB: Boolean
	dynamic monitored loadpressposreachedROBOT_PRESS: Boolean
	dynamic monitored unloadtableposreachedROBOT_ERT: Boolean
	derived tablereadyforunloading: Boolean
	derived pressreadyforloading: Boolean
	derived pressreadyforunloading: Boolean
	derived pressinloadposition: Boolean
	derived pressinunloadposition: Boolean
	derived tableinunloadposition: Boolean

	//functions for "PRESS"	
	dynamic controlled currphasep: PRESSAgent -> Phasepress
	dynamic monitored middlepositionPRESS: Boolean
	dynamic monitored bottompositionPRESS: Boolean
	dynamic monitored toppositionPRESS: Boolean
	dynamic monitored forgincompletedPRESS: Boolean

	//functions for "DEPOSIT"
	dynamic controlled currphased: DEPOSITBELTAgent -> Phasedeposit
	dynamic monitored pieceindepositbeltlightbarrierDB: Boolean
	dynamic monitored pieceatdepositbeltendremovedDB: Boolean

	//function for "ROBOT", "TRANSPORT", "ERT"
	dynamic controlled tableloaded: Boolean

	//function for "ROBOT", "PRESS"
	dynamic controlled pressloaded: Boolean

	//function for "ROBOT", "DEPOSIT"
	dynamic controlled depositbeltreadyforloading: Boolean

	//function for "DEPOSIT"
	dynamic controlled pieceatdepositbeltend: Boolean

	//function for "TRANSPORT"
	dynamic controlled feedbeltfree: Boolean
	
	static feedbelt: FEEDBELTAgent
	static ert: ERTAgent
	static robot: ROBOTAgent
	static press: PRESSAgent
	static depositbelt: DEPOSITBELTAgent

definitions:

//function for "FEED BELT"

	//function tablereadyforloading
	function tablereadyforloading =
			tableinloadposition = true and not(tableloaded)

	//function tableinloadposition
	function tableinloadposition  =
		currphasee(ert)  = STOPPEDINLOADPOS

//functions for "ROBOT"

	//function tableinunloadposition
	function tableinunloadposition =
		currphasee(ert) = STOPPEDINUNLOADPOS
		

	//function tablereadyforunloading
	function tablereadyforunloading =
		tableinunloadposition = true and tableloaded = true
			
	//function pressreadyforunloading
	function pressreadyforunloading =
		pressinunloadposition = true and pressloaded = true

	//function pressinunloadposition
	function pressinunloadposition =
		currphasep(press) = OPENFORUNLOAD

	//function pressreadyforloading
	function pressreadyforloading =
 		pressinloadposition = true and not(pressloaded)

	//function pressinloadposition
	function pressinloadposition = 
		currphasep(press) = OPENFORLOAD


//Rules for FEED BELT

	//Rule that defines the NormalRun
	rule r_FbNormal =
		if currphasef(self) = NORMALRUN then
			if piecefeedbeltlightbarrier = true then
				par
					feedbeltfree:=true
					if tablereadyforloading = true then
			 			currphasef(self) := CRITICALRUN 
					else
						currphasef(self) := STOPPED
					endif
				endpar
			endif
		endif
			
			
	//Rule that defines the Stopped
	rule r_FbStopped =
		if currphasef(self) = STOPPED and tablereadyforloading = true then
			  currphasef(self) :=CRITICALRUN
		endif

	//Rule that defines the CriticalRun
	rule r_FbCritical =
		if currphasef(self) = CRITICALRUN then
			if not(piecefeedbeltlightbarrier) then
				par
				  currphasef(self) :=NORMALRUN
				  tableloaded := true
				endpar
			endif
		endif

//Rules for "ERT"

	//Rule that defines the WaitingLoad
	rule r_WaitingLoad =
		if currphasee(self) = STOPPEDINLOADPOS and tableloaded = true then
			currphasee(self) := MOVINGTOUNLOADPOS
		endif

	//Rule that defines the MovingUnload
	rule r_MovingUnload =
		if currphasee(self) = MOVINGTOUNLOADPOS then
			if unloadpositionreachedERT_ROBOT = true then
				currphasee(self) := STOPPEDINUNLOADPOS
			endif
		endif

	//Rule that defines the MovingLoad
	rule r_MovingLoad =
		if currphasee(self) = MOVINGTOLOADPOS then
			if loadpositionreachedERT_FB = true then
				currphasee(self) := STOPPEDINLOADPOS
			endif
		endif

	//Rule that defines the WaitingUnload
	rule r_WaitingUnload=
		if currphasee(self) = STOPPEDINUNLOADPOS and tableloaded = false then
			currphasee(self) := MOVINGTOLOADPOS
		endif

//Rules for "ROBOT"

	//Rules that define the waiting

	rule r_Waiting1 =
		if currphaser(self) = WAITINGINUNLOADTABLEPOS and tablereadyforunloading = true then
			currphaser(self) := UNLOADINGTABLE
		endif

	rule r_Waiting2 =
		if currphaser(self) = WAITINGINUNLOADPRESSPOS and pressreadyforunloading = true then
			currphaser(self) := UNLOADINGPRESS
		endif

	rule r_Waiting3 =
		if currphaser(self) = WAITINGINLOADDEPBELTPOS and depositbeltreadyforloading = true then
			currphaser(self) := LOADINGDEPBELT
		endif
	
	rule r_Waiting4 =
		if currphaser(self) = WAITINGINLOADPRESSPOS and pressreadyforloading = true then
			currphaser(self) := LOADINGPRESS
		endif

	//Rules that defines the action

	rule r_Action1 =
		if currphaser(self) = UNLOADINGTABLE then
			if unloadtablecompERT_ROBOT = true then
				par
					currphaser(self) := MOVINGTOUNLOADPRESSPOS
					tableloaded := false
				endpar
			endif
		endif

	rule r_Action2 =
		if currphaser(self) = UNLOADINGPRESS then
			if unloadpresscompPRESS_ROBOT = true then
				par
					currphaser(self) := MOVINGTOLOADDEPBELTPOS
					pressloaded := false
				endpar
			endif
		endif

	rule r_Action3 =
		if currphaser(self) = LOADINGDEPBELT then
			if loaddepbeltcompROBOT_DB = true then
				par
					currphaser(self) := MOVINGTOLOADPRESSPOS
					depositbeltreadyforloading := false
				endpar
			endif
		endif

	rule r_Action4 =
		if currphaser(self) = LOADINGPRESS then
			if loadpresscompROBOT_PRESS = true then
				par
					currphaser(self) := MOVINGTOUNLOADTABLEPOS
					pressloaded := true
				endpar
			endif
		endif

	//Rules that defines the Moving

	rule r_Moving1 =
		if currphaser(self) = MOVINGTOUNLOADPRESSPOS then
			if unloadpressposreachedROBOT_PRESS = true then
				currphaser(self) := WAITINGINUNLOADPRESSPOS
			endif
		endif

	rule r_Moving2 =
		if currphaser(self) = MOVINGTOLOADDEPBELTPOS then
			if loaddepbeltposreachedROBOT_DB = true then
				currphaser(self) := WAITINGINLOADDEPBELTPOS
			endif
		endif

	rule r_Moving3 =
		if currphaser(self) = MOVINGTOLOADPRESSPOS then
			if loadpressposreachedROBOT_PRESS = true then
				currphaser(self) := WAITINGINLOADPRESSPOS
			endif
		endif

	rule r_Moving4 =
		if currphaser(self) = MOVINGTOUNLOADTABLEPOS then
			if unloadtableposreachedROBOT_ERT = true then
				currphaser(self) := WAITINGINUNLOADTABLEPOS
			endif
		endif

//Rules for "PRESS"

	//Rule that defines the Waiting_Unload
	rule r_Waiting_Unload =
		if currphasep(self) = OPENFORUNLOAD and pressloaded = false then
			currphasep(self) := MOVINGTOMIDDLEPOSITION
		endif

	//Rule that defines the Moving to Middle
	rule r_Moving_To_Middle =
		if currphasep(self) = MOVINGTOMIDDLEPOSITION then
			if middlepositionPRESS= true then
				currphasep(self) := OPENFORLOAD
			endif
		endif

	//Rule that defines the Waiting_Load
	rule r_Waiting_Load =
		if currphasep(self) = OPENFORLOAD and pressloaded = true then
			currphasep(self) := MOVINGTOTOPPOSITION
		endif

	//Rule that defines the Moving_To_Upper
	rule r_Moving_To_Upper =
		if currphasep(self) = MOVINGTOTOPPOSITION then
			if toppositionPRESS = true then
				currphasep(self) := CLOSEDFORFORGIN
			endif
		endif

	//Rule that defines the Closed
	rule r_Closed =
		if currphasep(self) = CLOSEDFORFORGIN then
			if forgincompletedPRESS = true then
				currphasep(self) := MOVINGTOBOTTOMPOSITION
			endif
		endif

	//Rule that definse the Moving_To_Lower
	rule r_Moving_To_Lower =
		if currphasep(self) = MOVINGTOBOTTOMPOSITION then
			if bottompositionPRESS = true then
				currphasep(self) := OPENFORUNLOAD
			endif
		endif

//Rules for "DEPOSIT"

	//Rule that defines the Db_Normal
	rule r_Db_Normal =
		if currphased(self) = NORMAL then
			if pieceindepositbeltlightbarrierDB = true then
				currphased(self) := CRITICAL
			endif
		endif

	//Rule that defines the Db_Critical
	rule r_Db_Critical =
		if currphased(self) = CRITICAL then
			if not(pieceindepositbeltlightbarrierDB) then
				par
					currphased(self) := STOP
					depositbeltreadyforloading := true
					pieceatdepositbeltend := true
				endpar
			endif
		endif

	//Rule that defines the Db_Stopped 
	/*rule r_Db_Stopped =
		if currphased(self) = STOP and pieceatdepositbeltend = false then
			currphased(self) := NORMAL
		endif*/
		
	//mia per eliminare il crane
	rule r_Db_Stopped =
		if currphased(self) = STOP then
			if pieceatdepositbeltendremovedDB then
				par
					currphased(self) := NORMAL
					pieceatdepositbeltend := false
				endpar
			endif
		endif

	rule r_FEEDBELTAgent =
		seq
			r_FbNormal []
			r_FbStopped [] 
			r_FbCritical []
		endseq

	rule r_ERTAgent =
		seq
			r_WaitingLoad []
			r_MovingUnload []
			r_MovingLoad []
			r_WaitingUnload []
		endseq

	rule r_ROBOTAgent =
		seq
			r_Waiting1 []
			r_Waiting2 []
			r_Waiting3 []
			r_Waiting4 []
			r_Action1  []
			r_Action2  []
			r_Action3  []
			r_Action4  []
			r_Moving1  []
			r_Moving2  []
			r_Moving3  []
			r_Moving4  []
		endseq

	rule r_PRESSAgent =
		seq
			r_Waiting_Unload [] 
			r_Moving_To_Middle []
			r_Waiting_Load []
			r_Moving_To_Upper []
			r_Closed []
			r_Moving_To_Lower [] 
		endseq

	rule r_DEPOSITBELTAgent =
		seq
			r_Db_Normal []
			r_Db_Critical []
			r_Db_Stopped []
		endseq


// run all

	main rule r_prod_cell = 
	//choose $f in FEEDBELTAgent, $e in ERTAgent, $r in ROBOTAgent, $p in PRESSAgent, $d in DEPOSITBELTAgent with true do
	//forall $f in FEEDBELTAgent, $e in ERTAgent, $r in ROBOTAgent, $p in PRESSAgent, $d in DEPOSITBELTAgent with true do
		//par
		seq
			program(feedbelt)
			program(ert)
			program(robot)
			program(press)
			program(depositbelt)
		endseq
		//endpar 


//define the initial states 

default init initial_state:
	
	//function for "FEED BELT"
	function currphasef($f in FEEDBELTAgent) =  NORMALRUN
	function piecefeedbeltlightbarrier = false
	function feedbeltfree = true
	
	function currphasee($e in ERTAgent)  = STOPPEDINLOADPOS
	function tableloaded = false
	
	//function for "ROBOT", "DEPOSIT"
	function currphaser($r in ROBOTAgent) = WAITINGINUNLOADTABLEPOS
	
	function currphasep($p in PRESSAgent) = OPENFORUNLOAD
	function pressloaded = true
	
	function currphased($d in DEPOSITBELTAgent) = NORMAL
	function depositbeltreadyforloading = true
	
	agent FEEDBELTAgent:
		r_FEEDBELTAgent[]
		
	agent ERTAgent:
		r_ERTAgent[]

	agent ROBOTAgent:
		r_ROBOTAgent[]

	agent PRESSAgent:
		r_PRESSAgent[]
		
	agent DEPOSITBELTAgent:
		r_DEPOSITBELTAgent[]
