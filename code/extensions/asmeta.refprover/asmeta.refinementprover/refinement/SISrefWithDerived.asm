asm SISrefWithDerived
	
import StandardLibrary
	
signature:
	domain WaterpressureType subsetof Integer
	domain WaterpressureDeltaType subsetof Integer
	enum domain Pressure = { TOOLOW |NORMAL |HIGH }
	derived pressure : Pressure
	controlled waterpressure: WaterpressureType
	monitored delta  : WaterpressureDeltaType

definitions:
	domain WaterpressureType = {0 : 2000}    
	domain WaterpressureDeltaType = {0  : 20}
	
	function pressure =
		if waterpressure < 500 then
			TOOLOW
		else
			if waterpressure < 1000 then
				NORMAL
			else
				HIGH
			endif
		endif

	invariant inv_invForRef over pressure: isDef(pressure)

	main rule r_SIS = 
		if waterpressure + delta >= 0 and waterpressure + delta < 2000 then
			waterpressure :=  waterpressure + delta
		endif

default init s1:   
  function waterpressure = 12