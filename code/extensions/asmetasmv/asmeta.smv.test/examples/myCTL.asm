asm myCTL
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain
	dynamic controlled fooA: MyDomain
	dynamic monitored monA: Boolean

definitions:
	domain MyDomain = {1..4}

	//Proprieta' CTL
	CTLSPEC ag(foo=1 iff axN(foo=4, 3n))
	//e' debole. non si potrebbe renderla piu' forte? Forse no. Forse e' proprio
	//il comportamento del modello AsmetaL.
	CTLSPEC ag(ef(fooA=1 implies exN(fooA=4, 3n)))

	//falsa. Se fooA=1 nello stato in cui mon e' false, dopo tre passi non sara' mai fooA = 4
	//CTLSPEC ag(fooA=1 implies exN(fooA=4, 3n))

	main rule r_Main =
		par
			if(foo < 4) then
				foo := foo + 1
			else
				foo := 1
			endif
			if(fooA < 4) then
				if(monA) then
					fooA := fooA + 1
				endif
			else
				fooA := 1
			endif
		endpar

default init s0:
	function foo = 1
	function fooA = 1
