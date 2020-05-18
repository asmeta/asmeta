asm chooseMon

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	domain ConcrDom subsetof Integer
	dynamic controlled fooB: Boolean
	dynamic monitored monB: Boolean
	dynamic controlled fooB2: Boolean
	dynamic monitored monB2: Boolean
	dynamic controlled fooE: EnumDom
	dynamic monitored monE: EnumDom
	dynamic controlled fooI: ConcrDom
	dynamic monitored monI: ConcrDom

definitions:
	domain ConcrDom = {1..10}

	//proprieta' CTL con la vecchia interpretazione delle monitorate: le monitorate
	//appartengono al nuovo updateSet
	/*CTLSPEC ag(fooB = monB)
	CTLSPEC ag(fooB2 = not(monB2))
	CTLSPEC ag(fooE = monE)
	CTLSPEC (fooI = monI) and  ax(ag(fooI != monI))*/


	//proprieta' CTL con la nuova interpretazione delle monitorate: le monitorate
	//appartengono al vecchio updateSet
	CTLSPEC ag(monB implies ax(fooB))
	CTLSPEC ag(not(monB) implies ax(not(fooB)))
	//equivalente alle due precedenti
	CTLSPEC (forall $b in Boolean with ag(monB=$b implies ax(fooB=$b)))

	CTLSPEC ag(monB2 implies ax(not(fooB2)))
	CTLSPEC ag(not(monB2) implies ax(fooB2))
	//equivalente alle due precedenti
	CTLSPEC (forall $b in Boolean with ag(monB2=$b implies ax(fooB2=not($b))))

	CTLSPEC (forall $e in EnumDom with ag(monE=$e implies ax(fooE=$e)))
	CTLSPEC (forall $i in ConcrDom with ag(monI=$i implies ax(fooI!=$i)))

	main rule r_Main =
		par
			choose $x in Boolean with $x = monB do
				fooB := $x
			choose $y in Boolean with $y = monB2 do
				fooB2 := not($y)
			choose $e in EnumDom with $e = monE do
				fooE := $e
			choose $i in ConcrDom with $i != monI do
				fooI := $i
		endpar

default init s0:
	function fooB = monB
	function fooB2 = not(monB2)
	function fooE = monE
	function fooI = monI
