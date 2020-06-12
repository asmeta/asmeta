asm implies

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
	CTLSPEC ag(fooFalse implies fooFalse)
	CTLSPEC ag(fooFalse implies fooTrue)
	CTLSPEC ag(not(fooTrue implies fooFalse))
	CTLSPEC ag(fooTrue implies fooTrue)
	CTLSPEC ag((monA implies monB) = (not(monA) or monB))
	CTLSPEC ag(fooA = true)
	CTLSPEC ag(fooB = true)
	CTLSPEC ag(fooC = false)
	CTLSPEC ag(fooD = true)
	CTLSPEC ag(fooE = true)
	CTLSPEC ag(fooF = true)
	CTLSPEC ag(ef(fooG = true))
	CTLSPEC ag(ef(fooG = false))
	CTLSPEC ag(ef(fooH = true))
	CTLSPEC ag(ef(fooH = false))

	//proprieta' CTL con la vecchia interpretazione delle monitorate: le monitorate
	//appartengono al nuovo updateSet
	//CTLSPEC ag(foo iff not(monA and not(monB)))

	//proprieta' CTL con la nuova interpretazione delle monitorate: le monitorate
	//appartengono al vecchio updateSet
	CTLSPEC ag(not(monA and not(monB)) iff ax(foo))

	main rule r_main =
		par
			foo := monA implies monB
			fooA := false implies false
			fooB := false implies true
			fooC := true implies false
			fooD := true implies true
			fooE := false implies monA
			fooF := monB implies true
			fooG := monB implies false
			fooH := true implies monA
		endpar

default init s0:
	function foo = monA implies monB
	function fooA = false implies false
	function fooB = false implies true
	function fooC = true implies false
	function fooD = true implies true
	function fooE = false implies monA
	function fooF = monB implies true
	function fooG = monB implies false
	function fooH = true implies monA
