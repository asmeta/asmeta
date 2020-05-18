asm derivedBehaviour

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	dynamic controlled fooA: Boolean
	dynamic controlled fooB: Boolean
	dynamic controlled fooC: Boolean
	dynamic controlled fooD: Boolean
	
	monitored mon: Boolean
	
	derived der: Boolean
	derived derMon: Boolean
	derived derMixedMon: Boolean

definitions:
	function der = fooA
	function derMon = mon
	
	function derMixedMon = mon and fooA //nuovo mon e vecchio fooA

	//vecchia interpretazione
	/*//AsmetaL invariants
	invariant over der, fooA: der = fooA //true
	//invariant over der, fooB: fooB = der //false, perche' a fooB assume il vecchio valore di der
	//invariant over fooB, fooA: fooB = fooA //false
	invariant over derMon, mon: derMon = mon //true
	invariant over fooC, derMon: fooC = derMon //true perche' derMon e' derivato dalla monitorata mon che viene settata all'inizio della transizione
	invariant over fooC, mon: fooC = mon //true: poiche' fooC e' uguale a derMon che e' uguale a mon
	invariant over derMixedMon, mon, fooA:  derMixedMon = (mon and fooA) //true
	//invariant over derMixedMon, fooD: fooD = derMixedMon //false
	*/

	//proprieta' CTL con la vecchia interpretazione delle monitorate: le monitorate
	//appartengono al nuovo updateSet
	/*CTLSPEC ag(der = fooA)//true
	CTLSPEC ag(fooB = der) //false
	CTLSPEC (der = fooB) and ax(ag(fooB!=der))//true
	CTLSPEC ag(fooB = fooA) //false
	CTLSPEC (fooA = fooB) and ax(ag(fooA!=fooB))//true
	CTLSPEC ag(derMon = mon) //true
	CTLSPEC ag(fooC = derMon) //true
	CTLSPEC ag(fooC = mon) //true
	CTLSPEC ag(derMixedMon = (mon and fooA)) //true
	CTLSPEC ag(fooD = derMixedMon) //false
	*/

	//proprieta' CTL con la nuova interpretazione delle monitorate: le monitorate
	//appartengono al vecchio updateSet
	//devono essere tutte vere
	CTLSPEC ag(der = fooA)
	CTLSPEC (forall $b in Boolean with ag(der = $b implies ax(fooB = $b)))
	CTLSPEC (forall $b in Boolean with ag(fooA = $b implies ax(fooB = $b)))
	CTLSPEC ag(derMon = mon)
	CTLSPEC (forall $b in Boolean with ag(derMon = $b implies ax(fooC = $b)))
	CTLSPEC (forall $b in Boolean with ag(mon = $b implies ax(fooC = $b)))
	CTLSPEC ag(derMixedMon = (mon and fooA))
	CTLSPEC (forall $b in Boolean with ag( derMixedMon = $b implies ax(fooD = $b)))
	CTLSPEC (forall $b in Boolean with ag((mon and fooA) = $b implies ax(fooD = $b)))

	main rule r_Main =
		par
			fooA := not(fooA)
			fooB := der
			fooC := derMon
			fooD := derMixedMon
		endpar

default init s0:
	function fooA = true
	function fooB = der
	function fooC = derMon
	function fooD = derMixedMon
