/** simple counter
*/

module  IncludedModule

import ../STDL/StandardLibrary
export *

signature:

	monitored a: Integer  
	controlled b: Integer

definitions:

	rule r_inc = b:= a +1

default init s1:    
	function a = 0
