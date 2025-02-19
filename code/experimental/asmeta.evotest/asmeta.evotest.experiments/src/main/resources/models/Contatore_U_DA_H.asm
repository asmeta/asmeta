// LUZZANA

asm Contatore_U_DA_H  //Nome della macchina a stati astratti

import STDL/StandardLibrary  //import della libreria standard

signature:  //definizione del vocabolario

	/*Dichiaro i nomi di dominio*/
	domain Unita subsetof Integer
	domain Decine subsetof Integer
	domain Centinaia subsetof Integer      
	domain Migliaia subsetof Integer   
	
	/*Dichiaro i nomi di funzine*/
	dynamic monitored click : Boolean  
	dynamic controlled unit : Unita
	dynamic controlled da : Decine    
	dynamic controlled h : Centinaia  
	dynamic controlled m : Migliaia
  
/*Definizioni di domini*/
definitions:    
	domain Unita = {0 : 10}
	domain Decine = {0 : 10}
	domain Centinaia = {0 : 10}
	domain Migliaia = {0 : 10}

/*Definizione delle regole*/
//regola che incrementa le migliaia
	rule r_upMigliaia =
		m := (m+ 1) mod 10


//Regola che incrementa le Centinaia, modulo 10 per resettare il valore
	macro rule r_upCentinaia = 
		par
			if h=9 then
				r_upMigliaia[]
			endif
			h := (h+ 1) mod 10
		endpar


//regola che incrementa le Decine, modulo 10 per resettare il valore
	macro rule r_upDecine =  
		par
			if da=9 then
				r_upCentinaia[]
			endif
			da := (da + 1) mod 10
		endpar
                      

//regola che incrementa le unit�, modulo 10 per resettare il valore
	macro rule r_upUnita =
		par
			if unit=9 then
				r_upDecine[] 
			endif 
			unit := (unit+1) mod 10 
		endpar 

/*Definizione della main rule*/	
//Regola main	
	main rule r_Contatore = 
	if click 
	then
		r_upUnita[]
	endif

/*Inizializzazioni*/
default init s1:    
	function click = false    
	function unit = 0     //I contatori partono da zero
	function da = 0     
	function h = 0
	function m = 0
