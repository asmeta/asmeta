asm telecamere0

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain Numtelecamere subsetof Integer
	controlled telecamere: Numtelecamere -> Boolean
	monitored teleChoice: Numtelecamere 

definitions:
	domain Numtelecamere = {1:3}
	
	main rule r_Main =
		if(teleChoice>1)  then 
			if telecamere(teleChoice-1) then
				telecamere(teleChoice):= true
			endif
		endif

default init s0:
	function telecamere($n in Numtelecamere) = true
