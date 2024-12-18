asm telecamere_v2

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLLibrary

signature:
	// DOMAINS
	domain Camera subsetof Integer
	enum domain Stato = {ON | OFF}
	enum domain Azione = {ACCENDI | SPEGNI}
	
	// FUNCTIONS
	controlled stato: Camera -> Stato // Mi d� lo stato di ogni telecamera
	monitored scelta: Azione //Scelta di accedere o spegnere una telecamera casuale
	
	//Funzioni statiche per determinare le telecamere adiacenti
	static precedente: Camera -> Camera
	static successiva: Camera -> Camera

definitions:
	// DOMAIN DEFINITIONS
	domain Camera = {1:4}

	// FUNCTION DEFINITIONS
	function precedente($c in Camera) = 
		switch $c 
			case 1 : 4
			case 2 : 1
			case 3 : 2
			case 4 : 3			
		endswitch
	
	function successiva($c in Camera) = 
		switch $c 
			case 1 : 2
			case 2 : 3
			case 3 : 4
			case 4 : 1		
		endswitch

	// RULE DEFINITIONS
	rule r_accendi($c in Camera)  =
		stato($c) := ON

	rule r_spegni($c in Camera) = 
		stato($c) := OFF	
			
	//CTL PROPERTIES
	
	//La prima e ultima telecamere non possono essere contemporaneamente spente
	
	CTLSPEC ag(not(stato(1)=OFF and stato(4)=OFF))
	
	//Le telecamere non possono mai essere tutte spente
	
	CTLSPEC not ef((forall $c in Camera with stato($c)=OFF))
	
	//Se la telacamera 2 � spenta allora le telecamere 1 e 3 sono accese
	
	CTLSPEC ag(stato(2)=OFF implies stato(1)=ON and stato(3)=ON)
	
	//Se una telecamere � accesa pu� rimanere accesa o essere spenta
	
	CTLSPEC(forall $c in Camera with ag(stato(1)=ON implies ax(stato(1)=ON or stato(1)=OFF)))
	
	// Se le telecamere 1 e 3 sono spente e viene scelto ACCENDI, allora nello stato successivo le telecamere 2 e 4 rimangono spente
	
	CTLSPEC ag(stato(1)=ON and stato(3)=ON and scelta = ACCENDI implies ax(stato(2)=OFF and stato(2)=OFF))
	
	
	
	// MAIN RULE
	main rule r_Main =
		if(scelta = ACCENDI) then
			choose $c in Camera with stato($c) = OFF do
				r_accendi[$c]
		else
			choose $c2 in Camera with stato($c2) = ON and stato(precedente($c2)) = ON and stato(successiva($c2)) = ON do
				r_spegni[$c2]
		endif				

// INITIAL STATE
default init s0:
	function stato($c in Camera) = ON
