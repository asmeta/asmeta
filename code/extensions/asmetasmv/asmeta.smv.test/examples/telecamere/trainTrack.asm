// a simple example with a tic tac toe game

asm trainTrack

import StandardLibrary

import CTLlibrary

signature:
	// DOMAINS
	//enum domain Segments = {SEG0 | SEG1 | SEG2}
	// 0 ... 4
	domain NumSegmenti subsetof Integer		
	domain NumTreniSegmento subsetof Integer	
	// FUNCTIONS
	// numero di treni in ogni segmento
	controlled numTreni: NumSegmenti -> NumTreniSegmento
	// monitorate
	// treno vuole entrare nel track
	monitored  vuoleEntrare: Boolean
	// quale treno vuole avanzare
	monitored  vuoleAvanzare: NumSegmenti 
	//
	derived tuttiOccupati : Boolean
	

definitions:
	// DOMAIN DEFINITIONS
	domain NumSegmenti = {0:3}
	// metto 2
	domain NumTreniSegmento = {0:2}
	
	function tuttiOccupati = numTreni(0) >0 and numTreni(1) >0 and numTreni(2) >0 and numTreni(3) >0 

	// RULE DEFINITIONS
	// un treno entra
	rule r_entra = numTreni(0):= numTreni(0)+1 
	
	// un treno che si muove
	macro rule r_move($s in NumSegmenti) = 
		par
			numTreni($s):= numTreni($s)-1 
			numTreni(($s + 1) mod 4):= numTreni(($s + 1) mod 4)+1 
		endpar
	
	//CTLSPEC ag((forall $s in NumSegmenti with numTreni($s) <= 1))
	CTLSPEC ag(numTreni(0) <= 1)
	
	// MAIN RULE
	main rule r_Main = 
	
	/*	if vuoleEntrare and numTreni(0) = 0 then r_entra[]
		else if numTreni(vuoleAvanzare)  = 1 and numTreni((vuoleAvanzare+1) mod 4) = 0
				then  r_move[vuoleAvanzare]
			endif
		endif*/
		par
			if vuoleEntrare and numTreni(0) = 0 then r_entra[] endif
			if numTreni(vuoleAvanzare)  = 1 and numTreni((vuoleAvanzare+1) mod 4) = 0
				then  r_move[vuoleAvanzare]
			endif
		endpar
		

// INITIAL STATE
default init s0:
	function numTreni($s in NumSegmenti) = 0
