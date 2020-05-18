asm ClientDisplayOutput

//Ground model

import ../StandardLibrary

signature:
	domain Device subsetof Agent
    enum domain State = {WAITING_FOR_MESSAGE | EXECUTE_CLIENT_TESTS | CHECKING_FOR_EXTRA_RESOURCES | DISPLAYING_THE_MESSAGE} 
  
	controlled state: Device -> State
	monitored extraResources: Device -> Boolean
	monitored messageArrived: Device -> Boolean
	monitored clientTestsAvailable: Device -> Boolean

	//The devices are created as static constants
	static device1: Device
	static device2: Device
	static device3: Device

definitions:	
	rule r_DownloadExtraResources = skip //this rule remains abstract

	rule r_DecryptMessage =	skip //this rule remains abstract

	rule r_updateCookieProfile =	skip
						
	rule r_Display = 
		par
			if (state(self) = WAITING_FOR_MESSAGE) then
				if (messageArrived(self)=true) then
					//seq
						//r_DecryptMessage[]
						if (clientTestsAvailable(self)=true) then
							state(self) := EXECUTE_CLIENT_TESTS
						else
							state(self) := CHECKING_FOR_EXTRA_RESOURCES
						endif
					//endseq
				endif
			endif
			if (state(self) = EXECUTE_CLIENT_TESTS) then
				par
					r_updateCookieProfile[] 
					state(self) := CHECKING_FOR_EXTRA_RESOURCES
				endpar
			endif
			if (state(self) = CHECKING_FOR_EXTRA_RESOURCES) then
				par
					if extraResources(self) then
						r_DownloadExtraResources[]
					endif
					state(self) := DISPLAYING_THE_MESSAGE	
				endpar
			endif
			if (state(self) = DISPLAYING_THE_MESSAGE) then
				//this device reached the final state -> the message was displayed
				skip
			endif
		endpar	
	
	main rule r_Main =
		forall $d in Device do //all the devices run in parallel
			program($d) 
 
default init initial_state:
	function state($d in Device) = WAITING_FOR_MESSAGE

	agent Device: 
		r_Display[]