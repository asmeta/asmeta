/** at every step increments the seconds 
*/
asm ProdDomain

import STDL/StandardLibrary

signature:
	domain Second subsetof Integer
	domain Minute subsetof Integer
	domain Hour subsetof Integer
	controlled time: Prod(Second, Minute, Hour) -> Second

definitions:
	domain Second = {0 : 59}
	domain Minute= {0 : 59}
	domain Hour = {0 : 23}


	main rule r_Main = 
		if (time(0,0,0)=0) then
			time(1,3,2):=1
		endif

default init s0:
	function time($x in Second, $y in Minute, $z in Hour) = 0

