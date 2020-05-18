asm test_let_term_in
	
import ../../../STDL/StandardLibrary
	
signature:


static total: Powerset(Natural) -> Natural

definitions:

function total($so in Powerset(Natural)) =
        if (isEmpty($so)) then 0n
        else
                let ( $first = first(asSequence($so))) in
                 $first + total(excluding($so,$first)) // THIS WORKS		
		//first(asSequence($so)) + total(excluding($so,first(asSequence($so))))  //THIS WORKS	
		endlet
        endif



