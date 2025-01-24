/** simple counter
*/

asm Counter

import ../STDL/StandardLibrary

signature:

	monitored signal: Boolean  
	controlled counter: Integer

definitions:

	main rule r_count =
		if signal then
		   counter := (counter+1)
		endif

default init s1:    
	function counter = 0
