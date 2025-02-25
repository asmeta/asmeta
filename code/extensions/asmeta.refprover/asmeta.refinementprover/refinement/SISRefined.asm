asm SISRefined
	
import StandardLibrary
	
signature:
	domain WaterpressureType subsetof Integer
	enum domain Switch = {ON | OFF}
	enum domain Pressure = { TOOLOW |NORMAL |HIGH }
	monitored waterpressure: WaterpressureType 
	monitored reset : Switch
	monitored block : Switch
	controlled pressure : Pressure
	controlled overridden : Boolean
	controlled safetyInjection : Switch
	static low : WaterpressureType
	static permit : WaterpressureType

definitions:
	
domain WaterpressureType = {0 : 2000}    

function low = 900

function permit = 1000

macro rule  r_1 = 
           if ((waterpressure >= low) and (pressure = TOOLOW)) then
     
             pressure := NORMAL

           endif

macro rule r_2 = if (waterpressure >= permit) and (pressure = NORMAL) then  par

	  pressure := HIGH
	  overridden := false

          endpar     endif

macro rule  r_3 = if waterpressure < low and pressure = NORMAL then 

        pressure := TOOLOW

	endif

macro rule r_4 = if waterpressure < permit and pressure = HIGH 

    then par pressure := NORMAL

         overridden := false

	endpar endif

macro rule r_5 = if reset = ON and 

    (pressure = TOOLOW or pressure = NORMAL)

    then overridden := false

    endif

macro rule r_6 =  if block = ON and 

      reset = OFF and pressure = TOOLOW 

    then overridden := true

    endif

	macro rule r_7 =  if pressure = TOOLOW 
    then if overridden 

         then safetyInjection := OFF

         else safetyInjection := ON

         endif
    else 
         safetyInjection := OFF 
    endif


// invariant for delta of waterpressure

// invariant over waterpressure : (abs (waterpressure - next(waterpressure)) < 10) 
	
main rule r_SIS = par r_1[] r_2[] r_3[] r_4[] r_5[] r_6[] r_7[] endpar
	
default init s1:   
	function waterpressure = 12
	function pressure = TOOLOW
	function reset = OFF
	function block = OFF
	function overridden = false
	function safetyInjection = OFF
