/* example of ASM specification */
//MODELLO ASM DI TEST PER LA COMPONENETE FMS_IO

asm fms_io_for_atgt

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
    
    domain Label_IN3_value subsetof Integer
    domain Label_IN8_value subsetof Integer               
    enum domain Label_IN14_value = {NONE | INIT | CIVIL_GPS_1 | CIVIL_GPS_2 | EGI_DVS_INS | EGI_PURE_INS | DME_DME | VOR_DME | AMMC | EGI_PURE_GPS | EGI_GPS_INS}       

    enum domain Label_name_group_1_3 = {PSEUDO_LOC_DEVIATION}   
    enum domain Cpm_label_name_group_1_3 = {CPM_173_PSEUDO_LOC_DEVIATION}
        
    enum domain Label_name_group_2_1 = {SEL_ADU_COMPUTED_AIRSPEED}   
    enum domain Cpm_label_name_group_2_1 = {CPM_FMS_BUS_INDICATED_AIRSPEED}

    
    enum domain Label_name_group_3_4 = {BEST_POSN_SOURCE}   
    enum domain Cpm_label_name_group_3_4 = {CPM_MON_ST_BEST_POSN_SOURCE}
    
    
    domain TimerD subsetof Integer
        
    enum domain Group_label = {GROUP_LABEL_1 | GROUP_LABEL_2 | GROUP_LABEL_3}
    
    enum domain Bus = {BUS_ARINC_CH1 | BUS_AFDX | BUS_ARINC_CH2}
    
// functions 
     
    //label che devono essere mandate sui bus periodicamente ogni 20 ms
    //valori in dall'ambiente esterno
    dynamic monitored  label_group_1_3 : Label_name_group_1_3 -> Prod(Label_IN3_value,Label_IN1_validity)
    dynamic controlled cpm_label_group_1_3 : Cpm_label_name_group_1_3 -> Prod(Prod(Label_IN3_value,Label_OUT1_validity),Bus)
          
//label che devono essere mandate sui bus ogni 40 ms

dynamic monitored label_group_2_1 : Label_name_group_2_1 -> Prod(Label_IN8_value,Label_IN2_validity)
dynamic controlled cpm_label_group_2_1 : Cpm_label_name_group_2_1 -> Prod(Prod(Label_IN8_value,Label_OUT2_validity),Bus)                      
                                                                                   
//label che devono essere mandate sul bus ogni 320 ms
dynamic monitored label_group_3_4 : Label_name_group_3_4 -> Prod(Label_IN14_value,Label_IN3_validity)
dynamic controlled cpm_label_group_3_4 : Cpm_label_name_group_3_4 -> Prod(Prod(Label_IN14_value,Label_OUT3_validity),Bus) 
   
derived label_1_3 : Prod(Label_IN3_value, Label_IN1_validity) -> Prod(Label_IN3_value, Label_OUT1_validity)
derived label_2_1 : Prod(Label_IN8_value, Label_IN2_validity) -> Prod(Label_IN8_value, Label_OUT2_validity)
derived label_3_4 : Prod(Label_IN14_value, Label_IN3_validity) -> Prod(Label_IN14_value, Label_OUT3_validity)   

                   
 //timer per simulare i ms passati   
    dynamic controlled timer : TimerD
    dynamic controlled nexttimer : TimerD  
    
 //DICE QUALE BUS E CANALE USARE     
    static switch_label : Group_label -> Bus
    

// definitions 

definitions:
		
  domain TimerD = {0 : 720}
  domain Label_IN3_value = {0 : 1000}
  domain Label_IN8_value = {0 : 450}
  
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
                            
function label_1_3($v in Label_IN3_value, $l in Label_IN1_validity) =
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
              
              
 //gruppo3--------------------------------------------------------------------------                                         
                                                                     
function label_3_4($v in Label_IN14_value, $l in Label_IN3_validity) =
              if $l = VALID3 then
                ($v, VALIDITY3)
              else
                ($v, NCD3)  
              endif 
                                                       
 
//------------------------------------------------------------------------

//rules
//assegnazione valori alle label di uscita gruppo1(mandate sul bus arinc canale 1 ogni 20 ms)                                  
macro rule r_read_grouplabel1 = 
                           
                           cpm_label_group_1_3(CPM_173_PSEUDO_LOC_DEVIATION) := (label_1_3(first(label_group_1_3(PSEUDO_LOC_DEVIATION)), second(label_group_1_3(PSEUDO_LOC_DEVIATION))),switch_label(GROUP_LABEL_1))                                                                                                         
                          

//assegnazione valori alle label in uscita gruppo2(mandate sul bus afdx ogni 40 ms)                           
macro rule r_read_grouplabel2 = 
                            cpm_label_group_2_1(CPM_FMS_BUS_INDICATED_AIRSPEED) := (label_2_1(first(label_group_2_1(SEL_ADU_COMPUTED_AIRSPEED)), second(label_group_2_1(SEL_ADU_COMPUTED_AIRSPEED))),switch_label(GROUP_LABEL_2))                                                                                                                                              
                           

//assegnazione valori label in uscita gruppo3(mandate sul bus arinc canale 2 ogni 320 ms)                      	 
macro rule r_read_grouplabel3 = 
                            cpm_label_group_3_4(CPM_MON_ST_BEST_POSN_SOURCE) := (label_3_4(first(label_group_3_4(BEST_POSN_SOURCE)), second(label_group_3_4(BEST_POSN_SOURCE))),switch_label(GROUP_LABEL_3))                            
                                                	   
    
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

function cpm_label_group_1_3($n in Cpm_label_name_group_1_3) = ((0, NCD),BUS_ARINC_CH1)

function cpm_label_group_2_1($n in Cpm_label_name_group_2_1) = ((0, NO_COMPUTED_DATA),BUS_AFDX)  
   
function cpm_label_group_3_4($n in Cpm_label_name_group_3_4) = ((NONE, NCD3),BUS_ARINC_CH2)





	

		
