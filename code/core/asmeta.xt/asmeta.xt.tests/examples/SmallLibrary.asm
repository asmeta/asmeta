module SmallLibrary

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

	static idiv: Prod(Integer, Integer) -> Integer
	static mod: Prod(Integer, Integer) -> Integer

definitions:
