asm zona

import ../../../../../../../asm_examples/STDL/StandardLibrary
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
	dynamic controlled state_priority: ZoneAgent -> Position
	
	dynamic monitored sensor: ZoneAgent -> Boolean
	dynamic monitored switchenabled: ZoneAgent -> Boolean
	
	derived utilizzo: ZoneAgent -> UsedWater
	
	static zone01: ZoneAgent
	static zone02: ZoneAgent
	static zone03: ZoneAgent
	static zone04: ZoneAgent
	static zone05: ZoneAgent


definitions:

	domain RandomRange = {1:2}
	domain UsedWater = {0:1}
	domain Position = {0:5}
		
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
			//controlliamo il sensore solo se la zona � attiva e se non vogliamo cambiare lo stato (da attiva a disattiva)
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
				
	//inizio modifica PA 18 agosto
	/*rule r_priority_aumenta = //non va non si sa il perch�
		//se non sono nella posizione pi� alta e sono in coda
		if ((state_priority(self) > 0) and (state_priority(self) < 5)) then
			forall $c in Position with ($c > state_priority(self)) do 
				choose $g in ZoneAgent
				with (state_priority($g) = $c )
				do skip 
				ifnone state_priority(self) :=  state_priority(self) + 1
		endif	*/
	rule r_priority_aumenta =
		if ((state_priority(self) > 0) and (state_priority(self) < 5)) then
			if(exist $c in Position with (	$c > state_priority(self) and
											(forall $g in ZoneAgent with state_priority($g) != $c )
										)) then
				state_priority(self) :=  state_priority(self) + 1
		endif
	endif
	//fine modifica PA 18 agosto
	
//	rule r_priority_aumenta =
//		//In coda (quindi state_priority(self) � tra 1 e 5)
//		//controllo se sopra di me c'� un buco
//		//se c'�, allora mi alzo di uno
//		//Gli altri agenti in coda si presume che faranno lo stesso
//		//quindi saremo in grado mantenere la coda correttamente 
//		par
//			//Se sono il posizione 1,2,3,4 controllo se la posizione 5 � libera
//			if (state_priority(self) < 5) then
//					choose $c in ZoneAgent
//					with (state_priority($c) = 5)
//					do skip 
//					ifnone state_priority(self) :=  state_priority(self) + 1
//			endif	
//		
//			//Se sono il posizione 1,2,3 controllo se la posizione 4 � libera
//			if (state_priority(self) < 4) then
//					choose $d in ZoneAgent
//					with (state_priority($d) = 4)
//					do skip 
//					ifnone state_priority(self) :=  state_priority(self) + 1
//			endif	
//		
//			//Se sono il posizione 1,2 controllo se la posizione 3 � libera
//			if (state_priority(self) < 3) then
//					choose $e in ZoneAgent
//					with (state_priority($e) = 3)
//					do skip 
//					ifnone state_priority(self) :=  state_priority(self) + 1
//			endif	
//		
//			//Se sono il posizione 1 controllo se la posizione 2 � libera
//			if (state_priority(self) < 2) then
//					choose $f in ZoneAgent
//					with (state_priority($f) = 2)
//					do skip 
//					ifnone state_priority(self) :=  state_priority(self) + 1
//			endif
//		endpar
		
		
	rule r_priority_update =
		//Se la zona � disabilitata
		//Se la valvola � aperta
		//Se sto per essere disabilitata
		//Allora la mia priorit� deve essere messa a 0
		if 	(state_activation (self) = DISABLED) 
			or (state_valvola(self) = OPEN)
			or ((state_activation (self) = ENABLED) and (switchenabled(self) = true)) then
			
					state_priority(self) := 0
						
		else
		
			par
				//Se devo essere messo in coda
				//Scelgo la priorit� pi� alta possibile
				if (sensor(self) and (state_sensor(self) = DONT_NEED)) then
					state_priority(self) := 1
				endif
				
				//Se il sensor dice che ho bisogno d'acqua d ero gi� in coda
				//controllo se � possibile aumentare la mia priorit�
				if (sensor(self) and state_sensor(self) = NEED) then
					r_priority_aumenta[]
				endif
				
				//Se il sensore diche non mi serve acqua ed ero in coda/
				//Allora mi tolgo dalla coda
				if ((sensor(self) = false) and (state_sensor(self) = NEED)) then
					state_priority(self) := 0
				endif
				
				//Ultimo de 4 casi
				//Situazione "NORMALE"
				//Il sensor dice che non mi serve acqua e non sono in coda
				if ((sensor(self) = false) and (state_sensor(self) = DONT_NEED)) then
					state_priority(self) := 0
				endif
			endpar
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
					state_priority(self) :=  0
				endpar
			else
				par
					//Se non c'� stato un guasto allora eseguiamo le regole per la zona
					r_enable[]
					r_disable[]
					r_needwater[]
					r_priority_update[]
				endpar
			endif
		
		
	rule r_ZoneAgent =
			r_guasto[]
	
	
default init s0:
	