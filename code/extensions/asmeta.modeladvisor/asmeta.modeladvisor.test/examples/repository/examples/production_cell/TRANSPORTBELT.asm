//DANIELA CUGLIETTA
//Esercizio pag. 188

asm TRANSPORTBELT

import ../../STDL/StandardLibrary
export tableloaded,feedbeltfree


//declare universes and functions

	
signature:
		
    enum domain Phasetransport = {NORMALRUN | CRITICALRUN | STOPPED | STOPPEDINLOADPOS} 
	abstract domain Transport
	
	dynamic monitored piecefeedbeltlightbarrier : Boolean
	derived tablereadyforloading: Transport -> Boolean
	static tableinloadposition: Transport-> Boolean
	dynamic controlled currphaset: Transport -> Phasetransport 
	//function for Agent "ROBOT", "TRANSPORT", "ERT"
	dynamic shared tableloaded: Boolean
	//function for Agent "CRANE", "TRANSPORT"
	dynamic shared feedbeltfree: Boolean
	
definitions:


//function tablereadyforloading
	
	function tablereadyforloading ($t in Transport)=
		tableinloadposition($t) = true and
		tableloaded = false
			

//function tableinloadposition

	function tableinloadposition ($t in Transport) =
			currphaset ($t) = STOPPED

//Rule that defines the NormalRun

	rule r_FbNormal ($t in Transport)=
		if currphaset ($t) = NORMALRUN and piecefeedbeltlightbarrier = true then
			feedbeltfree:=true
		endif
		
//Rule 
	rule r_Change ($t in Transport)=
		if tablereadyforloading ($t)=true then
			 currphaset ($t):=CRITICALRUN 
		else
			 currphaset ($t):=STOPPED
		endif

//Rule that defines the Stopped
	
	rule r_FbStopped ($t in Transport)=
		if currphaset ($t)= STOPPED and tablereadyforloading ($t)=true then
			  currphaset ($t) :=CRITICALRUN
		endif

//Rule that defines the CriticalRun

	rule r_FbCritical ($t in Transport)=
		if currphaset ($t)= CRITICALRUN and piecefeedbeltlightbarrier= false then
			seq
			  currphaset ($t):=NORMALRUN
			  tableloaded:= true
			endseq
		endif
//Main Rule

	main rule r_Ground_Modell=
	forall $t in Transport with true do
			seq
				r_FbNormal [$t]
				r_Change [$t]
				r_FbStopped [$t]
				r_FbCritical [$t]
			endseq
	
//define the initial states
	
default init initial_state:
	
	function currphaset ($t in Transport) = 
		 NORMALRUN
	function piecefeedbeltlightbarrier = false
	function feedbeltfree = true
	