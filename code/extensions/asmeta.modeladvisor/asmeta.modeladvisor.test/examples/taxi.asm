asm taxi

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	enum domain Client = {CL1|CL2|CL3|GR2|GR3}
	enum domain State = {WAITING | TRAVELLING | IDLE | CALLTAXI}
	domain Taxi subsetof Integer
	domain Lengths subsetof Integer
	static neededTaxi: Client -> Taxi
	derived numtaxis: Taxi
	dynamic controlled state: Client -> State
	dynamic controlled travelLength: Client -> Lengths
	dynamic monitored calltaxi: Client -> Boolean
	dynamic controlled keepTaxi: Client -> Taxi	
	dynamic monitored chooseLength: Lengths

definitions:
	domain Taxi = {0:3}
	domain Lengths = {1 : 3}
	function numtaxis = 3- keepTaxi(CL1) - keepTaxi(CL2) - keepTaxi(CL3) - keepTaxi(GR2) - keepTaxi(GR3)

	function neededTaxi($c in Client) =
		if($c=CL1 or $c=CL2 or $c=CL3) then
			 1
		else
			if($c=GR2) then
				2
			else
				3
			endif
		endif

	rule r_waiting = 
		choose $c in Client with ((state($c) = WAITING) and numtaxis>=neededTaxi($c)) do
			par
				state($c) := TRAVELLING
				keepTaxi($c) := neededTaxi($c)				
				travelLength($c) := chooseLength
			endpar
	
	rule r_travel =
		forall $c in Client with state($c)=TRAVELLING do //sometimes empty (but not always)
			if(travelLength($c) = 1) then
				par
					state($c) :=  IDLE
					keepTaxi($c) := 0
				endpar
			else
				travelLength($c) := travelLength($c) - 1
			endif
	
	rule r_idle = 
		forall $c in Client with state($c)=IDLE do //sometimes empty (but not always)
			if(calltaxi($c)) then
				state($c) := CALLTAXI
			endif
	
	rule r_callTaxi = 
		forall $c in Client with state($c)=CALLTAXI do //sometimes empty (but not always)
			state($c) := WAITING
	
	//it exists a three minutes period in which
	//there aren't available taxis
	CTLSPEC ef(numtaxis = 0 and ex(numtaxis = 0 and ex(numtaxis = 0)))

	//do not exists a path in which the taxis are
	//always busy
	CTLSPEC not(ef(numtaxis = 0 and eg(numtaxis = 0)))

	//liveness property: if there aren't available taxis,
	//sooner or later at least one taxi will
	//become available
	CTLSPEC ag(numtaxis = 0 implies af(numtaxis > 0))

	//it exists a state in which group GR3 asks all 3
	//taxis and it's request is accepted; in the
	//following state all the 3 taxis are busy
	CTLSPEC ef((numtaxis = 3 and state(GR3) = WAITING) and ex(numtaxis = 0 and state(GR3) = TRAVELLING))

	//liveness property: if a client or a group is
	//waiting a taxi, sooner or later the taxi
	//will arrive
	CTLSPEC ag(state(CL1) = WAITING implies ef(state(CL1) = TRAVELLING))
	CTLSPEC ag(state(CL2) = WAITING implies ef(state(CL2) = TRAVELLING))
	CTLSPEC ag(state(CL3) = WAITING implies ef(state(CL3) = TRAVELLING))
	CTLSPEC ag(state(GR2) = WAITING implies ef(state(GR2) = TRAVELLING))
	CTLSPEC ag(state(GR3) = WAITING implies ef(state(GR3) = TRAVELLING))
	
	//reachability property: it exists a state in which
	//all the single clients and group GR2 have called
	//a taxi. The property has been negated in order to
	//obtain the print of a particular example.
	CTLSPEC not(ef(state(CL1) = WAITING and state(CL2) = WAITING and state(CL3) = WAITING and state(GR2) = WAITING))
	
	//deadlock absence
	CTLSPEC ag(ex(true))

	main rule r_Main = 
		par
			r_idle[]
			r_waiting[]
			r_travel[]
			r_callTaxi[]
		endpar

default init s0:
	function state($c in Client) = IDLE
	function keepTaxi($c in Client) = 0
