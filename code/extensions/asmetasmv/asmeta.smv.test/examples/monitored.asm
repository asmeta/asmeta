asm monitored

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	dynamic controlled foo: Boolean
	dynamic controlled foo2: Boolean
	dynamic controlled foo3: Boolean
	dynamic controlled foo45: Boolean
	dynamic monitored mon: Boolean
	dynamic monitored mon3: Boolean
	dynamic monitored mon4: Boolean
	dynamic monitored mon5: Boolean

definitions:

	//AsmetaL invariants: erano veri con il vecchio modo di controllo degli invarianti
	//invariant over foo: foo = mon //false
	//invariant over foo2: foo2 = mon //false
	//invariant over foo3: foo3 = not(mon3) //false

	//proprieta' CTL con la vecchia interpretazione delle monitorate: le monitorate
	//appartengono al nuovo updateSet
	/*CTLSPEC ag(foo = mon and foo2 = mon)
	CTLSPEC ag(foo3 = not(mon3))*/

	//proprieta' CTL con la nuova interpretazione delle monitorate: le monitorate
	//appartengono al vecchio updateSet
	CTLSPEC foo = mon and (forall $b in Boolean with ag(mon=$b implies ax(foo = $b)))
	CTLSPEC foo2 = mon and (forall $b in Boolean with ag(mon=$b implies ax(foo2 = $b)))
	CTLSPEC foo3 = not(mon3) and (forall $b in Boolean with ag(mon3=$b implies ax(foo3 = not($b))))

	main rule r_Main =
		par
			foo := mon
			if(mon) then
				foo2 := true
			else
				foo2 := false
			endif
			if(mon3) then
				foo3 := not(mon3)
			else
				foo3 := mon3 or not(mon3)
			endif
			if(mon4) then
				if(mon5) then
					foo45 := true
				else
					foo45 := false
				endif
			endif
		endpar

default init s0:
	function foo = mon
	function foo2 = mon
	function foo3 = not(mon3)
