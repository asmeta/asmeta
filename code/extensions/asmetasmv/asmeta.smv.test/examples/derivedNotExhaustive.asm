/*La seguente specifica derivedNotExhaustive.asm dichiara una funzione derived
non completamente specificata.
Nel caso in cui mon1 sia false, infatti, il valore della funzione non puo' essere valutato.
In esecuzione, quando nella main rule il simulatore valuta il valore di def, se la variabile monitorata
mon1 assume valore false, indipendentemente dal valore assunto da mon2, restituisce un UndefValue che non può
essere castato a Boolean. Il simulatore genera un'ecezione che non può essere gestita ed interrompe l'esecuzione.
java.lang.ClassCastException: org.asmeta.interpreter.value.UndefValue cannot be cast to org.asmeta.interpreter.value.BooleanValue
La traduzione in NuSMV, in questo caso e' utile perche' e' in grado di scoprire la falla.
Quando si prova ad eseguire il codice derivedNotExhaustive.smv, infatti, viene restituito il seguente errore:
file derivedNotExhaustive.smv: line 17:
type error: value = FAILURE("case conditions are not exhaustive", line 10)
Expected a boolean expression
NuSMV, infatti, vuole che il set di condizioni sui cui è condizionato il valore di una variabile sia
esaustivo.*/

asm derivedNotExhaustive

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo : MyDomain
	dynamic monitored mon1: Boolean
	dynamic monitored mon2: Boolean
	derived der: Boolean

definitions:
	domain MyDomain = {1:4}

	function der =
		if(mon1) then
			if(mon2) then
				true
			else
				false
			endif
		endif

	main rule r_Main =
		if(der) then
			foo := 1
		endif
