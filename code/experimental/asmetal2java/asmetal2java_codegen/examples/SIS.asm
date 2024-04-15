asm SIS
	
import STDL/StandardLibrary

signature:
	
	domain WaterpressureType subsetof Integer
	domain Delta subsetof Integer
	enum domain Switch = {ON | OFF}
	enum domain Pressure = { TOOLOW |NORMAL |HIGH }

	controlled waterpressure: WaterpressureType 
	controlled pressure : Pressure
	controlled overridden : Boolean
	controlled safetyInjection : Switch

	monitored reset : Switch
	monitored block : Switch
	monitored delta : Delta

	static low : WaterpressureType
	static permit : WaterpressureType
	static maxwp : WaterpressureType
	static minwp : WaterpressureType

definitions:
	
	domain WaterpressureType = {0 : 100}
	domain Delta = {-5 : +5}

	function low = 9
	function permit = 10
	function maxwp = 20
	function minwp = 0

	// modify the water pressure: if it is < then 0 take 0
	// it it is > max, take max
	macro rule r_wp = 
		if  waterpressure + delta < minwp then  
			waterpressure := minwp
		else if waterpressure + delta > maxwp then  
			waterpressure := maxwp
		else 
			waterpressure := waterpressure  + delta
		endif
		endif

	macro rule r_1 = 
		if waterpressure >= low and pressure = TOOLOW then
			pressure := NORMAL
		endif

	macro rule r_2 = 
		if waterpressure >= permit and pressure = NORMAL then
			par
				pressure := HIGH
				overridden := false
			endpar
		endif

	macro rule r_3 = 
		if waterpressure < low and pressure = NORMAL then 
	        pressure := TOOLOW
		endif

	macro rule r_4 = 
		if waterpressure < permit and pressure = HIGH then
			par 
				pressure := NORMAL
				overridden := false
			endpar
		endif

	macro rule r_5 = 
		if reset = ON and (pressure = TOOLOW or pressure = NORMAL) then
			overridden := false
		endif

	macro rule r_6 = 
		if block = ON and reset = OFF and pressure = TOOLOW then 
			overridden := true
		endif

	macro rule r_7 =  
		if pressure = TOOLOW then 
			if overridden then 
				safetyInjection := OFF
			else 
				safetyInjection := ON
			endif
		endif

	macro rule r_8 =  
		if pressure != TOOLOW then 
			safetyInjection := OFF 
	    endif
	
	main rule r_Main = 
		par 
			r_wp[] 
			r_1[] 
			r_2[] 
			r_3[] 
			r_4[] 
			r_5[] 
			r_6[] 
			r_7[] 
		endpar

default init s0:

  function waterpressure = 3
  function pressure = TOOLOW
  function reset = OFF
  function block = OFF
  function overridden = false
  function safetyInjection = OFF
