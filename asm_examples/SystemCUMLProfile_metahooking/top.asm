module top

	import StandardLibrary
	import display
	import ruleSM
	import clock
	import chan
	export r_initTop

signature:


definitions:
/*
 * Inizializza ed esegue gli elementi del livello TOP del SystemC
 */
 macro rule r_initTop =
		seq
			r_init[lOAD]
			r_initInt[dIN]
			r_initInt[dOUT]
			r_initClock[cLOCK, 20, 0.5, 10, true] //contiene l'init per il canale bool
			r_initDisplay[u_display, dOUT]
			r_initCount[u_count, lOAD, cLOCK, dIN, dOUT] //all'interno vi è il funzionamento meta-hooking
			 result := print("Terminata_simulazione_count")
			r_initCount_stim[u_count_stim, 8, lOAD, cLOCK, dIN, dOUT] //all'interno vi è il funzionamento meta-hooking
			 result := print("Terminata_simulazione_count_stim")
		endseq
