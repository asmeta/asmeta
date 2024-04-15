//
// for testing the use of undef in equals
//
asm STDL_funcsUndefEQ
	
import ../../../STDL/StandardLibrary
	
signature:
	static b : Boolean
	static c : Integer
	static u : Undef

	// direct
	controlled c1: Boolean
	controlled c2: Boolean
	controlled c3: Boolean
	controlled c4: Boolean
	controlled c5: Boolean
	controlled c6: Boolean
	controlled d1: Boolean
	controlled d2: Boolean
	controlled d3: Boolean
	controlled d4: Boolean
	controlled d5: Boolean
	controlled d6: Boolean


definitions:
	// not undef
	function b = true
	function c = 1
	// useless: function d = undef
                       
	main rule r_main =
	par
		// direct assignemnt
		c1:=  (b = undef)
		c2:=  (c = undef)
		c3:=  (undef = b)
		c4:=  (undef = c)
	endpar

default init s0:
