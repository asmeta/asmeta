asm main

	import chan
	import sched
	import top
	
signature:

    

definitions:
	
	
/*
 * Richiama la r_start nel file sched ed esegue la r_initTop nel file top
 */
 main rule r_main =
		r_start[1000, <<r_initTop>>]
		
