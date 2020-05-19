/* example of ASM specification */
//esempio piu' piccolo con tipi per funzionare con atgt

asm for_atgt

import ../STDL/StandardLibrary

//declare universes and functions
		
signature:

// domains 
		
    enum domain Label_IN1_validity = {VALID | NOT_COMPUTED | NOT_VALID}    
    enum domain Label_OUT1_validity = {NORMAL | NCD | FW}     
    enum domain Label_IN2_validity = {TEST | VALIDITY | NOT_VALIDITY | NO_COMPUTED | NA}    
    enum domain Label_IN3_validity = {VALID3 | NOT_VALID3}     
    
    enum domain Label_OUT2_validity = {FUNCTIONAL_TEST | NORMAL_OPERATION | NO_COMPUTED_DATA | FAILURE_WARNING}
 
     enum domain Label_OUT3_validity = {VALIDITY3 | NCD3}
 	
    domain Label_IN1_value subsetof Integer
    
    enum domain Label_IN2_value = {NAV | APPROACH | APPROACH_HOVER}
    
    domain Label_IN3_value subsetof Integer 
    domain Label_IN4_value subsetof Integer
    domain Label_IN7_value subsetof Integer
    domain Label_IN8_value subsetof Integer
    domain Label_IN13_value subsetof Integer                      
    
    domain TimerD subsetof Integer
    
    //domain Label_IN3_value subsetof Real
    
    //domain Label_OUT1 Product(Label_OUT1_value, Label_OUT1_validity)
    
    //domain Label_OUT2 Product(Label_OUT2_value, Label_OUT1_validity)
    
    
    
    
    //enum domain Group_label = {GROUP_LABEL_1 | GROUP_LABEL_2 | GROUP_LABEL_3}
    
    //enum domain Bus = {BUS_ARINC | BUS_AFDX}
    
// functions 
    //label che devono essere mandate sui bus periodicamente ogni 20 ms
    //valori in dall'ambiente esterno
	dynamic monitored  pSEUDO_LOC_DEVIATION_value      : Label_IN3_value //DMM(0.4 - INFINITO)
    dynamic monitored  pSEUDO_GS_DEVIATION_value       : Label_IN4_value //DMM(-0.8 - 0.8)  
    dynamic monitored  pSEUDO_LOC_DEVIATION_validity      : Label_IN1_validity
    dynamic monitored  pSEUDO_GS_DEVIATION_validity       : Label_IN1_validity

//valori out sul bus arinc        
dynamic controlled cpm173PseudoLocDeviation_value      : Label_IN3_value
dynamic controlled cpm174PseudoGsDeviation_value       : Label_IN4_value
dynamic controlled cpm173PseudoLocDeviation_validity      : Label_OUT1_validity
dynamic controlled cpm174PseudoGsDeviation_validity       : Label_OUT1_validity


//label che devono essere mandati sui bus ogni 40 ms

dynamic monitored bEST_MAG_HEADING_value : Label_IN1_value   
dynamic monitored sEL_ADU_COMPUTED_AIRSPEED_value : Label_IN8_value //Kts(0 - 450)                             
dynamic monitored bEST_MAG_HEADING_validity          : Label_IN2_validity                                                                                   
dynamic monitored sEL_ADU_COMPUTED_AIRSPEED_validity : Label_IN2_validity 

dynamic controlled cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_value : Label_IN1_value        
dynamic controlled cPM_FMS_BUS_INDICATED_AIRSPEED_value	  : Label_IN8_value                                                                                                 
dynamic controlled cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_validity  : Label_OUT2_validity
dynamic controlled cPM_FMS_BUS_INDICATED_AIRSPEED_validity	  : Label_OUT2_validity

//label che devono essere mandate sul bus ogni 320 ms

dynamic monitored cIVIL_GPS1_LATITUDE_value : Label_IN13_value //degree(-90 - 90) 
dynamic monitored cIVIL_GPS1_LONGITUDE_value : Label_IN7_value                    
dynamic monitored cIVIL_GPS1_LATITUDE_validity : Label_IN3_validity
dynamic monitored cIVIL_GPS1_LONGITUDE_validity : Label_IN3_validity       

dynamic controlled cPM_MON_ST_GPS1_LATITUDE_DATA : Label_IN13_value 
dynamic controlled cPM_MON_ST_GPS1_LONGITUDE_DATA : Label_IN7_value 
dynamic controlled cPM_MON_ST_GPS1_LATITUDE_FSS : Label_OUT3_validity 
dynamic controlled cPM_MON_ST_GPS1_LONGITUDE_FSS : Label_OUT3_validity 
 
 //timer per simulare i ms passati   
    dynamic controlled timer : TimerD
    
    //static switch_label : Group_label -> Integer
    

// static functions 

	//static odd : Natural -> Boolean

// definitions 

definitions:

  domain TimerD = {0 : 720}
  domain Label_IN1_value = {-360 : 360}
  domain Label_IN3_value = {0 : 1000}
  domain Label_IN4_value = {-1 : 1}
  domain Label_IN7_value = {-180 : 180}
  domain Label_IN8_value = {0 : 450}
  domain Label_IN13_value = {-90 : 90}
  
          
                       
macro rule r_grouplabel1 = 
                        //par switch_label(GROUP_LABEL_1) 
                          if (pSEUDO_LOC_DEVIATION_validity = VALID) and                                           	
                             (pSEUDO_GS_DEVIATION_validity = VALID)                                                                                        
                         then                                                                                
                            par                                                                              
                              cpm173PseudoLocDeviation_validity := NORMAL                                 
                              cpm173PseudoLocDeviation_value := pSEUDO_LOC_DEVIATION_value                                
                            endpar                                                                       
                         else if (pSEUDO_LOC_DEVIATION_validity = NOT_COMPUTED) and                     
                                 (pSEUDO_GS_DEVIATION_validity = NOT_COMPUTED) then                    
                            par                                                                          
                              cpm173PseudoLocDeviation_validity := NCD                                   
                              cpm174PseudoGsDeviation_validity := NCD                                                                       
                            endpar                                                                      
                         else if (pSEUDO_LOC_DEVIATION_validity = NOT_VALID) and                    
                                 (pSEUDO_GS_DEVIATION_validity = NOT_VALID) then                   
                            par                                                                          
                              cpm173PseudoLocDeviation_validity := FW                                   
                              cpm174PseudoGsDeviation_validity := FW                                                                       
                            endpar                                                                         
                         endif                                                                          
                         endif
                         endif
                         //endpar 
                             
macro rule r_grouplabel2 =
                         //par switch_label(GROUP_LABEL_2) 
                           if (bEST_MAG_HEADING_validity = VALIDITY) and 
                              (sEL_ADU_COMPUTED_AIRSPEED_validity = VALIDITY)   
                            then
                             par
                               cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_value := bEST_MAG_HEADING_value
                               cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_validity := NORMAL_OPERATION                                                                           
                               cPM_FMS_BUS_INDICATED_AIRSPEED_value := sEL_ADU_COMPUTED_AIRSPEED_value
                               cPM_FMS_BUS_INDICATED_AIRSPEED_validity := NORMAL_OPERATION  	          
                               
                             endpar
                           else if (bEST_MAG_HEADING_validity = TEST) and  
                              (sEL_ADU_COMPUTED_AIRSPEED_validity = TEST) then
                              par
                               cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_validity := FUNCTIONAL_TEST  
                               cPM_FMS_BUS_INDICATED_AIRSPEED_validity := FUNCTIONAL_TEST	  	          
                               
                              endpar 
                           else if (bEST_MAG_HEADING_validity = NO_COMPUTED) and
                              (sEL_ADU_COMPUTED_AIRSPEED_validity = NO_COMPUTED) then
                              par
                               cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_validity := NO_COMPUTED_DATA  
                               cPM_FMS_BUS_INDICATED_AIRSPEED_validity := NO_COMPUTED_DATA	            
                             endpar  
                           else if ((bEST_MAG_HEADING_validity = NOT_VALIDITY)or (bEST_MAG_HEADING_validity = NA))
                               and
                              ((sEL_ADU_COMPUTED_AIRSPEED_validity = NOT_VALIDITY) or (sEL_ADU_COMPUTED_AIRSPEED_validity = NA))       
                              then 
                             par
                               cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_validity := FAILURE_WARNING  
                               cPM_FMS_BUS_INDICATED_AIRSPEED_validity := FAILURE_WARNING	  	          
                             endpar           
                           endif 
                           endif 
                           endif endif                                               
                      	 //endpar 
                      	 
macro rule r_grouplabel3 =
                             par                            
                              cPM_MON_ST_GPS1_LATITUDE_DATA           := cIVIL_GPS1_LATITUDE_value         
                              cPM_MON_ST_GPS1_LONGITUDE_DATA          := cIVIL_GPS1_LONGITUDE_value       
                                        
                            if (cIVIL_GPS1_LATITUDE_validity  = VALID3) and
                               (cIVIL_GPS1_LONGITUDE_validity = VALID3) then
                             par   
                              cPM_MON_ST_GPS1_LATITUDE_FSS           := VALIDITY3 
                              cPM_MON_ST_GPS1_LONGITUDE_FSS          := VALIDITY3     
                             endpar                            
                            else if (cIVIL_GPS1_LATITUDE_validity  = NOT_VALID3) and
                                    (cIVIL_GPS1_LONGITUDE_validity = NOT_VALID3) then
                              par    
                               cPM_MON_ST_GPS1_LATITUDE_FSS           := NCD3 
                               cPM_MON_ST_GPS1_LONGITUDE_FSS          := NCD3
                              endpar 
                            endif endif    
                           endpar                       	   
                      	                         	         	
	
	macro rule r_send_labels =//while (timer <= 200) do
	                        par 
	                          if timer != 0 then
	                           par                            
	                            if (timer mod 20) = 0 then r_grouplabel1[]	                            
	                            endif 
	                            if (timer mod 40) = 0 then r_grouplabel2[]	                                                                                                                                                                   
	                            endif
	                            if (timer mod 320) = 0 then r_grouplabel3[]	                             
	                            endif
	                           endpar	                              	                            
	                          endif
	                          timer := timer + 10
	                        endpar 
	                            
//Main Rule
    
    main rule r_fmsio = r_send_labels[]                            
                          
                           

	//main rule r_S = par r_1[] r_2[] endpar
	
	 
//define the initial states 

default init s0:
	
	//function prova  = true
	
function timer = 0
function cpm173PseudoLocDeviation_validity = NCD     
function cpm173PseudoLocDeviation_value    = 0     
function cpm174PseudoGsDeviation_validity  = NCD     
function cpm174PseudoGsDeviation_value     = 0     

function cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_validity = NO_COMPUTED_DATA  
function cPM_FMS_BUS_INDICATED_AIRSPEED_validity = NO_COMPUTED_DATA	  
function cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_value = 0  
function cPM_FMS_BUS_INDICATED_AIRSPEED_value = 0	  

function cPM_MON_ST_GPS1_LATITUDE_DATA           = 0
function cPM_MON_ST_GPS1_LONGITUDE_DATA          = 0
function cPM_MON_ST_GPS1_LATITUDE_FSS           = NCD3
function cPM_MON_ST_GPS1_LONGITUDE_FSS          = NCD3



	

		
