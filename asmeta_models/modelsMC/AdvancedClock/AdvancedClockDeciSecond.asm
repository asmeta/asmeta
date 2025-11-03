/*
The advanced clock is a clock that at each step increment the deciseconds, 
when deciseconds reaches 10 the seconds increment and deciseconds are reset to 0,
when seconds reaches 60 the minutes increments and seconds are reset to 0, 
when minutes reaches 60 minutes the hours increment and minutes are reset to 0,
when hours reaches 24, minutes 59 and seconds 59, the clock is reset to 0 and it starts again.
*/
asm AdvancedClockDeciSecond

import ../../STDL/StandardLibrary
import ../../STDL/LTLLibrary

signature:
	domain DeciSecond subsetof Integer //Domain for deciseconds
	domain Second subsetof Integer //Domain for seconds
	domain Minute subsetof Integer //Domain for minutes
	domain Hour subsetof Integer //Domain for hours
	controlled deciSeconds: DeciSecond //deciseconds function
	controlled seconds: Second // seconds function
	controlled minutes: Minute  // minutes function  
	controlled hours: Hour // hours function  

definitions:
	domain DeciSecond = {0 : 9} //Domain for deciseconds range
	domain Second = {0 : 59} //Domain for seconds range
	domain Minute= {0 : 59} //Domain for minutes range
	domain Hour = {0 : 23} //Domain for hours range

	macro rule r_IncMinHours =  
		par
			// when minutes is equal to 59
			if minutes = 59 then
				hours := (hours + 1) mod 24 // increments hours
			endif
			minutes := (minutes + 1) mod 60	// increments minutes	
		endpar

//deciseconds are always lower or equals to 10, minutes and seconds are always lower or equals to 60, hours are always lower or equals to 24
LTLSPEC g(deciSeconds<10 and seconds<60 and minutes<60 and hours<24)
//When deciseconds is equal to 9, then it is reset to 0
LTLSPEC g(deciSeconds=9 implies x(deciSeconds=0))
//When seconds is equal to 59 and deciseconds is equal to 9, then they are both reset to 0
LTLSPEC g((seconds=59 and deciSeconds=9) implies x(deciSeconds=0 and seconds=0))
//When minutes is equal to 59, seconds is equal to 59 and deciseconds is equal to 9, then they are reset to 0
LTLSPEC g((minutes=59 and seconds=59 and deciSeconds=9) implies x(minutes=0 and seconds=0 and deciSeconds=0))
//When hours reaches 23, minutes 59, seconds 59 and deciseconds is equal to 9, the clock is reset to 0
LTLSPEC g((hours=23 and minutes=59 and seconds=59 and deciSeconds=9) implies x(hours=0 and minutes=0 and seconds=0 and deciSeconds=0))


	main rule r_Main = 
		par
			// when deciSeconds is equal to 9
			if deciSeconds = 9 then
				par
					// when seconds is equal to 59
					if seconds = 59 then 
						r_IncMinHours[] // increments minutes and hours
					endif
					seconds := (seconds + 1) mod 60 // increments seconds
				endpar
			endif
			deciSeconds := (deciSeconds + 1) mod 10 // increments deciseconds
		endpar

default init s0:
//initialize deciSeconds, seconds, minutes and hours to 0
	function deciSeconds = 0
	function seconds = 0
	function minutes = 0
	function hours = 0
