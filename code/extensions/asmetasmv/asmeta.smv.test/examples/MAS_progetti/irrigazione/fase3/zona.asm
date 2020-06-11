asm zona

import ../../../asm_examples/STDL/StandardLibrary
export *


signature:
	domain ZoneAgent subsetof Agent
	domain RandomRange subsetof Integer
	domain UsedWater subsetof Integer
	domain Position subsetof Integer
	
	enum domain ValvolaState = {OPEN | CLOSED} 
	enum domain EnabledState = {ENABLED | DISABLED} 
	enum domain NeedState = {NEED | DONT_NEED | BROKEN} 	
	
	dynamic controlled state_valvola: ZoneAgent -> ValvolaState
	dynamic controlled state_activation : ZoneAgent -> EnabledState
	dynamic controlled state_sensor: ZoneAgent -> NeedState
	
	dynamic monitored sensor: ZoneAgent -> Boolean
	dynamic monitored switchenabled: ZoneAgent -> Boolean
	
	derived utilizzo: ZoneAgent -> UsedWater
	
	static zone01: ZoneAgent
	static zone02: ZoneAgent
	static zone03: ZoneAgent
	static zone04: ZoneAgent
	static zone05: ZoneAgent


definitions:


	domain RandomRange = {1..100}
	domain UsedWater = {0..1}
	domain Position = {0..5}
		
		
	function utilizzo($c in ZoneAgent) =
		if (state_valvola($c) = OPEN) then
			1
		else
			0
		endif
				

	rule r_enable = 
		//Se voglio abilitare la zona e questa era disabilitata
		if (switchenabled(self) and (state_activation (self) = DISABLED))then
			par
				//Riabilitiamo la zona
				//Settiamo il bisogno di acqua a DONT_NEED
				state_activation (self) := ENABLED
				state_sensor(self) := DONT_NEED
			endpar
		endif


	rule r_disable = 
			//Se voglio disabilitare la zona e questa era abilitata
			if (switchenabled(self) and (state_activation (self) = ENABLED))then
				par
					//Disabilitiamo la zona
					//e chiudiamo l'acqua nel caso in qui fosse aperta
					state_activation (self) := DISABLED
					state_valvola(self) := CLOSED
				endpar
			endif
		
		
	rule r_needwater = 
			//controlliamo il sensor solo se la zona � attiva e se non vogliamo cambiare lo stato (da attiva a disattiva)
			if ((state_activation (self) = ENABLED) and (switchenabled(self) = false))then
				if (sensor(self))then
					state_sensor(self) := NEED
				else
					par
						//Se non serve acqua chiudo la valvola nel caso in cui fosse
						//aperta per l'irrigazione
						state_sensor(self) := DONT_NEED
						state_valvola(self) := CLOSED
					endpar
				endif
			else
				state_sensor(self) := DONT_NEED
			endif
			
		
	rule r_guasto = 
		//Creazione Casuale di Guasti
		choose $r in RandomRange
		with true do
			//Se l'intero scelto � 1 allora c'� stato un guasto
			if (($r = 1) and (state_activation (self) = ENABLED)) then
				par
					//Se c'� stato un guasto disabilitiamo la zona, chiudiamo la valvola se � aperta
					//la togliamo dalla zona e segnaliamo che c'� stato un guasto
					state_activation (self) := DISABLED
					state_sensor(self) := BROKEN
					state_valvola(self) := CLOSED
				endpar
			else
				par
					//Se non c'� stato un guasto allora eseguiamo le regole per la zona
					r_enable[]
					r_disable[]
					r_needwater[]
				endpar
			endif
		
		
	rule r_ZoneAgent =
			r_guasto[]
	
default init s0:
	