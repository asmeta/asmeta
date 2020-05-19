asm petriNet_forModelAdvisor_InconsUpdateViolation3

//for the chapter "Formal semantics for Domain Specific Languages" of the book
//"Formal and Practical Aspects of Domain-Specific Languages: Recent Developments",
//publisher "IGI Global" 

//petri net with edge weight, place capacity and
//avoiding the use of seq in the firing of the transition.

//the edges are described with two matrices (two boolean functions)

//version for the ModelAdvisor:
//- the rule r_fire is wrong since it can update simultaneously the location tokens($p)
//  of a place $p that is both an input and an output place.

//It's difficult to discover the inconsistent update with the simulation:
//- you have to make three steps
//- 87,5% of the runs with three steps do not discover the inconsistency

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLlibrary

signature:
	abstract domain Place
	abstract domain Transition

	domain TokenDomain subsetof Integer

	controlled tokens : Place -> TokenDomain

	//the order in the cartesian product reflect the staring and the arriving
	//point of the edge.
	static inArcWeight: Prod(Place, Transition) -> TokenDomain
	static outArcWeight: Prod(Transition, Place) -> TokenDomain
	
	//delta of tokens that a place gains when a transition fires
	static incidenceMatrix: Prod(Place, Transition) -> TokenDomain
	
	static capacity: Place -> TokenDomain

	static isInputPlace: Prod(Place, Transition) -> Boolean
	static isOutputPlace: Prod(Place, Transition) -> Boolean

	static p1: Place
	static p2: Place
	static p3: Place
	static p4: Place
	static p5: Place
	static p6: Place
	static p7: Place
	static p8: Place
	static p9: Place
	static p10: Place
	static p11: Place
	static p12: Place
	static p13: Place
	static p14: Place
 	static t1: Transition
 	static t2: Transition
 	static t3: Transition
 	static t4: Transition
 	static t5: Transition
 	static t6: Transition
 	static t7: Transition
 	static t8: Transition
 	static t9: Transition
 	static t10: Transition
 	static t11: Transition
 	static t12: Transition
 	static t13: Transition
 	static t14: Transition
 
	derived isEnabled : Transition -> Boolean
	derived availableCapacity: Place -> TokenDomain

definitions:
	domain TokenDomain = {0 : 7}

	function inArcWeight($p in Place, $t in Transition) =
		switch($p)
 			case p1: if($t = t1 or $t = t2) then 1 else 0 endif
			case p2: if($t = t3 or $t = t4) then 1 else 0 endif
			case p3: if($t = t5 or $t = t6) then 1 else 0 endif
			case p4: if($t = t7 or $t = t8) then 1 else 0 endif
			case p5: if($t = t9 or $t = t10) then 1 else 0 endif
			case p6: if($t = t11 or $t = t12) then 1 else 0 endif
			case p7: if($t = t13 or $t = t14) then 1 else 0 endif
			otherwise 0
		endswitch

 	function outArcWeight($t in Transition, $p in Place) =
 		switch($p)
 			case p1: 0 
			case p2: if($t = t1) then 1 else 0 endif
			case p3: if($t = t2) then 1 else 0 endif
			case p4: if($t = t3 or $t = t7) then 1 else 0 endif
			case p5: if($t = t4) then 1 else 0 endif
			case p6: if($t = t5) then 1 else 0 endif
			case p7: if($t = t6) then 1 else 0 endif
			case p8: if($t = t8) then 1 else 0 endif
			case p9: if($t = t9) then 1 else 0 endif
			case p10: if($t = t10) then 1 else 0 endif
			case p11: if($t = t11) then 1 else 0 endif
			case p12: if($t = t12) then 1 else 0 endif
			case p13: if($t = t13) then 1 else 0 endif
			case p14: if($t = t14) then 1 else 0 endif
		endswitch

	//delta of tokens that a place "$p" gains when a transition "$t" fires
	function incidenceMatrix($p in Place, $t in Transition) =
		outArcWeight($t, $p) - inArcWeight($p, $t)

	function isInputPlace($p in Place, $t in Transition) =
		inArcWeight($p, $t) > 0

	function isOutputPlace($p in Place, $t in Transition) =
		outArcWeight($t, $p) > 0

	function capacity($p in Place) = 1

	function availableCapacity($p in Place) =
		capacity($p) - tokens($p)

	function isEnabled ($t in Transition) =
		(forall $p in Place with (isInputPlace($p, $t) implies tokens($p) >= inArcWeight($p, $t)) and
								 (isOutputPlace($p, $t) implies availableCapacity($p) >= incidenceMatrix($p, $t))
		)

	rule r_fire($t in Transition) =
		par
			forall $i in Place with isInputPlace($i, $t) do
				tokens($i) := tokens($i) - inArcWeight($i, $t)
			forall $o in Place with isOutputPlace($o, $t) do
				tokens($o) := tokens($o) + outArcWeight($t, $o)
		endpar

	main rule r_Main =
		choose $t in Transition with isEnabled($t) do
			r_fire[$t]

default init s0:
	 //initial marking
 	function tokens($p in Place) = at({p1 -> 1, p2 -> 0, p3 -> 0, p4 -> 0, p5 -> 0,
 									p6 -> 0, p7 -> 0, p8 -> 0, p9 -> 0, p10 -> 0,
 									p11 -> 0, p12 -> 0, p13 -> 0, p14 -> 0}, $p) 
