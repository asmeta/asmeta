asm seq2

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a: MyDomain
	dynamic controlled var_b: MyDomain
	dynamic monitored mon: MyDomain
definitions:
	domain MyDomain = {1:4}

	//proprieta' CTL con la vecchia interpretazione delle monitorate: le monitorate
	//appartengono al nuovo updateSet
	/*CTLSPEC ax(ag(var_b=mon iff mon=4))
	CTLSPEC ax(ag(var_b=mon+1 iff mon!=4))*/

	//proprieta' CTL con la nuova interpretazione delle monitorate: le monitorate
	//appartengono al vecchio updateSet
	CTLSPEC (forall $i in MyDomain with ag((mon=$i and $i<4) implies ax(var_b=$i+1)))
	CTLSPEC ag(mon=4 implies ax(var_b=4))
	CTLSPEC ag(mon >= 3 implies ax(var_b=4))

	main rule r_Main = 
		seq
			var_b := mon
			if(var_b != 4) then
				var_b := var_b + 1
			endif
		endseq
		
default init s0:
	function var_b = 1
