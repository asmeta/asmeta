asm anyDomain

import ../../STDL/StandardLibrary

signature:
	domain Person subsetof Agent
	domain ConcrDom subsetof Integer
	controlled foo: Prod(Integer, Integer)
	controlled foo1: Integer
	controlled foo1a: Integer -> Boolean
	controlled foo2: Prod(ConcrDom, ConcrDom)
	controlled foo3: ConcrDom
	controlled foo3a: ConcrDom -> Boolean
	controlled foo4: Prod(Boolean, Person)
	controlled foo5: Person
	controlled foo5a: Person -> Boolean

	static person1: Person
	static person2: Person

definitions:
	domain ConcrDom = {1 : 5}
	
	rule r_rule =
		skip

	main rule r_Main =
		par
			foo1 := second(foo)
			foo1a(second(foo)) := true
			foo3 := second(foo2)
			foo3a(second(foo2)) := true
			foo5 := second(foo4)
			foo5a(second(foo4)) := true
		endpar

default init s0:
	function foo = (1, 2)
	function foo1 = 0
	function foo1a($i in Integer) = false
	function foo2 = (3, 4)
	function foo3 = 5
	function foo3a($c in ConcrDom) = false
	function foo4 = (false, person1)
	function foo5 = person2
	function foo5a($p in Person) = false
