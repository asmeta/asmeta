//DANIELA CUGLIETTA, PATRIZIA SCANDURRA, ANGELO
//EXERCISE pag. 188			SU UNICO FILE
//descritta nell'articolo "Integrating ASMs into the Software Development Life Cycle" di Borger-Mearelli
//senza il crane
//senza agenti

asm Production_Cell_no_agents

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
	
	//functions for "FEED BELT"
	dynamic controlled currphasef: Phasefeed 
	dynamic monitored piecefeedbeltlightbarrier : Boolean
	
	//functions for "ERT"
	dynamic controlled currphasee: Phasert
	derived tablereadyforloading: Boolean
	derived tableinloadposition: Boolean
	dynamic monitored loadpositionreachedERT_FB: Boolean
	dynamic monitored unloadpositionreachedERT_ROBOT: Boolean

	//fuctions for "ROBOT"	
	dynamic controlled currphaser: Phaserobot
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
	dynamic controlled currphasep: Phasepress
	dynamic monitored middlepositionPRESS: Boolean
	dynamic monitored bottompositionPRESS: Boolean
	dynamic monitored toppositionPRESS: Boolean
	dynamic monitored forgincompletedPRESS: Boolean

	//functions for "DEPOSIT"
	dynamic controlled currphased: Phasedeposit
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

	//function for "FEED BELT"
	dynamic controlled feedbeltfree: Boolean


definitions:

//function for "FEED BELT"

	//function tablereadyforloading
	function tablereadyforloading =
			tableinloadposition = true and not(tableloaded)

	//function tableinloadposition
	function tableinloadposition  =
		currphasee  = STOPPEDINLOADPOS

//functions for "ROBOT"

	//function tableinunloadposition
	function tableinunloadposition =
		currphasee = STOPPEDINUNLOADPOS

	//function tablereadyforunloading
	function tablereadyforunloading =
		tableinunloadposition = true and tableloaded = true
			
	//function pressreadyforunloading
	function pressreadyforunloading =
		pressinunloadposition = true and pressloaded = true

	//function pressinunloadposition
	function pressinunloadposition =
		currphasep = OPENFORUNLOAD

	//function pressreadyforloading
	function pressreadyforloading =
 		pressinloadposition = true and not(pressloaded)

	//function pressinloadposition
	function pressinloadposition = 
		currphasep = OPENFORLOAD


//Rules for FEED BELT

	//Rule that defines the NormalRun
	rule r_FbNormal =
		if currphasef = NORMALRUN then
			if piecefeedbeltlightbarrier = true then
				par
					feedbeltfree:=true
					if tablereadyforloading = true then
			 			currphasef:=CRITICALRUN 
					else
						currphasef:=STOPPED
					endif
				endpar
			endif
		endif
			
			
	//Rule that defines the Stopped
	rule r_FbStopped =
		if currphasef = STOPPED and tablereadyforloading = true then
			  currphasef :=CRITICALRUN
		endif

	//Rule that defines the CriticalRun
	rule r_FbCritical =
		if currphasef = CRITICALRUN then
			if not(piecefeedbeltlightbarrier) then
				par
				  currphasef :=NORMALRUN
				  tableloaded := true
				endpar
			endif
		endif

//Rules for "ERT"

	//Rule that defines the WaitingLoad
	rule r_WaitingLoad =
		if currphasee = STOPPEDINLOADPOS and tableloaded = true then
			currphasee := MOVINGTOUNLOADPOS
		endif

	//Rule that defines the MovingUnload
	rule r_MovingUnload =
		if currphasee= MOVINGTOUNLOADPOS then
			if unloadpositionreachedERT_ROBOT = true then
				currphasee := STOPPEDINUNLOADPOS
			endif
		endif

	//Rule that defines the MovingLoad
	rule r_MovingLoad =
		if currphasee= MOVINGTOLOADPOS then
			if loadpositionreachedERT_FB = true then
				currphasee := STOPPEDINLOADPOS
			endif
		endif

	//Rule that defines the WaitingUnload
	rule r_WaitingUnload=
		if currphasee = STOPPEDINUNLOADPOS and tableloaded = false then
			currphasee := MOVINGTOLOADPOS
		endif

//Rules for "ROBOT"

	//Rules that define the waiting

	rule r_Waiting1 =
		if currphaser = WAITINGINUNLOADTABLEPOS and tablereadyforunloading = true then
			currphaser := UNLOADINGTABLE
		endif

	rule r_Waiting2 =
		if currphaser =WAITINGINUNLOADPRESSPOS and pressreadyforunloading = true then
			currphaser := UNLOADINGPRESS
		endif

	rule r_Waiting3 =
		if currphaser = WAITINGINLOADDEPBELTPOS and depositbeltreadyforloading = true then
			currphaser := LOADINGDEPBELT
		endif
	
	rule r_Waiting4 =
		if currphaser = WAITINGINLOADPRESSPOS and pressreadyforloading = true then
			currphaser := LOADINGPRESS
		endif

	//Rules that defines the action

	rule r_Action1 =
		if currphaser = UNLOADINGTABLE then
			if unloadtablecompERT_ROBOT = true then
				par
					currphaser := MOVINGTOUNLOADPRESSPOS
					tableloaded := false
				endpar
			endif
		endif

	rule r_Action2 =
		if currphaser = UNLOADINGPRESS then
			if unloadpresscompPRESS_ROBOT = true then
				par
					currphaser := MOVINGTOLOADDEPBELTPOS
					pressloaded := false
				endpar
			endif
		endif

	rule r_Action3 =
		if currphaser = LOADINGDEPBELT then
			if loaddepbeltcompROBOT_DB = true then
				par
					currphaser := MOVINGTOLOADPRESSPOS
					depositbeltreadyforloading := false
				endpar
			endif
		endif

	rule r_Action4 =
		if currphaser = LOADINGPRESS then
			if loadpresscompROBOT_PRESS = true then
				par
					currphaser := MOVINGTOUNLOADTABLEPOS
					pressloaded := true
				endpar
			endif
		endif

	//Rules that defines the Moving

	rule r_Moving1 =
		if currphaser = MOVINGTOUNLOADPRESSPOS then
			if unloadpressposreachedROBOT_PRESS = true then
				currphaser := WAITINGINUNLOADPRESSPOS
			endif
		endif

	rule r_Moving2 =
		if currphaser = MOVINGTOLOADDEPBELTPOS then
			if loaddepbeltposreachedROBOT_DB = true then
				currphaser := WAITINGINLOADDEPBELTPOS
			endif
		endif

	rule r_Moving3 =
		if currphaser = MOVINGTOLOADPRESSPOS then
			if loadpressposreachedROBOT_PRESS = true then
				currphaser := WAITINGINLOADPRESSPOS
			endif
		endif

	rule r_Moving4 =
		if currphaser = MOVINGTOUNLOADTABLEPOS then
			if unloadtableposreachedROBOT_ERT = true then
				currphaser := WAITINGINUNLOADTABLEPOS
			endif
		endif

//Rules for "PRESS"

	//Rule that defines the Waiting_Unload
	rule r_Waiting_Unload =
		if currphasep = OPENFORUNLOAD and pressloaded = false then
			currphasep := MOVINGTOMIDDLEPOSITION
		endif

	//Rule that defines the Moving to Middle
	rule r_Moving_To_Middle =
		if currphasep = MOVINGTOMIDDLEPOSITION then
			if middlepositionPRESS= true then
				currphasep := OPENFORLOAD
			endif
		endif

	//Rule that defines the Waiting_Load
	rule r_Waiting_Load =
		if currphasep = OPENFORLOAD and pressloaded = true then
			currphasep := MOVINGTOTOPPOSITION
		endif

	//Rule that defines the Moving_To_Upper
	rule r_Moving_To_Upper =
		if currphasep = MOVINGTOTOPPOSITION then
			if toppositionPRESS = true then
				currphasep := CLOSEDFORFORGIN
			endif
		endif

	//Rule that defines the Closed
	rule r_Closed =
		if currphasep = CLOSEDFORFORGIN then
			if forgincompletedPRESS = true then
				currphasep := MOVINGTOBOTTOMPOSITION
			endif
		endif

	//Rule that definse the Moving_To_Lower
	rule r_Moving_To_Lower =
		if currphasep = MOVINGTOBOTTOMPOSITION then
			if bottompositionPRESS = true then
				currphasep := OPENFORUNLOAD
			endif
		endif

//Rules for "DEPOSIT"

	//Rule that defines the Db_Normal
	rule r_Db_Normal =
		if currphased = NORMAL then
			if pieceindepositbeltlightbarrierDB = true then
				currphased := CRITICAL
			endif
		endif

	//Rule that defines the Db_Critical
	rule r_Db_Critical =
		if currphased = CRITICAL then
			if not(pieceindepositbeltlightbarrierDB) then
				par
					currphased := STOP
					depositbeltreadyforloading := true
					pieceatdepositbeltend := true
				endpar
			endif
		endif

	//Rule that defines the Db_Stopped 
	/*rule r_Db_Stopped =
		if currphased = STOP and pieceatdepositbeltend = false then
			currphased := NORMAL
		endif*/
		
	//mia per eliminare il crane
	rule r_Db_Stopped =
		if currphased = STOP then
			if pieceatdepositbeltendremovedDB then
				par
					currphased := NORMAL
					pieceatdepositbeltend := false
				endpar
			endif
		endif

	macro rule r_Feed =
		seq
			r_FbNormal []
			r_FbStopped [] 
			r_FbCritical []
		endseq
		
	macro rule r_Ert =
		seq
			r_WaitingLoad []
			r_MovingUnload []
			r_MovingLoad []
			r_WaitingUnload []
		endseq
		
	macro rule r_Deposit =
		seq
			r_Db_Normal []
			r_Db_Critical []
			r_Db_Stopped []
		endseq 
	
	
	macro rule r_Press =
		seq
			r_Waiting_Unload [] 
			r_Moving_To_Middle []
			r_Waiting_Load []
			r_Moving_To_Upper []
			r_Closed []
			r_Moving_To_Lower [] 
		endseq
		
	macro rule r_Robot =
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


// run all

	main rule r_prod_cell = 
		//par
		seq
			r_Feed[]
			r_Ert[]
			r_Robot[]
			r_Press[]
			r_Deposit[]
		endseq
		//endpar 


//define the initial states 

default init initial_state:
	
	//function for "FEED BELT"
	function currphasef =  NORMALRUN
	function piecefeedbeltlightbarrier = false
	function feedbeltfree = true
	
	function currphasee  = STOPPEDINLOADPOS
	function tableloaded = false
	
	//function for "ROBOT", "DEPOSIT"
	function currphaser = WAITINGINUNLOADTABLEPOS
	
	function currphasep = OPENFORUNLOAD
	function pressloaded = true
	
	function currphased = NORMAL
	function depositbeltreadyforloading = true
	
