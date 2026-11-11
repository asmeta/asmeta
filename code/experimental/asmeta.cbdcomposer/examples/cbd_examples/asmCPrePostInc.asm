/** at every step increments by 1
*/
asm asmCPrePostInc

import ../StandardLibrary

signature:
	monitored funcC: Integer
	out funcInc: Integer

definitions:

invariant inv_2 over funcC: funcC>=10
invariant inv_3 over funcInc: funcInc>=0

	main rule r_Main = 
		funcInc := funcC+1


default init s0:
	function funcInc = 0
	
