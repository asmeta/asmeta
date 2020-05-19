/* example of ASM specification */
//MODELLO ASM DI TEST PER LA COMPONENETE FMS_IO

asm FMS_IO

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
 	
    domain Label_IN1_value subsetof Real
    
    enum domain Label_IN2_value = {NAV | APPROACH | APPROACH_HOVER}
    
    domain Label_IN3_value subsetof Real
    domain Label_IN4_value subsetof Real
    domain Label_IN5_value subsetof Real
    domain Label_IN6_value subsetof Real 
    domain Label_IN7_value subsetof Real
    domain Label_IN8_value subsetof Real
    domain Label_IN9_value subsetof Real
    domain Label_IN10_value subsetof Real               
    domain Label_IN11_value subsetof Real
    domain Label_IN12_value subsetof Real
    domain Label_IN13_value subsetof Real               
    enum domain Label_IN14_value = {NONE | INIT | CIVIL_GPS_1 | CIVIL_GPS_2 | EGI_DVS_INS | EGI_PURE_INS | DME_DME | VOR_DME | AMMC | EGI_PURE_GPS | EGI_GPS_INS}       
    
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
    dynamic monitored  dISTANCE_TO_FPCP_value          : Label_IN5_value //NM (-512 - 512)
    dynamic monitored  pSEUDO_GS_PATH_ANGLE_value      : Label_IN6_value //DEGREE(-45 - 45)
    dynamic monitored  sELECTED_RUNWAY_HEADING_value   : Label_IN7_value //DEGREE(-180 - 180)
    dynamic monitored  cROSS_TRACK_DISTANCE_value      : Label_IN1_value //NM(-128 - 128)
    dynamic monitored  vERTICAL_LINEAR_DEVIATION_value : Label_IN1_value 
    dynamic monitored  lATERAL_SCALE_FACTOR_value      : Label_IN1_value 
    dynamic monitored  vERTICAL_SCALE_FACTOR_value     : Label_IN1_value 
    dynamic monitored  oPERATING_MODE_PHASE_value      : Label_IN2_value //NIENTE UNITA DI MISURA   
    dynamic monitored  pSEUDO_LOC_DEVIATION_validity      : Label_IN1_validity
    dynamic monitored  pSEUDO_GS_DEVIATION_validity       : Label_IN1_validity
    dynamic monitored  dISTANCE_TO_FPCP_validity          : Label_IN1_validity
    dynamic monitored  pSEUDO_GS_PATH_ANGLE_validity      : Label_IN1_validity
    dynamic monitored  sELECTED_RUNWAY_HEADING_validity   : Label_IN1_validity
    dynamic monitored  cROSS_TRACK_DISTANCE_validity      : Label_IN1_validity
    dynamic monitored  vERTICAL_LINEAR_DEVIATION_validity : Label_IN1_validity
    dynamic monitored  lATERAL_SCALE_FACTOR_validity      : Label_IN1_validity
    dynamic monitored  vERTICAL_SCALE_FACTOR_validity     : Label_IN1_validity
    dynamic monitored  oPERATING_MODE_PHASE_validity      : Label_IN1_validity

//valori out sul bus arinc        
dynamic controlled cpm173PseudoLocDeviation_value      : Label_IN3_value
dynamic controlled cpm174PseudoGsDeviation_value       : Label_IN4_value
dynamic controlled cpm177DistanceToFpcp_value          : Label_IN5_value
dynamic controlled cpm322PseudoGsPathAngle_value       : Label_IN6_value
dynamic controlled cpm105SelectedRunWayHeading_value   : Label_IN7_value
dynamic controlled cpm116CrossTrackDistance_value      : Label_IN1_value
dynamic controlled cpm117VerticalLinearDeviation_value : Label_IN1_value
dynamic controlled cpm326LateralScaleFactor_value      : Label_IN1_value
dynamic controlled cpm327VerticalScaleFactor_value     : Label_IN1_value
dynamic controlled cpm275OperatingModePhase_value      : Label_IN2_value
dynamic controlled cpm173PseudoLocDeviation_validity      : Label_OUT1_validity
dynamic controlled cpm174PseudoGsDeviation_validity       : Label_OUT1_validity
dynamic controlled cpm177DistanceToFpcp_validity          : Label_OUT1_validity
dynamic controlled cpm322PseudoGsPathAngle_validity       : Label_OUT1_validity
dynamic controlled cpm105SelectedRunWayHeading_validity   : Label_OUT1_validity
dynamic controlled cpm116CrossTrackDistance_validity      : Label_OUT1_validity
dynamic controlled cpm117VerticalLinearDeviation_validity : Label_OUT1_validity
dynamic controlled cpm326LateralScaleFactor_validity      : Label_OUT1_validity
dynamic controlled cpm327VerticalScaleFactor_validity     : Label_OUT1_validity
dynamic controlled cpm275OperatingModePhase_validity      : Label_OUT1_validity

//label che devono essere mandati sui bus ogni 40 ms

dynamic monitored bEST_MAG_HEADING_value : Real   
dynamic monitored sEL_ADU_COMPUTED_AIRSPEED_value : Label_IN8_value //Kts(0 - 450)               
dynamic monitored bEST_VELOCITY_VERT_value        : Label_IN9_value //Ft/min(-20000 - 20000)              
dynamic monitored sEL_ADU_TRUE_AIRSPEED_value     : Label_IN10_value //Kts(0 - 599)               
dynamic monitored gROUND_SPEED_value              : Real //kts               
dynamic monitored tRACK_value                     : Real //degree               
dynamic monitored bEST_ROLL_value                 : Real //degree              
dynamic monitored sEL_AHRS_VERT_VEL_INERT_value   : Label_IN9_value //ft/min               
dynamic monitored bEST_TRUE_HEADING_value         : Label_IN7_value //degree(-180 - 180)               
dynamic monitored sEL_RA_ALTITUDE_value           : Label_IN11_value //ft(-8192 - 8192)              
dynamic monitored sEL_WIND_AT_ALT_SPEED_value     : Real // kts               
dynamic monitored sEL_WIND_AT_ALT_ANGLE_value     : Real //degree               

dynamic monitored bEST_MAG_HEADING_validity          : Label_IN2_validity                                                                                   
dynamic monitored sEL_ADU_COMPUTED_AIRSPEED_validity : Label_IN2_validity
dynamic monitored bEST_VELOCITY_VERT_validity        : Label_IN2_validity
dynamic monitored sEL_ADU_TRUE_AIRSPEED_validity     : Label_IN2_validity
dynamic monitored gROUND_SPEED_validity              : Label_IN2_validity
dynamic monitored tRACK_validity                     : Label_IN2_validity
dynamic monitored bEST_ROLL_validity                 : Label_IN2_validity
dynamic monitored sEL_AHRS_VERT_VEL_INERT_validity   : Label_IN2_validity
dynamic monitored bEST_TRUE_HEADING_validity         : Label_IN2_validity
dynamic monitored sEL_RA_ALTITUDE_validity           : Label_IN2_validity
dynamic monitored sEL_WIND_AT_ALT_SPEED_validity     : Label_IN2_validity
dynamic monitored sEL_WIND_AT_ALT_ANGLE_validity     : Label_IN2_validity  

dynamic controlled cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_value : Real        
dynamic controlled cPM_FMS_BUS_INDICATED_AIRSPEED_value	  : Label_IN8_value        
dynamic controlled cPM_FMS_BUS_VERTICAL_SPEED_value       : Label_IN9_value        
dynamic controlled cPM_FMS_BUS_TRUE_AIRSPEED_value	      : Label_IN10_value        
dynamic controlled cPM_FMS_BUS_GROUND_SPEED_value	        : Real        
dynamic controlled cPM_FMS_BUS_TRACK_ANGLE_value		      : Real        
dynamic controlled cPM_FMS_BUS_ROLL_ANGLE_value		        : Real        
dynamic controlled cPM_FMS_BUS_INERT_VERTICAL_SPEED_value : Label_IN9_value        
dynamic controlled cPM_FMS_BUS_TRUE_HEADING_value		      : Label_IN7_value        
dynamic controlled cPM_FMS_BUS_RADIO_HEIGHT_value		      : Label_IN11_value        
dynamic controlled cPM_FMS_BUS_WIND_SPEED_value	          : Real        
dynamic controlled cPM_FMS_BUS_WIND_ANGLE_value	          : Real        
                                                                                   
dynamic controlled cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_validity  : Label_OUT2_validity
dynamic controlled cPM_FMS_BUS_INDICATED_AIRSPEED_validity	  : Label_OUT2_validity
dynamic controlled cPM_FMS_BUS_VERTICAL_SPEED_validity        : Label_OUT2_validity
dynamic controlled cPM_FMS_BUS_TRUE_AIRSPEED_validity	        : Label_OUT2_validity
dynamic controlled cPM_FMS_BUS_GROUND_SPEED_validity	        : Label_OUT2_validity
dynamic controlled cPM_FMS_BUS_TRACK_ANGLE_validity		        : Label_OUT2_validity
dynamic controlled cPM_FMS_BUS_ROLL_ANGLE_validity		        : Label_OUT2_validity
dynamic controlled cPM_FMS_BUS_INERT_VERTICAL_SPEED_validity  : Label_OUT2_validity
dynamic controlled cPM_FMS_BUS_TRUE_HEADING_validity		      : Label_OUT2_validity
dynamic controlled cPM_FMS_BUS_RADIO_HEIGHT_validity		      : Label_OUT2_validity
dynamic controlled cPM_FMS_BUS_WIND_SPEED_validity	          : Label_OUT2_validity
dynamic controlled cPM_FMS_BUS_WIND_ANGLE_validity	          : Label_OUT2_validity

//label che devono essere mandate sul bus ogni 320 ms

dynamic monitored cIVIL_GPS1_LATITUDE_value : Label_IN13_value //degree(-90 - 90) 
dynamic monitored cIVIL_GPS1_LONGITUDE_value : Label_IN7_value
dynamic monitored cIVIL_GPS2_LATITUDE_value : Label_IN13_value //degree(-90 - 90)
dynamic monitored cIVIL_GPS2_LONGITUDE_value : Label_IN7_value
dynamic monitored iNIT_LAT_value : Label_IN13_value //degree(-90 - 90)          
dynamic monitored iNIT_LONG_value : Label_IN7_value    
dynamic monitored aMMC_LAT_value : Label_IN13_value //degree(-90 - 90)   
dynamic monitored aMMC_LONG_value : Label_IN7_value   
dynamic monitored bEST_LAT_value : Label_IN13_value //degree(-90 - 90)   
dynamic monitored bEST_LONG_value : Label_IN7_value    
dynamic monitored bEST_POSN_SOURCE_value : Label_IN14_value  
dynamic monitored dME_DME_LAT_value : Label_IN13_value //degree(-90 - 90)       
dynamic monitored dME_DME_LONG_value : Label_IN7_value      
dynamic monitored vOR_DME_LAT_value : Label_IN13_value //degree(-90 - 90)       
dynamic monitored vOR_DME_LONG_value : Label_IN7_value      
dynamic monitored pOSN_DIFF_CIVIL_GPS1_value : Label_IN12_value //NM(0 - 100)
dynamic monitored pOSN_DIFF_CIVIL_GPS2_value : Label_IN12_value
dynamic monitored pOSN_DIFF_DME_DME_value : Label_IN12_value   
dynamic monitored pOSN_DIFF_VOR_DME_value : Label_IN12_value
dynamic monitored pOSN_DIFF_AMMC_value : Label_IN12_value
dynamic monitored sEL_ADU_BARO_CORR_ALTITUDE_value : Real //feet
dynamic monitored bEST_MSL_ALT_value : Real 
                   
dynamic monitored cIVIL_GPS1_LATITUDE_validity : Label_IN3_validity
dynamic monitored cIVIL_GPS1_LONGITUDE_validity : Label_IN3_validity
dynamic monitored cIVIL_GPS2_LATITUDE_validity : Label_IN3_validity
dynamic monitored cIVIL_GPS2_LONGITUDE_validity : Label_IN3_validity
dynamic monitored iNIT_LAT_validity : Label_IN3_validity          
dynamic monitored iNIT_LONG_validity : Label_IN3_validity    
dynamic monitored aMMC_LAT_validity : Label_IN3_validity   
dynamic monitored aMMC_LONG_validity : Label_IN3_validity   
dynamic monitored bEST_LAT_validity : Label_IN3_validity   
dynamic monitored bEST_LONG_validity : Label_IN3_validity    
dynamic monitored bEST_POSN_SOURCE_validity : Label_IN3_validity  
dynamic monitored dME_DME_LAT_validity : Label_IN3_validity       
dynamic monitored dME_DME_LONG_validity : Label_IN3_validity      
dynamic monitored vOR_DME_LAT_validity : Label_IN3_validity       
dynamic monitored vOR_DME_LONG_validity : Label_IN3_validity      
dynamic monitored pOSN_DIFF_CIVIL_GPS1_validity : Label_IN3_validity
dynamic monitored pOSN_DIFF_CIVIL_GPS2_validity : Label_IN3_validity
dynamic monitored pOSN_DIFF_DME_DME_validity : Label_IN3_validity   
dynamic monitored pOSN_DIFF_VOR_DME_validity : Label_IN3_validity
dynamic monitored pOSN_DIFF_AMMC_validity : Label_IN3_validity
dynamic monitored sEL_ADU_BARO_CORR_ALTITUDE_validity : Label_IN3_validity
dynamic monitored bEST_MSL_ALT_validity : Label_IN3_validity       

dynamic controlled cPM_MON_ST_GPS1_LATITUDE_DATA : Label_IN13_value 
dynamic controlled cPM_MON_ST_GPS1_LONGITUDE_DATA : Label_IN7_value
dynamic controlled cPM_MON_ST_GPS2_LATITUDE_DATA : Label_IN13_value 
dynamic controlled cPM_MON_ST_GPS2_LONGITUDE_DATA : Label_IN7_value
dynamic controlled cPM_MON_ST_INIT_LATITUDE_DATA : Label_IN13_value                                            
dynamic controlled cPM_MON_ST_INIT_LONGITUDE_DATA : Label_IN7_value           
dynamic controlled cPM_MON_ST_AMMC_DEAD_REC_LATITUDE_DATA : Label_IN13_value   
dynamic controlled cPM_MON_ST_AMMC_DEAD_REC_LONGITUDE_DATA : Label_IN7_value  
dynamic controlled cPM_MON_ST_BEST_LATITUDE_DATA : Label_IN13_value             
dynamic controlled cPM_MON_ST_BEST_LONGITUDE_DATA : Label_IN7_value           
dynamic controlled cPM_MON_ST_BEST_POSN_SOURCE_DATA : Label_IN14_value         
dynamic controlled cPM_MON_ST_DME_DME_LATITUDE_DATA : Label_IN13_value         
dynamic controlled cPM_MON_ST_DME_DME_LONGITUDE_DATA : Label_IN7_value        
dynamic controlled cPM_MON_ST_VOR_DME_LATITUDE_DATA : Label_IN13_value         
dynamic controlled cPM_MON_ST_VOR_DME_LONGITUDE_DATA : Label_IN7_value 
dynamic controlled cPM_MON_ST_POSN_DIFF_CIVIL_GPS1_data : Label_IN12_value       
dynamic controlled cPM_MON_ST_POSN_DIFF_CIVIL_GPS2_data : Label_IN12_value 
dynamic controlled cPM_MON_ST_POSN_DIFF_DME_DME_data : Label_IN12_value      
dynamic controlled cPM_MON_ST_POSN_DIFF_VOR_DME_data : Label_IN12_value      
dynamic controlled cPM_MON_ST_POSN_DIFF_AMMC_data : Label_IN12_value  
dynamic controlled cPM_MON_ST_BEST_BAR_ALT_CORR_data : Real          
dynamic controlled cPM_MON_ST_BEST_MSL_ALT_data : Real 

dynamic controlled cPM_MON_ST_GPS1_LATITUDE_FSS : Label_OUT3_validity 
dynamic controlled cPM_MON_ST_GPS1_LONGITUDE_FSS : Label_OUT3_validity
dynamic controlled cPM_MON_ST_GPS2_LATITUDE_FSS : Label_OUT3_validity
dynamic controlled cPM_MON_ST_GPS2_LONGITUDE_FSS : Label_OUT3_validity
dynamic controlled cPM_MON_ST_INIT_LATITUDE_FSS : Label_OUT3_validity                                            
dynamic controlled cPM_MON_ST_INIT_LONGITUDE_FSS : Label_OUT3_validity           
dynamic controlled cPM_MON_ST_AMMC_DEAD_REC_LATITUDE_FSS : Label_OUT3_validity   
dynamic controlled cPM_MON_ST_AMMC_DEAD_REC_LONGITUDE_FSS : Label_OUT3_validity  
dynamic controlled cPM_MON_ST_BEST_LATITUDE_FSS : Label_OUT3_validity             
dynamic controlled cPM_MON_ST_BEST_LONGITUDE_FSS : Label_OUT3_validity           
dynamic controlled cPM_MON_ST_BEST_POSN_SOURCE_FSS : Label_OUT3_validity         
dynamic controlled cPM_MON_ST_DME_DME_LATITUDE_FSS : Label_OUT3_validity         
dynamic controlled cPM_MON_ST_DME_DME_LONGITUDE_FSS : Label_OUT3_validity        
dynamic controlled cPM_MON_ST_VOR_DME_LATITUDE_FSS : Label_OUT3_validity         
dynamic controlled cPM_MON_ST_VOR_DME_LONGITUDE_FSS : Label_OUT3_validity 
dynamic controlled cPM_MON_ST_POSN_DIFF_CIVIL_GPS1_FSS : Label_OUT3_validity       
dynamic controlled cPM_MON_ST_POSN_DIFF_CIVIL_GPS2_FSS : Label_OUT3_validity 
dynamic controlled cPM_MON_ST_POSN_DIFF_DME_DME_FSS : Label_OUT3_validity      
dynamic controlled cPM_MON_ST_POSN_DIFF_VOR_DME_FSS : Label_OUT3_validity      
dynamic controlled cPM_MON_ST_POSN_DIFF_AMMC_FSS : Label_OUT3_validity  
dynamic controlled cPM_MON_ST_BEST_BAR_ALT_CORR_FSS : Label_OUT3_validity          
dynamic controlled cPM_MON_ST_BEST_MSL_ALT_FSS : Label_OUT3_validity 
 
 //timer per simulare i ms passati   
    dynamic controlled timer : TimerD
    
    //static switch_label : Group_label -> Integer
    

// static functions 

	//static odd : Natural -> Boolean

// definitions 

definitions:

  domain TimerD = {0 : 720}
  domain Label_IN1_value = {-128.0 : 128.0}
  domain Label_IN3_value = {0.0 : 1000.0}
  domain Label_IN4_value = {-0.8 : 0.8}
  domain Label_IN5_value = {-512.0 : 512.0}
  domain Label_IN6_value = {-45.0 : 45.0}
  domain Label_IN7_value = {-180.0 : 180.0}
  domain Label_IN8_value = {0.0 : 450.0}
  domain Label_IN9_value = {-200.0 : 200.0}  //deve essere 20000
  domain Label_IN10_value = {0.0 : 599.0}
  domain Label_IN11_value = {-80.0 : 80.0}//{-8192.0 : 8192.0}
  domain Label_IN12_value = {0.0 : 100.0}
  domain Label_IN13_value = {-90.0 : 90.0}
  
          
                       
macro rule r_grouplabel1   =
                        //par switch_label(GROUP_LABEL_1) 
                          if (pSEUDO_LOC_DEVIATION_validity = VALID) and                                           	
                             (pSEUDO_GS_DEVIATION_validity = VALID) and                                                    
                             (dISTANCE_TO_FPCP_validity = VALID) and                                                       
                             (pSEUDO_GS_PATH_ANGLE_validity = VALID) and                                                   
                             (sELECTED_RUNWAY_HEADING_validity = VALID) and                                                
                             (cROSS_TRACK_DISTANCE_validity = VALID) and                                                   
                             (vERTICAL_LINEAR_DEVIATION_validity = VALID) and                                                
                             (lATERAL_SCALE_FACTOR_validity = VALID) and                                                    
                             (vERTICAL_SCALE_FACTOR_validity = VALID) and                                           
                             (oPERATING_MODE_PHASE_validity = VALID)                                      
                         then                                                                                
                            par                                                                              
                              cpm173PseudoLocDeviation_validity := NORMAL                                 
                              cpm173PseudoLocDeviation_value := pSEUDO_LOC_DEVIATION_value                
                              cpm174PseudoGsDeviation_validity := NORMAL                                  
                              cpm174PseudoGsDeviation_value := pSEUDO_GS_DEVIATION_value                  
                              cpm177DistanceToFpcp_validity := NORMAL                                     
                              cpm177DistanceToFpcp_value := dISTANCE_TO_FPCP_value                        
                              cpm322PseudoGsPathAngle_validity := NORMAL                                  
                              cpm322PseudoGsPathAngle_value := pSEUDO_GS_PATH_ANGLE_value                 
                              cpm105SelectedRunWayHeading_validity := NORMAL                              
                              cpm105SelectedRunWayHeading_value := sELECTED_RUNWAY_HEADING_value          
                              cpm116CrossTrackDistance_validity := NORMAL                                 
                              cpm116CrossTrackDistance_value := cROSS_TRACK_DISTANCE_value                
                              cpm117VerticalLinearDeviation_validity := NORMAL                            
                              cpm117VerticalLinearDeviation_value := vERTICAL_LINEAR_DEVIATION_value      
                              cpm326LateralScaleFactor_validity := NORMAL                                 
                              cpm326LateralScaleFactor_value := lATERAL_SCALE_FACTOR_value                
                              cpm327VerticalScaleFactor_validity := NORMAL                                
                              cpm327VerticalScaleFactor_value := vERTICAL_SCALE_FACTOR_value              
                              cpm275OperatingModePhase_validity := NORMAL                                 
                              cpm275OperatingModePhase_value := oPERATING_MODE_PHASE_value                
                            endpar                                                                       
                         else if (pSEUDO_LOC_DEVIATION_validity = NOT_COMPUTED) and                     
                                 (pSEUDO_GS_DEVIATION_validity = NOT_COMPUTED) and                      
                                 (dISTANCE_TO_FPCP_validity = NOT_COMPUTED) and                         
                                 (pSEUDO_GS_PATH_ANGLE_validity = NOT_COMPUTED) and                     
                                 (sELECTED_RUNWAY_HEADING_validity = NOT_COMPUTED) and                  
                                 (cROSS_TRACK_DISTANCE_validity = NOT_COMPUTED) and                     
                                 (vERTICAL_LINEAR_DEVIATION_validity = NOT_COMPUTED) and                
                                 (lATERAL_SCALE_FACTOR_validity = NOT_COMPUTED) and                     
                                 (vERTICAL_SCALE_FACTOR_validity = NOT_COMPUTED) and                    
                                 (oPERATING_MODE_PHASE_validity = NOT_COMPUTED) then                    
                            par                                                                          
                              cpm173PseudoLocDeviation_validity := NCD                                   
                              cpm174PseudoGsDeviation_validity := NCD                                    
                              cpm177DistanceToFpcp_validity := NCD                                       
                              cpm322PseudoGsPathAngle_validity := NCD                                    
                              cpm105SelectedRunWayHeading_validity := NCD                                
                              cpm116CrossTrackDistance_validity := NCD                                   
                              cpm117VerticalLinearDeviation_validity := NCD                              
                              cpm326LateralScaleFactor_validity := NCD                                   
                              cpm327VerticalScaleFactor_validity := NCD                                  
                              cpm275OperatingModePhase_validity := NCD                                   
                            endpar                                                                      
                         else if (pSEUDO_LOC_DEVIATION_validity = NOT_VALID) and                    
                                 (pSEUDO_GS_DEVIATION_validity = NOT_VALID) and                     
                                 (dISTANCE_TO_FPCP_validity = NOT_VALID) and                        
                                 (pSEUDO_GS_PATH_ANGLE_validity = NOT_VALID) and                    
                                 (sELECTED_RUNWAY_HEADING_validity = NOT_VALID) and                 
                                 (cROSS_TRACK_DISTANCE_validity = NOT_VALID) and                    
                                 (vERTICAL_LINEAR_DEVIATION_validity = NOT_VALID) and               
                                 (lATERAL_SCALE_FACTOR_validity = NOT_VALID) and                    
                                 (vERTICAL_SCALE_FACTOR_validity = NOT_VALID) and                   
                                 (oPERATING_MODE_PHASE_validity = NOT_VALID) then                   
                            par                                                                          
                              cpm173PseudoLocDeviation_validity := FW                                   
                              cpm174PseudoGsDeviation_validity := FW                                    
                              cpm177DistanceToFpcp_validity := FW                                       
                              cpm322PseudoGsPathAngle_validity := FW                                    
                              cpm105SelectedRunWayHeading_validity := FW                                
                              cpm116CrossTrackDistance_validity := FW                                   
                              cpm117VerticalLinearDeviation_validity := FW                              
                              cpm326LateralScaleFactor_validity := FW                                   
                              cpm327VerticalScaleFactor_validity := FW                                  
                              cpm275OperatingModePhase_validity := FW                                   
                            endpar                                                                         
                         endif                                                                          
                         endif
                         endif
                         //endpar 
                             
macro rule r_grouplabel2 =
                         //par switch_label(GROUP_LABEL_2) 
                           if (bEST_MAG_HEADING_validity = VALIDITY) and 
                              (sEL_ADU_COMPUTED_AIRSPEED_validity = VALIDITY) and
                              (bEST_VELOCITY_VERT_validity = VALIDITY) and       
                              (sEL_ADU_TRUE_AIRSPEED_validity = VALIDITY) and    
                              (gROUND_SPEED_validity = VALIDITY) and             
                              (tRACK_validity = VALIDITY) and                    
                              (bEST_ROLL_validity = VALIDITY) and                
                              (sEL_AHRS_VERT_VEL_INERT_validity = VALIDITY) and
                              (bEST_TRUE_HEADING_validity = VALIDITY) and         
                              (sEL_RA_ALTITUDE_validity = VALIDITY) and          
                              (sEL_WIND_AT_ALT_SPEED_validity = VALIDITY) and    
                              (sEL_WIND_AT_ALT_ANGLE_validity = VALIDITY)   
                            then
                             par
                               cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_value := bEST_MAG_HEADING_value
                               cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_validity := NORMAL_OPERATION                                                                           
                               cPM_FMS_BUS_INDICATED_AIRSPEED_value := sEL_ADU_COMPUTED_AIRSPEED_value
                               cPM_FMS_BUS_INDICATED_AIRSPEED_validity := NORMAL_OPERATION  
                               cPM_FMS_BUS_VERTICAL_SPEED_value := bEST_VELOCITY_VERT_value
                               cPM_FMS_BUS_VERTICAL_SPEED_validity := NORMAL_OPERATION
                               cPM_FMS_BUS_TRUE_AIRSPEED_value := sEL_ADU_TRUE_AIRSPEED_value       
                               cPM_FMS_BUS_TRUE_AIRSPEED_validity := NORMAL_OPERATION	
                                  cPM_FMS_BUS_GROUND_SPEED_value := gROUND_SPEED_value       
                                  cPM_FMS_BUS_GROUND_SPEED_validity := NORMAL_OPERATION
                                  cPM_FMS_BUS_TRACK_ANGLE_value	:= tRACK_value       
                                  cPM_FMS_BUS_TRACK_ANGLE_validity := NORMAL_OPERATION
                                  cPM_FMS_BUS_ROLL_ANGLE_value := bEST_ROLL_value		        
                                  cPM_FMS_BUS_ROLL_ANGLE_validity := NORMAL_OPERATION
                                  cPM_FMS_BUS_INERT_VERTICAL_SPEED_value :=	sEL_AHRS_VERT_VEL_INERT_value	        
                                  cPM_FMS_BUS_INERT_VERTICAL_SPEED_validity := NORMAL_OPERATION
                                  cPM_FMS_BUS_TRUE_HEADING_value := bEST_TRUE_HEADING_value 
                                  cPM_FMS_BUS_TRUE_HEADING_validity := NORMAL_OPERATION
                                  cPM_FMS_BUS_RADIO_HEIGHT_value :=	sEL_RA_ALTITUDE_value	      
                                  cPM_FMS_BUS_RADIO_HEIGHT_validity := NORMAL_OPERATION
                                  cPM_FMS_BUS_WIND_SPEED_value := sEL_WIND_AT_ALT_SPEED_value		      
                                  cPM_FMS_BUS_WIND_SPEED_validity := NORMAL_OPERATION	
                                  cPM_FMS_BUS_WIND_ANGLE_value := sEL_WIND_AT_ALT_ANGLE_value        
                                  cPM_FMS_BUS_WIND_ANGLE_validity := NORMAL_OPERATION	          
                               
                             endpar
                           else if (bEST_MAG_HEADING_validity = TEST) and  
                              (sEL_ADU_COMPUTED_AIRSPEED_validity = TEST) and
                              (bEST_VELOCITY_VERT_validity = TEST) and       
                              (sEL_ADU_TRUE_AIRSPEED_validity = TEST) and    
                              (gROUND_SPEED_validity = TEST) and             
                              (tRACK_validity = TEST) and                    
                              (bEST_ROLL_validity = TEST) and                
                              (sEL_AHRS_VERT_VEL_INERT_validity = TEST) and
                              (bEST_TRUE_HEADING_validity = TEST) and         
                              (sEL_RA_ALTITUDE_validity = TEST) and          
                              (sEL_WIND_AT_ALT_SPEED_validity = TEST) and    
                              (sEL_WIND_AT_ALT_ANGLE_validity = TEST) then
                              par
                               cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_validity := FUNCTIONAL_TEST  
                               cPM_FMS_BUS_INDICATED_AIRSPEED_validity := FUNCTIONAL_TEST	  
                               cPM_FMS_BUS_VERTICAL_SPEED_validity := FUNCTIONAL_TEST        
                               cPM_FMS_BUS_TRUE_AIRSPEED_validity := FUNCTIONAL_TEST	        
                               cPM_FMS_BUS_GROUND_SPEED_validity := FUNCTIONAL_TEST	        
                               cPM_FMS_BUS_TRACK_ANGLE_validity := FUNCTIONAL_TEST		        
                               cPM_FMS_BUS_ROLL_ANGLE_validity := FUNCTIONAL_TEST		        
                               cPM_FMS_BUS_INERT_VERTICAL_SPEED_validity := FUNCTIONAL_TEST  
                               cPM_FMS_BUS_TRUE_HEADING_validity := FUNCTIONAL_TEST		      
                               cPM_FMS_BUS_RADIO_HEIGHT_validity := FUNCTIONAL_TEST		      
                               cPM_FMS_BUS_WIND_SPEED_validity := FUNCTIONAL_TEST	          
                               cPM_FMS_BUS_WIND_ANGLE_validity := FUNCTIONAL_TEST	          
                               
                              endpar 
                           else if (bEST_MAG_HEADING_validity = NO_COMPUTED) and
                              (sEL_ADU_COMPUTED_AIRSPEED_validity = NO_COMPUTED) and
                              (bEST_VELOCITY_VERT_validity = NO_COMPUTED) and       
                              (sEL_ADU_TRUE_AIRSPEED_validity = NO_COMPUTED) and    
                              (gROUND_SPEED_validity = NO_COMPUTED) and             
                              (tRACK_validity = NO_COMPUTED) and                    
                              (bEST_ROLL_validity = NO_COMPUTED) and                
                              (sEL_AHRS_VERT_VEL_INERT_validity = NO_COMPUTED) and
                              (bEST_TRUE_HEADING_validity = NO_COMPUTED) and         
                              (sEL_RA_ALTITUDE_validity = NO_COMPUTED) and          
                              (sEL_WIND_AT_ALT_SPEED_validity = NO_COMPUTED) and    
                              (sEL_WIND_AT_ALT_ANGLE_validity = NO_COMPUTED) then
                              par
                               cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_validity := NO_COMPUTED_DATA  
                               cPM_FMS_BUS_INDICATED_AIRSPEED_validity := NO_COMPUTED_DATA	  
                               cPM_FMS_BUS_VERTICAL_SPEED_validity := NO_COMPUTED_DATA       
                               cPM_FMS_BUS_TRUE_AIRSPEED_validity := NO_COMPUTED_DATA	        
                               cPM_FMS_BUS_GROUND_SPEED_validity := NO_COMPUTED_DATA	        
                               cPM_FMS_BUS_TRACK_ANGLE_validity	:= NO_COMPUTED_DATA	        
                               cPM_FMS_BUS_ROLL_ANGLE_validity := NO_COMPUTED_DATA	        
                               cPM_FMS_BUS_INERT_VERTICAL_SPEED_validity := NO_COMPUTED_DATA 
                               cPM_FMS_BUS_TRUE_HEADING_validity := NO_COMPUTED_DATA		      
                               cPM_FMS_BUS_RADIO_HEIGHT_validity := NO_COMPUTED_DATA		      
                               cPM_FMS_BUS_WIND_SPEED_validity := NO_COMPUTED_DATA	          
                               cPM_FMS_BUS_WIND_ANGLE_validity := NO_COMPUTED_DATA          
                             endpar  
                           else if ((bEST_MAG_HEADING_validity = NOT_VALIDITY)or (bEST_MAG_HEADING_validity = NA))
                               and
                              ((sEL_ADU_COMPUTED_AIRSPEED_validity = NOT_VALIDITY) or (sEL_ADU_COMPUTED_AIRSPEED_validity = NA))
                               and
                              ((bEST_VELOCITY_VERT_validity = NOT_VALIDITY) or (bEST_VELOCITY_VERT_validity = NA))
                               and       
                              ((sEL_ADU_TRUE_AIRSPEED_validity = NOT_VALIDITY) or (sEL_ADU_TRUE_AIRSPEED_validity = NA))
                               and    
                              ((gROUND_SPEED_validity = NOT_VALIDITY) or (gROUND_SPEED_validity = NA))
                               and             
                              ((tRACK_validity = NOT_VALIDITY) or (tRACK_validity = NA))
                               and                    
                              ((bEST_ROLL_validity = NOT_VALIDITY) or (bEST_ROLL_validity = NA))
                               and                
                              ((sEL_AHRS_VERT_VEL_INERT_validity = NOT_VALIDITY) or (sEL_AHRS_VERT_VEL_INERT_validity = NA))
                               and
                              ((bEST_TRUE_HEADING_validity = NOT_VALIDITY) or (bEST_TRUE_HEADING_validity = NA))
                              and         
                              ((sEL_RA_ALTITUDE_validity = NOT_VALIDITY) or (sEL_RA_ALTITUDE_validity = NA))
                               and          
                              ((sEL_WIND_AT_ALT_SPEED_validity = NOT_VALIDITY) or (sEL_WIND_AT_ALT_SPEED_validity = NA))
                              and    
                              ((sEL_WIND_AT_ALT_ANGLE_validity = NOT_VALIDITY) or (sEL_WIND_AT_ALT_ANGLE_validity = NA))
                              then 
                             par
                               cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_validity := FAILURE_WARNING  
                               cPM_FMS_BUS_INDICATED_AIRSPEED_validity := FAILURE_WARNING	  
                               cPM_FMS_BUS_VERTICAL_SPEED_validity := FAILURE_WARNING       
                               cPM_FMS_BUS_TRUE_AIRSPEED_validity := FAILURE_WARNING	        
                               cPM_FMS_BUS_GROUND_SPEED_validity := FAILURE_WARNING	        
                               cPM_FMS_BUS_TRACK_ANGLE_validity	:= FAILURE_WARNING	        
                               cPM_FMS_BUS_ROLL_ANGLE_validity := FAILURE_WARNING		        
                               cPM_FMS_BUS_INERT_VERTICAL_SPEED_validity := FAILURE_WARNING 
                               cPM_FMS_BUS_TRUE_HEADING_validity := FAILURE_WARNING		      
                               cPM_FMS_BUS_RADIO_HEIGHT_validity := FAILURE_WARNING		      
                               cPM_FMS_BUS_WIND_SPEED_validity := FAILURE_WARNING	          
                               cPM_FMS_BUS_WIND_ANGLE_validity := FAILURE_WARNING	          
                             endpar           
                           endif 
                           endif 
                           endif endif                                               
                      	 //endpar 
                      	 
macro rule r_grouplabel3 = seq 
                            par                            
                            cPM_MON_ST_GPS1_LATITUDE_DATA           := cIVIL_GPS1_LATITUDE_value         
                            cPM_MON_ST_GPS1_LONGITUDE_DATA          := cIVIL_GPS1_LONGITUDE_value      
                            cPM_MON_ST_GPS2_LATITUDE_DATA           := cIVIL_GPS2_LATITUDE_value         
                            cPM_MON_ST_GPS2_LONGITUDE_DATA          := cIVIL_GPS2_LONGITUDE_value       
                            cPM_MON_ST_INIT_LATITUDE_DATA           := iNIT_LAT_value                                                               
                            cPM_MON_ST_INIT_LONGITUDE_DATA          := iNIT_LONG_value                             
                            cPM_MON_ST_AMMC_DEAD_REC_LATITUDE_DATA  := aMMC_LAT_value                      
                            cPM_MON_ST_AMMC_DEAD_REC_LONGITUDE_DATA := aMMC_LONG_value                    
                            cPM_MON_ST_BEST_LATITUDE_DATA           := bEST_LAT_value                               
                            cPM_MON_ST_BEST_LONGITUDE_DATA          := bEST_LONG_value                             
                            cPM_MON_ST_BEST_POSN_SOURCE_DATA        := bEST_POSN_SOURCE_value                    
                            cPM_MON_ST_DME_DME_LATITUDE_DATA        := dME_DME_LAT_value                         
                            cPM_MON_ST_DME_DME_LONGITUDE_DATA       := dME_DME_LONG_value                       
                            cPM_MON_ST_VOR_DME_LATITUDE_DATA        := vOR_DME_LAT_value                         
                            cPM_MON_ST_VOR_DME_LONGITUDE_DATA       := vOR_DME_LONG_value                
                            cPM_MON_ST_POSN_DIFF_CIVIL_GPS1_data    := pOSN_DIFF_CIVIL_GPS1_value              
                            cPM_MON_ST_POSN_DIFF_CIVIL_GPS2_data    := pOSN_DIFF_CIVIL_GPS2_value        
                            cPM_MON_ST_POSN_DIFF_DME_DME_data       := pOSN_DIFF_DME_DME_value                
                            cPM_MON_ST_POSN_DIFF_VOR_DME_data       := pOSN_DIFF_VOR_DME_value                
                            cPM_MON_ST_POSN_DIFF_AMMC_data          := pOSN_DIFF_AMMC_value            
                            cPM_MON_ST_BEST_BAR_ALT_CORR_data       := sEL_ADU_BARO_CORR_ALTITUDE_value         
                            cPM_MON_ST_BEST_MSL_ALT_data            := bEST_MSL_ALT_value 
                            endpar            
                            if (cIVIL_GPS1_LATITUDE_validity  = VALID3) and
                               (cIVIL_GPS1_LONGITUDE_validity = VALID3) and
                               (cIVIL_GPS2_LATITUDE_validity  = VALID3) and
                               (cIVIL_GPS2_LONGITUDE_validity = VALID3) and
                               (iNIT_LAT_validity             = VALID3) and
                               (iNIT_LONG_validity            = VALID3) and
                               (aMMC_LAT_validity             = VALID3) and
                               (aMMC_LONG_validity            = VALID3) and
                               (bEST_LAT_validity             = VALID3) and
                               (bEST_LONG_validity            = VALID3) and
                               (bEST_POSN_SOURCE_validity     = VALID3) and
                               (dME_DME_LAT_validity          = VALID3) and
                               (dME_DME_LONG_validity         = VALID3) and
                               (vOR_DME_LAT_validity          = VALID3) and
                               (vOR_DME_LONG_validity         = VALID3) and
                               (pOSN_DIFF_CIVIL_GPS1_validity = VALID3) and
                               (pOSN_DIFF_CIVIL_GPS2_validity = VALID3) and
                               (pOSN_DIFF_DME_DME_validity    = VALID3) and
                               (pOSN_DIFF_VOR_DME_validity    = VALID3) and
                               (pOSN_DIFF_AMMC_validity       = VALID3) and
                               (sEL_ADU_BARO_CORR_ALTITUDE_validity = VALID3) and
                               (bEST_MSL_ALT_validity = VALID3) then
                            par   
                             cPM_MON_ST_GPS1_LATITUDE_FSS           := VALIDITY3 
                             cPM_MON_ST_GPS1_LONGITUDE_FSS          := VALIDITY3
                             cPM_MON_ST_GPS2_LATITUDE_FSS           := VALIDITY3 
                             cPM_MON_ST_GPS2_LONGITUDE_FSS          := VALIDITY3
                             cPM_MON_ST_INIT_LATITUDE_FSS           := VALIDITY3                                            
                             cPM_MON_ST_INIT_LONGITUDE_FSS          := VALIDITY3           
                             cPM_MON_ST_AMMC_DEAD_REC_LATITUDE_FSS  := VALIDITY3   
                             cPM_MON_ST_AMMC_DEAD_REC_LONGITUDE_FSS := VALIDITY3  
                             cPM_MON_ST_BEST_LATITUDE_FSS           := VALIDITY3             
                             cPM_MON_ST_BEST_LONGITUDE_FSS          := VALIDITY3           
                             cPM_MON_ST_BEST_POSN_SOURCE_FSS        := VALIDITY3         
                             cPM_MON_ST_DME_DME_LATITUDE_FSS        := VALIDITY3         
                             cPM_MON_ST_DME_DME_LONGITUDE_FSS       := VALIDITY3        
                             cPM_MON_ST_VOR_DME_LATITUDE_FSS        := VALIDITY3         
                             cPM_MON_ST_VOR_DME_LONGITUDE_FSS       := VALIDITY3 
                             cPM_MON_ST_POSN_DIFF_CIVIL_GPS1_FSS    := VALIDITY3       
                             cPM_MON_ST_POSN_DIFF_CIVIL_GPS2_FSS    := VALIDITY3 
                             cPM_MON_ST_POSN_DIFF_DME_DME_FSS       := VALIDITY3      
                             cPM_MON_ST_POSN_DIFF_VOR_DME_FSS       := VALIDITY3      
                             cPM_MON_ST_POSN_DIFF_AMMC_FSS          := VALIDITY3
                             cPM_MON_ST_BEST_BAR_ALT_CORR_FSS       := VALIDITY3        
                             cPM_MON_ST_BEST_MSL_ALT_FSS            := VALIDITY3     
                           endpar                            
                                else if (cIVIL_GPS1_LATITUDE_validity  = NOT_VALID3) and
                                        (cIVIL_GPS1_LONGITUDE_validity = NOT_VALID3) and
                                        (cIVIL_GPS2_LATITUDE_validity  = NOT_VALID3) and
                                        (cIVIL_GPS2_LONGITUDE_validity = NOT_VALID3) and
                                        (iNIT_LAT_validity             = NOT_VALID3) and
                                        (iNIT_LONG_validity            = NOT_VALID3) and
                                        (aMMC_LAT_validity             = NOT_VALID3) and
                                        (aMMC_LONG_validity            = NOT_VALID3) and
                                        (bEST_LAT_validity             = NOT_VALID3) and
                                        (bEST_LONG_validity            = NOT_VALID3) and
                                        (bEST_POSN_SOURCE_validity     = NOT_VALID3) and
                                        (dME_DME_LAT_validity          = NOT_VALID3) and
                                        (dME_DME_LONG_validity         = NOT_VALID3) and
                                        (vOR_DME_LAT_validity          = NOT_VALID3) and
                                        (vOR_DME_LONG_validity         = NOT_VALID3) and
                                        (pOSN_DIFF_CIVIL_GPS1_validity = NOT_VALID3) and
                                        (pOSN_DIFF_CIVIL_GPS2_validity = NOT_VALID3) and
                                        (pOSN_DIFF_DME_DME_validity    = NOT_VALID3) and
                                        (pOSN_DIFF_VOR_DME_validity    = NOT_VALID3) and
                                        (pOSN_DIFF_AMMC_validity       = NOT_VALID3) and
                                        (sEL_ADU_BARO_CORR_ALTITUDE_validity = NOT_VALID3) and
                                        (bEST_MSL_ALT_validity = NOT_VALID3) then
                                    par    
                                      cPM_MON_ST_GPS1_LATITUDE_FSS           := NCD3 
                                      cPM_MON_ST_GPS1_LONGITUDE_FSS          := NCD3
                                      cPM_MON_ST_GPS2_LATITUDE_FSS           := NCD3 
                                      cPM_MON_ST_GPS2_LONGITUDE_FSS          := NCD3
                                      cPM_MON_ST_INIT_LATITUDE_FSS           := NCD3                                            
                                      cPM_MON_ST_INIT_LONGITUDE_FSS          := NCD3           
                                      cPM_MON_ST_AMMC_DEAD_REC_LATITUDE_FSS  := NCD3   
                                      cPM_MON_ST_AMMC_DEAD_REC_LONGITUDE_FSS := NCD3  
                                      cPM_MON_ST_BEST_LATITUDE_FSS           := NCD3             
                                      cPM_MON_ST_BEST_LONGITUDE_FSS          := NCD3           
                                      cPM_MON_ST_BEST_POSN_SOURCE_FSS        := NCD3         
                                      cPM_MON_ST_DME_DME_LATITUDE_FSS        := NCD3         
                                      cPM_MON_ST_DME_DME_LONGITUDE_FSS       := NCD3        
                                      cPM_MON_ST_VOR_DME_LATITUDE_FSS        := NCD3         
                                      cPM_MON_ST_VOR_DME_LONGITUDE_FSS       := NCD3 
                                      cPM_MON_ST_POSN_DIFF_CIVIL_GPS1_FSS    := NCD3       
                                      cPM_MON_ST_POSN_DIFF_CIVIL_GPS2_FSS    := NCD3 
                                      cPM_MON_ST_POSN_DIFF_DME_DME_FSS       := NCD3      
                                      cPM_MON_ST_POSN_DIFF_VOR_DME_FSS       := NCD3      
                                      cPM_MON_ST_POSN_DIFF_AMMC_FSS          := NCD3
                                      cPM_MON_ST_BEST_BAR_ALT_CORR_FSS       := NCD3        
                                      cPM_MON_ST_BEST_MSL_ALT_FSS            := NCD3
                                    endpar 
                                    endif endif    
                           endseq                       	   
                      	                         	         	
	
	macro rule r_send_labels =//while (timer <= 200) do
	                        seq 
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
	                        endseq 
	                            
//Main Rule
    
    main rule r_fmsio = r_send_labels[]                            
                          
                           

	//main rule r_S = par r_1[] r_2[] endpar
	
	 
//define the initial states 

default init s0:
	
	//function prova  = true
	
function timer = 0
function cpm173PseudoLocDeviation_validity = NCD     
function cpm173PseudoLocDeviation_value    = 0.0     
function cpm174PseudoGsDeviation_validity  = NCD     
function cpm174PseudoGsDeviation_value     = 0.0     
function cpm177DistanceToFpcp_validity     = NCD     
function cpm177DistanceToFpcp_value        = 0.0     
function cpm322PseudoGsPathAngle_validity  = NCD     
function cpm322PseudoGsPathAngle_value     = 0.0    
function cpm105SelectedRunWayHeading_validity = NCD  
function cpm105SelectedRunWayHeading_value    = 0.0  
function cpm116CrossTrackDistance_validity    = NCD  
function cpm116CrossTrackDistance_value       = 0.0  
function cpm117VerticalLinearDeviation_validity = NCD
function cpm117VerticalLinearDeviation_value    = 0.0
function cpm326LateralScaleFactor_validity      = NCD
function cpm326LateralScaleFactor_value         = 0.0
function cpm327VerticalScaleFactor_validity     = NCD
function cpm327VerticalScaleFactor_value        = 0.0
function cpm275OperatingModePhase_validity      = NCD
function cpm275OperatingModePhase_value         = NAV

function cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_validity = NO_COMPUTED_DATA  
function cPM_FMS_BUS_INDICATED_AIRSPEED_validity = NO_COMPUTED_DATA	  
function cPM_FMS_BUS_VERTICAL_SPEED_validity = NO_COMPUTED_DATA       
function cPM_FMS_BUS_TRUE_AIRSPEED_validity = NO_COMPUTED_DATA	        
function cPM_FMS_BUS_GROUND_SPEED_validity = NO_COMPUTED_DATA	        
function cPM_FMS_BUS_TRACK_ANGLE_validity	= NO_COMPUTED_DATA	        
function cPM_FMS_BUS_ROLL_ANGLE_validity = NO_COMPUTED_DATA	        
function cPM_FMS_BUS_INERT_VERTICAL_SPEED_validity = NO_COMPUTED_DATA 
function cPM_FMS_BUS_TRUE_HEADING_validity = NO_COMPUTED_DATA		      
function cPM_FMS_BUS_RADIO_HEIGHT_validity = NO_COMPUTED_DATA		      
function cPM_FMS_BUS_WIND_SPEED_validity   = NO_COMPUTED_DATA	          
function cPM_FMS_BUS_WIND_ANGLE_validity   = NO_COMPUTED_DATA
function cPM_FMS_BUS_AIRCRAFT_MAG_HEADING_value = 0.0  
function cPM_FMS_BUS_INDICATED_AIRSPEED_value = 0.0	  
function cPM_FMS_BUS_VERTICAL_SPEED_value = 0.0       
function cPM_FMS_BUS_TRUE_AIRSPEED_value = 0.0	        
function cPM_FMS_BUS_GROUND_SPEED_value = 0.0	        
function cPM_FMS_BUS_TRACK_ANGLE_value	= 0.0	        
function cPM_FMS_BUS_ROLL_ANGLE_value = 0.0	        
function cPM_FMS_BUS_INERT_VERTICAL_SPEED_value = 0.0 
function cPM_FMS_BUS_TRUE_HEADING_value = 0.0	      
function cPM_FMS_BUS_RADIO_HEIGHT_value = 0.0	      
function cPM_FMS_BUS_WIND_SPEED_value   = 0.0         
function cPM_FMS_BUS_WIND_ANGLE_value   = 0.0

function cPM_MON_ST_GPS1_LATITUDE_DATA           = 0.0
function cPM_MON_ST_GPS1_LONGITUDE_DATA          = 0.0
function cPM_MON_ST_GPS2_LATITUDE_DATA           = 0.0
function cPM_MON_ST_GPS2_LONGITUDE_DATA          = 0.0
function cPM_MON_ST_INIT_LATITUDE_DATA           = 0.0
function cPM_MON_ST_INIT_LONGITUDE_DATA          = 0.0
function cPM_MON_ST_AMMC_DEAD_REC_LATITUDE_DATA  = 0.0
function cPM_MON_ST_AMMC_DEAD_REC_LONGITUDE_DATA = 0.0
function cPM_MON_ST_BEST_LATITUDE_DATA           = 0.0
function cPM_MON_ST_BEST_LONGITUDE_DATA          = 0.0
function cPM_MON_ST_BEST_POSN_SOURCE_DATA        = INIT
function cPM_MON_ST_DME_DME_LATITUDE_DATA        = 0.0
function cPM_MON_ST_DME_DME_LONGITUDE_DATA       = 0.0
function cPM_MON_ST_VOR_DME_LATITUDE_DATA        = 0.0
function cPM_MON_ST_VOR_DME_LONGITUDE_DATA       = 0.0
function cPM_MON_ST_POSN_DIFF_CIVIL_GPS1_data    = 0.0
function cPM_MON_ST_POSN_DIFF_CIVIL_GPS2_data    = 0.0
function cPM_MON_ST_POSN_DIFF_DME_DME_data       = 0.0
function cPM_MON_ST_POSN_DIFF_VOR_DME_data       = 0.0
function cPM_MON_ST_POSN_DIFF_AMMC_data          = 0.0
function cPM_MON_ST_BEST_BAR_ALT_CORR_data       = 0.0
function cPM_MON_ST_BEST_MSL_ALT_data            = 0.0
function cPM_MON_ST_GPS1_LATITUDE_FSS           = NCD3
function cPM_MON_ST_GPS1_LONGITUDE_FSS          = NCD3
function cPM_MON_ST_GPS2_LATITUDE_FSS           = NCD3
function cPM_MON_ST_GPS2_LONGITUDE_FSS          = NCD3
function cPM_MON_ST_INIT_LATITUDE_FSS           = NCD3
function cPM_MON_ST_INIT_LONGITUDE_FSS          = NCD3
function cPM_MON_ST_AMMC_DEAD_REC_LATITUDE_FSS  = NCD3
function cPM_MON_ST_AMMC_DEAD_REC_LONGITUDE_FSS = NCD3
function cPM_MON_ST_BEST_LATITUDE_FSS           = NCD3
function cPM_MON_ST_BEST_LONGITUDE_FSS          = NCD3
function cPM_MON_ST_BEST_POSN_SOURCE_FSS        = NCD3
function cPM_MON_ST_DME_DME_LATITUDE_FSS        = NCD3
function cPM_MON_ST_DME_DME_LONGITUDE_FSS       = NCD3
function cPM_MON_ST_VOR_DME_LATITUDE_FSS        = NCD3
function cPM_MON_ST_VOR_DME_LONGITUDE_FSS       = NCD3
function cPM_MON_ST_POSN_DIFF_CIVIL_GPS1_FSS    = NCD3
function cPM_MON_ST_POSN_DIFF_CIVIL_GPS2_FSS    = NCD3
function cPM_MON_ST_POSN_DIFF_DME_DME_FSS       = NCD3
function cPM_MON_ST_POSN_DIFF_VOR_DME_FSS       = NCD3
function cPM_MON_ST_POSN_DIFF_AMMC_FSS          = NCD3
function cPM_MON_ST_BEST_BAR_ALT_CORR_FSS       = NCD3
function cPM_MON_ST_BEST_MSL_ALT_FSS            = NCD3


	

		
