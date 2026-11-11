module pillbox_sanitiser

import ../StandardLibrary
import prescriptionParameters_Knowledge
export *

signature:
	//*************************************************
	// DOMAINS
	//*************************************************
	enum domain States = {INIT | NORMAL}
	abstract domain Compartment
	enum domain LedLights = {OFF | ON | BLINKING}
	//*************************************************
	// FUNCTIONS
	//*************************************************
	
	controlled state: States
	controlled prevRedLed: Compartment -> LedLights
	controlled amount: String -> Natural
	controlled minTimeToIntake: String -> Natural 
	controlled minToInterferer: Prod(String,String) -> Natural
	controlled time: String -> Seq(Natural)
	controlled medicine_list: Seq(String)
	controlled deltaDelay: String -> Natural
	controlled nextDrugIndexN: Compartment ->Natural
	
	derived next: Compartment ->  Powerset(Compartment) 
	derived ledStatusUpdateOk: Compartment -> Boolean
	derived nextDrugIndex: Compartment -> Natural // next drug index of the given compartment 
	
	
	//IN from Pillbox
	monitored time_consumption: Compartment -> Seq(Natural)
	monitored name: Compartment -> String
	monitored drugIndex: Compartment -> Natural
	monitored redLed: Compartment -> LedLights
	
definitions:
	//*************************************************
	// FUNCTIONS DEFINITIONS
	//*************************************************
	
	//***************Derived function declared in PrescriptionParameters_Knowledge module
	function minTimeToIntake($medicine in Medicine) = minTimeToIntake(id($medicine))
    function minToInterferer($m in Medicine, $n in Medicine) = minToInterferer(id($m),id($n))
    function time($m in Medicine) = time(id($m))
    function deltaDelay($m in Medicine) = deltaDelay(id($m))
    function nextDrugIndex($compartment in Compartment) = 	let ( $i = drugIndex($compartment) + 1n ) in 
		if  $i < iton(length(time_consumption($compartment)))  then $i else 0n endif
		endlet 
    //***************Derived function declared in this module
	function ledStatusUpdateOk($compartment in Compartment) = ((prevRedLed($compartment) = OFF and redLed($compartment)= BLINKING) or
			    /*(prevRedLed($compartment) = ON and redLed($compartment)= OFF) or*/
			    (prevRedLed($compartment) = BLINKING and redLed($compartment)= ON))
	
	/*function nextDrugIndex($compartment in Compartment) = 	let ( $i = drugIndex($compartment) + 1n ) in 
		if  $i < iton(length(time_consumption($compartment)))  then $i else 0n endif
		endlet */
				
	function next($compartment in Compartment) =  
		{$c in Compartment | (at(time_consumption($c),drugIndex($c)) > at(time_consumption($compartment),drugIndex($compartment))) : $c}
	

    rule r_keepPrevLiths =
	    forall $compartment in Compartment do
	    par
	       //Keep previous led status
		   prevRedLed($compartment) := redLed($compartment)
		   nextDrugIndexN($compartment):=nextDrugIndex($compartment)
		endpar
	
	
    			      	 
	//*************************************************
	// INVARIANT DEFINITIONS
	//*************************************************
    //The only valid traces of a led light are the sequences: OFF, ON, BLINKING and BLINKING, OFF
    invariant over redLed: ( forall $c in Compartment with not ledStatusUpdateOk($c) ) 
    
    //Knowledge consistency:
    //Pills type consistency between the prescription and the compartments of the pillbox
    invariant over medicine_list:  ( forall $c in Compartment with ( contains(medicine_list,name($c))))
    invariant over Compartment: (forall $m in asSet(medicine_list) with (exist $c in Compartment with name($c)=$m))
    //invariant over Compartment, Medicine: (forall $m in Medicine with (exist $c in Compartment with name($c)=id($m)))
    //invariant over Compartment, Medicine: (forall $c in Compartment with (exist $m in Medicine with name($c)=id($m)))
    
    //Pills amount consistency: the amount of pills to intake and the number of the corresponding compartment slots must be the same
    //invariant over Medicine: ( forall $m in Medicine with iton(length(time($m))) = amount($m) ) //da NullPointerException 
    invariant over Compartment: ( forall $c in Compartment with iton(length(time_consumption($c))) = amount(name($c)) )
    
    
    //Pills time consistency: the time schedule of the compartment's slots must be the same of those scheduled for the medicine and there must be no interferences
     invariant over Compartment, Medicine: state=INIT implies (forall $c in Compartment with ((
    	(drugIndex($c) < iton(length(time_consumption($c))) and
    	at(time_consumption($c),drugIndex($c)) = (at(time(name($c)),drugIndex($c)))
    	))))
   
    //Static interferences checking:
    //Per una medicina in un dato compartment e slot, la differenza tra il suo tempo di assunzione e il tempo di assunzione delle medicine successive
    //(slot successivo dello stesso compartment o di un altro compartment) deve essere maggiore o uguale al minToInterferer della medicina con le medicine successive 
    //Viene violato sempre in base allo stato iniziale di adesso. Dove sbaglio? 
   invariant over Compartment: ( forall $compartment in Compartment with 
    	(forall $c in next($compartment) with 
    	/*( iton((at(time_consumption($c),drugIndex($c)) - at(time_consumption($compartment),drugIndex($compartment)))) 
    		//	(  iton((at(time_consumption($c),nextDrugIndex($c)) - at(time_consumption($compartment),drugIndex($compartment)))) 
    		>= minToInterferer(name($compartment),name($c)) 
    	)*/
    	(  (iton((at(time_consumption($c),drugIndex($c)) - at(time_consumption($compartment),drugIndex($compartment)))) 
			>= (minToInterferer(name($compartment),name($c))) and $c!=$compartment) or (iton((at(time_consumption($c),nextDrugIndex($c)) - at(time_consumption($compartment),drugIndex($compartment)))) 
			>= (minToInterferer(name($compartment),name($c))) and $c=$compartment)
		)
    ))
    //$c != $compartment  and --> non serve perchè il next anche se è dello stesso compartment comunque fa riferimento ad un altro orario
    //se il compartment è uguale uso il nextDrugIndex altrimenti drugIndex
    	
    //Dynamic interferences checking (pills intake may be delayed; actual time of assumption must be considered)
    //TODO:Similmente all'invariante precedente, introdurre una funzione actual_time_assumption su Compartment che verrà settata per riportare il tempo effettivo dell'assunzione di una 
    //pillola in un dato scomparto (drugIndex).
    //Scrivere un invariante per: per tutte le medicine in un dato compartment e slot, la differenza tra tempo effettivo e tempo nominale deve essere
    //maggiore o uguale al minToInterferer con le medicine successive (slot successivo dello stesso compartment o di un altro compartment)
    //Interferences checks for pillows intaken not in time
    //invariant over minToInterferer: ( forall $c in Compartment with 
    	//(drugIndex($c)<iton(length(time_consumption($c)))) and //index in range
    //	((at(actual_time_consumption($c),drugIndex($c)) - (at(time_consumption($c),drugIndex($c)))
    //	 >= minToInterferer(name($c), name(next($c))) 	
    //	)))
    	
	/*forall $c2 in next($compartment) with 
			   	(  ((iton(at(time_consumption($c2),drugIndex($c2)) - (systemTime mod 1440n)) //it should be actual_time_consumption, but since it is updated in the next state I use systemTime here
				<= (minToInterferer(name($compartment),name($c2)))) and $c2!=$compartment) or ((iton(at(time_consumption($c2),nextDrugIndex($c2)) - (systemTime mod 1440n))
				<= (minToInterferer(name($compartment),name($c2)))) and $c2=$compartment)
				)*/
	
	