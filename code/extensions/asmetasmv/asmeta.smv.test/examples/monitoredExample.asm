asm monitoredExample
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
	derived fooG: Boolean
	dynamic controlled fooH: Boolean
	dynamic controlled fooI: Boolean
	dynamic controlled fooL: Boolean
	dynamic controlled fooM: Boolean
	dynamic monitored mon: Boolean
	dynamic monitored mon2: Boolean
	dynamic monitored mon3: Boolean
	derived der: Boolean
	
definitions:
	function der = mon
	function fooG = fooH

	//AsmetaL invariant: da rivedere
	//invariant over foo, mon: foo = mon //always true
	invariant over der: der = mon //always true
	invariant over fooG, fooH: fooG = fooH

	//Proprieta' CTL
	CTLSPEC ag(der = mon)
	CTLSPEC (fooA = fooB) and ax(ag(fooA != fooB))
	CTLSPEC ef(mon) //reachability
	CTLSPEC ef(not(mon)) //reachability
	CTLSPEC ag(ef(mon=true) and ef(mon=false)) //reachability: a partire da ogni stato e' sempre possibile che mon valga sia true che false (piu' forte del semplice ef(mon) e ef(not(mon)))


	//proprieta' CTL con la vecchia interpretazione delle monitorate: le monitorate
	//appartengono al nuovo updateSet
	/*CTLSPEC ag(foo = mon) //true
	CTLSPEC ag(fooC=mon) //fooC e' sempre uguale al valore di mon
	CTLSPEC ag(fooD=der) //fooD e' sempre uguale al valore di der
	CTLSPEC ag(fooC=der) //per proprieta' transistiva
	CTLSPEC ag(fooD=mon)
	CTLSPEC ag(fooE!=mon) //fooE deve essere sempre uguale a !mon
	CTLSPEC ag(fooF!=der)
	CTLSPEC ag(fooE!=der)
	CTLSPEC ag(fooF!=mon)
	CTLSPEC ag(fooI=mon2)//true
	CTLSPEC ag(fooL=mon3)//true
	CTLSPEC ag(fooM=der)*/

	//proprieta' CTL con la nuova interpretazione delle monitorate: le monitorate
	//appartengono al vecchio updateSet
	CTLSPEC foo = mon and (forall $b in Boolean with ag(mon=$b iff ax(foo=$b)))
	CTLSPEC fooD = der and (forall $b in Boolean with ag(der=$b iff ax(fooD=$b)))
	CTLSPEC fooC = der and (forall $b in Boolean with ag(der=$b iff ax(fooC=$b))) //per proprieta' transistiva
	CTLSPEC fooD = mon and (forall $b in Boolean with ag(mon=$b iff ax(fooD=$b)))
	CTLSPEC fooE = not(mon) and (forall $b in Boolean with ag(mon=$b iff ax(fooE=not($b))))
	CTLSPEC fooE = not(der) and (forall $b in Boolean with ag(der=$b iff ax(fooE=not($b))))
	CTLSPEC fooF = not(der) and (forall $b in Boolean with ag(der=$b iff ax(fooF=not($b))))
	CTLSPEC fooF = not(mon) and (forall $b in Boolean with ag(mon=$b iff ax(fooF=not($b))))
	CTLSPEC fooI = mon2 and (forall $b in Boolean with ag(mon2=$b iff ax(fooI=$b)))
	CTLSPEC fooL = mon3 and (forall $b in Boolean with ag(mon3=$b iff ax(fooL=$b)))
	CTLSPEC fooM = der and (forall $b in Boolean with ag(der=$b iff ax(fooM=$b)))

	main rule r_Main =
		par
			fooB := not(fooB)
			fooA := fooB //fooA != fooB perche' fooA prende il vecchio valore
			foo := mon
			if(mon) then
				fooC := true //e' equivalente a scrivere fooC := mon 
			else
				fooC := false //e' equivalente a scrivere fooC := mon
			endif
			if(der) then
				fooD := true //e' equivalente a scrivere fooD := der
			else
				fooD := false //e' equivalente a scrivere fooD := der
			endif
			if(not(mon)) then
				fooE := true
			else
				fooE := false
			endif
			if(not(der)) then
				fooF := true
			else
				fooF := false
			endif

			if(fooG) then
				fooH := not(fooG)
			else
				fooH := fooG
			endif
			
			choose $x in {true, false} with $x=mon2 do
				fooI := $x
				
			forall $k in {true, false} with $k=mon3 do
				fooL := $k
				
			fooM := der
		endpar

default init s0:
	function foo = mon
	function fooA = true //nello stato iniziale fooA = fooB
	function fooB = true //nello stato iniziale fooA = fooB
	function fooC = mon
	function fooD = der
	function fooE = not(mon)
	function fooF = not(der)
	function fooH = false
	function fooI = mon2
	function fooL = mon3
	function fooM = der
