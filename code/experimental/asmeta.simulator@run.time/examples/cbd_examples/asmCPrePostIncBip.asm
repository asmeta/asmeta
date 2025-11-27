/** at every step increments by 1
*/
asm asmCPrePostIncBip

import ../StandardLibrary

signature:
	monitored funcC: Integer
	out funcS: Integer
	out funcH: Integer

definitions:

invariant inv_2 over funcC: funcC>=10
invariant inv_3 over funcS: funcS>=0
invariant inv_3 over funcH: funcH>=0

	main rule r_Main = 
		par
			funcS := funcC-100
			funcH := funcC+2
		endpar


default init s0:
	function funcS = 0
	function funcH = 0
