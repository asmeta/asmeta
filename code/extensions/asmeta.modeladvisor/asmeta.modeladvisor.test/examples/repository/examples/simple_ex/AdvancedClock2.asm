/** advanced clock con signal - da demo pisa*/

asm AdvancedClock2

import ../../STDL/StandardLibrary

signature:

domain Second subsetof Integer
domain Minute subsetof Integer
domain Hour subsetof Integer      

monitored signal:Boolean  
controlled seconds:Second    
controlled minutes:Minute    
controlled hours:Hour    

definitions:    

domain Second={0:3}    
domain Minute={0:3}    
domain Hour={0:3}

macro rule r_IncMinHours =  par
                        if minutes=2 then
                           hours := (hours+ 1) mod 3
                        endif
                        minutes := (minutes + 1) mod 3
                      endpar

main rule r_AdvancedClock = if signal then
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
