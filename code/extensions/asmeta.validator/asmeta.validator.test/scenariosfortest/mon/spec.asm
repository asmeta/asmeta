// spec with a monitored initialized
asm spec

import ../StandardLibrary


signature:

        dynamic monitored a: Integer

definitions:

main rule r_main = skip


// define the initial states 
default init s0:
	
	function a  = 0
        
        
