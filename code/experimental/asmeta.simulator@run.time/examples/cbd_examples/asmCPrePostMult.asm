/** at every step multiply by 2
*/
asm asmCPrePostMult

import ../StandardLibrary

signature:
	monitored funcInc: Integer
	out funcM: Integer

definitions:

invariant inv_4 over funcInc: funcInc>=11
invariant inv_5 over funcM: funcM>=20

	main rule r_Main = 
		funcM := (funcInc*2)


default init s0:
	function funcM = 20
	
