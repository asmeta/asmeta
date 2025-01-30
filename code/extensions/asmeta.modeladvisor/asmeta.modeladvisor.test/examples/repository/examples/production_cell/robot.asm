//DANIELA CUGLIETTA
//Esercizio pag. 188

asm robot

import ../../STDL/StandardLibrary
import TRANSPORTBELT (tableloaded)
export pressloaded
//declare universes and functions

	
signature:
	enum domain Phaserobot = {WAITINGINLOADDEPBELTPOS | WAITINGINUNLOADPRESSPOS | 			
	WAITINGINUNLOADTABLEPOS | WAITINGINLOADPRESSPOS |UNLOADINGTABLE|UNLOADINGPRESS| 	LOADINGDEPBELT| LOADINGPRESS| MOVINGTOUNLOADPRESSPOS|MOVINGTOLOADDEPBELTPOS| 	MOVINGTOLOADPRESSPOS| MOVINGTOUNLOADTABLEPOS| STOPPEDIULOADPOS| OPENFLOAD | OPENFUNLOAD}
	abstract domain Robot
	dynamic controlled currphaser: Robot-> Phaserobot
	dynamic monitored unloadtablecomp: Boolean
	dynamic monitored unloadpresscomp: Boolean
	dynamic monitored loaddepbeltcomp: Boolean
	dynamic monitored loadpresscomp: Boolean
	dynamic monitored unloadpressposreached: Boolean
	dynamic monitored loaddepbeltposreached: Boolean
	dynamic monitored loadpressposreached: Boolean
	dynamic monitored unloadtableposreached: Boolean
	derived tablereadyforunloading: Robot -> Boolean
	derived pressreadyforloading: Robot ->Boolean
	derived pressreadyforunloading: Robot ->Boolean
	derived pressinloadposition: Robot ->Boolean
	derived pressinunloadposition: Robot -> Boolean
	derived tableinunloadposition: Robot-> Boolean
	dynamic controlled depositbeltreadyforloading: Robot-> Boolean
	//function for Agent "ROBOT", "PRESS"
	dynamic shared pressloaded: Boolean
	
definitions:

//function tableinunloadposition
	
	function tableinunloadposition ($r in Robot)=
			currphaser ($r) = STOPPEDIULOADPOS


//function tablereadyforunloading

	function tablereadyforunloading ($r in Robot)=
			tableinunloadposition($r) = true and tableloaded = true
		
//function pressreadyforunloading

	function pressreadyforunloading ($r in Robot)=
			pressinloadposition($r) = true and pressloaded = true

//function pressinunloadposition

	function pressinunloadposition ($r in Robot)=
			currphaser($r)=OPENFUNLOAD

//function pressreadyforloading

	function pressreadyforloading ($r in Robot)=
 			pressinloadposition($r)= true and pressloaded = false

//function pressinloadposition

	function pressinloadposition ($r in Robot)= 
			currphaser ($r)= OPENFLOAD

//Rules that define the waiting

	rule r_Waiting1 ($r in Robot)=
		if currphaser ($r)= WAITINGINUNLOADTABLEPOS and 
			tablereadyforunloading ($r)= true then
			currphaser ($r) := UNLOADINGTABLE
		endif

	rule r_Waiting2 ($r in Robot)=
		if currphaser ($r)=WAITINGINUNLOADPRESSPOS and 
			pressreadyforunloading ($r)=true then
			currphaser ($r):= UNLOADINGPRESS
		endif

	rule r_Waiting3 ($r in Robot)=
		if currphaser ($r)=WAITINGINLOADDEPBELTPOS and 
			depositbeltreadyforloading ($r)=true then
			currphaser ($r):= LOADINGDEPBELT
		endif
	
	rule r_Waiting4 ($r in Robot)=
		if currphaser ($r)=WAITINGINLOADPRESSPOS and pressreadyforloading ($r)=true then
			currphaser ($r):= LOADINGPRESS
		endif

//Rules that defines the action

	rule r_Action1 ($r in Robot)=
		if currphaser ($r) = UNLOADINGTABLE and unloadtablecomp = true then
			seq
				currphaser ($r):= MOVINGTOUNLOADPRESSPOS
				tableloaded := false
			endseq
		endif

	rule r_Action2 ($r in Robot)=
		if currphaser ($r)= UNLOADINGPRESS and unloadpresscomp =true then
			seq
				currphaser ($r):= MOVINGTOLOADDEPBELTPOS
				pressloaded := false
			endseq
		endif

	rule r_Action3 ($r in Robot)=
		if currphaser ($r)=LOADINGDEPBELT and loaddepbeltcomp =true then
			seq
				currphaser($r):= MOVINGTOLOADPRESSPOS
				depositbeltreadyforloading($r) := false
			endseq
		endif

	rule r_Action4 ($r in Robot)=
		if currphaser ($r)= LOADINGPRESS and loadpresscomp=true then
			seq
				currphaser($r):= MOVINGTOUNLOADTABLEPOS
				pressloaded := true
			endseq
		endif

//Rules that defines the Moving

	rule r_Moving1 ($r in Robot)=
		if currphaser ($r)= MOVINGTOUNLOADPRESSPOS and unloadpressposreached = true then
			currphaser ($r):= WAITINGINUNLOADPRESSPOS
		endif

	rule r_Moving2 ($r in Robot)=
		if currphaser ($r)= MOVINGTOLOADDEPBELTPOS and loaddepbeltposreached = true then
			currphaser ($r):= WAITINGINLOADDEPBELTPOS
		endif

	rule r_Moving3 ($r in Robot)=
		if currphaser ($r)= MOVINGTOLOADPRESSPOS and loadpressposreached = true then
			currphaser ($r):= WAITINGINLOADPRESSPOS
		endif

	rule r_Moving4 ($r in Robot)=
		if currphaser ($r)= MOVINGTOUNLOADTABLEPOS and unloadtableposreached = true then
			currphaser ($r):= WAITINGINUNLOADTABLEPOS
		endif


//main rule

	main rule r_Ground_Modell=
	forall $r in Robot with true do
			seq
				r_Waiting1 [$r]
				r_Waiting2 [$r]
				r_Waiting3 [$r]
				r_Waiting4 [$r]
				r_Action1 [$r]
				r_Action2[$r]
				r_Action3 [$r]
				r_Action4 [$r]
				r_Moving1 [$r]
				r_Moving2 [$r]
				r_Moving3 [$r]
				r_Moving4 [$r]
			endseq
			
	default init initial_state:

	//function for Agent "ROBOT"
	function depositbeltreadyforloading($r in Robot) = true
			