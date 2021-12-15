asm controlAB
 
import ../../STDL/StandardLibrary

signature:

	monitored workingB: Boolean
	monitored workingA: Boolean

definitions:

	main rule r_Main =
		if workingB and workingA then
			skip
		endif