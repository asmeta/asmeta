asm test_let_rule_in1
	
import ../../../STDL/StandardLibrary
	
signature:


static total: Powerset(Natural) -> Natural
controlled tot: Natural
controlled s: Powerset(Natural)

definitions:

macro rule r_r1($so in Powerset(Natural)) = if (isEmpty($so)) then skip
        else
                let ( $first = first(asSequence($so))) in
                 tot := $first + total(excluding($so,$first)) 	
		endlet
        endif

main rule r_test_let_rule_in1 = r_r1[s] //macro call rule

default init s_1:

function s = {1n,2n,3n}
function tot = 0n


