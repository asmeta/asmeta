//DANIELA CUGLIETTA
//Esercizio pag. 188

asm ert

import ../../STDL/StandardLibrary
import TRANSPORTBELT  (tableloaded)

//declare universes and functions

	
signature:
	enum domain Phasert = {STOPPEDINLOADPOS| STOPPEDINUNLOADPOS | 	MOVINGTOLOADPOS | 			MOVINGTOUNLOADPOS}
	abstract domain Ert
	dynamic monitored loadpositionreached: Boolean
	dynamic monitored unloadpositionreached: Boolean
	dynamic controlled currphasee: Ert -> Phasert

definitions:

//Rule that defines the WaitingLoad

	rule r_WaitingLoad ($e in Ert)=
		if currphasee ($e)= STOPPEDINLOADPOS and tableloaded = true then
			currphasee ($e):= MOVINGTOUNLOADPOS
		endif

//RUle that defines the MovingUnload
	
	rule r_MovingUnload ($e in Ert)=
		if currphasee ($e)= MOVINGTOUNLOADPOS and unloadpositionreached = true then
			currphasee ($e):= STOPPEDINUNLOADPOS
		endif

//Rule that defines the MovingLoad

	rule r_MovingLoad ($e in Ert)=
		if currphasee ($e)= MOVINGTOLOADPOS and loadpositionreached = true then
			currphasee ($e):= STOPPEDINLOADPOS
		endif

//Rule that defines the WaitingUnload

	rule r_WaitingUnload($e in Ert)=
		if currphasee ($e)= STOPPEDINUNLOADPOS and tableloaded = false then
			currphasee ($e):= MOVINGTOLOADPOS
		endif


//Main Rule

	main rule r_Ground_Modell=
	forall $e in Ert with true do
			seq
			r_WaitingLoad [$e]
			r_MovingUnload [$e]
			r_MovingLoad [$e]
			r_WaitingUnload[$e]
			endseq
	