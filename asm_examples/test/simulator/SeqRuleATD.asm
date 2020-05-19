//ATD abstract sets : in seq where not computed correctlty

asm SeqRuleATD

import ../../STDL/StandardLibrary

signature:

	//abstract domain NumCard
	abstract domain NumCard //numero della carta (e del conto di riferimento)

    controlled check: Boolean

    static card1 : NumCard
    static card2 : NumCard
    static card3 : NumCard
    
definitions:


	main rule r_main = 
		seq
	      skip 
	      if (exist $c in NumCard) then
				check := true							
		  endif
	    endseq
	
// define the initial states 
default init s0:
	function check = false
