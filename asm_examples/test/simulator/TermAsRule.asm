// a term which is a rule

asm TermAsRule
import ../../STDL/StandardLibrary
signature:

	controlled m: Integer -> Rule

definitions:

	macro rule r_skip =
		skip

	main rule r_main = m(0)
	
default init s0:
	function m($i in Integer) = <<r_skip>>
