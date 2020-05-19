asm ertms_hl3_integer_disco_lostint_lengthchanged

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

import ../../../STDL/StandardLibrary

signature:	

//	Agents	
	domain Trains subsetof Agent 
	domain Rbc subsetof Agent
	domain TrackController subsetof Agent	
	
	abstract domain Vss 
	abstract domain Ttd  
	
	domain Vss_Count subsetof Integer
	domain Ttd_Count subsetof Integer
	domain Train_Count subsetof Integer
	domain Track_Count subsetof Integer
	domain Vss_Units subsetof Integer
	domain Train_Speed subsetof Integer
	domain Seconds subsetof Integer

	enum domain Ttd_state = {TTD_FREE | TTD_OCCUPIED}
	enum domain Ptd_state = {INTEGRITY | TIMEOUT}
	enum domain Train_state = {LOSTINTEGRITY | NOTLOSTINTEGRITY | LENGTHCHANGED | NOTLENGTHCHANGED | CONNECTED | NOTCONNECTED | SHADOWTRAIN | NOTSHADOWTRAIN | GHOSTTRAIN | NOTGHOSTTRAIN | INTEGRITYCONFIRMED | NOTINTEGRITYCONFIRMED }
	enum domain Vss_state = {UNKNOWN | AMBIGUOUS | FREE | OCCUPIED}

//	VSS States
    dynamic controlled vss_states: Vss -> Vss_state
	dynamic controlled vss_prestates: Vss -> Vss_state
	derived vss_rear_train: Trains -> Vss
	derived vss_advance_train: Trains -> Vss
	derived vss_advance_vss: Vss -> Vss
	derived vss_related_to_train_position: Vss_Units -> Vss
	derived position_related_to_vss: Vss -> Vss_Count
	derived vss_absolute_to_relative_position: Vss_Units -> Vss_Count
	derived ma_on_vss: Prod(Vss, Trains) -> Boolean
	derived vss_rear_ttd: Prod(Vss, Ttd) -> Boolean

//	TTD States	
	dynamic controlled ttd_states: Ttd -> Ttd_state
	dynamic controlled ttd_prestates:  Ttd -> Ttd_state	
	derived ttd_rear_train: Trains -> Ttd
	derived train_on_ttd: Prod(Ttd, Trains) -> Boolean
	derived ma_on_ttd: Prod(Ttd, Trains) -> Boolean
	derived vss_on_ttd: Vss -> Ttd
	derived ttd_status_free: Ttd -> Ttd_state
	derived lefting_ttd_check_status_free: Ttd -> Ttd_state
	derived position_related_to_ttd: Ttd -> Ttd_Count
	derived ttd_related_to_absolute_position: Vss_Units -> Ttd_Count
	derived ttd_related_to_position: Vss_Units -> Ttd

//	TRAIN States	
	dynamic controlled trains_integer: Trains -> Boolean
	dynamic controlled trains_notreconnecting: Trains -> Boolean
	dynamic controlled trains_chased: Trains -> Boolean
	dynamic controlled trains_chasing: Trains -> Boolean
	dynamic controlled trains_shadow: Trains -> Boolean
	dynamic controlled trains_integrity_info_avail: Trains -> Boolean
	dynamic controlled trains_confirmed_integrity: Trains -> Boolean
	dynamic controlled confirmed_safe_rear_end: Trains -> Vss_Units
	dynamic controlled train_state: Trains ->  Seq(Train_state)
	dynamic controlled train_prestate: Trains ->  Seq(Train_state)
	derived train_left_vss : Trains -> Boolean
	derived train_is_rear_ttd: Prod(Ttd, Vss_Units) -> Boolean
	derived min_safe_rear_end: Trains -> Vss_Units
	derived train_rear_another_train: Prod(Trains, Trains) -> Boolean
	
	// Movement Authority for the train expressed in vss_unit
	dynamic monitored ma: Trains -> Prod(Vss_Units, Vss_Units)
	
	// trains position on the Trackside expressed in vss_unit 
	dynamic monitored train_position: Trains -> Vss_Units
	
	// trains length on the Trackside expressed in vss_unit
	dynamic monitored train_length: Trains -> Vss_Units
	
	// trains speed on the Trackside expressed in vss_unit
	dynamic monitored train_speed: Trains -> Train_Speed
	
	// disconnect propagation timer 
	dynamic monitored disconnect_propagation_timer: Vss -> Boolean
	
	// if mute_timer = true the communication session is considered as lost by the RBC
	dynamic controlled mute_timer: Trains -> Boolean
	
	dynamic controlled brake_factor: Real
	
// Collecting trains data (Position, Length, Speed....)
	dynamic controlled collect_trains_data: Trains -> Prod(Vss_Units, Vss_Units, Train_Speed, Vss_Units, Vss_Units) 
	dynamic controlled collect_trains_predata: Trains -> Prod(Vss_Units, Vss_Units, Train_Speed, Vss_Units, Vss_Units)
	
	static vss_11: Vss
	static vss_12: Vss
	static vss_21: Vss
	static vss_22: Vss
	static vss_23: Vss
	static vss_31: Vss
	static vss_32: Vss
	static vss_33: Vss
	
	static ttd_10: Ttd
	static ttd_20: Ttd
	static ttd_30: Ttd
	
	static train1: Trains
	static train2: Trains
	
	static rbc_supervisor: Rbc
	
	static trackcontroller1: TrackController
	
	monitored rbc_conn: Trains -> Boolean	
	monitored lost_integrity: Trains -> Boolean
	monitored length_changed: Trains -> Boolean
	monitored wait_integrity_timer: Trains -> Boolean 

definitions:

	domain Vss_Count = {1 : 8}
	domain Ttd_Count = {1 : 3}
	domain Train_Count = {1 : 1}
	domain Vss_Units = {1 : 80}  // means that each VSS is divided into 10 pieces
	domain Train_Speed = {0 : 200}
    			

// if all VSSs within the TTD are free, the TTD becomes free 
// vedere anche lo stato TTD_OCCUPIED se tutte le vss della ttd sono OCCUPIED   

	function ttd_status_free($ttd in Ttd) = 
			
	            switch($ttd)
	            		case ttd_10:
					  if (vss_states(vss_11) = FREE and vss_states(vss_12) = FREE) then
					  	   	TTD_FREE 
					  else
					  		TTD_OCCUPIED	    
					  endif
					case ttd_20:   
					  if (vss_states(vss_21) = FREE and vss_states(vss_22) = FREE and vss_states(vss_23) = FREE) then
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

// the train is moving behind the indicated TTD ($ttd)		 
	function train_is_rear_ttd($ttd in Ttd, $trains_position in Vss_Units) =
	            
	            switch($ttd)
					case ttd_20:   
					  if ($trains_position <= 20) then
						   true
					  else
					       false	   
					  endif
					case ttd_30:  
					  if ($trains_position > 20) and ($trains_position <=50) then
						   true
					  else
					       false	   
					  endif
					otherwise false  
		 		endswitch 					     

    function vss_absolute_to_relative_position($absolute_position in Vss_Units) =
    
    			    if $absolute_position >= 1 and $absolute_position <= 10 then
			    	   	1
			    else if $absolute_position > 10 and $absolute_position <= 20 then 
						2
				 else if $absolute_position > 20 and $absolute_position <= 30 then
				        	  3
				      else if $absolute_position > 30 and $absolute_position <= 40 then 
				              4
				           else if $absolute_position > 40 and $absolute_position <= 50 then 
				                  5
				                else if $absolute_position > 50 and $absolute_position <= 60 then  
				                        6
				                      else if $absolute_position > 60 and $absolute_position <= 70 then  
				                              7
				                           else if $absolute_position > 70 and $absolute_position <= 80 then  
				                                   8     
											   endif
										   endif
									 endif
								endif
						   endif	
					  endif
				   endif	  	   	 	   	   
			    endif							
    
    function position_related_to_vss($vss in Vss) = 

			 switch($vss)
			     case vss_11:
			     				1
			     case vss_12:
			     				2
			     case vss_21:
			     				3
			     case vss_22:
			     				4
			     case vss_23:
			     				5
			     case vss_31:
			     				6
			     case vss_32:
			     				7
			     case vss_33:
			     				8
			 endswitch    																

// VSS related to the train position
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


	function vss_advance_vss($vss in Vss) =
			
			switch($vss)
					case(vss_11):
							vss_12
					case(vss_12):
							vss_21
					case(vss_21):
							vss_22
					case(vss_22):
							vss_23
					case(vss_23):
							vss_31
					case(vss_31):
							vss_32
					case(vss_32):
							vss_33
					case(vss_33):
							vss_33		
			endswitch						

// VSS rear	train
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

	function vss_rear_ttd($vss in Vss, $ttd in Ttd) =
	
			switch($ttd)
				  case ttd_20:
				        if vss_on_ttd($vss) = ttd_10 then
				           true
				        else
				           false   
				        endif
				  case ttd_30:
				        if (vss_on_ttd($vss) = ttd_10) or (vss_on_ttd($vss) = ttd_20) then
				           true
				        else
				           false   
				        endif
			endswitch	                 

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
	
			
			if (train_position($trains) - train_length($trains) + 1) >= 51 and (train_position($trains) - train_length($trains) + 1) <= 80 then
			      ttd_20
			else if  (train_position($trains) - train_length($trains) + 1) >= 21 and (train_position($trains) - train_length($trains) + 1) <= 50 then
			          ttd_10
			      endif
			endif                   
				
	function ma_on_ttd($ttd in Ttd, $trains in Trains) =

			if (ttd_related_to_absolute_position(second(ma($trains))) >= position_related_to_ttd($ttd)) and (ttd_related_to_absolute_position(first(ma($trains))) <= position_related_to_ttd($ttd)) then
			        true
			else
					false
			endif	
			
	
	function ma_on_vss($vss in Vss, $trains in Trains) =

			    if position_related_to_vss($vss) <= vss_absolute_to_relative_position(second(ma($trains))) and  position_related_to_vss($vss) >= vss_absolute_to_relative_position(first(ma($trains)))then
			        true
			    else
					false
			    endif				        	           		

	function vss_on_ttd($vss in Vss) =
		     
	        if ($vss = vss_11) or ($vss = vss_12) then
	            ttd_10
	        else  if ($vss = vss_21) or ($vss = vss_22) or ($vss = vss_23) then
	                   ttd_20	           
	               else if ($vss = vss_31) or ($vss = vss_32) or ($vss = vss_33) then
	                       ttd_30
	                   endif
	               endif
	        endif                                    

// true if train1 is rear train2			 
	function train_rear_another_train($train1 in Trains, $train2 in Trains) =
	
	         if (train_position($train2) - train_length($train2) + 1) > train_position($train1) then 
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
	
	
	// set all trains vss to occupied			
	macro rule  r_vss_train_occupied ($trains in Trains) =	
	
			   forall $position in Vss_Units with (($position <= train_position($trains)) and ($position > (train_position($trains) - train_length($trains))) and $position <= second(ma($trains))) do
			     par
					 vss_states(vss_related_to_train_position($position)) := OCCUPIED
					 vss_prestates(vss_related_to_train_position($position)) := vss_states(vss_related_to_train_position($position))
					 ttd_states(ttd_related_to_position($position)) := TTD_OCCUPIED
					 ttd_prestates(ttd_related_to_position($position)) := ttd_states(ttd_related_to_position($position))						 							       					  
				 endpar	     
 
// set all trains vss to free			
	macro rule  r_vss_train_free ($trains in Trains) =	
	
			  forall $position in Vss_Units with (($position <= train_position($trains)) and ($position > (train_position($trains) - train_length($trains))) and $position <= second(ma($trains))) do
			     par
					 vss_states(vss_related_to_train_position($position)) := FREE
					 vss_prestates(vss_related_to_train_position($position)) := vss_states(vss_related_to_train_position($position))					  
					 if ttd_status_free(vss_on_ttd(vss_related_to_train_position($position))) = TTD_FREE then
					     par
					          ttd_states(ttd_related_to_position($position)) := TTD_FREE
					       	  ttd_prestates(ttd_related_to_position($position)) := ttd_states(ttd_related_to_position($position))
					     endpar	
					 endif      
				 endpar	 
					 
// set all trains vss to unknown			
	macro rule  r_vss_train_unknown ($trains in Trains) =	
	
			  forall $position in Vss_Units with (($position <= train_position($trains)) and ($position > (train_position($trains) - train_length($trains))) and $position <= second(ma($trains))) do
				 par		   
					 vss_states(vss_related_to_train_position($position)) := UNKNOWN
					 vss_prestates(vss_related_to_train_position($position)) := vss_states(vss_related_to_train_position($position))
					 ttd_states(ttd_related_to_position($position)) := TTD_OCCUPIED
					 ttd_prestates(ttd_related_to_position($position)) := ttd_states(ttd_related_to_position($position))				
				 endpar						 					 					 
            
    	macro rule  r_vss_train_ambiguous ($trains in Trains) =	
	
			  forall $position in Vss_Units with (($position <= train_position($trains)) and ($position > (train_position($trains) - train_length($trains))) and $position <= second(ma($trains))) do
				 par	
					 vss_states(vss_related_to_train_position($position)) := AMBIGUOUS
					 vss_prestates(vss_related_to_train_position($position)) := vss_states(vss_related_to_train_position($position))			
					 ttd_states(ttd_related_to_position($position)) := TTD_OCCUPIED
					 ttd_prestates(ttd_related_to_position($position)) := ttd_states(ttd_related_to_position($position))					     
				 endpar	
    
    macro rule r_check_trains_chasing_chased =	
	  
	          forall $trains_in_advance in Trains, $trains in Trains with $trains != $trains_in_advance and train_rear_another_train($trains, $trains_in_advance) do
//	            if (train_position($trains) <  train_position($trains_in_advance) - train_length($trains_in_advance) - min_safe_rear_end($trains_in_advance) + 2) and (train_position($trains) >  (train_position($trains_in_advance) - train_length($trains_in_advance) - min_safe_rear_end($trains_in_advance) - 2)) then
	            if train_position($trains) >= confirmed_safe_rear_end($trains_in_advance) then
	              par  
	                trains_chasing($trains) := true
	                trains_chased($trains_in_advance) := true
	              endpar  
	            else
	            		par  
	                trains_chasing($trains) := false
	                trains_chased($trains_in_advance) := false
	              endpar 
	            endif        
    
    macro rule  r_collect_train_data ($trains in Trains) =
    
    			     par
    				    	collect_trains_data($trains) := (train_position($trains), train_length($trains), train_speed($trains), first(ma($trains)), second(ma($trains)))  
    				    	collect_trains_predata($trains) := collect_trains_data($trains)
    				 endpar   	

	macro rule r_check_rbc_trains_connection = 
	
		  forall $trains in Trains with true do	
			if not rbc_conn($trains) then 
			    par
			       mute_timer($trains) := true
			       trains_confirmed_integrity($trains) := false
			    endpar 
			else
			   par
				  mute_timer($trains) :=false
				  if contains(train_state($trains), NOTCONNECTED) then
				       trains_confirmed_integrity($trains) := false
				  else
				       trains_confirmed_integrity($trains) := true
				  endif          
			   endpar	  
			endif	    

// set Vss Unknown within the MA when the train disconnects from the track	
	macro rule r_Set_Vss_Unknown_Whitinma_When_Train_Disconnected($trains in Trains, $ttd in Ttd, $ma_trains in Prod(Vss_Units,Vss_Units), $trains_position in Vss_Units) =

			    switch($ttd)
					case ttd_20:   //first free TTD
					     forall $vss in Vss with vss_rear_ttd($vss, $ttd) do						    	     
					       if ma_on_vss($vss, $trains) then				    
					           par
						         vss_states($vss) := UNKNOWN
						         vss_prestates($vss) := vss_states($vss)
						       endpar
						   endif     				          
					case ttd_30:  //first free TTD
					 	  forall $vss2 in Vss with vss_rear_ttd($vss2, $ttd) do				     
					       if ma_on_vss($vss2, $trains) then				    
					           par
						         vss_states($vss2) := UNKNOWN
						         vss_prestates($vss2) := vss_states($vss2)
						       endpar
						   endif            
						 // ****** Verificare ***** da completare: se train1 ha una MA di 5 e si trova in posizione 2, la TTD libera è la TTD_30
						 // ****** Verificare se sulla TTD_20 c'è un altro treno (train2) non posso mettere le VSS_21, VSS_22, VSS_23 ad unknown     				         										  
		 		endswitch 	
		    
	macro rule r_check_train_status =
	         	       	         
	         forall $trains in Trains with true do
	          par
	            seq            
	              if lost_integrity($trains) then 
				         train_state($trains) := replaceAt(train_state($trains), 0n, LOSTINTEGRITY)   
				  else
						 train_state($trains) := replaceAt(train_state($trains), 0n, NOTLOSTINTEGRITY)
				  endif
				  if (length_changed($trains)) then
				         train_state($trains) := replaceAt(train_state($trains), 1n, LENGTHCHANGED)
				  else
				  		 train_state($trains) := replaceAt(train_state($trains), 1n, NOTLENGTHCHANGED) 
				  endif
				  if (rbc_conn($trains)) then
				         train_state($trains) := replaceAt(train_state($trains), 2n, CONNECTED)
				  else
				         train_state($trains) := replaceAt(train_state($trains), 2n, NOTCONNECTED) 	
				  endif
				  if (trains_shadow($trains)) then				     	 
				         train_state($trains) := replaceAt(train_state($trains), 3n, SHADOWTRAIN) 			
				  else
				         train_state($trains) := replaceAt(train_state($trains), 3n, NOTSHADOWTRAIN)
				  endif
                   if rbc_conn($trains) and not length_changed($trains) and not lost_integrity($trains) then
				         train_state($trains) := replaceAt(train_state($trains), 4n, INTEGRITYCONFIRMED)		
				  else
				     	 train_state($trains) := replaceAt(train_state($trains), 4n, NOTINTEGRITYCONFIRMED)
				  endif
				endseq   
				train_prestate($trains) :=  train_state($trains)	 
			  endpar  	     	    

	macro rule r_Trains_lostintegrity ($trains in Trains) =
 
                if trains_chased($trains) then
			         if (first(train_state($trains)) = LOSTINTEGRITY) and train_left_vss($trains) and vss_states(vss_related_to_train_position(train_position($trains))) = AMBIGUOUS then
			                   par		       		                    
			                     vss_states(vss_related_to_train_position(train_position($trains))) := UNKNOWN
					             vss_prestates(vss_related_to_train_position(train_position($trains))) :=  vss_states(vss_related_to_train_position(train_position($trains)))
			                  endpar
			          else if lost_integrity($trains) or length_changed($trains) or wait_integrity_timer($trains) then
			                     r_vss_train_ambiguous[$trains]				               
			               endif   		
			         endif
			   endif 

// 3.8.1 Train Disconnection
	macro rule r_Trains_disconnection ($trains in Trains) =

           if not(rbc_conn($trains)) and train_speed($trains) > 0 then
             par
                 r_vss_train_unknown[$trains]
                 if (train_position($trains) <= second(ma($trains))) then
                   forall $ttd in Ttd with (ttd_states($ttd) = TTD_FREE and train_is_rear_ttd($ttd, train_position($trains))) do
                     par 
                        r_Set_Vss_Unknown_Whitinma_When_Train_Disconnected[$trains, $ttd, ma($trains), train_position($trains)] 
                         mute_timer($trains) := true
                     endpar     
                 endif 
                 forall $ttd2 in Ttd with  ma_on_ttd($ttd2, $trains) do
                      if (ttd_states($ttd2) = TTD_OCCUPIED) then 
                         forall $vss in Vss with (vss_on_ttd($vss) = $ttd2) and ma_on_vss($vss, $trains) do
                      	   if vss_states(vss_advance_vss($vss)) = OCCUPIED then
                      	     par  
                      	        vss_states($vss) := UNKNOWN
                      	        vss_prestates($vss) := vss_states($vss)
                      	     endpar
                      	   endif	        
                      endif
                 forall $vss2 in Vss with  not(disconnect_propagation_timer(vss_rear_train($trains)))  do
                    if trains_chased($trains) and ttd_states(vss_on_ttd($vss2)) = TTD_OCCUPIED then  
                       forall $tr in Trains with $tr != $trains do
                          if train_rear_another_train($tr, $trains) and trains_chasing($tr) and (not ma_on_vss($vss2, $tr)) then
                          	par   
                      	        vss_states($vss2) := UNKNOWN
                      	        vss_prestates($vss2) := vss_states($vss2)
                      	     endpar
                          endif
                    endif      
              endpar         	    	
           endif         
          
// 3.5	Operation of trains treated as integer			
	macro rule r_Trains_integer ($trains in Trains) =
	   
	   par
			if trains_confirmed_integrity($trains) and train_position($trains) <= second(ma($trains))  and  rbc_conn($trains) then
			   par  
			     confirmed_safe_rear_end($trains) := train_position($trains) - train_length($trains) - min_safe_rear_end($trains)
			     if (first(train_prestate($trains)) = NOTLOSTINTEGRITY) and not(wait_integrity_timer($trains)) then
			           r_vss_train_occupied[$trains]     
			     endif
			   endpar  
			 else if train_left_vss($trains) and vss_states(vss_rear_train($trains)) = OCCUPIED and train_speed($trains) > 0 then 
			        par			                    
			            vss_states(vss_rear_train($trains)) := UNKNOWN
					    vss_prestates(vss_rear_train($trains)) := vss_states(vss_rear_train($trains))
			        endpar
			      endif       
			 endif        
			 forall $t in Trains with $t != $trains do
			   if (not(rbc_conn($t))) and train_rear_another_train($t, $trains) then
			          trains_shadow($trains) := true
			   else
			       par
			          trains_shadow($trains) := false
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
             
    macro rule r_Trains_connected ($trains in Trains) =
				
				if rbc_conn($trains) then
	         		par
	         			confirmed_safe_rear_end($trains) := train_position($trains) - train_length($trains) - min_safe_rear_end($trains)    	                  	            	    
	            	   	 	mute_timer($trains) :=false
	            	   	 	if contains(train_state($trains), CONNECTED) then
	            	    		    trains_confirmed_integrity($trains) := true
	            	    		else
	            	    		    trains_confirmed_integrity($trains) := false
	            	    		endif       
	        	    		endpar
	        		else
	        	   		par
	        	    			mute_timer($trains) :=true
	            	    		trains_confirmed_integrity($trains) := false
	            		endpar        
	        		endif		 	        	            
		     										
	macro rule r_Trains_Run ($trains in Trains, $vss in Vss) =
	    
			par	    		
	  		   r_Trains_lostintegrity[$trains]
	  		   r_Trains_integer[$trains] 
	  		   r_Trains_disconnection[$trains] 	
			   r_Trains_connected[$trains]
  			endpar	

	macro rule r_Rbc_Supervisor  =

			par 			   			   
			   r_check_train_status[]
			   r_check_rbc_trains_connection[] 			   		   			    			   			   	      
			endpar   
	
	macro rule r_Trains_on_TrackSide =
		  		
	   	   par   
			   forall $position in Vss_Units with $position <= second(ma(self)) and $position >= first(ma(self)) do
			       r_Trains_Run[self, vss_related_to_train_position($position)] 
			   r_collect_train_data[self]        
		   endpar	       		    			        
		   
	macro rule r_TrackController =
			
			r_check_trains_chasing_chased[]	       
	     
	main rule r_Main = 
		  
		  par
			forall $trains in Trains with true do
					program($trains)
			forall $rbc in Rbc with true do
			        program($rbc)
			forall $trackcontrol in TrackController with true do
			        program($trackcontrol)        
		  endpar	        

default init initial_state:
	
	function brake_factor = 4.0
	
	function vss_states ($vss in Vss) = FREE	
	function vss_prestates ($vss in Vss) = FREE
	
	function ttd_states($ttd in Ttd) = TTD_FREE
	function ttd_prestates($ttd in Ttd) = TTD_FREE
	
	function trains_integer($trains in Trains) = false
	function trains_notreconnecting($trains in Trains) = false
	function trains_chased($trains in Trains) = false	
	function trains_chasing($trains in Trains) = false
	function trains_shadow($trains in Trains) = false
	function trains_integrity_info_avail($trains in Trains) = false
	function trains_confirmed_integrity($trains in Trains) = true
	function confirmed_safe_rear_end($trains in Trains) = 0	
	
	function collect_trains_data($trains in Trains) = (0, 0, 0, 0, 0)
	function collect_trains_predata($trains in Trains) = (0, 0, 0, 0, 0)  
	function train_state($trains in Trains) = [NOTLOSTINTEGRITY,  NOTLENGTHCHANGED, CONNECTED, NOTSHADOWTRAIN, INTEGRITYCONFIRMED]
	function train_prestate($trains in Trains) = [NOTLOSTINTEGRITY,  NOTLENGTHCHANGED, CONNECTED, NOTSHADOWTRAIN, INTEGRITYCONFIRMED]

	
	function mute_timer($trains in Trains) = false                  	

	agent TrackController: r_TrackController[]	
	agent Rbc: r_Rbc_Supervisor[]
	agent Trains: r_Trains_on_TrackSide[]
	
		
		
		
		
