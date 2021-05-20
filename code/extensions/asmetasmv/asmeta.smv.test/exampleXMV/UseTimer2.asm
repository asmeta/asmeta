//
// a light that every 1000 ms changes from ON to OFF and from OFF to ON
// 
// use of a derived function
//
asm UseTimer2

import StandardLibrary
import LTLlibrary
import TimeLibrary
signature:

	enum domain Light =  {ON, OFF}

	static timerstep: Timer
	
	controlled current: Light
	
definitions:
	
	LTLSPEC f(current=OFF)
	
	main rule r_main =
		if expired(timerstep) then
		par
			r_reset_timer[timerstep]			  		
			if current = ON then 
				current:= OFF
			else if current = OFF then
				current:= ON
				endif
			endif
		endpar
		endif

default init s0:
    function current =  ON
    function start($t in Timer) = currentTime($t)
    function duration($t in Timer) = 2
    function timerUnit($t in Timer) = SEC
