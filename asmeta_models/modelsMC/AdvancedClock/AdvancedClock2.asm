/*
The advanced clock is a special clock that at each step increment the seconds only if the boolean function signal is equal to true.
Compare to the traditional clock, in this version seconds, minutes and hours can be between 0 and 3.
When hours reaches 3, minutes 3 and seconds 3, the clock is reset to 0 and it starts again.
 */
 
 asm AdvancedClock2

import ../../STDL/StandardLibrary
import ../../STDL/LTLLibrary

signature:
	domain Second subsetof Integer //Domain for seconds
	domain Minute subsetof Integer //Domain for minutes
	domain Hour subsetof Integer //Domain for hours
	controlled seconds: Second // seconds function
	controlled minutes: Minute  // minutes function  
	controlled hours: Hour // hours function    

	monitored signal: Boolean //functions that if true increments seconds

definitions:
	domain Second = {0 : 3}  //Domain for seconds range
	domain Minute = {0 : 3} //Domain for minutes range
	domain Hour = {0 : 3} //Domain for hours range

	macro rule r_IncMinHours =
		par
			// when minutes is equal to 2
			if minutes = 2 then
				hours := (hours+ 1) mod 3 // increments hours
			endif
			minutes := (minutes + 1) mod 3 // increments minutes
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
				// when seconds is equal to 2
				if seconds=2 then
					r_IncMinHours[] // increments minutes and hours
				endif
				seconds := (seconds+1) mod 3 // increments seconds
			endpar
		endif

default init s1:   
//initialize the external signal to false
	function signal = false 
//initialize seconds, minutes and hours to 0
	function seconds = 0
	function minutes = 0
	function hours = 0
