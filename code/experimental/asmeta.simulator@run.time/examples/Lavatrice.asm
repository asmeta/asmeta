// a simple example with a tic tac toe game

asm Lavatrice

 //FUNCTIONAMENTO IM MOD INTERACTIVA COL ANIMATOR
 //1 -OUVRIR
 //2- FERMER
 //3- ALLUMER
 //4- MODIFIER TEMPERATURE: le niveau de temperature definir un type precis de lavage
 //5- LANCER
 //6- PAUSE(optionelle)
 //7- TERMINER

import StandardLibrary

signature:
     
   //DOMINI
   
	//i possibili stati 
	enum domain Etat = { ETEINT_FERME | ETEINT_OUVERT | ALLUME_FERME | ALLUME_OUVERT | INFUNCTION | ALLUME_PAUSA}
	
	//i possibili tipi di lavaggio(Nessuno, Manuale, Delicato, ) ==> Posso Implementare anche le Potenze Volendo
	enum domain TypeDeLavage = { AUCUN | NORMAL | MANUEL | DELICAT } 
	
	//le possibile potenze sono
	enum domain Temperature = {ZERO | BASSE | MOYENNE | HAUTE}
	
	//i possibili input ( che l'utente puo fare)
	enum domain Operation = { ALLUMER | ETEINDRE | LANCER | PAUSE | OUVRIR | FERMER | MOINS | PLUS | TERMINER }
                                                  
	// FUNCTIONS
	
	dynamic controlled etat : Etat
	dynamic controlled lavage : TypeDeLavage
	dynamic controlled temperature : Temperature  //potrebbe essere una moinitorata anche ==> Dopo Fare un Test di Quello
	dynamic monitored operation : Operation

definitions:
  //Lavatrice spenta e chiusa		
	rule r_ChangeEteintFerme =
		seq	
			let ( $i = operation) in //introduco una nuova variabile logica con let
			     if( $i = OUVRIR ) then
			       etat := ETEINT_OUVERT
			     else
			         if( $i = ALLUMER) then
			            etat := ALLUME_FERME //CERA ATTENTE
			          else
			             etat := ETEINT_FERME //mantieni lo stesso stato per tutti gli altri operazioni
			         endif
			     endif
			endlet
			par
			  temperature := ZERO
			  lavage := AUCUN
			endpar
		endseq
		
 	//Lavatrice spenta e aperta	==> La Puoi Solo Chiudere per altre op lo stato non cambia	
	rule r_ChangeEteintOuvert =
		seq
			let ( $i = operation ) in
				if ($i = FERMER) then 
				   etat := ETEINT_FERME
				else
				  etat := ETEINT_OUVERT 
				endif	
			endlet
			par
			  temperature := ZERO
			  lavage := AUCUN
			endpar
		endseq
		
			
	//Lavatrice accesa e chiudo (Normale Funzionamento)				
	rule r_ChangerAllumeFermer =
		let ($i = operation) in
				switch ( $i )
					case ETEINDRE:
						etat := ETEINT_FERME
						
					case LANCER:
						  
								if (temperature != ZERO) then  //la macchina non funziona a temperatura diverso da zero oppure se non gli si è stato dato nessun tipo di lavaggio
									etat := INFUNCTION
								  else
								       etat := ALLUME_FERME //rimani nello stato acceso e spento   
								endif
								
					case OUVRIR: 
						etat := ALLUME_OUVERT
						
					case PLUS:
						switch(temperature)
							case ZERO:
								par
								  temperature := BASSE
								  lavage := NORMAL
								endpar
							case BASSE:
								par
								  temperature := MOYENNE
								  lavage := MANUEL
								endpar
							case MOYENNE:
								par
								  temperature := HAUTE
								  lavage := DELICAT
							    endpar
						endswitch
						
					case MOINS:
						switch(temperature)
							case BASSE:
								par
								  temperature := ZERO
								  lavage := AUCUN
							    endpar
							case MOYENNE:
								par
								  temperature := BASSE
								  lavage := NORMAL
							    endpar
							case HAUTE:
								par
								  temperature := MOYENNE
								  lavage := MANUEL
							    endpar
						endswitch
				endswitch
		endlet
		
	//lavatrice acceso e aperto	
	//la puoi solo chiudere o spegnere altri operazioni non sono consentiti mantiene lo stato corrente		
	rule r_ChangellumeOuvert =
		 let ($i = operation) in
				if( $i = FERMER) then
				  etat := ALLUME_FERME
				else 
				     if ( $i = ETEINDRE ) then
				         etat := ETEINT_OUVERT
				      else
				          etat := ALLUME_OUVERT //altrimenti mantiene lo stato corrente
				     endif
				endif		    		
		endlet
		
		
		//lavatrice in funzione
		rule r_ChangeINFUNCTION =
			let ($i = operation) in
					if( $i = PAUSE) then
					  etat := ALLUME_PAUSA
					else 
					   if( $i = TERMINER) then //quando ho terminato risetto tutto
						   par
						     etat := ETEINT_FERME
						     temperature := ZERO
				             lavage := AUCUN
						   endpar
					    else
					        etat := INFUNCTION //altrimenti rimani sempre in funzione
					   endif
					endif
						    		
			endlet
		
		//lavatrice in stato accesa e pausa
		rule r_ChangeALLUMEPAUSA =
			let ($i = operation) in
					if( $i = LANCER) then
					  etat := INFUNCTION
					else 
					   etat := ALLUME_PAUSA //altrimenti rimani sempre in pausa
					endif
						    		
			endlet
		
		
	

// MAIN RULE
	main rule r_Main =
		if (true) then			
			if (etat = ETEINT_FERME) then
				r_ChangeEteintFerme[]
			else	
				if (etat = ETEINT_OUVERT) then
					r_ChangeEteintOuvert[]
				else
					if (etat = INFUNCTION) then
						r_ChangeINFUNCTION[]
						
					else
						if (etat = ALLUME_FERME) then
							r_ChangerAllumeFermer[]
						else 
						   if(etat = ALLUME_PAUSA) then
						      r_ChangeALLUMEPAUSA[]
						      else
						          if(etat = ALLUME_OUVERT) then
							     	 r_ChangellumeOuvert[]
							     	 
							      else
						             r_ChangellumeOuvert[]
						             
						      endif 
						   endif	
						endif
					endif			
				endif		
			endif	
		endif
		
// INITIAL STATE
default init s0:

//lo stato iniziale parte da "spento e chiuso"
	function etat = ETEINT_FERME
	
	//la temperature iniziale "zero"
	function temperature = ZERO
	
	//setto il lavaggio a AUCUN
	function lavage = AUCUN
