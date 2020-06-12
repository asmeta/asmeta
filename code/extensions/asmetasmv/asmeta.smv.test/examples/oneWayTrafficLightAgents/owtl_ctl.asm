asm owtl_ctl

import ../../../../../../asm_examples/STDL/StandardLibrary
export LightUnit, goLight, stopLight, phase, gPulse, rPulse, PhaseDomain, r_ONEWAYTRAFLIGHTCTLTAgent, onewaytraflightctl

signature:
	enum domain LightUnit = {LIGHTUNIT1 | LIGHTUNIT2}
	domain ONEWAYTRAFLIGHTCTLTAgent subsetof Agent
	enum domain PhaseDomain = { STOP1STOP2 | GO2STOP1 | STOP2STOP1 | GO1STOP2 | STOP1STOP2CHANGING | GO2STOP1CHANGING | STOP2STOP1CHANGING | GO1STOP2CHANGING }
	domain Intervals subsetof Integer
	dynamic controlled phase: PhaseDomain
	dynamic controlled stopLight: LightUnit -> Boolean
	dynamic controlled goLight: LightUnit -> Boolean
	dynamic monitored passed: Intervals -> Boolean
	dynamic controlled rPulse: LightUnit -> Boolean
	dynamic controlled gPulse: LightUnit -> Boolean
	static onewaytraflightctl: ONEWAYTRAFLIGHTCTLTAgent
	
definitions:
	domain Intervals = {50, 120}

	macro rule r_emit($pulse in Boolean) =
		$pulse := true
		
	rule r_switchToGo2 =
		par
			r_emit[rPulse(LIGHTUNIT2)]
			r_emit[gPulse(LIGHTUNIT2)]
		endpar
	
	rule r_switchToStop2 =
		par
			r_emit[rPulse(LIGHTUNIT2)]
			r_emit[gPulse(LIGHTUNIT2)]
		endpar

	rule r_switchToGo1 =
		par
			r_emit[rPulse(LIGHTUNIT1)]
			r_emit[gPulse(LIGHTUNIT1)]
		endpar
		
	rule r_switchToStop1 =
		par
			r_emit[rPulse(LIGHTUNIT1)]
			r_emit[gPulse(LIGHTUNIT1)]
		endpar
	
	rule r_stop1stop2_to_stop1stop2changing =
		if(phase=STOP1STOP2) then
			if(passed(50)) then
				par
					r_switchToGo2[]
					phase:=STOP1STOP2CHANGING
				endpar
			endif
		endif
		
	rule r_go2stop1_to_go2stop1changing =
		if(phase=GO2STOP1) then
			if(passed(120)) then
				par
					r_switchToStop2[]
					phase:=GO2STOP1CHANGING
				endpar
			endif
		endif
	
	rule r_stop2stop1_to_stop2stop1changing =
		if(phase=STOP2STOP1) then
			if(passed(50)) then
				par
					r_switchToGo1[]
					phase:=STOP2STOP1CHANGING
				endpar
			endif
		endif
		
	rule r_go1stop2_to_go1stop2changing =
		if(phase=GO1STOP2) then
			if(passed(120)) then
				par
					r_switchToStop1[]
					phase:=GO1STOP2CHANGING
				endpar
			endif
		endif
	

	
	macro rule r_changeState =
		par
			if(phase=STOP1STOP2CHANGING) then
				phase := GO2STOP1
			endif
			if(phase=GO2STOP1CHANGING) then
				phase := STOP2STOP1
			endif
			if(phase=STOP2STOP1CHANGING) then
				phase := GO1STOP2
			endif
			if(phase=GO1STOP2CHANGING) then
				phase := STOP1STOP2
			endif
		endpar
	
	rule r_ONEWAYTRAFLIGHTCTLTAgent = 
		par
			r_stop1stop2_to_stop1stop2changing[]
			r_go2stop1_to_go2stop1changing[]
			r_stop2stop1_to_stop2stop1changing[]
			r_go1stop2_to_go1stop2changing[]
			r_changeState[]
		endpar
