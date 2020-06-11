//Se si importano due funzioni con nome e dominio uguali
//ma diverse per codominio il parser non segnala errore.
//In esecuzione, a seconda dell'ordine di importazione dei moduli,
//viene importata una o l'altra funzione

asm main

import ../../../../../../asm_examples/STDL/StandardLibrary

//modo 1: viene importata la funzione di moduleA (codominio Boolean)
import moduleA
import moduleB

//modo 2: viene importata la funzione di moduleB (codominio enum Domain)
//import moduleB
//import moduleA

signature:
	
definitions:

	main rule r_Main =
		foo := true //modo 1
		//foo := AA //modo 2
default init s0:
