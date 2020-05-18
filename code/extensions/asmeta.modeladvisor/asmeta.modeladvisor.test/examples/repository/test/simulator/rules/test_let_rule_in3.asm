asm test_let_rule_in3
	
import ../../../STDL/StandardLibrary
	
signature:


static total: Powerset(Natural) -> Natural
controlled tot: Natural
controlled sub: Natural
controlled s: Powerset(Natural)

definitions:


/*------- main rule   --------*/

main rule r_test_let_rule_in3 = let ( $first = first(asSequence(s))) in
                  par
                    tot := $first + total(excluding(s,$first)) 
                    sub := $first 
                  endpar
		endlet
        

default init s_1:

function s = {1n,2n,3n}
function tot = 0n
function sub = 0n