asm SiGistica

import ../../STDL/StandardLibrary


signature:


	enum domain Service = {SPEDIZIONE | GESTIONE_CLIENTE}
	enum domain UffLog = {OFFICINA | RICH_INFO}
	enum domain PagDip = {YES | NO}
	enum domain Mercato = {AUTISTA | GASOLIO}
	
	domain QuantityAut subsetof Integer
	domain QuantityGas subsetof Integer
	domain Stipendio subsetof Integer
	domain PersInfo subsetof Integer
	
	
	dynamic monitored selService: Service
	dynamic monitored selPag: PagDip
	dynamic controlled qtAutGas: Mercato -> QuantityGas
	dynamic controlled checkMezzo: UffLog -> Boolean
	dynamic controlled pagDomain: Stipendio
	dynamic controlled perInfo: PersInfo
	


definitions:

	domain QuantityAut = {0 : 10}
	domain QuantityGas = {0 : 10}
	domain Stipendio = {0 : 10}
	domain PersInfo = {0 : 10}

	

	macro rule r_rifornimento = 
		choose $g in Mercato with ($g = GASOLIO and qtAutGas($g) > 0) do 
			qtAutGas(GASOLIO) := qtAutGas(GASOLIO) - 1
	

	macro rule r_officina($a in UffLog)=
	 	if($a = RICH_INFO) then skip
	 	else if($a = OFFICINA and  checkMezzo($a) = false) then
				checkMezzo($a) := true
		else if($a = OFFICINA and checkMezzo($a) = true) then
				checkMezzo($a) := false
			endif
	 	endif
	 	endif

	

	macro rule r_pagamento =  
		if(pagDomain < 10) then 
			pagDomain := pagDomain + 1
			endif
	

	macro rule r_choosePag = 
		switch(selPag)
			case (YES):
				r_pagamento[]
			case (NO):
				skip
			endswitch	
	

	macro rule r_autista  =  
		choose $a in Mercato with ($a = AUTISTA and qtAutGas($a) > 0) do
			par
				qtAutGas(AUTISTA) := qtAutGas(AUTISTA) - 1
				r_officina[OFFICINA]
				r_choosePag[]			
			endpar
	

	macro rule r_info  = 
		if(perInfo > 0) then
			perInfo := perInfo - 1
		endif
	


	main rule r_Main = 
		switch(selService)
			case (SPEDIZIONE):
				par
					r_rifornimento[]
					r_autista[]
				endpar
			case (GESTIONE_CLIENTE):
				r_info[]
			endswitch


default init s0:
	function qtAutGas($a in Mercato) = 10
	function checkMezzo($c in UffLog) = false
	function pagDomain = 0
	function perInfo = 10
