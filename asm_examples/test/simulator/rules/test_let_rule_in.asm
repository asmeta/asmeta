asm test_let_rule_in
	
import ../../../STDL/StandardLibrary
	
signature:


static total: Powerset(Natural) -> Natural
controlled tot: Natural
controlled sub: Natural
controlled s: Powerset(Natural)

definitions:


/*------- main rule   --------*/

main rule r_test_let_rule_in = let ( $first = first(asSequence(s))) in
                  tot := $first + total(excluding(s,$first)) 
		endlet
        

default init s_1:

function s = {1n,2n,3n}
function tot = 0n


