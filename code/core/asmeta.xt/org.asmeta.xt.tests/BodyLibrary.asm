module BodyLibrary

export idiv

signature :
	/*----------- Domains --------------*/
	basic domain Boolean
	basic domain String
	basic domain Undef
	basic domain Complex
	basic domain Real
	basic domain Integer
	basic domain Natural
	basic domain Char
	
	abstract domain Philosopher				
	
	domain Cherries subsetof Integer

definitions:
