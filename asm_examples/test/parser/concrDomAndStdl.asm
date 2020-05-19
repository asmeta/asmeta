asm concrDomAndStdl

import ../../STDL/StandardLibrary

signature:
	domain Actors subsetof Agent
	dynamic controlled foo: Actors -> Boolean
	dynamic controlled fooA: Boolean -> Prod(Actors, Boolean)
	static actorA: Actors
	static actorB: Actors

definitions:

	main rule r_Main =
		foo(first(fooA(true))) := true

default init s0:
	function fooA($b in Boolean) = (actorA, true)
