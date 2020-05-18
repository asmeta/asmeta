asm SISAbstract
	
import StandardLibrary
	
signature:
	domain WaterpressureType subsetof Integer
	enum domain Switch = {ON | OFF}
	enum domain Pressure = { TOOLOW |NORMAL |HIGH }
	monitored changeMode : Boolean
	monitored reset : Switch
	monitored block : Switch
	controlled pressure : Pressure
	controlled overridden : Boolean
	controlled safetyInjection : Switch

definitions:

	macro rule  r_1 =
		if pressure = TOOLOW and changeMode then
			pressure := NORMAL
		endif

	macro rule r_2 =
		if pressure = NORMAL and changeMode then 
			choose $x in Pressure with $x = HIGH or $x = TOOLOW do
				pressure := $x
		endif

	macro rule  r_3 =
		if pressure = HIGH and changeMode then
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
		if block = ON and reset = OFF and pressure = TOOLOW  then
			overridden := true
		endif

	macro rule r_7 =
		if pressure = TOOLOW then
			if overridden then
				safetyInjection := OFF
	         else
				safetyInjection := ON
			endif
		else
			safetyInjection := OFF
		endif

	main rule r_SIS =
		par
			r_1[]
			r_2[]
			r_3[]
			r_5[]
			r_6[]
			r_7[]
		endpar

default init s1:   
	function pressure = TOOLOW
	function reset = OFF
	function block = OFF
	function overridden = false
	function safetyInjection = OFF
