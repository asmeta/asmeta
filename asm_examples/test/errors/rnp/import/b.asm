asm b

	import ../../../STDL/StandardLibrary
	import c

signature:

	abstract domain Dom
	
	static foo: Dom -> Rule(Integer)
	
	static d1: Dom
	static d2: Dom

definitions:

	function foo($d in Dom) =
		if $d = d1 then
			<<r_m1(Integer)>>
		else
			<<r_m2(Integer)>>
		endif
		
	main rule r_main =
		forall $d in Dom do
			foo($d)

default init s0:

	function f = 0
	function g = 20
	
