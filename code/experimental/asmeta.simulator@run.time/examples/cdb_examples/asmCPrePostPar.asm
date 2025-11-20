/* at every step sum
*/
asm asmCPrePostPar

import ../StandardLibrary

signature:
	monitored funcS: Integer
	monitored funcH: Integer
	monitored myinput: Integer //unbound input
	out funcC: Integer
	out funcInc: Integer

definitions:

invariant inv_0 over funcS: funcS>=0
invariant inv_1 over funcC: funcC>=0
invariant inv_2 over funcInc: funcInc>=0

	main rule r_Main = 
		par
		funcC := funcS + funcH + myinput
		funcInc := funcS + funcH + myinput
		endpar


default init s0:
	function funcC = 0
	function funcInc = 0
	
