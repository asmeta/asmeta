//DANIELA CUGLIETTA
//Esercizio pag. 188

asm DEPOSITBELT

import ../../STDL/StandardLibrary
export pieceatdepositbeltend

//declare universes and functions

	
signature:
	enum domain Phasedeposit= {NORMAL| CRITICAL | STOP}	
	abstract domain Deposit
	dynamic controlled currphased : Deposit -> Phasedeposit
	dynamic monitored pieceindepositbeltlightbarrier: Boolean
	dynamic controlled pieceatdepositbeltend: Boolean
	dynamic controlled depositbeltreadyforload: Deposit -> Boolean


definitions:

	//Rule that defines the Db_Normal

	rule r_Db_Normal ($d in Deposit)=
		if currphased ($d)= NORMAL and pieceindepositbeltlightbarrier =true then
			currphased ($d):= CRITICAL
		endif

//Rule that defines the Db_Critical
	
	rule r_Db_Critical ($d in Deposit)=
		if currphased ($d)= CRITICAL and pieceindepositbeltlightbarrier =false then
		seq
			currphased ($d):= STOP
			depositbeltreadyforload ($d) := true
			pieceatdepositbeltend := true
		endseq
		endif

//Rule that defines the Db_Stopped 
	
	rule r_Db_Stopped ($d in Deposit)=
		if currphased ($d)= STOP and  pieceatdepositbeltend = false then
			currphased ($d):= NORMAL
		endif

//Main Rule

	main rule r_Ground_Modell=
	forall $d in Deposit with true do
			seq
				r_Db_Normal [$d]
				r_Db_Critical [$d]
				r_Db_Stopped [$d]
			endseq

    default init initial_state:

	function pieceatdepositbeltend = false
	function depositbeltreadyforload ($d in Deposit)= true
			