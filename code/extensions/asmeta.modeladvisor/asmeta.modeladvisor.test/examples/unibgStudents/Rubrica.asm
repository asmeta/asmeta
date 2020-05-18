// COCO DARIO

asm Rubrica

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
    abstract domain Contact
	enum domain State = { SCELTA | INSCONTACT | DELCONTACT | ERROR }
	enum domain Service = { INS | DEL }
	dynamic controlled rubricaState : State
	//dynamic controlled contacts: Seq(String)
	//dynamic controlled outmex: Any
	dynamic monitored selectedService: Service
	dynamic monitored selectedContact: Contact
	
	static contact1 : Contact
	static contact2 : Contact
	static contact3 : Contact
	
	
	dynamic controlled scelta: Boolean
	
definitions:

	macro rule r_scelta =
		if (selectedService = INS) then
			rubricaState := INSCONTACT
			else if (selectedService = DEL) then
				 rubricaState := DELCONTACT
			     endif
		endif
		
	macro rule r_ins =
		if (rubricaState = INSCONTACT) then
			//par
			 	//outmex := "Contact inserted!"
				rubricaState := SCELTA
			//endpar
		endif
		
	macro rule r_del =
		if (rubricaState = DELCONTACT) then
			//par
			 	//outmex := "Contact deleted!"
				rubricaState := SCELTA
			//endpar
		endif
		

	main rule r_main =
		par
			scelta := not(scelta)
			if(scelta) then
				r_scelta[]
			else
				par
					r_ins[] 
					r_del[] 
				endpar
			endif
		endpar
		/*seq
			r_scelta[]
			par
				r_ins[] 
				r_del[] 
			endpar
		endseq*/
	
	default init s0:
		function rubricaState = SCELTA
		function scelta = true	