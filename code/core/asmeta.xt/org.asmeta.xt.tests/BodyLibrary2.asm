module BodyLibrary

export idiv, Philosopher, Integer

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
	
	static philo1: Philosopher
	static philo2: Philosopher
	
	derived leftNeighbour: Integer -> Philosopher

	static idiv: Prod(Integer, Integer) -> Integer
	static mod: Prod(Integer, Integer) -> Integer

definitions:
