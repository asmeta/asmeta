asm sluiceGateGround
import ../../../STDL/TimeLibrary
signature:
	enum domain PhaseDomain = { FULLYCLOSED | FULLYOPEN }
	dynamic controlled phase: PhaseDomain
	static timer10MinPassed: Timer
	static timer3hPassed: Timer
definitions:
main rule r_Main =
	par
		if phase=FULLYCLOSED and expired(timer3hPassed) then
			par
				phase := FULLYOPEN
				r_reset_timer[timer10MinPassed]
				
			endpar
		endif
		if phase=FULLYOPEN and expired(timer10MinPassed) then
			par
				phase := FULLYCLOSED
				r_reset_timer[timer3hPassed]
				
			endpar
		endif
	endpar	

default init s0:
	function duration($t in Timer) = 
		if $t = timer10MinPassed 	then 10
		else if $t = timer3hPassed	then 3 endif endif	
	function timerUnit($t in Timer) = 
		if $t = timer10MinPassed then MIN
		else if $t = timer3hPassed	then HOUR endif endif	
	function start($t in Timer) = initStart($t)
	function phase = FULLYCLOSED
