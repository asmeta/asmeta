// a simple example with a tic tac toe game

asm telecamere1

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLlibrary

signature:
	// DOMAINS
	enum domain Status={TRANSIZIONECORRETTA | NONESEGUITA}
	enum domain Azioni={SPEGNI | ACCENDI| BASTA}
	domain Numtelecamere subsetof Integer
	// FUNCTIONS
	controlled telecamere: Numtelecamere -> Boolean
	controlled status: Status
	monitored teleChoice: Numtelecamere 
	monitored az :Azioni

definitions:
	// DOMAIN DEFINITIONS
	domain Numtelecamere = {1:3}
	
	//primo a poi la telecamera uno viene spenta
	CTLSPEC ef(telecamere(1)=false)
	//se la telecamera uno viene spenta implica che nel prossimo step non potro mai avere la telecamera adiacente a true 
	CTLSPEC ag(telecamere(1)=false implies ax(telecamere(2)=true))
	//esiste uno stato che ha tutte e tre le telecamere false
	CTLSPEC eg(telecamere(1)=false and telecamere(2)=false and telecamere(3)=false)
	//non esisteranno mai due telecamere spente contigue
	CTLSPEC not(eg(telecamere(1)=false and telecamere(2)=false))
	
	
	
	
	// MAIN RULE
	main rule r_Main =
		if(az!=BASTA) then
	par
		if(teleChoice<3 and teleChoice>1 and az=SPEGNI and not(az=ACCENDI))  then
			if (telecamere(teleChoice)=true and telecamere(teleChoice+1)=true and telecamere(teleChoice-1)=true)  then
			par
				telecamere(teleChoice):=false
				status:=TRANSIZIONECORRETTA
			endpar
			else
				status:=NONESEGUITA
			endif
		endif
		if(teleChoice=1 and az=SPEGNI and not(az=ACCENDI)) then
				if (telecamere(teleChoice)=true and telecamere(teleChoice+1)=true)  then
				par
					telecamere(teleChoice):=false
					status:=TRANSIZIONECORRETTA
				endpar
				else
					status:=NONESEGUITA
				endif
		endif
		if(teleChoice=3 and az=SPEGNI and not(az=ACCENDI)) then
				if (telecamere(teleChoice)=true and telecamere(teleChoice-1)=true)  then
				par
					telecamere(teleChoice):=false
					status:=TRANSIZIONECORRETTA
				endpar
				else
					status:=NONESEGUITA
				endif
		endif
		if(teleChoice>3 and teleChoice<1)then
			status:=NONESEGUITA
		endif
		if(teleChoice<=3 and teleChoice>=1 and az=ACCENDI and not(az=SPEGNI) and telecamere(teleChoice)=false)  then

				telecamere(teleChoice):=true
		endif
	endpar
	endif

// INITIAL STATE
default init s0:
	function telecamere($n in Numtelecamere) = true
