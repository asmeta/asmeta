asm equals

	import ../../../STDL/StandardLibrary

signature:

	enum domain Enu1 = {EE1 | EE2}
	enum domain Enu2 = {EE3}
	
	abstract domain A
	domain B subsetof A

	controlled f1: Integer -> Undef
	controlled f2: Integer -> Undef
	controlled f3: Real -> Undef
	
	controlled f4: Enu1 -> Undef
	controlled f5: Enu2 -> Undef
	
	controlled f6: A -> Undef
	controlled f7: B -> Undef

	controlled f9:  Prod(Prod(Integer, Real), Powerset(Boolean)) -> Undef
	controlled f10: Prod(Prod(Integer, Real), Powerset(Boolean)) -> Undef
	controlled f11: Prod(Prod(Integer, Char), Powerset(Boolean)) -> Undef
	
	controlled f12: Seq(Seq(A)) -> Undef
	controlled f13: Seq(Seq(A)) -> Undef
	controlled f14: Seq(Seq(B)) -> Undef
	
	controlled f15: Rule(Integer) -> Undef
	controlled f16: Rule(Integer) -> Undef
	controlled f17: Rule(Rule(Integer)) -> Undef
	controlled f18: Rule(Rule(Integer)) -> Undef

	
definitions:

	main rule r_main =
		skip
