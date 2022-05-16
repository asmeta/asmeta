//
// a light that  changes from ON to OFF and from OFF to ON
// 
asm light

import STDL/StandardLibrary
signature:

	enum domain Light =  {ON, OFF}

	controlled current: Light
	
	controlled step: Integer
			    
definitions:
	
	main rule r_main =
		if current = ON and step > 3 then		 
				current:= OFF
		else par
				current:= ON
				step:= step +1
			 endpar
		endif

default init s0:
    function current =  ON
    function step = 0
