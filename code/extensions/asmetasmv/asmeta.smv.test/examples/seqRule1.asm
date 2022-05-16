asm seqRule1

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled fooA: MyDomain
	dynamic controlled fooB: MyDomain	
	dynamic monitored mon: Boolean

definitions:
	domain MyDomain = {1:4}

	//proprieta' CTL con la vecchia interpretazione delle monitorate: le monitorate
	//appartengono al nuovo updateSet
	//CTLSPEC isUndef(fooB) and ax(ag(mon iff fooB=3))

	//proprieta' CTL con la nuova interpretazione delle monitorate: le monitorate
	//appartengono al vecchio updateSet
	CTLSPEC ag(mon iff ax(fooA=2 and fooB=3))
	CTLSPEC ag(not(mon) iff ax(fooA=3 and fooB=4))
	CTLSPEC isUndef(fooA) and isUndef(fooB)

	main rule r_Main = 
		seq
			if(mon) then
				fooA := 2
			else
				fooA := 3
			endif
			fooB := fooA + 1
		endseq
