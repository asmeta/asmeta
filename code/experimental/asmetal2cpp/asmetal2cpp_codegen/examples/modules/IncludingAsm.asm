/** this inlcudes the other
*/

asm  IncludingAsm

import ../STDL/StandardLibrary
import IncludedModule

signature:

	monitored c: Boolean  
	controlled d: Integer

definitions:

	main rule r_main = if c then r_inc[] endif
	
	
	

default init s1:    
	function c = true
