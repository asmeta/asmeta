//
// for testing the use of undef in logical operators like and ...
//
asm STDL_funcsUndef
	
import ../../../STDL/StandardLibrary
	
signature:
	static t : Boolean
	static f : Boolean
	static ub : Boolean
	static d : Undef
	// direct
	controlled c1: Boolean
	// FOR AND
	controlled c2: Boolean
	controlled c3: Boolean
	controlled c4: Boolean
	controlled c5: Boolean
	controlled c6: Boolean
	// FOR OR
	controlled d1: Boolean
	controlled d2: Boolean
	controlled d3: Boolean
	controlled d4: Boolean
	controlled d5: Boolean
	controlled d6: Boolean


definitions:

	function t = true
	function f = false
	function ub = undef
	// useless: function d = undef
                       
	main rule r_main =
	par
		// direct assignemnt
		c1:= undef  // -> c1 undef
		// AND OPERATOR skip those trivial
		c2:= t and ub //-> undef
		c3:= ub and t //-> undef
		c4:= f and ub //-> false
		c5:= ub and f //-> false
		c6:= ub and ub //-> undef
		// OR OPERATOR
		d1:= t or ub //-> true
		d2:= ub or t //-> true
		d3:= f or ub //-> undef
		d4:= ub or f //-> undef
		d5:= ub or ub //-> undef
		// not
		d6:= not ub //-> undef

	endpar

default init s0:
