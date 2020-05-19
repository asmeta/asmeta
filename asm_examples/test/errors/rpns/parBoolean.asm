//Se si eseguono due aggiornamenti su due locazioni booleane
//con lo stesso valore viene generato un errore NullPointerException.
//Se i valori degli aggiornamenti sono diversi non c'e' alcun errore.
//la versione di ASMETA 1129 non presenta questo errore.
asm parBoolean

import ../../../STDL/StandardLibrary

signature:
	dynamic controlled fooA: Boolean
	dynamic controlled fooB: Boolean
	
definitions:
    invariant over fooA, fooB: not(fooA) and not(fooB)

	main rule r_Main =
		//errore java.lang.NullPointerException
		par
			fooA := true
			fooB := true
		endpar

		//errore java.lang.NullPointerException
		/*par
			fooA := false
			fooB := false
		endpar*/

		//nessun errore
		/*par
			fooA := true
			fooB := false
		endpar*/

default init s0:
	function fooA = true
	function fooB = false
