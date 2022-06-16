asm supervisorAdapt
import StandardLibrary
import TimeLibraryControlled

signature: 

/****************************					 
 * DEFINITION OF DOMAINS	*
 ***************************/						
	enum domain StatesSup = {STARTUP | INIT | SELFTEST | VENTILATIONON | VENTILATIONOFF | FAILSAFE}
	
	enum domain Modes = {PCV | PSV |ASV} //ASV new
	
	enum domain Reply = {RESPONSE | ERROR | NORESPONSE}
	
	//breath_on = VENTILATIONON
	enum domain Watchst = {INACTIVE | BREATHON | ALARM} //watchdogs bits status
	
	enum domain BuzState = {ENABLE|DISABLE}
	
	enum domain StatusSelf = {NOTSTART | PERFORMING | ENDED}
	
	enum domain ValveStatus = {OPEN | CLOSED}
	
	enum domain BreathSync = {EXP | INSP} 

	enum domain Alarm = {NONE | LOW | MEDIUM | HIGH} //Alarm bits
	
	enum domain Led = {YELLOW_CONSTANT | RED_CONSTANT | YELLOW_FLASHING | RED_FLASHING | OFF}

	domain Num subsetof Integer
	
	domain Perc subsetof Integer
/************************					 
 * INPUT SUPERVISOR	*
 ************************/	
	dynamic monitored watchdog: Boolean 
	dynamic monitored respirationMode_sup: Modes
		
	/* ***MONITORED*** */
	//start - stop bits
	dynamic monitored enter_self: Boolean
	dynamic monitored exit_self: Boolean
	
	//Run bit 1 when ventilation on and 0 otherwise
	
	dynamic monitored run_command: Boolean   
	dynamic monitored stop_command: Boolean
	
	/* ***MONITORED*** */
	dynamic  monitored temperature: Num
	
	//TRUE working || FALSE not working	
	/* ***MONITORED*** */
	dynamic monitored fan_working: Boolean	 //tachometer	
			
	/* PRESSURE SENSOR ***MONITORED*** */
	dynamic monitored pi_6: Num
	dynamic monitored pi_6_reply: Reply //There is a response from the sensor || The response reported an error

	/* ADC ***MONITORED*** */	 
	dynamic monitored adc_reply: Reply //There is a response from the ADC || The response reported an error

	dynamic monitored breath_sync: BreathSync
	
	/* ***MONITORED*** */	
	dynamic monitored all_cont: Alarm
	dynamic monitored snooze: Boolean 
	
	//v1 e v4 = true means enable
	dynamic monitored iValve: ValveStatus
	dynamic monitored oValve: ValveStatus
	
	dynamic monitored weight_sup: Real
	dynamic monitored compliance: Real
	dynamic monitored vol_min_perc: Real //changed from Perc to real
	dynamic monitored rcexp: Real
	dynamic monitored peep: Real
	dynamic monitored current_volume: Real
 /*
  * IN OUTPUT
  */
	dynamic out watchdog_st: Watchst //It indicates the state of the supervisor to the controller
	dynamic out status_selftest: StatusSelf //Self test bit
	
	//dynamic out run_status: Boolean /*****************/
		
	//dynamic out run_bit: Boolean /*****************/
	
	//out
	dynamic controlled led: Led
	
	//out
	dynamic controlled buzzer: BuzState 
	
	//out	
	dynamic out al_bit: Alarm
		
	//dynamic out respirationMode: Modes
	dynamic out respirationMode_out: Modes
	/* 
	 *	SUPPORT VARIABLES  
	 */
	dynamic controlled state: StatesSup
		
	dynamic controlled count_ie: Num
	
	dynamic controlled count_ppmax: Num
	dynamic controlled count_pkmax: Num  
	dynamic controlled count_pkmin: Num 
	dynamic controlled count_rr_max: Num
	dynamic controlled count_rr_min: Num 
	
	dynamic controlled time_insp: Integer //t_insp = time of inspiration
	dynamic controlled time_exp: Integer //t_esp = time of expiration

	//max attempts using counter   SUP.16
	dynamic controlled max_attempts_pi_6: Num
	dynamic controlled max_attempts_adc: Num 	
	
	dynamic controlled peep_max: Num
	dynamic controlled peep_min: Num //PER.52 default parameter?
	
	//Is the range ok?
	dynamic controlled peak_max: Num //ex. peak_c
	dynamic controlled peak_min: Num
	
	dynamic out insp_valve: ValveStatus
	dynamic out exp_valve: ValveStatus

	dynamic controlled previous_breath: BreathSync
	
	dynamic controlled min_rr: Real
	dynamic controlled max_rr: Real
	dynamic controlled f_prev: Real
	dynamic controlled rr_supp:Real
	dynamic controlled count_dropPAW: Integer
	dynamic controlled ver_rr: Real	
		
	//dynamic monitored mCurrSecs: Integer
	
	dynamic controlled freq_target: Real 
	dynamic controlled vol_curr_target: Real
	
	dynamic monitored limit_pa: Real
	
	dynamic monitored dropPAW_ITS_sup: Boolean

	derived temperatured: Boolean
	
	derived watch: Boolean
	
	derived pi_6_time: Boolean
	
	derived rr_check_min: Boolean
	derived rr_check_max: Boolean
	
	derived max_attempts: Num -> Boolean
	
	derived calc_t: Timer->Integer
	
	derived revo: Prod(Integer,Integer) -> Real
	
	/*Adapt */
	derived vt_min: Real 
	derived vol_min_target: Real
	derived check_freq_max: Real
	derived check_press_max: Real
	
	derived freq_max_mode1: Real 
	derived freq_max_mode2: Real
	
	derived press_max_mode1: Real
	derived press_max_mode2: Real
	
	derived vt: Real
	derived vd: Real
	derived rr: Real
	derived alpha: Real
	derived f_resp_target: Real
	
	derived otis_boundaries: Boolean
	derived breath_triggered: Boolean
	/********/
	
	/* 
	 *	Timer 
	 */
	static timer_breath: Timer
	static timer_insp: Timer
	static timer_exp: Timer
	
	static timer1secondPassed:Timer
	static timer4secondPassed: Timer
	
	static freq_min: Num
	
	static paw_max: Num 
	/* 
	 *	Function F
	 */

	
definitions:		
	domain Num = {-1000:1000}
	domain Perc = {0:200}
	function paw_max = 65
	function freq_min = 5 //cicli al minuto
	
	function alpha =
		(2.0*(3.14159 * 3.14159))/60.0
	
	function vd =
		(2.2 * weight_sup) / 1000.0
	
	function f_resp_target =
		(pwr((1.0 + 2.0 * alpha * rcexp * ((vol_min_target - (f_prev) * vd)/vd)),0.5) - 1.0)/(alpha * rcexp)  
	
	function vt = 
		vol_min_target / itor(rtoi(f_resp_target))
	
	function calc_t($t in Timer) =
		currentTime($t) - start($t)
	
	function revo($insp in Integer, $expi in Integer) =
		60/($insp + $expi)
	
	function watch = 
		if(expired(timer4secondPassed) and not(watchdog)) then
			false
		else
			 true
		endif
	
	function temperatured =	//derivate
		if(temperature >= -25 and temperature <= 75) then
			true
		else
			false
		endif
		
	function pi_6_time =
		if not(expired(timer1secondPassed) and (pi_6 > paw_max)) then // SUP.152.1
			true
		else
			false
		endif
			
	function max_attempts($t in Num) =
		if($t = 4) then
			true
		else
			false
		endif
	
	function rr =
		revo(time_insp, calc_t(timer_exp))
	
	function rr_check_min =
		if(revo(time_insp, calc_t(timer_exp)) < min_rr) then
			true
		else
			false
		endif
	
	function rr_check_max =
		if(revo(time_insp, calc_t(timer_exp)) > max_rr) then
			true
		else
			false
		endif		 
	//Lettera B
	function vt_min =
		 (4.4 * weight_sup)/1000.0
		 
	function vol_min_target =
		 (weight_sup * 0.1 * ((vol_min_perc)/100.0)) //problema segnalato
		 	
	/*Questa è la lettera C */ 
	function freq_max_mode1 = 
	 	vol_min_target/vt_min
	 
	//da controllare 
	function freq_max_mode2 =
		20.0 * rcexp
	 
	function check_freq_max = 
		if(freq_max_mode1<freq_max_mode2 and freq_max_mode1 < 60.0)then 
	 		freq_max_mode1
		else 
			if(freq_max_mode2<freq_max_mode1 and freq_max_mode2 < 60.0) then
		 		freq_max_mode2
		 	else
	 			if(freq_max_mode1 >= 60.0 or freq_max_mode2 >= 60.0) then
	 				60.0
	 			endif
	 		endif
	 	endif
	 
	 /*Questa è la lettera A */
	 function press_max_mode1 =
	   compliance * (limit_pa - 10.0 - peep)	 	
	 
	 function press_max_mode2 =
	 	(22.0 * weight_sup)/1000.0 /**è peso / 1000 */
	 	//div(mult(22.0,weight_sup),1000.0)
	 
	 function check_press_max =
	 	if(press_max_mode1 < press_max_mode2) then
	 		press_max_mode1
	 	else
	 		if(press_max_mode2 < press_max_mode1) then
	 			press_max_mode2
	 		endif
	 	endif
	 /*Fine lettera A*/
	 	
	 function otis_boundaries =
	 	if current_volume > check_press_max or current_volume < vt_min or rr > check_freq_max or rr < itor(freq_min) then
	 		false
	 	else
	 		true
	 	endif
	
	function breath_triggered = 
		if(count_dropPAW = 2 and dropPAW_ITS_sup and respirationMode_out = PCV) then
			true
		else
			false
	 	endif
	 	 
	//SUP 26.3
	macro rule r_check_peep_max =
			if(pi_6 > peep_max) then
				par
					if(count_ppmax = 3) then
						state := FAILSAFE
					endif
					count_ppmax := count_ppmax + 1 
				endpar
			else
				count_ppmax := 0
			endif
	
	//SUP 26.4
	macro rule r_check_peak_max =
		if(pi_6 > peak_max) then
			par
				if(count_pkmax = 3) then
					state := FAILSAFE
				endif
				count_pkmax := count_pkmax + 1
			endpar
		else
			count_pkmax := 0
		endif
		
	//SUP 27.1
	macro rule r_check_peep_min =
		if(pi_6 < peep_min) then
			al_bit := HIGH		
		endif
	
	//SUP 26.3
	macro rule r_check_peak_min =
		if(pi_6 < peak_min) then
			par
				if(count_pkmin = 3) then
					al_bit := HIGH
				endif
				count_pkmin := count_pkmin + 1
			endpar
		else
			count_pkmin := 0	
		endif		

	//SUP 26.5 
	macro rule r_check_ie($insp in Integer,$exp in Integer) =
		if((div($insp,$exp))<0.01) then
			par
			count_ie :=  count_ie + 1
				if(count_ie = 4) then
					state := FAILSAFE
				endif
			endpar
		else
			count_ie := 0
		endif
		
	//SUP 25.3		
	macro rule r_check_rr_min =
			if(rr_check_min) then
				par
					if(count_rr_min = 3) then
						al_bit := HIGH
					endif
					count_rr_min := count_rr_min + 1
				endpar
			else
				count_rr_min := 0
			endif
 
	//SUP 25.3
	macro rule r_check_rr_max =
			if (rr_check_max) then
				par
					if(count_rr_max = 3) then
						al_bit := HIGH
					endif
					count_rr_max := count_rr_max + 1
				endpar
			else
				count_rr_max := 0
			endif
		
	macro rule r_check_drop =
			if(dropPAW_ITS_sup and respirationMode_out = PCV) then
				count_dropPAW := count_dropPAW + 1
			else
				if(not(dropPAW_ITS_sup) and respirationMode_out = PCV) then
					count_dropPAW := 0		
				endif
			endif
		
	macro rule r_breath_transition =
		if(not(previous_breath = breath_sync) and not(isUndef(previous_breath))) then
			par
				previous_breath := breath_sync	
				if(breath_sync = EXP) then  //Insp-->Exp
						par				
							time_insp:= calc_t(timer_insp) 
							r_check_peak_max[] //SUP 26.4
							r_check_peak_min[] //SUP 26.3
							
							r_reset_timer[timer_exp]
						endpar
			     endif
				if(breath_sync = INSP) then
					par	
						
						ver_rr := rr //inserita per controllo rr
						
						rr_supp := revo(time_insp, calc_t(timer_exp))
						
						r_check_peep_max[]	//SUP 26.2
						r_check_peep_min[]	//SUP.27.1	
						
						r_check_rr_min[]	//SUP.25.3	
						r_check_rr_max[]	//SUP.25.3
						r_check_drop[]
						time_exp:= calc_t(timer_exp)
						
						r_reset_timer[timer_insp]	
						r_reset_timer[timer_breath]
						
						r_check_ie[time_insp, (currentTime(timer_insp) - start(timer_exp))] //SUP.26.5	
						
						f_prev := f_resp_target
															 
						if((respirationMode_sup = PCV and not(otis_boundaries)) or breath_triggered  )then
								//respirationMode := ASV
								respirationMode_out := ASV
							else
								//respirationMode := respirationMode_sup	
								respirationMode_out := respirationMode_sup
						endif
							
					endpar 
				endif
			endpar
		endif
	
	macro rule r_same =
		if(breath_sync = previous_breath)then
					par
						previous_breath := breath_sync
						if(breath_sync = EXP) then				
								time_exp:= calc_t(timer_exp)  
						endif
						if(breath_sync = INSP) then
								time_insp:= calc_t(timer_insp)	
						endif
					endpar		
		endif
		
	macro rule r_firstbreath =
		if(isUndef(previous_breath))then	
			par
				state := VENTILATIONON
				watchdog_st := BREATHON	
				
				respirationMode_out := respirationMode_sup 
					
				r_check_peak_max[] //SUP 26.4
				r_check_peak_min[] //SUP 26.3
						
				r_reset_timer[timer_insp]
				r_reset_timer[timer_breath]
				r_reset_timer[timer1secondPassed]
		    	r_reset_timer[timer4secondPassed]
						
				previous_breath := breath_sync
								
				insp_valve := OPEN //SUP.23
				exp_valve := CLOSED
			endpar
		endif

	macro rule r_check_sync =		
				if not(expired(timer_breath)) then //25.4
					par
						r_breath_transition[] //when previous breath != of the current
						r_same[] // previous breath = of the current
					endpar
				else
					state := FAILSAFE
				endif
				
				
	macro rule r_led_board($s in Alarm) =	
		switch($s)
			case LOW:
					led := YELLOW_CONSTANT
			case MEDIUM:
					led := YELLOW_FLASHING	
			case HIGH:
					led := RED_FLASHING	
			otherwise
					led := OFF
		endswitch	
		
	macro rule r_al_manage($r in Alarm) = 		
			if not($r=NONE) then
				par
					r_led_board[$r]
							if(snooze)then
								buzzer := DISABLE
							else
								buzzer := ENABLE
							endif
				endpar
			endif

	macro rule r_al_con =
		if al_bit = NONE and not(all_cont = NONE) then //supervisor takes precedence SUP.30
			r_al_manage[all_cont] //SUP.150
		endif 
		 /*
		    SUP150 already set 
			insp_board_valve = CLOSED
			esp_board_valve = OPEN
		  */	

	macro rule r_execute_st =
		par
		  state := SELFTEST
		  insp_valve := OPEN  //SUP.160
		  exp_valve := CLOSED
		  status_selftest := PERFORMING
		endpar

	macro rule r_stop_st =
		par
		  state := VENTILATIONOFF
		  insp_valve := CLOSED //SUP.19
		  exp_valve := OPEN
		  r_reset_timer[timer1secondPassed]
		  r_reset_timer[timer4secondPassed]
		  status_selftest := ENDED
		endpar

		/*Controller and supervisor say the same thing */
	macro rule r_check_v =
		if(breath_sync = INSP) then
			if not(iValve = OPEN and oValve = CLOSED) then
				state := FAILSAFE
			endif
		else if(breath_sync = EXP) then
				if not(oValve = OPEN and iValve = CLOSED) then
					state := FAILSAFE
				endif
			endif
		endif

	macro rule r_start_v =
 		par
 			r_firstbreath[]
			//r_check_sync[] --eliminare
			r_al_con[]
			r_check_v[]				
		endpar

	macro rule r_iniz =
		par	
			count_pkmin := 0
			count_pkmax := 0
			rr_supp := 0.0
			count_ppmax := 0
			count_rr_min := 0	//here or at the start of ventilation on?
			count_rr_max := 0
		endpar
					
	macro rule r_stop_v =
 		if not(expired(timer_breath)) then // è necessario?
 			par
 			state := VENTILATIONOFF
 			previous_breath := undef
 			watchdog_st := INACTIVE	
 			count_dropPAW := 0
			r_iniz[]
		    r_reset_timer[timer1secondPassed]
		    r_reset_timer[timer4secondPassed]
			endpar	
		else
			state := FAILSAFE
 		endif
		

	//SUP.16
	macro rule r_check_pi6 =
		if not(pi_6_reply = RESPONSE) then //response received with errors from pi_6
			par
				if(max_attempts(max_attempts_pi_6)) then
					state := FAILSAFE
				endif					
				max_attempts_pi_6 := max_attempts_pi_6 + 1
			endpar
		else
			max_attempts_pi_6 := 0
		endif
	
	//SUP.17
	macro rule r_check_adc =
		if not(adc_reply = RESPONSE) then //response received with errors from  adc
			par
				if(max_attempts(max_attempts_adc)) then
					state := FAILSAFE	
				endif
				max_attempts_adc := max_attempts_adc + 1
			endpar
		else
			max_attempts_adc := 0
		endif
	
	macro rule r_startup =
			par
				watchdog_st := INACTIVE
				status_selftest := NOTSTART
				if(watch) then	//SUP.18
					if(watchdog) then //SUP.13 first watchdog from the controller
						par
							state := INIT
							/***SUP.14 already declared in the initialization 
							insp_board_valve := CLOSED 
							exp_board_valve := OPEN    
							***/
							r_reset_timer[timer1secondPassed]
							r_reset_timer[timer4secondPassed]
						endpar
					endif
				else
					state := FAILSAFE
				endif
			endpar
			
	macro rule r_init =
		if temperatured and fan_working and watch and pi_6_time then //SUP.151.3 //SUP.151.4 //SUP.152.1 //SUP.152.2  
				par
				 	r_al_con[]
					if (enter_self) then //152.3 ??? 
						par	
							r_execute_st[]
							r_reset_timer[timer4secondPassed]
						endpar
					endif 
					if(watchdog) then
						r_reset_timer[timer4secondPassed]
					endif
				endpar
		else 
			state := FAILSAFE	
		endif
	
	macro rule r_selftest =
			if watch then //SUP.161
				par
					if not(al_bit = NONE) then //SUP.162 SUP.163 
						r_al_manage[al_bit]
					endif
					if(watchdog) then
						r_reset_timer[timer4secondPassed]
					endif
					if (exit_self)then	//SUP.164
						r_stop_st[]						
					endif
				endpar
			 else
				state := FAILSAFE
			endif		 	
		
	macro rule r_ventilation_off =
			if(temperatured) //SUP 20.4
				and (watch) //SUP 21.2
				and (pi_6_time) then //21.1
					par	
						rr_supp := 0.0
						count_ppmax := 0
						count_rr_min := 0	//here or at the start of ventilation on?
						count_rr_max := 0
						r_reset_timer[timer_insp]
						r_reset_timer[timer_exp]
						r_al_con[] //SUP.19
						if(watchdog) then
							r_reset_timer[timer4secondPassed]
						endif
						if (run_command) then
								r_start_v[]
						endif
					endpar			
			else
					state := FAILSAFE	
			endif		
	
	
	macro rule r_ventilation_on =  
			if(temperatured) and (watch) and (pi_6_time) //SUP 20.4 //SUP.21.1 //SUP.21.2
				 then 
					if(stop_command) then
						par
							r_stop_v[]
							r_reset_timer[timer4secondPassed]
							r_reset_timer[timer1secondPassed]
						endpar
					else
						par
							insp_valve := iValve
					     	exp_valve := oValve
							r_al_con[]
							r_check_sync[]
						/*	if(rr_supp != 0.0) then
								par
									r_check_rr_min[]
									r_check_rr_max[]
								endpar
							endif*/
							r_check_v[] //SUP.25.1 //SUP.25.2
						endpar
					endif					
			else
				state := FAILSAFE	
			endif
									
	macro rule r_failsafe =
		par
			insp_valve := CLOSED //SUP.104
			exp_valve := OPEN 
			watchdog_st := ALARM 
			r_al_manage[al_bit] //SUP.30
		endpar
				
main rule r_main =  
	if(state = SELFTEST) then
	 	r_selftest[]
	 else
	 	 if(state = FAILSAFE) then
		 	r_failsafe[]
		else
			 par
			 freq_target := itor(rtoi(f_resp_target))
			vol_curr_target := vt
			 r_check_adc[]
			 r_check_pi6[]
				 if (adc_reply = RESPONSE) //SUP.100.2 e SUP.17
					and (pi_6_reply = RESPONSE) then //SUP.100.1 e SUP.16 
						if (fan_working) then //SUP.20.5 SUP.15 SUP.151.4
						 	switch(state)
								case(STARTUP):									
									r_startup[]										
								case(INIT):
									r_init[]
								case(VENTILATIONOFF):
									r_ventilation_off[]
								case(VENTILATIONON):
									r_ventilation_on[]
							endswitch
						 
						else
							state := FAILSAFE
						endif
			endif	
		endpar		
		endif 
	endif
	
default init s0:
	function state = STARTUP 
	
	function duration($t in Timer) = 
		if $t = timer1secondPassed then
			1 
		else
			if $t = timer4secondPassed then
				4 
		else 
			if $t = timer_breath then
				62 //in test 620 for ie
			endif
			endif
		endif
	
	
	/*Check all parameters */
	
	function start($t in Timer) = 
		currentTime($t)
	
	//function run_bit = false	
	function max_attempts_pi_6 = 0
	function max_attempts_adc = 0
	function insp_valve = CLOSED //SUP.14
	function exp_valve = OPEN
	function peep_max = 25
	function peak_max = 25 //Test parameters !!!
	function peak_min = 20 //Test parameters !!!
	function time_insp = 0
	function time_exp = 0
	function count_ie = 0
	function peep_min = 20 //Test parameters!!!
	function min_rr = 4.0
	function max_rr = 10.0//cambiare a 50
	
	//Counter initialization 
	function count_ppmax = 0
	function count_pkmax = 0
	function count_pkmin = 0
	function count_rr_min = 0
	function count_rr_max = 0
	function rr_supp = 0.0
	
	/*Out*/
	function al_bit = NONE
	function status_selftest = NOTSTART
	function watchdog_st = INACTIVE
	
	/*Monitorate*/
	function adc_reply = RESPONSE
	function fan_working = true
	function pi_6 = 25
	function pi_6_reply = RESPONSE
	function all_cont = NONE
	function breath_sync = EXP
	function enter_self = false
	function exit_self = false
	function iValve = CLOSED
	function mCurrSecs = 1
	function oValve = OPEN
	function run_command = false
	function stop_command = false
	//function watchdog = true	
	function snooze = false
	
	function f_prev = 			 
		if (weight_sup>30.0 and weight_sup<=39.0) then
				14.0
		else
			if (weight_sup>40.0 and weight_sup<=59.0) then
					12.0
			else
				if(weight_sup>60.0 and weight_sup<=89.0) then
						10.0
				else
					if(weight_sup>90.0 and weight_sup<=100.0)then
							10.0
					else
						if(weight_sup>100.0) then
								10.0 
						else
							0.0
						endif 
					endif
				endif
			endif
		endif
	
	function compliance = 3.0
	function weight_sup = 70.0
	function peep = 5.0		 
	function respirationMode_sup = PSV	 
	function rcexp = 0.5
	function vol_min_perc = 100.0
	function current_volume = 6.0
	function limit_pa = 19.0
	function dropPAW_ITS_sup = false
	function count_dropPAW = 0