/*
The advanced clock is a special clock that at each step increment the seconds only if the boolean function signal is equal to true.
Compare to the traditional clock, in this version seconds, minutes and hours can be between 0 and 3.
When hours reaches 3, minutes 3 and seconds 3, the clock is reset to 0 and it starts again.
 */
 
 asm AdvancedClock2

import ../../STDL/StandardLibrary
import ../../STDL/LTLLibrary

signature:
	domain Second subsetof Integer
	domain Minute subsetof Integer
	domain Hour subsetof Integer      

	monitored signal: Boolean  
	controlled seconds: Second    
	controlled minutes: Minute    
	controlled hours: Hour

definitions:
	domain Second = {0 : 3}
	domain Minute = {0 : 3}
	domain Hour = {0 : 3}

	macro rule r_IncMinHours =
		par
			if minutes = 2 then
				hours := (hours+ 1) mod 3
			endif
			minutes := (minutes + 1) mod 3
		endpar


//hours, minutes and seconds are always lower to 3
LTLSPEC g(seconds< 3 and minutes<3 and hours<3)
//When seconds is equal to 3, then it is reset to 0
LTLSPEC g(seconds=3 implies x(seconds=0))
//When minutes is equal to 3 and seconds is equal to 3, then they are both reset to 0
LTLSPEC g(minutes=3 and seconds=3 implies x(minutes=0 and seconds=0))
//When hours reaches 3, minutes 59 and seconds 3, the clock is reset to 0
LTLSPEC g((hours=3 and minutes=3 and seconds=3) implies x(hours=0 and minutes=0 and seconds=0))

	main rule r_AdvancedClock =
		if signal then
			par
				if seconds=2 then
					r_IncMinHours[] 
				endif
				seconds := (seconds+1) mod 3 
			endpar
		endif

default init s1:    
	function signal = false
	function seconds = 0
	function minutes = 0
	function hours = 0
