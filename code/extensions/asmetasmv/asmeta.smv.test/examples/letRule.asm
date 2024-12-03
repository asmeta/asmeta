asm letRule
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	dynamic controlled foo: Boolean
	dynamic controlled fooA: Boolean
	dynamic controlled fooB: Boolean
	dynamic controlled fooC: Boolean -> Boolean
	dynamic controlled fooD: Boolean
	dynamic controlled fooE: Boolean -> Boolean
	dynamic monitored mon: Boolean

definitions:
	CTLSPEC not(foo) and ax(ag(foo))
	CTLSPEC ag((fooB iff ax(not(fooB))) and (not(fooB) iff ax(fooB)))
	CTLSPEC (fooC(true) and not(fooC(false))) and
					ax(ag(not(fooC(true)) and fooC(false)))

	//proprieta' CTL con la vecchia interpretazione delle monitorate: le monitorate
	//appartengono al nuovo updateSet
	//CTLSPEC not(fooA) and ax(ag(fooA=not(mon)))
	//CTLSPEC fooD=mon and ax(ag(fooD=not(mon)))

	//proprieta' CTL con la nuova interpretazione delle monitorate: le monitorate
	//appartengono al vecchio updateSet
	CTLSPEC (forall $b in Boolean with ag($b=mon implies ax(fooA=not($b))))
	CTLSPEC (forall $b in Boolean with ag($b=mon implies ax(fooD=not($b))))
	CTLSPEC (forall $b in Boolean, $b1 in Boolean with ag((fooD = $b and fooE($b) = $b1) implies ax(fooE($b) = not($b1))) )

	main rule r_main =
		par
			let ($x = true) in
				foo := $x
			endlet
			let ($y = mon) in
				fooA := not($y)
			endlet
			let ($z = fooB) in
				fooB := not($z)
			endlet
			forall $b in Boolean with true do
				let ($k = $b) in
					fooC($k) := not($b)
				endlet
			choose $t in Boolean with $t=mon do
				let ($s = $t) in
					fooD := not($s)
				endlet
			let ($arg = fooD) in
				fooE($arg) := not(fooE($arg))
			endlet
		endpar

default init s0:
	function foo = false
	function fooA = false
	function fooB = true
	function fooC($b in Boolean) = $b
	function fooD = mon
	function fooE($b in Boolean) = true