// a simple example with a tic tac toe game

asm Forno

import STDL/StandardLibrary
import STDL/CTLLibrary
import STDL/LTLLibrary


signature:
	// DOMAINS
	enum domain Stato = {ON | OFF}
	enum domain Porta = {APERTA | CHIUSA}
	
	// FUNCTIONS
	controlled stato: Stato
	controlled porta: Porta
	
	monitored control : Boolean
	
	static acceso: Boolean
	static aperto: Boolean
	
definitions:
	// DOMAIN DEFINITIONS

	// FUNCTION DEFINITIONS
	function acceso = stato = ON
	function aperto = porta = APERTA
	
	// RULE DEFINITIONS

	// INVARIANTS
	
	//CTL
	// Quando è acceso la porta è sempre chiusa
	CTLSPEC ag(acceso implies aperto)
	// Prima o poi si può accendere in qualsiasi momento in futuro.
	CTLSPEC ef(acceso and not aperto)
	// La porta può essere aperta dopo che viene acceso.
	//CTLSPEC ag(acceso and not control implies ex(aperto))
	// Questa proprietà non è vera perchè il forno non può essere acceso e avere la porta aperta.
	// posso dire che, se il forno è acceso, posso aprire la porta solo se poi lo spengo
	CTLSPEC ag((stato = ON and not(control)) implies ex(stato = OFF and porta = APERTA))
	//Quando è acceso, la porta rimane chiusa fino quando rimane acceso (usa Until). (non esce)
	LTLSPEC u(stato = ON and porta = CHIUSA and not(control), stato = OFF and porta = APERTA)
	
	// MAIN RULE
	main rule r_Main =
	par
		if(stato = OFF and porta = CHIUSA and control) then
			porta := APERTA
		endif
		
		if(stato = OFF and porta = APERTA and control) then
		par	
			stato := ON
			porta := CHIUSA
		endpar
		endif
		
		if(stato = ON and porta = CHIUSA and not(control)) then
		par	
			stato := OFF
			porta := APERTA
		endpar
		endif
		
		if(stato = OFF and porta = APERTA and not(control)) then
		par	
			stato := OFF
			porta := CHIUSA
		endpar
		endif
		
	endpar

// INITIAL STATE
default init s0:
	function stato = OFF
	function porta = CHIUSA