asm volumePressureVentOptimal
 
import StandardLibrary
import TimeLibrarySimple
 
signature:
	
    enum domain VentilationMode={PCV | ASV | PCVFREQOPT}
    
    //Parameters for PCV and PCVFREQOPT ventilation mode
    dynamic monitored respiratoryRate: Real
    dynamic monitored ieRatio: Real
    dynamic monitored pinsp: Real
    dynamic monitored peep: Real
    
    //Parameters for ASV ventilation mode
    dynamic monitored startingVolume: Real //volume for ASV ventilation mode when optimal_lung_volume is undef
    
    dynamic monitored ventilationMode: VentilationMode // set by user
    
    dynamic monitored optimal_lung_volume: Real // read by external queue is the optimal value of the volume
	dynamic monitored optimal_respiratory_frequency: Real // read by external queue is the optimal value of RR
         
    static simTime: Timer
 
    dynamic out value: Real // is the output volume/pressure based on the ventilation type
	dynamic out ventilatorType: String // ventilator type: "Pressure" or "Volume"
	
definitions:
 
	macro rule r_setVentilatorType=
		par
			if (ventilationMode=PCV or ventilationMode=PCVFREQOPT) then
				ventilatorType :="Pressure"
			endif
			if (ventilationMode=ASV) then
				ventilatorType := "Volume"
			endif
		endpar
 
	macro rule r_setOutPressure($respRate in Real)=
		 let ($inspiratoryTime = 60.0 / $respRate * (ieRatio / (1.0 + ieRatio)),
	                $cycleTime = currentTime(simTime) mod rtoi(60.0 / $respRate)) in
		     if ($cycleTime < rtoi($inspiratoryTime)) then
		         value := pinsp
		     else
		         value := peep
		     endif
	     endlet
	     
    main rule r_Main =
	    par
	    //Ventilation in pressure mode
	    if (ventilationMode=PCV or ventilationMode=PCVFREQOPT) then
	        if(isUndef(simTime)) then
	            value := peep
	        else
	           par
	       			// use the optimal respiratory frequency if available
	           	if (ventilationMode=PCVFREQOPT)then
		           if (isUndef(optimal_respiratory_frequency)) then
		           		r_setOutPressure[respiratoryRate]
		           	else
		           		 r_setOutPressure[optimal_respiratory_frequency]
		           endif
		        endif
		        	//run in PCV mode
		        if (ventilationMode=PCV) then
		        	r_setOutPressure[respiratoryRate]
		        endif
	           endpar
	        endif
	     endif
	      	if (ventilationMode=ASV) then
	      		//use the optimal lung volume otherwise the value set by the user
	      		if (isUndef(optimal_lung_volume)) then
	      			value := startingVolume
	      		else
	      			value := optimal_lung_volume
	      		endif
	      	endif
	     r_setVentilatorType[]
	     endpar
 
default init s0:
    function value = 0.0