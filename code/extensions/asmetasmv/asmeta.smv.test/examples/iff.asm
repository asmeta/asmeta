asm iff

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	dynamic controlled foo: Boolean
	dynamic controlled fooA: Boolean
	dynamic controlled fooB: Boolean
	dynamic controlled fooC: Boolean
	dynamic controlled fooD: Boolean
	dynamic controlled fooE: Boolean
	dynamic controlled fooF: Boolean
	dynamic controlled fooG: Boolean
	dynamic controlled fooH: Boolean
	dynamic monitored monA: Boolean
	dynamic monitored monB: Boolean
	static fooTrue: Boolean
	static fooFalse: Boolean

definitions:
	function fooTrue = true
	function fooFalse = false

	//Proprieta' CTL
	CTLSPEC ag(fooFalse iff fooFalse)
	CTLSPEC ag(not(fooFalse iff fooTrue))
	CTLSPEC ag(not(fooTrue iff fooFalse))
	CTLSPEC ag(fooTrue iff fooTrue)
	CTLSPEC ag((monA iff monB) = ((not(monA) or monB) and ((not(monB) or monA))))
	CTLSPEC ag(fooA = true)
	CTLSPEC ag(fooB = false)
	CTLSPEC ag(fooC = false)
	CTLSPEC ag(fooD = true)
	CTLSPEC ag(ef(fooE = true))
	CTLSPEC ag(ef(fooE = false))
	CTLSPEC ag(ef(fooF = true))
	CTLSPEC ag(ef(fooF = false))
	CTLSPEC ag(ef(fooG = true))
	CTLSPEC ag(ef(fooG = false))
	CTLSPEC ag(ef(fooH = true))
	CTLSPEC ag(ef(fooH = false))

	//proprieta' CTL con la vecchia interpretazione delle monitorate: le monitorate
	//appartengono al nuovo updateSet
	//CTLSPEC ag(foo=true iff (monA = monB))

	//proprieta' CTL con la nuova interpretazione delle monitorate: le monitorate
	//appartengono al vecchio updateSet
	CTLSPEC ag((monA = monB) iff ax(foo=true))

	main rule r_main =
		par
			foo := monA iff monB
			fooA := false iff false
			fooB := false iff true
			fooC := true iff false
			fooD := true iff true
			fooE := false iff monA
			fooF := monB iff true
			fooG := monB iff false
			fooH := true iff monA
		endpar

default init s0:
	function foo = monA iff monB
	function fooA = false iff false
	function fooB = false iff true
	function fooC = true iff false
	function fooD = true iff true
	function fooE = false iff monA
	function fooF = monB iff true
	function fooG = monB iff false
	function fooH = true iff monA
	
