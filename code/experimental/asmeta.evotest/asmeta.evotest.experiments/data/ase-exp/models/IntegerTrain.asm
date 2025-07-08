asm IntegerTrain // Integer_train

//	Visual reference model

//	  VSS_11		 VSS_12	    VSS_21	  VSS_22		 VSS_23	   VSS_31	  VSS_32		 VSS_33
// |----------|----------|----------|----------|----------|----------|----------|----------|
// 0			 10			20		   30		 40			50		   60		  70			80
//{		   TTD_10		}{			   TTD_20			}{				TTD_30			 }
//
// Step 1 			Train_Position		Train_Length		Train_Speed			MA(Start,End)
//
// Train1				45					10				100				  (40,60)
// Train2				20					 5				 50				  (10,35)
//
// Means: Train1 occupies the VSS_23 and VSS_22 and is running from VSS_22 to VSS_31
//		  Train2 occupies the VSS_12 and is running from VSS_11 to VSS_22


import STDL/StandardLibrary

signature:	

	domain Trains subsetof Agent 	//	Train Agent - manages train behavior	

	abstract domain Vss 				//  A virtual sub-section, corresponding to a sub-division of a TTD section
	abstract domain Ttd  			//  A section defined by a conventional trackside train detection system
	
	domain Vss_Count subsetof Integer		//  Number of VSS, 8 in this model
	domain Ttd_Count subsetof Integer		//  Number of TTD, 3 in this model
	domain Train_Count subsetof Integer		//  Number of Train, 2 in this model
	domain Track_Count subsetof Integer		//  Number of Track, 1 in this model
	domain Vss_Units subsetof Integer		//  VSS Sub-Section, used to model train movement along the track
											//  each VSS is divided into 10 Vss_Units
	domain Train_Speed subsetof Integer		//  Train Speed, maximum limit 200 Km / h,
											//  used together with the break_factor to calculate the min safe rear end


	enum domain Ttd_state = {TTD_FREE | TTD_OCCUPIED}		// TTD States
	
	// Snapshot of the train status at each computation step 
	enum domain Train_state = {LOSTINTEGRITY | NOTLOSTINTEGRITY | LENGTHCHANGED | NOTLENGTHCHANGED | INTEGRITYCONFIRMED | NOTINTEGRITYCONFIRMED | RECONNECTING | NOTRECONNECTING | CONNECTED | NOTCONNECTED | SHADOWTRAIN | NOTSHADOWTRAIN | GHOSTTRAIN | NOTGHOSTTRAIN }
	enum domain Vss_state = {UNKNOWN | AMBIGUOUS | FREE | OCCUPIED}  // VSS states

//	VSS Functions
    dynamic controlled vss_states: Vss -> Vss_state		//	Used to handle previous VSS states, e.g. when in the guidelines there 
    	dynamic controlled vss_prestates: Vss -> Vss_state	//  are references to the past state that influence the current state
    	
	static last_vss_onthe_ttd: Ttd -> Vss				//  Receives in input a TTD and returns the last VSS of the TTD e.g .: TTD_20 -> VSS_23
	derived vss_rear_train: Trains -> Vss				//  Given a train returns the VSS behind the train's tail
	static vss_related_to_train_position: Vss_Units -> Vss		// Returns the VSS referred to the head of the train, e.g. 55 -> VSS_31
	static position_related_to_vss: Vss -> Vss_Count				// Returns the VSS number referred to the position of the train, e.g. VSS_31 -> 6

//	TTD Functions	
	dynamic controlled ttd_states: Ttd -> Ttd_state		//	Used to handle previous TTD states, e.g. when in the guidelines there
	dynamic controlled ttd_prestates:  Ttd -> Ttd_state	//  are references to the past state that influence the current state
	
	derived ttd_rear_train: Trains -> Ttd					//  Given a train, it returns the TTD behind the train
	derived train_on_ttd: Prod(Ttd, Trains) -> Boolean		//	Given a train and a TTD, it returns true if the train is in the TTD, false otherwise
	derived lefting_ttd_check_status_free: Ttd -> Ttd_state	//  It puts the TTD to TTD_FREE if the last VSS of the TTD I'm leaving is OCCUPIED and all the others are FREE
	static position_related_to_ttd: Ttd -> Ttd_Count			//  Returns the TTD number referring to the position of the train, e.g. TTD_20 -> 2
	static ttd_related_to_absolute_position: Vss_Units -> Ttd_Count		//	Returns the number of the TTD referred to the position of the train, e.g. 55 -> 3	
	static ttd_related_to_position: Vss_Units -> Ttd						//  Returns the TTD referred to the position of the train, e.g. 55 -> TTD_30	

//	TRAIN Functions
	dynamic controlled trains_lostintegrity: Trains -> Boolean			// 	Returns true if the train has lost integrity, false otherwise
	dynamic controlled trains_connected: Trains -> Boolean				//	Returns true if the train is connected, false otherwise
	dynamic controlled trains_shadow: Trains -> Boolean					//	Returns true if, respect to the train, there is a shadow along the track
	dynamic controlled confirmed_safe_rear_end: Trains -> Vss_Units		//	For each train the minimum safety distance confirmed is returned
	
//	Used to handle previous Train states, e.g. when in the guidelines there are references to the past state that influence the current state
	dynamic controlled train_state: Trains -> Prod(Train_state, Train_state, Train_state, Train_state, Train_state, Train_state)
	dynamic controlled train_prestate: Trains -> Prod(Train_state, Train_state, Train_state, Train_state, Train_state, Train_state)
	derived train_left_vss : Trains -> Boolean							//	Returns true if the train left the VSS, false otherwise
	derived min_safe_rear_end: Trains -> Vss_Units						//	Calculates the min safe rear end based on train speed and brake factor
	derived train_rear_another_train: Prod(Trains, Trains) -> Boolean		//	Returns true if there is a train behind another train
		
	dynamic monitored ma: Trains -> Prod(Vss_Units, Vss_Units)			// Movement Authority for the train expressed in Vss_Unit
	
	dynamic monitored train_position: Trains -> Vss_Units					// Trains position on the Trackside expressed in Vss_Units
	
	dynamic monitored train_length: Trains -> Vss_Units					// Trains length on the Trackside expressed in Vss_Units
	
	dynamic monitored train_speed: Trains -> Train_Speed					// Trains speed on the Trackside expressed in Vss_Units
	
    dynamic controlled brake_factor: Real								// Used to calculate the min safe rear end
	
// Collecting trains data (Position, Length, Speed, Ma(start, end)) during the movement of the train
	dynamic controlled collect_trains_data: Trains -> Prod(Vss_Units, Vss_Units, Train_Speed, Vss_Units, Vss_Units) 
	dynamic controlled collect_trains_predata: Trains -> Prod(Vss_Units, Vss_Units, Train_Speed, Vss_Units, Vss_Units)
	
//	VSS canonical name	
	static vss_11: Vss
	static vss_12: Vss
	static vss_21: Vss
	static vss_22: Vss
	static vss_23: Vss
	static vss_31: Vss
	static vss_32: Vss
	static vss_33: Vss

//	TTD canonical name		
	static ttd_10: Ttd
	static ttd_20: Ttd
	static ttd_30: Ttd

//	Train canonical name		
	static train1: Trains
	static train2: Trains
	
		
	monitored wait_integrity_timer: Trains -> Boolean				// True means timer running, false means timer expired
	monitored train_confirmed_integrity: Trains -> Boolean 		// True means integrity confirmed, flase means integrity not confirmed

definitions:

	domain Vss_Count = {1 : 8}
	domain Ttd_Count = {1 : 3}
	domain Train_Count = {1 : 1}
	domain Vss_Units = {1 : 80}  // means that each VSS is divided into 10 pieces
	domain Train_Speed = {0 : 200}
    			 

	function lefting_ttd_check_status_free($ttd in Ttd) = 
			
	            switch($ttd)
	            		case ttd_10:
					  if (vss_states(vss_11) = FREE and vss_states(vss_12) = OCCUPIED) then
					  	   	TTD_FREE 
					  else
					  		TTD_OCCUPIED	    
					  endif
					case ttd_20:   
					  if (vss_states(vss_21) = FREE and vss_states(vss_22) = FREE and vss_states(vss_23) = OCCUPIED) then
						   TTD_FREE
					  else
					  	   TTD_OCCUPIED		   
					  endif
					case ttd_30:  
					  if (vss_states(vss_31) = FREE and vss_states(vss_32) = FREE and vss_states(vss_33) = FREE) then
						   TTD_FREE
					  else
					  	   TTD_OCCUPIED		   
				 	  endif 
		 		endswitch                      

    function vss_related_to_train_position($train_position in Vss_Units) = 
			
			if ($train_position > 70) and ($train_position <= 80) then
			    vss_33
			else if ($train_position > 60) and ($train_position <= 70) then 
					vss_32
				 else if ($train_position > 50) and ($train_position <= 60) then
				         vss_31
				      else if ($train_position > 40) and ($train_position <= 50) then 
				              vss_23
				           else if ($train_position > 30) and ($train_position <= 40) then 
				                   vss_22
				                else if ($train_position > 20) and ($train_position <= 30) then  
				                        vss_21
				                      else if ($train_position > 10) and ($train_position <= 20) then  
				                              vss_12
				                           else if ($train_position >= 1) and ($train_position <= 10) then  
				                                   vss_11      
											   endif
										   endif
									 endif
								endif
						   endif	
					  endif
				 endif	  	   	 	   	   
			endif


   	function vss_rear_train($trains in Trains) = 
								 
			 if (train_position($trains) - train_length($trains) + 1) > 70 then 
					vss_32
				 else if (train_position($trains) - train_length($trains) + 1) > 60 then
				         vss_31
				      else if (train_position($trains) - train_length($trains) + 1) > 50 then 
				              vss_23
				           else if (train_position($trains) - train_length($trains) + 1) > 40 then 
				                   vss_22
				                else if (train_position($trains) - train_length($trains) + 1) > 30 then  
				                        vss_21
				                      else if (train_position($trains) - train_length($trains) + 1) > 20 then  
				                              vss_12
				                           else if (train_position($trains) - train_length($trains) + 1) > 10 then 
				                                    vss_11 
				                                 else
				                                    vss_11    
				                                endif    		                           
										  endif
									 endif
								endif
						   endif	
					  endif
				 endif	  	   	 	   	   


    function position_related_to_ttd($ttd in Ttd) = 

			 switch($ttd)
			     case ttd_10:
			     				1
			     case ttd_20:
			     				2
			     case ttd_30:
			     				3
			 endswitch 
			 
	function ttd_related_to_absolute_position($trains_position in Vss_Units) = 

			if ($trains_position >= 51) and ($trains_position <= 80) then
			     3
			else if  ($trains_position >= 21) and ($trains_position < 50) then
			        2
			     else if ($trains_position >= 1) and ($trains_position <= 20) then       
						1
					  endif
				 endif	
			endif	   	
	
	function ttd_related_to_position($trains_position in Vss_Units) = 

			if ($trains_position >= 51) and ($trains_position <= 80) then
			     ttd_30
			else if  ($trains_position >= 21) and ($trains_position <= 50) then
			        ttd_20
			     else if ($trains_position >= 1) and ($trains_position <= 20) then       
						ttd_10
					  endif
				 endif	
			endif	
						
	function train_on_ttd($ttd in Ttd, $trains in Trains) =

			if (ttd_related_to_absolute_position(train_position($trains)) = position_related_to_ttd($ttd)) then
			        true
			else
					false
			endif	   					
			
	function train_left_vss($trains in Trains) = 
	
		  	    if vss_related_to_train_position((train_position($trains) - train_length($trains)) + 1) != vss_related_to_train_position((first(collect_trains_data($trains)) - second(collect_trains_data($trains))) + 1) then
			  	    true
			  	else
			  	    false
			  	endif       
			
	function ttd_rear_train($trains in Trains) = 
				
			if (train_position($trains) - train_length($trains) + 1) >= 51 and (train_position($trains) - train_length($trains)) <= 80 then
			      ttd_20
			else if  (train_position($trains) - train_length($trains) + 1) >= 21 and (train_position($trains) - train_length($trains)) <= 50 then
			          ttd_10
			      endif
			endif                 
	
	function last_vss_onthe_ttd($ttd in Ttd) =   
	
			switch($ttd)
			   case ttd_10:
			   				vss_12
			   case ttd_20:
			   				vss_23
			   case ttd_30:
			   				vss_33
			endswitch   								

		 
	function train_rear_another_train($train1 in Trains, $train2 in Trains) =
	
	         if (train_position($train2) - train_length($train2)) > train_position($train1) then 
	              true
	         else
	         	  false
	         endif	  
	         	       
	function min_safe_rear_end($trains in Trains) =  
	
			if train_speed($trains) > 0 then 
			  	rtoi(((itor(train_speed($trains)) / brake_factor) * (itor(train_speed($trains)) / brake_factor)) / ((itor(train_speed($trains)) * itor(train_length($trains))) / brake_factor))	  	
			else
				0
			endif  	 		         	          	                 

	
	macro rule r_check_ttd_status_free ($ttd in Ttd) =	
	
			    if lefting_ttd_check_status_free($ttd) = TTD_FREE  and  ttd_states($ttd) = TTD_OCCUPIED then
			       par
					    ttd_states($ttd) := TTD_FREE
					    ttd_prestates($ttd) := ttd_states($ttd)
				   endpar
				endif   	 
	
	
	// Sets all the VSS where the train is located at OCCUPIED			
	macro rule  r_vss_train_occupied ($trains in Trains) =	
	
			   forall $position in Vss_Units with (($position <= train_position($trains)) and ($position > (train_position($trains) - train_length($trains))) and $position <= second(ma($trains))) do
			     par
					 vss_states(vss_related_to_train_position($position)) := OCCUPIED
					 vss_prestates(vss_related_to_train_position($position)) := vss_states(vss_related_to_train_position($position))
					 ttd_states(ttd_related_to_position($position)) := TTD_OCCUPIED
					 ttd_prestates(ttd_related_to_position($position)) := ttd_states(ttd_related_to_position($position))						 							       					  
				 endpar	     					 					 					         

	// Memorize the train parameters during its journey along the track		              	              
    macro rule  r_collect_train_data ($trains in Trains) =
    
    			     par
    				    	collect_trains_data($trains) := (train_position($trains), train_length($trains), train_speed($trains), first(ma($trains)), second(ma($trains)))  
    				    	collect_trains_predata($trains) := collect_trains_data($trains)
    				 endpar   	  
     

// 3.5	Operation of trains treated as integer. This is the first refinement of the train behavior treated as integer. 
// During all the phases of the modeling process, we tried to faithfully represent the specifications of the ERTMS HL 3 protocol.			
	macro rule r_Trains_integer ($trains in Trains) =

			if train_confirmed_integrity($trains) and train_position($trains) <= second(ma($trains)) then
			   par 
			     confirmed_safe_rear_end($trains) := train_position($trains) - train_length($trains) - min_safe_rear_end($trains)
			     if ((first(train_prestate($trains)) = NOTLOSTINTEGRITY) and not(wait_integrity_timer($trains))) then
			       par
			           trains_lostintegrity($trains) := false 
			           if trains_connected($trains) then
			               r_vss_train_occupied[$trains]
			           endif 
			       endpar    
			     else
			         trains_lostintegrity($trains) := true     
			     endif   
			     forall $t in Trains with $t != $trains do
			        if (not(trains_connected($t))) and train_rear_another_train ($t, $trains) then
			            trains_shadow($t) := true
			         else
			            par
			               trains_shadow($t) := false
			               if train_left_vss($trains) and vss_states(vss_rear_train($trains)) = OCCUPIED then 			                 
	                            par				                         
			                         vss_states(vss_rear_train($trains)) := FREE
							         vss_prestates(vss_rear_train($trains)) := vss_states(vss_rear_train($trains))
							         if isDef(ttd_rear_train($trains)) then 
							      		if not train_on_ttd(ttd_rear_train($trains), $t)  then
				   				     		r_check_ttd_status_free[ttd_rear_train($trains)]
				   				  		endif   
							   		 endif    
							   endpar        
			               endif 
			            endpar     
			        endif    
			   endpar
			 else if train_left_vss($trains) and vss_states(vss_rear_train($trains)) = OCCUPIED then 
			        par			                    
			            vss_states(vss_rear_train($trains)) := UNKNOWN
					    vss_prestates(vss_rear_train($trains)) := vss_states(vss_rear_train($trains))
			        endpar
			      endif        
			 endif
  
//	The first managed condition is the integer train that runs along the tracks								
	macro rule r_Trains_Run ($trains in Trains, $vss in Vss) =
	       		
	  		   r_Trains_integer[$trains] 		   
	
// The train moves on the track based on its MA, the train during the movement stores its vital data
	macro rule r_Trains_on_TrackSide =
		
			par  		 
			   forall $position in Vss_Units with $position <= second(ma(self)) and $position >= first(ma(self)) do 
			       r_Trains_Run[self, vss_related_to_train_position($position)] 
			   r_collect_train_data[self] 
			endpar	
			       	       		    			        
// Agents Train in running		   
	main rule r_Main = 
		  
			forall $trains in Trains with true do
					program($trains)              

//	Init State
default init initial_state:
	
	function brake_factor = 4.0
	
	function vss_states ($vss in Vss) = FREE
	function vss_prestates ($vss in Vss) = FREE
	
	function ttd_states($ttd in Ttd) = TTD_FREE
	function ttd_prestates($ttd in Ttd) = TTD_FREE
	
	function trains_connected($trains in Trains) = true
	function trains_shadow($trains in Trains) = false
	function confirmed_safe_rear_end($trains in Trains) = 0	
	
	function collect_trains_data($trains in Trains) = (0, 0, 0, 0, 0)
	function collect_trains_predata($trains in Trains) = (0, 0, 0, 0, 0)  
	function train_state($trains in Trains) = (NOTLOSTINTEGRITY,  NOTLENGTHCHANGED, INTEGRITYCONFIRMED, NOTRECONNECTING, CONNECTED, NOTSHADOWTRAIN)
	function train_prestate($trains in Trains) = (NOTLOSTINTEGRITY,  NOTLENGTHCHANGED, INTEGRITYCONFIRMED, NOTRECONNECTING, CONNECTED, NOTSHADOWTRAIN)                 	

	agent Trains: r_Trains_on_TrackSide[]

	
	
		
		
		
		
