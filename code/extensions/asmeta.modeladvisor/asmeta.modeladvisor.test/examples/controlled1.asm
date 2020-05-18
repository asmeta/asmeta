// a controled that never takes a value
asm controlled1

import ../../../../asm_examples/STDL/StandardLibrary

signature:
//bip
	enum domain State = { AWAITCARD | AWAITPIN | CHOOSE | OUTOFSERVICE }

	dynamic controlled atmState: State

	dynamic controlled atmInitState: State

	dynamic controlled atmErrState: State

	//dynamic monitored pinCode: Integer

//eip
definitions:
//bip
	main rule r_Main =
		par
		if(atmState = atmInitState) then atmState := AWAITPIN endif
		if(atmState=AWAITPIN)       then atmState := CHOOSE	endif
		if(atmState=CHOOSE)         then atmState := AWAITCARD endif
		endpar


// define the initial states
default init s0:
	function atmInitState = AWAITCARD
	function atmErrState = OUTOFSERVICE
	function atmState = atmInitState //all'inizio il bancomat e' in attesa di una carta
//eip

