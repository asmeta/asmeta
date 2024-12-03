asm LightArray2 

// this spec gave some errors regarding the use of natural number when translated to SMV

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain PosLuci subsetof Integer

	domain QuanteLuciAccese subsetof Integer
	
	enum domain StatoLuce = {ON | OFF}
	
	monitored luceDaCambiare : PosLuci
		
	controlled statoLuce : PosLuci -> StatoLuce
	controlled countAccese : QuanteLuciAccese
	
	derived isAccendibile : PosLuci -> Boolean

definitions:
	
	domain PosLuci = {1 : 10}
	
	domain QuanteLuciAccese = {0 : 10}


	
	function isAccendibile($p in PosLuci) =
		($p = 1 and statoLuce(10)=OFF and statoLuce(2)=OFF) or
		($p = 10 and statoLuce(9)=OFF and statoLuce(10)=OFF) or
		(not($p = 1) and not($p = 10) and statoLuce($p - 1)=OFF and statoLuce($p + 1)=OFF )
	
	rule r_AccendiLuce($p in PosLuci) =
		par
			statoLuce($p) := ON
			countAccese := countAccese + 1
		endpar
		
	rule r_SpegiLuce($p in PosLuci) =
		if (statoLuce($p) = ON) then 
			par		
				statoLuce($p) := OFF
				countAccese := countAccese - 1
			endpar
		endif
		
	rule r_Toggle($p in PosLuci) = 
		if (statoLuce($p) = OFF and isAccendibile($p)) then
			r_AccendiLuce[$p]
		else 
			r_SpegiLuce[$p]
		endif
		
	//al massimo ho 5 lampadine accese
	//CTLSPEC ag(countAccese <= 5)
	
	//la prima lampadina può essere sempre accesa
	CTLSPEC ag(isAccendibile(1) = true) // falso
	
	//se la prima è accesa, la seconda è spenta fino quando la prima verrà spenta
	CTLSPEC ag(statoLuce(1) = ON implies eu(statoLuce(1) = ON, statoLuce(2) = OFF) ) // vero
	
	//se do comando ad una lampadina, nello stato successivo essa cambia stato
	//CTLSPEC ag(r_Toggle($p in PosLuci) implies ea(statoLuce($p) = not(statoLuce($p))))
	
	main rule r_Main = 
	let ($p = luceDaCambiare) in
		r_Toggle[$p]
	endlet
	
default init s0:
	function statoLuce($p in PosLuci) = OFF
	function countAccese = 0
