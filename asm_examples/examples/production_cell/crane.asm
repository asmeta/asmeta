//DANIELA CUGLIETTA
//Esercizio pag. 188

asm crane

import ../../STDL/StandardLibrary
import DEPOSITBELT (pieceatdepositbeltend)
import TRANSPORTBELT (feedbeltfree)

//declare universes and functions

	
signature:
	enum domain Phasecrane = {WAITINGTOUNLOADDEPOSITBELT|UNLOADINGDEPOSITBELT| MOVINGTOLOADFEEDBELTPOS| WAITINGLOADFEEDBELT| LOADINGFEEDBELT |MOVINGTOUNLOADDEPOSITBELTPOS}
	abstract domain Crane	
	dynamic controlled currphasec : Crane -> Phasecrane
	dynamic monitored unloadingdepositbeltcompleted: Boolean
	dynamic monitored loadfeedbeltposreached: Boolean
	dynamic monitored loadingdepositbeltcompleted: Boolean
	dynamic monitored unloaddepositbeltposreached: Boolean
definitions:

//Rule that defines th waiting 
	
	rule r_Waiting_Db ($c in Crane)=
		if currphasec ($c)= WAITINGTOUNLOADDEPOSITBELT and 
			pieceatdepositbeltend = true then
			currphasec ($c):= UNLOADINGDEPOSITBELT
		endif

//RUle that defines the Unloading
	
	rule r_Unloading_Db ($c in Crane)=
		if currphasec ($c)= UNLOADINGDEPOSITBELT and 
			unloadingdepositbeltcompleted = true then
			seq
				currphasec ($c):= MOVINGTOLOADFEEDBELTPOS
				pieceatdepositbeltend:=false
			endseq
			endif

//Rule that defines the Moving
	
	rule r_Moving_Fb ($c in Crane)=
		if currphasec ($c)=  MOVINGTOLOADFEEDBELTPOS and loadfeedbeltposreached = true then
			currphasec ($c):= WAITINGLOADFEEDBELT
		endif

//Rule that defines the waiting (Fb)

	rule r_Waiting_Fb ($c in Crane)=
		if currphasec ($c)=WAITINGLOADFEEDBELT and feedbeltfree = true then
			currphasec ($c):= LOADINGFEEDBELT
		endif

//Rule that defines the Loading (Fb)

	rule r_Loading_Fb ($c in Crane)=
		if currphasec ($c)= LOADINGFEEDBELT and loadingdepositbeltcompleted = true then
			seq
				currphasec ($c):= MOVINGTOUNLOADDEPOSITBELTPOS
				feedbeltfree:=false
			endseq
			endif

//Rule that defines the Moving (Db)

	rule r_Moving_Db ($c in Crane)=
		if currphasec ($c)=   MOVINGTOUNLOADDEPOSITBELTPOS and 
			unloaddepositbeltposreached = true then
			currphasec ($c):= WAITINGTOUNLOADDEPOSITBELT
		endif
//Main Rule

	main rule r_Ground_Modell=
		forall $c in Crane with true do
		seq
				r_Waiting_Db [$c]
				r_Unloading_Db [$c]
				r_Moving_Fb [$c]
				r_Waiting_Fb [$c]
				r_Loading_Fb [$c]
				r_Moving_Db [$c]
		endseq
			