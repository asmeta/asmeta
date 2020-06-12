//Se si eseguono due aggiornamenti su due locazioni booleane
//con lo stesso valore viene generato un errore NullPointerException.
//Se i valori degli aggiornamenti sono diversi non c'e' alcun errore.
asm parBooleanProblem

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled fooA: Boolean
	dynamic controlled fooB: Boolean
	
definitions:

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
