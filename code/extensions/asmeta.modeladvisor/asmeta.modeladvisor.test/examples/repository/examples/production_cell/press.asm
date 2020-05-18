//DANIELA CUGLIETTA
//Esercizio pag. 188

asm press

import ../../STDL/StandardLibrary
import robot (pressloaded)

//declare universes and functions

	
signature:
	enum domain Phasepress= {OPENFORLOAD | OPENFORUNLOAD | MOVINGTOMIDDLEPOSITION | 			MOVINGTOTOPPOSITION | MOVINGTOBOTTOMPOSITION| CLOSEDFORFORGIN}
	abstract domain Press
	dynamic controlled currphasep : Press-> Phasepress
	dynamic monitored middleposition :Boolean
	dynamic monitored bottomposition :Boolean
	dynamic monitored topposition :Boolean
	dynamic monitored forgincompleted :Boolean

definitions:


//Rule that defines the Waiting_Unload

	  rule r_Waiting_Unload ($p in Press)=
		if currphasep ($p) = OPENFORLOAD and pressloaded = false then
			currphasep ($p):= MOVINGTOMIDDLEPOSITION
		endif

//Rule that defines the Moving to Middle
		
	  rule r_Moving_To_Middle ($p in Press)=
		if currphasep ($p)= MOVINGTOMIDDLEPOSITION and middleposition= true then
			currphasep ($p):= OPENFORLOAD
		endif

//Rule that defines the Waiting_Load
		
	  rule r_Waiting_Load ($p in Press)=
		if currphasep ($p)= OPENFORLOAD and pressloaded= true then
			currphasep ($p):= MOVINGTOTOPPOSITION
		endif

//Rule that defines the Moving_To_Upper

	  rule r_Moving_To_Upper($p in Press)=
		if currphasep ($p)= MOVINGTOTOPPOSITION and topposition= true then
			currphasep ($p):= CLOSEDFORFORGIN
		endif

//Rule that defines the Closed

	  rule r_Closed ($p in Press)=
		if currphasep ($p)= CLOSEDFORFORGIN and forgincompleted= true then
			currphasep ($p):= MOVINGTOBOTTOMPOSITION
		endif

//Rule that define the Moving_To_Lower

	  rule r_Moving_To_Lower ($p in Press)=
		if currphasep ($p)= MOVINGTOBOTTOMPOSITION and bottomposition= true then
			currphasep ($p):= OPENFORLOAD
		endif


//Main Rule

	main rule r_Ground_Modell=
	forall $p in Press with true do
			seq
				r_Waiting_Unload [$p]
				r_Moving_To_Middle [$p]
				r_Waiting_Load [$p]
 				r_Moving_To_Upper[$p]
				r_Closed [$p]
				r_Moving_To_Lower [$p]
			endseq