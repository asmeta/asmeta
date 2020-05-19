/* example of ASM specification */
//MODELLO ASM DI TEST PER LA COMPONENETE FMS_IO

asm FMS_IO_2

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
    
    enum domain Label_name_group_1_1 = {CROSS_TRACK_DISTANCE | VERTICAL_LINEAR_DEVIATION | LATERAL_SCALE_FACTOR | VERTICAL_SCALE_FACTOR}
    enum domain Label_name_group_1_2 = {OPERATING_MODE_PHASE}
    enum domain Label_name_group_1_3 = {PSEUDO_LOC_DEVIATION}
    enum domain Label_name_group_1_4 = {PSEUDO_GS_DEVIATION}
    enum domain Label_name_group_1_5 = {DISTANCE_TO_FPCP}
    enum domain Label_name_group_1_6 = {PSEUDO_GS_PATH_ANGLE}
    enum domain Label_name_group_1_7 = {SELECTED_RUNWAY_HEADING}
    
    enum domain Cpm_label_name_group_1_1 = {CPM_116_CROSS_TRACK_DISTANCE | CPM_117_VERTICAL_LINEAR_DEVIATION | CPM_326_LATERAL_SCALE_FACTOR | CPM_327_VERTICAL_SCALE_FACTOR}
    enum domain Cpm_label_name_group_1_2 = {CPM_275_OPERATING_MODE_PHASE}
    enum domain Cpm_label_name_group_1_3 = {CPM_173_PSEUDO_LOC_DEVIATION}
    enum domain Cpm_label_name_group_1_4 = {CPM_174_PSEUDO_GS_DEVIATION}
    enum domain Cpm_label_name_group_1_5 = {CPM_177_DISTANCE_TO_FPCP}
    enum domain Cpm_label_name_group_1_6 = {CPM_322_PSEUDO_GS_PATH_ANGLE}
    enum domain Cpm_label_name_group_1_7 = {CPM_105_SELECTED_RUNWAY_HEADING}
        
    enum domain Label_name_group_2_1 = {SEL_ADU_COMPUTED_AIRSPEED}
    enum domain Label_name_group_2_2 = {BEST_VELOCITY_VERT | SEL_AHRS_VERT_VEL_INERT}
    enum domain Label_name_group_2_3 = {SEL_ADU_TRUE_AIRSPEED}
    enum domain Label_name_group_2_4 = {SEL_RA_ALTITUDE}
    enum domain Label_name_group_2_5 = {AIRCRAFT_MAG_HEADING | GROUND_SPEED | TRACK_ANGLE | ROLL_ANGLE | SEL_WIND_AT_ALT_SPEED | SEL_WIND_AT_ALT_ANGLE}
    enum domain Label_name_group_2_6 = {BEST_TRUE_HEADING}
    
    enum domain Cpm_label_name_group_2_1 = {CPM_FMS_BUS_INDICATED_AIRSPEED}
    enum domain Cpm_label_name_group_2_2 = {CPM_FMS_BUS_VERTICAL_SPEED | CPM_FMS_BUS_INERT_VERTICAL_SPEED}
    enum domain Cpm_label_name_group_2_3 = {CPM_FMS_BUS_TRUE_AIRSPEED}
    enum domain Cpm_label_name_group_2_4 = {CPM_FMS_BUS_RADIO_HEIGHT}
    enum domain Cpm_label_name_group_2_5 = {CPM_FMS_BUS_AIRCRAFT_MAG_HEADING | CPM_FMS_BUS_GROUND_SPEED | CPM_FMS_BUS_TRACK_ANGLE | CPM_FMS_BUS_ROLL_ANGLE | CPM_FMS_BUS_WIND_SPEED | CPM_FMS_BUS_WIND_ANGLE}
    enum domain Cpm_label_name_group_2_6 = {CPM_FMS_BUS_TRUE_HEADING}
    
    enum domain Label_name_group_3_1 = {CIVIL_GPS1_LONGITUDE | CIVIL_GPS2_LONGITUDE | INIT_LONG | AMMC_LONG | BEST_LONG | DME_DME_LONG | VOR_DME_LONG}
    enum domain Label_name_group_3_2 = {POSN_DIFF_CIVIL_GPS1 | POSN_DIFF_CIVIL_GPS2 | POSN_DIFF_DME_DME | POSN_DIFF_VOR_DME | POSN_DIFF_AMMC}
    enum domain Label_name_group_3_3 = {CIVIL_GPS1_LATITUDE | CIVIL_GPS2_LATITUDE | INIT_LAT | AMMC_LAT | BEST_LAT | DME_DME_LAT | VOR_DME_LAT}
    enum domain Label_name_group_3_4 = {BEST_POSN_SOURCE}
    enum domain Label_name_group_3_5 = {SEL_ADU_BARO_CORR_ALTITUDE | BEST_MSL_ALT}
    
    enum domain Cpm_label_name_group_3_1 = {CPM_MON_ST_GPS1_LONGITUDE_DATA | CPM_MON_ST_GPS2_LONGITUDE_DATA | CPM_MON_ST_INIT_LONGITUDE_DATA | CPM_MON_ST_AMMC_DEAD_REC_LONGITUDE_DATA | CPM_MON_ST_BEST_LONGITUDE_DATA | CPM_MON_ST_DME_DME_LONGITUDE_DATA | CPM_MON_ST_VOR_DME_LONGITUDE_DATA}
    enum domain Cpm_label_name_group_3_2 = {CPM_MON_ST_POSN_DIFF_CIVIL_GPS1 | CPM_MON_ST_POSN_DIFF_CIVIL_GPS2 | CPM_MON_ST_POSN_DIFF_DME_DME | CPM_MON_ST_POSN_DIFF_VOR_DME | CPM_MON_ST_POSN_DIFF_AMMC}
    enum domain Cpm_label_name_group_3_3 = {CPM_MON_ST_GPS1_LATITUDE | CPM_MON_ST_GPS2_LATITUDE | CPM_MON_ST_INIT_LATITUDE | CPM_MON_ST_AMMC_DEAD_REC_LATITUDE | CPM_MON_ST_BEST_LATITUDE | CPM_MON_ST_DME_DME_LATITUDE | CPM_MON_ST_VOR_DME_LATITUDE}
    enum domain Cpm_label_name_group_3_4 = {CPM_MON_ST_BEST_POSN_SOURCE}
    enum domain Cpm_label_name_group_3_5 = {CPM_MON_ST_BEST_BAR_ALT_CORR | CPM_MON_ST_BEST_MSL_ALT}
    
    
    domain TimerD subsetof Integer
        
    enum domain Group_label = {GROUP_LABEL_1 | GROUP_LABEL_2 | GROUP_LABEL_3}
    
    enum domain Bus = {BUS_ARINC_CH1 | BUS_ARINC_CH2 | BUS_AFDX}
    
// functions 
     
    //label che devono essere mandate sui bus periodicamente ogni 20 ms
    //valori in dall'ambiente esterno
    dynamic monitored  label_group_1_1 : Label_name_group_1_1 -> Prod(Label_IN1_value,Label_IN1_validity)
    dynamic monitored  label_group_1_2 : Label_name_group_1_2 -> Prod(Label_IN2_value,Label_IN1_validity)
    dynamic monitored  label_group_1_3 : Label_name_group_1_3 -> Prod(Label_IN3_value,Label_IN1_validity)
    dynamic monitored  label_group_1_4 : Label_name_group_1_4 -> Prod(Label_IN4_value,Label_IN1_validity)
    dynamic monitored  label_group_1_5 : Label_name_group_1_5 -> Prod(Label_IN5_value,Label_IN1_validity)
    dynamic monitored  label_group_1_6 : Label_name_group_1_6 -> Prod(Label_IN6_value,Label_IN1_validity)
    dynamic monitored  label_group_1_7 : Label_name_group_1_7 -> Prod(Label_IN7_value,Label_IN1_validity)
 
    dynamic controlled cpm_label_group_1_1 : Cpm_label_name_group_1_1 -> Prod(Prod(Label_IN1_value,Label_OUT1_validity),Bus)
    dynamic controlled cpm_label_group_1_2 : Cpm_label_name_group_1_2 -> Prod(Prod(Label_IN2_value,Label_OUT1_validity),Bus)
    dynamic controlled cpm_label_group_1_3 : Cpm_label_name_group_1_3 -> Prod(Prod(Label_IN3_value,Label_OUT1_validity),Bus)
    dynamic controlled cpm_label_group_1_4 : Cpm_label_name_group_1_4 -> Prod(Prod(Label_IN4_value,Label_OUT1_validity),Bus)
    dynamic controlled cpm_label_group_1_5 : Cpm_label_name_group_1_5 -> Prod(Prod(Label_IN5_value,Label_OUT1_validity),Bus)
    dynamic controlled cpm_label_group_1_6 : Cpm_label_name_group_1_6 -> Prod(Prod(Label_IN6_value,Label_OUT1_validity),Bus)
    dynamic controlled cpm_label_group_1_7 : Cpm_label_name_group_1_7 -> Prod(Prod(Label_IN7_value,Label_OUT1_validity),Bus)          


//label che devono essere mandate sui bus ogni 40 ms

dynamic monitored label_group_2_1 : Label_name_group_2_1 -> Prod(Label_IN8_value,Label_IN2_validity)
dynamic monitored label_group_2_2 : Label_name_group_2_2 -> Prod(Label_IN9_value,Label_IN2_validity)
dynamic monitored label_group_2_3 : Label_name_group_2_3 -> Prod(Label_IN10_value,Label_IN2_validity)
dynamic monitored label_group_2_4 : Label_name_group_2_4 -> Prod(Label_IN11_value,Label_IN2_validity)
dynamic monitored label_group_2_5 : Label_name_group_2_5 -> Prod(Real,Label_IN2_validity)
dynamic monitored label_group_2_6 : Label_name_group_2_6 -> Prod(Label_IN7_value,Label_IN2_validity)

dynamic controlled cpm_label_group_2_1 : Cpm_label_name_group_2_1 -> Prod(Prod(Label_IN8_value,Label_OUT2_validity),Bus)
dynamic controlled cpm_label_group_2_2 : Cpm_label_name_group_2_2 -> Prod(Prod(Label_IN9_value,Label_OUT2_validity),Bus)
dynamic controlled cpm_label_group_2_3 : Cpm_label_name_group_2_3 -> Prod(Prod(Label_IN10_value,Label_OUT2_validity),Bus)
dynamic controlled cpm_label_group_2_4 : Cpm_label_name_group_2_4 -> Prod(Prod(Label_IN11_value,Label_OUT2_validity),Bus)
dynamic controlled cpm_label_group_2_5 : Cpm_label_name_group_2_5 -> Prod(Prod(Real,Label_OUT2_validity),Bus)
dynamic controlled cpm_label_group_2_6 : Cpm_label_name_group_2_6 -> Prod(Prod(Label_IN7_value,Label_OUT2_validity),Bus)                      
                                                                                   
//label che devono essere mandate sul bus ogni 320 ms

dynamic monitored label_group_3_1 : Label_name_group_3_1 -> Prod(Label_IN7_value,Label_IN3_validity)
dynamic monitored label_group_3_2 : Label_name_group_3_2 -> Prod(Label_IN12_value,Label_IN3_validity)
dynamic monitored label_group_3_3 : Label_name_group_3_3 -> Prod(Label_IN13_value,Label_IN3_validity)
dynamic monitored label_group_3_4 : Label_name_group_3_4 -> Prod(Label_IN14_value,Label_IN3_validity)
dynamic monitored label_group_3_5 : Label_name_group_3_5 -> Prod(Real,Label_IN3_validity)

dynamic controlled cpm_label_group_3_1 : Cpm_label_name_group_3_1 -> Prod(Prod(Label_IN7_value,Label_OUT3_validity),Bus)
dynamic controlled cpm_label_group_3_2 : Cpm_label_name_group_3_2 -> Prod(Prod(Label_IN12_value,Label_OUT3_validity),Bus)
dynamic controlled cpm_label_group_3_3 : Cpm_label_name_group_3_3 -> Prod(Prod(Label_IN13_value,Label_OUT3_validity),Bus)
dynamic controlled cpm_label_group_3_4 : Cpm_label_name_group_3_4 -> Prod(Prod(Label_IN14_value,Label_OUT3_validity),Bus)
dynamic controlled cpm_label_group_3_5 : Cpm_label_name_group_3_5 -> Prod(Prod(Real,Label_OUT3_validity),Bus) 

derived label_1_1 : Prod(Label_IN1_value, Label_IN1_validity) -> Prod(Label_IN1_value, Label_OUT1_validity)   
derived label_1_2 : Prod(Label_IN2_value, Label_IN1_validity) -> Prod(Label_IN2_value, Label_OUT1_validity)
derived label_1_3 : Prod(Label_IN3_value, Label_IN1_validity) -> Prod(Label_IN3_value, Label_OUT1_validity)
derived label_1_4 : Prod(Label_IN4_value, Label_IN1_validity) -> Prod(Label_IN4_value, Label_OUT1_validity)
derived label_1_5 : Prod(Label_IN5_value, Label_IN1_validity) -> Prod(Label_IN5_value, Label_OUT1_validity)
derived label_1_6 : Prod(Label_IN6_value, Label_IN1_validity) -> Prod(Label_IN6_value, Label_OUT1_validity)
derived label_1_7 : Prod(Label_IN7_value, Label_IN1_validity) -> Prod(Label_IN7_value, Label_OUT1_validity)

derived label_2_1 : Prod(Label_IN8_value, Label_IN2_validity) -> Prod(Label_IN8_value, Label_OUT2_validity)
derived label_2_2 : Prod(Label_IN9_value, Label_IN2_validity) -> Prod(Label_IN9_value, Label_OUT2_validity)
derived label_2_3 : Prod(Label_IN10_value, Label_IN2_validity) -> Prod(Label_IN10_value, Label_OUT2_validity)
derived label_2_4 : Prod(Label_IN11_value, Label_IN2_validity) -> Prod(Label_IN11_value, Label_OUT2_validity)
derived label_2_5 : Prod(Real, Label_IN2_validity) -> Prod(Real, Label_OUT2_validity)
derived label_2_6 : Prod(Label_IN7_value, Label_IN2_validity) -> Prod(Label_IN7_value, Label_OUT2_validity)

derived label_3_1 : Prod(Label_IN7_value, Label_IN3_validity) -> Prod(Label_IN7_value, Label_OUT3_validity)
derived label_3_2 : Prod(Label_IN12_value, Label_IN3_validity) -> Prod(Label_IN12_value, Label_OUT3_validity)
derived label_3_3 : Prod(Label_IN13_value, Label_IN3_validity) -> Prod(Label_IN13_value, Label_OUT3_validity)
derived label_3_4 : Prod(Label_IN14_value, Label_IN3_validity) -> Prod(Label_IN14_value, Label_OUT3_validity)   
derived label_3_5 : Prod(Real, Label_IN3_validity) -> Prod(Real, Label_OUT3_validity)
                   
 //timer per simulare i ms passati   
    dynamic controlled timer : TimerD
    dynamic controlled nexttimer : TimerD  
    
 //DICE QUALE BUS E CANALE USARE     
    static switch_label : Group_label -> Bus
    

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
  
  	function switch_label($g in Group_label) =
		if($g = GROUP_LABEL_1) then
			BUS_ARINC_CH1
		else if ($g = GROUP_LABEL_2) then
			BUS_AFDX
		else
		    BUS_ARINC_CH2	
		endif endif
   
//funzioni per il calcolo dei valori in uscita delle label in base ai valori in ingresso delle variabili monitorate provenienti dalle altre componenti  
//gruppo1------------------------------------------------------------------------
function label_1_1($v in Label_IN1_value, $l in Label_IN1_validity) =
              if $l = VALID then
                ($v, NORMAL)
              else if $l = NOT_COMPUTED then
                ($v, NCD)
              else 
                ($v, FW)
              endif endif          
              
function label_1_2($v in Label_IN2_value, $l in Label_IN1_validity) =
              if $l = VALID then
                ($v, NORMAL)
              else if $l = NOT_COMPUTED then
                ($v, NCD)
              else 
                ($v, FW)
              endif endif
              
function label_1_3($v in Label_IN3_value, $l in Label_IN1_validity) =
              if $l = VALID then
                ($v, NORMAL)
              else if $l = NOT_COMPUTED then
                ($v, NCD)
              else 
                ($v, FW)
              endif endif  
              
function label_1_4($v in Label_IN4_value, $l in Label_IN1_validity) =
              if $l = VALID then
                ($v, NORMAL)
              else if $l = NOT_COMPUTED then
                ($v, NCD)
              else 
                ($v, FW)
              endif endif
              
function label_1_5($v in Label_IN5_value, $l in Label_IN1_validity) =
              if $l = VALID then
                ($v, NORMAL)
              else if $l = NOT_COMPUTED then
                ($v, NCD)
              else 
                ($v, FW)
              endif endif
              
function label_1_6($v in Label_IN6_value, $l in Label_IN1_validity) =
              if $l = VALID then
                ($v, NORMAL)
              else if $l = NOT_COMPUTED then
                ($v, NCD)
              else 
                ($v, FW)
              endif endif                                                      

function label_1_7($v in Label_IN7_value, $l in Label_IN1_validity) =
              if $l = VALID then
                ($v, NORMAL)
              else if $l = NOT_COMPUTED then
                ($v, NCD)
              else 
                ($v, FW)
              endif endif
              
//gruppo2-------------------------------------------------------------------                                           
function label_2_1($v in Label_IN8_value, $l in Label_IN2_validity) =
              if $l = TEST then
                ($v, FUNCTIONAL_TEST)
              else if $l = VALIDITY then
                ($v, NORMAL_OPERATION)
              else if $l = NOT_VALIDITY or $l = NA then
                ($v, FAILURE_WARNING)
              else
                ($v, NO_COMPUTED_DATA)  
              endif endif endif
 
 function label_2_2($v in Label_IN9_value, $l in Label_IN2_validity) =
              if $l = TEST then
                ($v, FUNCTIONAL_TEST)
              else if $l = VALIDITY then
                ($v, NORMAL_OPERATION)
              else if $l = NOT_VALIDITY or $l = NA then
                ($v, FAILURE_WARNING)
              else
                ($v, NO_COMPUTED_DATA)  
              endif endif endif
              
 function label_2_3($v in Label_IN10_value, $l in Label_IN2_validity) =
              if $l = TEST then
                ($v, FUNCTIONAL_TEST)
              else if $l = VALIDITY then
                ($v, NORMAL_OPERATION)
              else if $l = NOT_VALIDITY or $l = NA then
                ($v, FAILURE_WARNING)
              else
                ($v, NO_COMPUTED_DATA)  
              endif endif endif                           
              
 function label_2_4($v in Label_IN11_value, $l in Label_IN2_validity) =
              if $l = TEST then
                ($v, FUNCTIONAL_TEST)
              else if $l = VALIDITY then
                ($v, NORMAL_OPERATION)
              else if $l = NOT_VALIDITY or $l = NA then
                ($v, FAILURE_WARNING)
              else
                ($v, NO_COMPUTED_DATA)  
              endif endif endif
              
 function label_2_5($v in Real, $l in Label_IN2_validity) =
              if $l = TEST then
                ($v, FUNCTIONAL_TEST)
              else if $l = VALIDITY then
                ($v, NORMAL_OPERATION)
              else if $l = NOT_VALIDITY or $l = NA then
                ($v, FAILURE_WARNING)
              else
                ($v, NO_COMPUTED_DATA)  
              endif endif endif
              
 function label_2_6($v in Label_IN7_value, $l in Label_IN2_validity) =
              if $l = TEST then
                ($v, FUNCTIONAL_TEST)
              else if $l = VALIDITY then
                ($v, NORMAL_OPERATION)
              else if $l = NOT_VALIDITY or $l = NA then
                ($v, FAILURE_WARNING)
              else
                ($v, NO_COMPUTED_DATA)  
              endif endif endif
              
 //gruppo3--------------------------------------------------------------------------                                         
function label_3_1($v in Label_IN7_value, $l in Label_IN3_validity) =
              if $l = VALID3 then
                ($v, VALIDITY3)
              else
                ($v, NCD3)  
              endif
              
function label_3_2($v in Label_IN12_value, $l in Label_IN3_validity) =
              if $l = VALID3 then
                ($v, VALIDITY3)
              else
                ($v, NCD3)  
              endif
              
function label_3_3($v in Label_IN13_value, $l in Label_IN3_validity) =
              if $l = VALID3 then
                ($v, VALIDITY3)
              else
                ($v, NCD3)  
              endif
                                                                     
function label_3_4($v in Label_IN14_value, $l in Label_IN3_validity) =
              if $l = VALID3 then
                ($v, VALIDITY3)
              else
                ($v, NCD3)  
              endif 
              
function label_3_5($v in Real, $l in Label_IN3_validity) =
              if $l = VALID3 then
                ($v, VALIDITY3)
              else
                ($v, NCD3)  
              endif                                          
 
//------------------------------------------------------------------------

//rules
//assegnazione valori alle label di uscita gruppo1(mandate sul bus arinc canale 1 ogni 20 ms)                                  
macro rule r_read_grouplabel1 = 
                          par 
                                                                                                                                 
                           cpm_label_group_1_1(CPM_116_CROSS_TRACK_DISTANCE) := (label_1_1(first(label_group_1_1(CROSS_TRACK_DISTANCE)), second(label_group_1_1(CROSS_TRACK_DISTANCE))),switch_label(GROUP_LABEL_1))
                           cpm_label_group_1_1(CPM_117_VERTICAL_LINEAR_DEVIATION) := (label_1_1(first(label_group_1_1(VERTICAL_LINEAR_DEVIATION)), second(label_group_1_1(VERTICAL_LINEAR_DEVIATION))),switch_label(GROUP_LABEL_1))                                                 
                           cpm_label_group_1_1(CPM_326_LATERAL_SCALE_FACTOR) := (label_1_1(first(label_group_1_1(LATERAL_SCALE_FACTOR)), second(label_group_1_1(LATERAL_SCALE_FACTOR))),switch_label(GROUP_LABEL_1))
                           cpm_label_group_1_1(CPM_327_VERTICAL_SCALE_FACTOR) := (label_1_1(first(label_group_1_1(VERTICAL_SCALE_FACTOR)), second(label_group_1_1(VERTICAL_SCALE_FACTOR))),switch_label(GROUP_LABEL_1))                          

                           cpm_label_group_1_2(CPM_275_OPERATING_MODE_PHASE) := (label_1_2(first(label_group_1_2(OPERATING_MODE_PHASE)), second(label_group_1_2(OPERATING_MODE_PHASE))),switch_label(GROUP_LABEL_1))

                           cpm_label_group_1_3(CPM_173_PSEUDO_LOC_DEVIATION) := (label_1_3(first(label_group_1_3(PSEUDO_LOC_DEVIATION)), second(label_group_1_3(PSEUDO_LOC_DEVIATION))),switch_label(GROUP_LABEL_1))
                           
                           cpm_label_group_1_4(CPM_174_PSEUDO_GS_DEVIATION) := (label_1_4(first(label_group_1_4(PSEUDO_GS_DEVIATION)), second(label_group_1_4(PSEUDO_GS_DEVIATION))),switch_label(GROUP_LABEL_1))
 
                           cpm_label_group_1_5(CPM_177_DISTANCE_TO_FPCP) := (label_1_5(first(label_group_1_5(DISTANCE_TO_FPCP)), second(label_group_1_5(DISTANCE_TO_FPCP))),switch_label(GROUP_LABEL_1))
                            
                           cpm_label_group_1_6(CPM_322_PSEUDO_GS_PATH_ANGLE) := (label_1_6(first(label_group_1_6(PSEUDO_GS_PATH_ANGLE)), second(label_group_1_6(PSEUDO_GS_PATH_ANGLE))),switch_label(GROUP_LABEL_1))
                           
                           cpm_label_group_1_7(CPM_105_SELECTED_RUNWAY_HEADING) := (label_1_7(first(label_group_1_7(SELECTED_RUNWAY_HEADING)), second(label_group_1_7(SELECTED_RUNWAY_HEADING))),switch_label(GROUP_LABEL_1))                                                                                                           
                          endpar

//assegnazione valori alle label in uscita gruppo2(mandate sul bus afdx ogni 40 ms)                           
macro rule r_read_grouplabel2 = par
                            
                            cpm_label_group_2_1(CPM_FMS_BUS_INDICATED_AIRSPEED) := (label_2_1(first(label_group_2_1(SEL_ADU_COMPUTED_AIRSPEED)), second(label_group_2_1(SEL_ADU_COMPUTED_AIRSPEED))),switch_label(GROUP_LABEL_2))                           
                            
                            cpm_label_group_2_2(CPM_FMS_BUS_VERTICAL_SPEED) := (label_2_2(first(label_group_2_2(BEST_VELOCITY_VERT)), second(label_group_2_2(BEST_VELOCITY_VERT))),switch_label(GROUP_LABEL_2))
                            cpm_label_group_2_2(CPM_FMS_BUS_INERT_VERTICAL_SPEED) := (label_2_2(first(label_group_2_2(SEL_AHRS_VERT_VEL_INERT)), second(label_group_2_2(SEL_AHRS_VERT_VEL_INERT))),switch_label(GROUP_LABEL_2))                            
 
                            cpm_label_group_2_3(CPM_FMS_BUS_TRUE_AIRSPEED) := (label_2_3(first(label_group_2_3(SEL_ADU_TRUE_AIRSPEED)), second(label_group_2_3(SEL_ADU_TRUE_AIRSPEED))),switch_label(GROUP_LABEL_2))
                             
                            cpm_label_group_2_4(CPM_FMS_BUS_RADIO_HEIGHT) := (label_2_4(first(label_group_2_4(SEL_RA_ALTITUDE)), second(label_group_2_4(SEL_RA_ALTITUDE))),switch_label(GROUP_LABEL_2))
                             
                            cpm_label_group_2_5(CPM_FMS_BUS_AIRCRAFT_MAG_HEADING) := (label_2_5(first(label_group_2_5(AIRCRAFT_MAG_HEADING)), second(label_group_2_5(AIRCRAFT_MAG_HEADING))),switch_label(GROUP_LABEL_2))
                            cpm_label_group_2_5(CPM_FMS_BUS_GROUND_SPEED) := (label_2_5(first(label_group_2_5(GROUND_SPEED)), second(label_group_2_5(GROUND_SPEED))),switch_label(GROUP_LABEL_2))
                            cpm_label_group_2_5(CPM_FMS_BUS_TRACK_ANGLE) := (label_2_5(first(label_group_2_5(TRACK_ANGLE)), second(label_group_2_5(TRACK_ANGLE))),switch_label(GROUP_LABEL_2))
                            cpm_label_group_2_5(CPM_FMS_BUS_ROLL_ANGLE) := (label_2_5(first(label_group_2_5(ROLL_ANGLE)), second(label_group_2_5(ROLL_ANGLE))),switch_label(GROUP_LABEL_2))
                            cpm_label_group_2_5(CPM_FMS_BUS_WIND_SPEED) := (label_2_5(first(label_group_2_5(SEL_WIND_AT_ALT_SPEED)), second(label_group_2_5(SEL_WIND_AT_ALT_SPEED))),switch_label(GROUP_LABEL_2))
                            cpm_label_group_2_5(CPM_FMS_BUS_WIND_ANGLE) := (label_2_5(first(label_group_2_5(SEL_WIND_AT_ALT_ANGLE)), second(label_group_2_5(SEL_WIND_AT_ALT_ANGLE))),switch_label(GROUP_LABEL_2))
                                                                                                                                                                         
                            cpm_label_group_2_6(CPM_FMS_BUS_TRUE_HEADING) := (label_2_6(first(label_group_2_6(BEST_TRUE_HEADING)), second(label_group_2_6(BEST_TRUE_HEADING))),switch_label(GROUP_LABEL_2))                                                                                       
                           endpar

//assegnazione valori label in uscita gruppo3(mandate sul bus arinc canale 2 ogni 320 ms)                      	 
macro rule r_read_grouplabel3 = par
                            
                            cpm_label_group_3_1(CPM_MON_ST_GPS1_LONGITUDE_DATA) := (label_3_1(first(label_group_3_1(CIVIL_GPS1_LONGITUDE)), second(label_group_3_1(CIVIL_GPS1_LONGITUDE))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_1(CPM_MON_ST_GPS2_LONGITUDE_DATA) := (label_3_1(first(label_group_3_1(CIVIL_GPS2_LONGITUDE)), second(label_group_3_1(CIVIL_GPS2_LONGITUDE))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_1(CPM_MON_ST_INIT_LONGITUDE_DATA) := (label_3_1(first(label_group_3_1(INIT_LONG)), second(label_group_3_1(INIT_LONG))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_1(CPM_MON_ST_AMMC_DEAD_REC_LONGITUDE_DATA) := (label_3_1(first(label_group_3_1(AMMC_LONG)), second(label_group_3_1(AMMC_LONG))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_1(CPM_MON_ST_BEST_LONGITUDE_DATA) := (label_3_1(first(label_group_3_1(BEST_LONG)), second(label_group_3_1(BEST_LONG))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_1(CPM_MON_ST_DME_DME_LONGITUDE_DATA) := (label_3_1(first(label_group_3_1(DME_DME_LONG)), second(label_group_3_1(DME_DME_LONG))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_1(CPM_MON_ST_VOR_DME_LONGITUDE_DATA) := (label_3_1(first(label_group_3_1(VOR_DME_LONG)), second(label_group_3_1(VOR_DME_LONG))),switch_label(GROUP_LABEL_3))
    
                            cpm_label_group_3_2(CPM_MON_ST_POSN_DIFF_CIVIL_GPS1) := (label_3_2(first(label_group_3_2(POSN_DIFF_CIVIL_GPS1)), second(label_group_3_2(POSN_DIFF_CIVIL_GPS1))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_2(CPM_MON_ST_POSN_DIFF_CIVIL_GPS2) := (label_3_2(first(label_group_3_2(POSN_DIFF_CIVIL_GPS2)), second(label_group_3_2(POSN_DIFF_CIVIL_GPS2))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_2(CPM_MON_ST_POSN_DIFF_DME_DME) := (label_3_2(first(label_group_3_2(POSN_DIFF_DME_DME)), second(label_group_3_2(POSN_DIFF_DME_DME))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_2(CPM_MON_ST_POSN_DIFF_VOR_DME) := (label_3_2(first(label_group_3_2(POSN_DIFF_VOR_DME)), second(label_group_3_2(POSN_DIFF_VOR_DME))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_2(CPM_MON_ST_POSN_DIFF_AMMC) := (label_3_2(first(label_group_3_2(POSN_DIFF_AMMC)), second(label_group_3_2(POSN_DIFF_AMMC))),switch_label(GROUP_LABEL_3))

                            cpm_label_group_3_3(CPM_MON_ST_GPS1_LATITUDE) := (label_3_3(first(label_group_3_3(CIVIL_GPS1_LATITUDE)), second(label_group_3_3(CIVIL_GPS1_LATITUDE))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_3(CPM_MON_ST_GPS2_LATITUDE) := (label_3_3(first(label_group_3_3(CIVIL_GPS2_LATITUDE)), second(label_group_3_3(CIVIL_GPS2_LATITUDE))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_3(CPM_MON_ST_INIT_LATITUDE) := (label_3_3(first(label_group_3_3(INIT_LAT)), second(label_group_3_3(INIT_LAT))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_3(CPM_MON_ST_AMMC_DEAD_REC_LATITUDE) := (label_3_3(first(label_group_3_3(AMMC_LAT)), second(label_group_3_3(AMMC_LAT))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_3(CPM_MON_ST_BEST_LATITUDE) := (label_3_3(first(label_group_3_3(BEST_LAT)), second(label_group_3_3(BEST_LAT))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_3(CPM_MON_ST_DME_DME_LATITUDE) := (label_3_3(first(label_group_3_3(DME_DME_LAT)), second(label_group_3_3(DME_DME_LAT))),switch_label(GROUP_LABEL_3))
                            cpm_label_group_3_3(CPM_MON_ST_VOR_DME_LATITUDE) := (label_3_3(first(label_group_3_3(VOR_DME_LAT)), second(label_group_3_3(VOR_DME_LAT))),switch_label(GROUP_LABEL_3))

                            cpm_label_group_3_4(CPM_MON_ST_BEST_POSN_SOURCE) := (label_3_4(first(label_group_3_4(BEST_POSN_SOURCE)), second(label_group_3_4(BEST_POSN_SOURCE))),switch_label(GROUP_LABEL_3))                            

                            cpm_label_group_3_5(CPM_MON_ST_BEST_BAR_ALT_CORR) := (label_3_5(first(label_group_3_5(SEL_ADU_BARO_CORR_ALTITUDE)), second(label_group_3_5(SEL_ADU_BARO_CORR_ALTITUDE))),switch_label(GROUP_LABEL_3)) 
                            cpm_label_group_3_5(CPM_MON_ST_BEST_MSL_ALT) := (label_3_5(first(label_group_3_5(BEST_MSL_ALT)), second(label_group_3_5(BEST_MSL_ALT))),switch_label(GROUP_LABEL_3))
                           endpar                     	   
    
//gestione del tempo di spedizione delle label sui bus corrispondenti sui canali corrispondenti  	
	macro rule r_read_labels =
	                        par 
                              timer := timer + 20
	                          if timer != 0 then
	                           par                            
	                            if (timer mod 20) = 0 then r_read_grouplabel1[]	                            
	                            endif 
	                            if (timer mod 40) = 0 then r_read_grouplabel2[]	                                                                                                                                                                   
	                            endif
	                            if (timer mod 320) = 0 then r_read_grouplabel3[]	                             
	                            endif
	                           endpar	                              	                            
	                          endif
	                          	                          
	                        endpar 
	                            
//Main Rule
    
    main rule r_fmsio = r_read_labels[]                             
	
	 
//define the initial states 

default init s0:
	
function timer = 0
function nexttimer = 0

function cpm_label_group_1_1($n in Cpm_label_name_group_1_1) = ((0.0, NCD),BUS_ARINC_CH1)
function cpm_label_group_1_2($n in Cpm_label_name_group_1_2) = ((NAV, NCD),BUS_ARINC_CH1)
function cpm_label_group_1_3($n in Cpm_label_name_group_1_3) = ((0.0, NCD),BUS_ARINC_CH1)
function cpm_label_group_1_4($n in Cpm_label_name_group_1_4) = ((0.0, NCD),BUS_ARINC_CH1)
function cpm_label_group_1_5($n in Cpm_label_name_group_1_5) = ((0.0, NCD),BUS_ARINC_CH1)
function cpm_label_group_1_6($n in Cpm_label_name_group_1_6) = ((0.0, NCD),BUS_ARINC_CH1)
function cpm_label_group_1_7($n in Cpm_label_name_group_1_7) = ((0.0, NCD),BUS_ARINC_CH1)

function cpm_label_group_2_1($n in Cpm_label_name_group_2_1) = ((0.0, NO_COMPUTED_DATA),BUS_AFDX)  
function cpm_label_group_2_2($n in Cpm_label_name_group_2_2) = ((0.0, NO_COMPUTED_DATA),BUS_AFDX)  
function cpm_label_group_2_3($n in Cpm_label_name_group_2_3) = ((0.0, NO_COMPUTED_DATA),BUS_AFDX) 
function cpm_label_group_2_4($n in Cpm_label_name_group_2_4) = ((0.0, NO_COMPUTED_DATA),BUS_AFDX) 
function cpm_label_group_2_5($n in Cpm_label_name_group_2_5) = ((0.0, NO_COMPUTED_DATA),BUS_AFDX)  
function cpm_label_group_2_6($n in Cpm_label_name_group_2_6) = ((0.0, NO_COMPUTED_DATA),BUS_AFDX)  
 
function cpm_label_group_3_1($n in Cpm_label_name_group_3_1) = ((0.0, NCD3),BUS_ARINC_CH2)
function cpm_label_group_3_2($n in Cpm_label_name_group_3_2) = ((0.0, NCD3),BUS_ARINC_CH2)
function cpm_label_group_3_3($n in Cpm_label_name_group_3_3) = ((0.0, NCD3),BUS_ARINC_CH2)
function cpm_label_group_3_4($n in Cpm_label_name_group_3_4) = ((NONE, NCD3),BUS_ARINC_CH2)
function cpm_label_group_3_5($n in Cpm_label_name_group_3_5) = ((0.0, NCD3),BUS_ARINC_CH2)




	

		
