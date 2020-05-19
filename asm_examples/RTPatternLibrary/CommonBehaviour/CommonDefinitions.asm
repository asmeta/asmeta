/* "Global variables" definition */

asm CommonDefinitions

import ../../STDL/StandardLibrary
export *
		
signature:
	// Subset of integer to initialize finite sequences
		domain ListSize subsetof Integer
	// Global step ofi time (single clock) 	
		static baseTime: Integer

definitions:
	domain ListSize = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10} // maximum dimension of Swinging buffers
	function baseTime = 100 // (100 us = 0.1 ms)
	
	
