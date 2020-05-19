asm petriNetNoEdgeWeightNoPlaceCapacity_seq

//for the chapter "Formal semantics for Domain Specific Languages" of the book
//"Formal and Practical Aspects of Domain-Specific Languages: Recent Developments",
//publisher "IGI Global"

//petri net with no edge weight, no place capacity and
//the use of seq in the firing of the transition.

import ../../STDL/StandardLibrary

signature:
	abstract domain Place
	abstract domain Transition

	controlled tokens : Place -> Integer

	static inputPlaces: Transition -> Powerset(Place)
	static outputPlaces: Transition -> Powerset(Place)

	static p1: Place
 	static p2: Place
 	static p3: Place
 	static p4: Place
 	static t1: Transition
 	static t2: Transition
 
	derived isEnabled : Transition -> Boolean

definitions:
	function inputPlaces($t in Transition) =  at({t1 -> {p1}, t2 -> {p2,p3}}, $t)
 	function outputPlaces($t in Transition) =  at({t1 -> {p2, p3}, t2 -> {p4,p1}}, $t)

	function isEnabled ($t in Transition) =
		(forall $p in inputPlaces($t) with tokens($p) > 0)

	rule r_fire($t in Transition) =
		seq
			forall $i in inputPlaces($t) do
				tokens($i) := tokens($i) - 1
			forall $o in outputPlaces($t) do
				tokens($o) := tokens($o) + 1
		endseq

	invariant over tokens: (forall $p in Place with tokens($p) >= 0)

	main rule r_Main =
		choose $t in Transition with isEnabled($t) do
			r_fire[$t]

default init s0:
	 //initial marking
 	function tokens($p in Place) = at({p1 -> 1, p2 -> 0, p3 -> 2, p4 -> 1}, $p) 