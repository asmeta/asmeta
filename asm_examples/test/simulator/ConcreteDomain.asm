// a concrete domain check tha the values can be read

asm ConcreteDomain

import ../../STDL/StandardLibrary

signature:
	// DOMAINS
	domain Moneta subsetof Integer
	monitored monetaInserita: Moneta
	controlled moneta: Moneta

definitions:
	domain Moneta={0,50,100,150,200}
	

	// MAIN RULE
	main rule r_Main = moneta := monetaInserita 

// INITIAL STATE
default init s0:
