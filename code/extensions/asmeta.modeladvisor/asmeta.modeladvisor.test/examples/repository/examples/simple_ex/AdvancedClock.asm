/** at every step increments the seconds 
*/
asm AdvancedClock

import ../../STDL/StandardLibrary

signature:

domain Second subsetof Integer
domain Minute subsetof Integer
domain Hour subsetof Integer      

controlled seconds:Second    
controlled minutes:Minute    
controlled hours:Hour    

definitions:    

domain Second = {0..59}    
domain Minute= {0..59}    
domain Hour = {0..23}

macro rule r_IncMinHours =  
	par
		if minutes = 59 then
		   hours := (hours + 1) mod 24
		endif
		minutes := (minutes + 1) mod 60
	endpar

main rule r_Main = 
	par
		if seconds = 59 then
			r_IncMinHours[]
		endif
		seconds := (seconds + 1) mod 60
	endpar

default init s0:

  function seconds = 0
  function minutes = 0
  function hours = 0
