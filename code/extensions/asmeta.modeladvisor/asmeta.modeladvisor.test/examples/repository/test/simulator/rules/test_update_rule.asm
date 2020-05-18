asm test_update_rule
	
import ../../../STDL/StandardLibrary
	
signature:


static total: Powerset(Natural) -> Natural
controlled tot: Natural
controlled sub: Natural
controlled s: Powerset(Natural)

definitions:


/*------- main rule   --------*/

main rule r_test_update_rule = tot := first(asSequence(s))

default init s_1:

function s = {1n,2n,3n}
function tot = 0n


        



