//foo non sfora il dominio, pero' ha bisogno del controllo lo stesso
//foo2 sfora il dominio e con il controllo modifica il comportamento del modello

asm concrDomCheck
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled cond: Boolean
	dynamic controlled foo: MyDomain
	dynamic controlled foo2: MyDomain

definitions:
	domain MyDomain = {1:4}

	CTLSPEC ag(foo = 1 or foo = 2)
	CTLSPEC af(foo2 = 4 iff ag(foo2=4))

	main  rule r_Main =
		par
			cond := not(cond)
			if(cond) then
				foo := foo + 1
			else
				foo := foo - 1
			endif
			foo2 := foo2 + 1
		endpar

default init s0:
	function foo = 1
	function cond = true
	function foo2 = 1
