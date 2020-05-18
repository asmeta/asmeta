asm test_let_rule_in2
	
import ../../../STDL/StandardLibrary
	
signature:


static total: Powerset(Natural) -> Natural
controlled tot: Natural
controlled s: Powerset(Natural)

definitions:


/*------- main rule   --------*/

main rule r_test_let_rule_in = if false then skip
       else
                let ( $first = first(asSequence(s))) in
                  tot := $first + total(excluding(s,$first)) 	
		endlet
        endif

default init s_1:

function s = {1n,2n,3n}
function tot = 0n

